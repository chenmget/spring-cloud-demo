package com.iwhalecloud.retail.warehouse.runable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.TypeConst;
import com.iwhalecloud.retail.warehouse.busiservice.ResouceInstTrackService;
import com.iwhalecloud.retail.warehouse.busiservice.ResourceInstService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceInstTrackDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstValidReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsTrackGetReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceUploadTempDelReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceUploadTemp;
import com.iwhalecloud.retail.warehouse.manager.ResourceReqDetailManager;
import com.iwhalecloud.retail.warehouse.manager.ResourceUploadTempManager;
import com.iwhalecloud.retail.warehouse.util.ExcutorServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by fadeaway on 2019/5/29.
 */
@Component
@Slf4j
public class ValidAndAddRunableTask {

    @Autowired
    private ResouceInstTrackService resouceInstTrackService;

    @Autowired
    private ResourceReqDetailManager detailManager;

    @Autowired
    private ResourceUploadTempManager resourceUploadTempManager;

    @Autowired
    private ResourceInstService resourceInstService;

    private final Integer perNum = 5000;

    private Map<String, List<Future<Boolean>>> validFutureTaskResult = new Hashtable();

    String reg12 = "([A-Z]|[0-9]|[-]){12}$";
    String reg24 = "([A-Z]|[0-9]|[-]){24}$";
    String reg32 = "([A-Z]|[0-9]|[-]){32}$";
    String reg39 = "([A-Z]|[0-9]|[-]){39}$";

    /**
     * 串码校验多线程处理
     * @param req
     */
    public String exceutorValid(ResourceInstValidReq req) {
        String batchId = resourceInstService.getPrimaryKey();
        try {
            ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
            List<String> nbrList = req.getMktResInstNbrs();
            List<String> ctCodeList = req.getCtCodeList();
            List<String> snCodeList = req.getSnCodeList();
            List<String> macCodeList = req.getMacCodeList();
            Integer excutorNum = nbrList.size()%perNum == 0 ? nbrList.size()/perNum : (nbrList.size()/perNum + 1);
            BlockingQueue<Callable<Boolean>> tasks = new LinkedBlockingQueue<>();
            for (Integer i = 0; i < excutorNum; i++) {
                Integer maxNum = perNum * (i + 1) > nbrList.size() ? nbrList.size() : perNum * (i + 1);
                List<String> subList = nbrList.subList(perNum * i, maxNum);
                CopyOnWriteArrayList<String> newList = new CopyOnWriteArrayList(subList);
                ResourceInstsTrackGetReq getReq = new ResourceInstsTrackGetReq();
                getReq.setTypeId(req.getTypeId());
                getReq.setMktResInstNbrList(newList);
                if (CollectionUtils.isNotEmpty(ctCodeList) && perNum * i < ctCodeList.size()) {
                    Integer ctCodeMaxNum = perNum * (i + 1) > ctCodeList.size() ? ctCodeList.size() : perNum * (i + 1);
                    CopyOnWriteArrayList<String> subCtCodeList = new CopyOnWriteArrayList(ctCodeList.subList(perNum * i, ctCodeMaxNum));
                    getReq.setCtCodeList(subCtCodeList);
                }
                if (CollectionUtils.isNotEmpty(snCodeList) && perNum * i < snCodeList.size()) {
                    Integer snCodeMaxNum = perNum * (i + 1) > snCodeList.size() ? snCodeList.size() : perNum * (i + 1);
                    CopyOnWriteArrayList<String> subSnCodeList = new CopyOnWriteArrayList(snCodeList.subList(perNum * i, snCodeMaxNum));
                    getReq.setSnCodeList(subSnCodeList);
                }
                if (CollectionUtils.isNotEmpty(macCodeList) && perNum * i < macCodeList.size()) {
                    Integer macCodeMaxNum = perNum * (i + 1) > macCodeList.size() ? macCodeList.size() : perNum * (i + 1);
                    CopyOnWriteArrayList<String> subMacCodeList = new CopyOnWriteArrayList(macCodeList.subList(perNum * i, macCodeMaxNum));
                    getReq.setMacCodeList(subMacCodeList);
                }
                log.info("ValidAndAddRunableTask.exceutorValid newList={}, batchId={}", JSON.toJSONString(newList), batchId);
                Callable<Boolean> callable = new ValidNbr(req, getReq, newList, batchId);
                tasks.put(callable);
            }
            List<Future<Boolean>> futures = executorService.invokeAll(tasks);
            validFutureTaskResult.put(batchId, futures);
            executorService.shutdown();
            return batchId;
        } catch (Exception e) {
            log.error("串码查询异常", e);
        }
        return batchId;
    }


    /**
     * 串码校验多线程处理是否完成
     */
    public Boolean validHasDone(String batchId) {
        try{
            Boolean hasDone = true;
            log.info("ValidAndAddRunableTask.validHasDone batchId={}, futures={}", batchId, JSON.toJSONString(validFutureTaskResult));
            if (null == validFutureTaskResult || CollectionUtils.isEmpty(validFutureTaskResult.get(batchId))) {
                return false;
            }
            List<Future<Boolean>> futures = validFutureTaskResult.get(batchId);
            for (Future<Boolean> future : futures) {
                if (!future.isDone()) {
                    validFutureTaskResult.remove(batchId);
                    return future.isDone();
                }
            }
            return hasDone;
        }catch (Throwable e) {
            if (e instanceof ExecutionException) {
                e = e.getCause();
            }
            log.error("error happen", e);
        }
        return null;
    }


    /**
     * 串码临时表删除多线程处理
     * @param req
     */
    public void exceutorDelNbr(ResourceUploadTempDelReq req) {
        ExecutorService executorService = ExcutorServiceUtils.initExecutorService();
        Callable callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Integer successNum = resourceUploadTempManager.delResourceUploadTemp(req);
                log.info("RunableTask.exceutorDelNbr resourceUploadTempManager.delResourceUploadTemp req={}, resp={}", JSON.toJSONString(req), successNum);
                return successNum;
            }
        };
        Future<Integer> delNbrFutureTask = executorService.submit(callable);
        executorService.shutdown();
    }

    class ValidNbr implements Callable<Boolean> {
        private ResourceInstValidReq req;
        private ResourceInstsTrackGetReq getReq;
        private CopyOnWriteArrayList<String> newList;
        private String batchId;

        public ValidNbr(ResourceInstValidReq req, ResourceInstsTrackGetReq getReq, CopyOnWriteArrayList<String> newList, String batchId) {
            this.req = req;
            this.getReq = getReq;
            this.newList = newList;
            this.batchId = batchId;
        }

        @Override
        public Boolean call() throws Exception {
            Date now = new Date();
            ResourceInstsTrackGetReq trackGetReq = new ResourceInstsTrackGetReq();
            BeanUtils.copyProperties(getReq, trackGetReq);
            trackGetReq.setSnCodeList(null);
            trackGetReq.setMacCodeList(null);
            trackGetReq.setCtCodeList(null);
            ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(trackGetReq);
            log.info("ValidAndAddRunableTask.exceutorValid resouceInstTrackService.listResourceInstsTrack getReq={}, resp={}", JSON.toJSONString(trackGetReq), JSON.toJSONString(instsTrackvO));
            List<String> detailExitstNbr = detailManager.getProcessingNbrList(newList);
            List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>();
            instList.addAll(this.validSnCode());
            instList.addAll(this.validMacCode());
            instList.addAll(this.validCtCode());
            if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                List<ResouceInstTrackDTO> instTrackDTOList = instsTrackvO.getResultData();
                // 删除的串码可再次导入
                String deleteStatus = ResourceConst.STATUSCD.DELETED.getCode();
                List<String> mktResInstNbrList = instTrackDTOList.stream().map(ResouceInstTrackDTO::getMktResInstNbr).collect(Collectors.toList());
                for (ResouceInstTrackDTO dto : instTrackDTOList) {
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setMktResUploadBatch(batchId);
                    inst.setUploadDate(now);
                    inst.setCreateDate(now);
                    if (!deleteStatus.equals(dto.getStatusCd()) && StringUtils.isNotBlank(dto.getSourceType())) {
                        inst.setResultDesc("串码库中已存在");
                        inst.setResult(ResourceConst.CONSTANT_YES);
                    }else{
                        inst.setResult(ResourceConst.CONSTANT_NO);
                    }
                    inst.setMktResInstNbr(dto.getMktResInstNbr());
                    inst.setCreateStaff(req.getCreateStaff());
                    instList.add(inst);
                }
                newList.removeAll(mktResInstNbrList);
            }
            if (CollectionUtils.isNotEmpty(detailExitstNbr)) {
                for (String mktResInstNbr : detailExitstNbr) {
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setMktResUploadBatch(batchId);
                    inst.setMktResInstNbr(mktResInstNbr);
                    inst.setResult(ResourceConst.CONSTANT_YES);
                    inst.setUploadDate(now);
                    inst.setCreateDate(now);
                    inst.setResultDesc("串码申请单中已存在");
                    inst.setCreateStaff(req.getCreateStaff());
                    instList.add(inst);
                }
                newList.removeAll(detailExitstNbr);
            }
            if (CollectionUtils.isNotEmpty(newList)) {
                for (String mktResInstNbr : newList) {
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setResult(ResourceConst.CONSTANT_NO);
                    ResouceUploadTemp checkInst = checkPatten(mktResInstNbr);
                    if (null != checkInst) {
                        instList.add(checkInst);
                        continue;
                    }
                    inst.setMktResUploadBatch(batchId);
                    inst.setMktResInstNbr(mktResInstNbr);
                    if (null != req.getCtCodeMap()) {
                        inst.setCtCode(req.getCtCodeMap().get(mktResInstNbr));
                    }
                    if (null != req.getSnCodeMap()) {
                        inst.setSnCode(req.getSnCodeMap().get(mktResInstNbr));
                    }
                    if (null != req.getMacCodeMap()) {
                        inst.setMacCode(req.getMacCodeMap().get(mktResInstNbr));
                    }
                    inst.setUploadDate(now);
                    inst.setCreateDate(now);
                    inst.setCreateStaff(req.getCreateStaff());
                    instList.add(inst);
                }
            }
            Boolean addResult = resourceUploadTempManager.saveBatch(instList);
            log.info("ValidAndAddRunableTask.exceutorValid resourceUploadTempManager.saveBatch req={}, resp={}", JSON.toJSONString(instList), addResult);
            return addResult;
        }


        /**
         * 校验SN、CT、MAC码的格式
         * @return
         */
        private ResouceUploadTemp checkPatten(String mktResInstNbr){
            Date now = new Date();
            ResouceUploadTemp inst = new ResouceUploadTemp();
            inst.setResult(ResourceConst.CONSTANT_NO);
            inst.setMktResUploadBatch(batchId);
            inst.setMktResInstNbr(mktResInstNbr);
            if (null != req.getCtCodeMap()) {
                inst.setCtCode(req.getCtCodeMap().get(mktResInstNbr));
            }
            if (null != req.getSnCodeMap()) {
                inst.setSnCode(req.getSnCodeMap().get(mktResInstNbr));
            }
            if (null != req.getMacCodeMap()) {
                inst.setMacCode(req.getMacCodeMap().get(mktResInstNbr));
            }
            inst.setUploadDate(now);
            inst.setCreateDate(now);
            inst.setCreateStaff(req.getCreateStaff());

            if (TypeConst.TYPE_DETAIL.ROUTER.getCode().equals(req.getDetailCode()) || TypeConst.TYPE_DETAIL.INTELLIGENT_TERMINA.getCode().equals(req.getDetailCode())) {
                Pattern pattern12= Pattern.compile(reg12);
                Boolean matchs = pattern12.matcher(mktResInstNbr).matches();
                if (!matchs) {
                    inst.setResult(ResourceConst.CONSTANT_YES);
                    inst.setResultDesc("路由器及泛智能终端的终端串码只能包含大写字母、数字和-，且必须为12位");
                }
            }else if (TypeConst.TYPE_DETAIL.OPTICAL_MODEM.getCode().equals(req.getDetailCode())) {
                Pattern pattern24= Pattern.compile(reg24);
                Boolean matchs = pattern24.matcher(mktResInstNbr).matches();
                if (!matchs) {
                    inst.setResult(ResourceConst.CONSTANT_YES);
                    inst.setResultDesc("光猫的终端串码只能包含大写字母、数字和-，且必须为24位");
                }
            }else if (TypeConst.TYPE_DETAIL.SET_TOP_BOX.getCode().equals(req.getDetailCode())) {
                Pattern pattern32= Pattern.compile(reg32);
                Pattern pattern39= Pattern.compile(reg39);
                Boolean matchs = pattern32.matcher(mktResInstNbr).matches() || pattern39.matcher(mktResInstNbr).matches();
                if (!matchs) {
                    inst.setResult(ResourceConst.CONSTANT_YES);
                    inst.setResultDesc("机顶盒的终端串码只能包含大写字母、数字和-，且必须为32位或39位");
                }
            }else if (TypeConst.TYPE_DETAIL.FUSION_TERMINAL.getCode().equals(req.getDetailCode())) {
                Pattern pattern32= Pattern.compile(reg32);
                Boolean matchs = pattern32.matcher(mktResInstNbr).matches();
                if (!matchs) {
                    inst.setResult(ResourceConst.CONSTANT_YES);
                    inst.setResultDesc("融合终端的终端串码只能包含大写字母、数字和-，且必须为32位");
                }
            }
            if (ResourceConst.CONSTANT_YES.equals(inst.getResult())) {
                return inst;
            }
            return null;
        }

        /**
         * 校验轨迹表SN码的唯一性
         * @return
         */
        private List<ResouceUploadTemp> validSnCode(){
            List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>();
            Boolean needValid = TypeConst.TYPE_DETAIL.FUSION_TERMINAL.getCode().equals(req.getDetailCode()) || TypeConst.TYPE_DETAIL.SET_TOP_BOX.getCode().equals(req.getDetailCode()) ||
                    TypeConst.TYPE_DETAIL.OPTICAL_MODEM.getCode().equals(req.getDetailCode()) || TypeConst.TYPE_DETAIL.ROUTER.getCode().equals(req.getDetailCode()) &&
                    TypeConst.TYPE_DETAIL.INTELLIGENT_TERMINA.getCode().equals(req.getDetailCode());
            if (!needValid) {
                return instList;
            }
            if (CollectionUtils.isNotEmpty(getReq.getSnCodeList())) {
                return instList;
            }

            List<String> mktResInstNbrList = getReq.getMktResInstNbrList();
            List<String> mktResInstMacList = getReq.getMacCodeList();
            List<String> mktResInstSnList = getReq.getSnCodeList();
            List<String> mktResInstCTList = getReq.getCtCodeList();
            ResourceInstsTrackGetReq trackGetReq = new ResourceInstsTrackGetReq();
            trackGetReq.setSnCodeList(getReq.getSnCodeList());
            ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(trackGetReq);
            log.info("ValidAndAddRunableTask.validSnCode resouceInstTrackService.listResourceInstsTrack getReq={}, resp={}", JSON.toJSONString(trackGetReq), JSON.toJSONString(instsTrackvO));
            if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                List<ResouceInstTrackDTO> instTrackDTOList = instsTrackvO.getResultData();
                // 删除的串码可再次导入
                String deleteStatus = ResourceConst.STATUSCD.DELETED.getCode();
                Date now = new Date();
                for (ResouceInstTrackDTO dto : instTrackDTOList) {
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setMktResUploadBatch(batchId);
                    inst.setUploadDate(now);
                    inst.setCreateDate(now);
                    if (!deleteStatus.equals(dto.getStatusCd()) && StringUtils.isNotBlank(dto.getSourceType())) {
                        inst.setResultDesc("SN码库中已存在");
                        inst.setResult(ResourceConst.CONSTANT_YES);
                    }else{
                        inst.setResult(ResourceConst.CONSTANT_NO);
                    }
                    inst.setSnCode(dto.getSnCode());
                    Integer index = mktResInstSnList.indexOf(dto.getSnCode());
                    inst.setCtCode(mktResInstCTList.get(index));
                    inst.setMacCode(mktResInstMacList.get(index));
                    inst.setMktResInstNbr(mktResInstNbrList.get(index));
                    inst.setCreateStaff(req.getCreateStaff());
                    instList.add(inst);
                    newList.remove(mktResInstNbrList.get(index));
                }
            }
            return instList;
        }


        /**
         * 校验轨迹表MAC码的唯一性
         * @return
         */
        private List<ResouceUploadTemp> validMacCode(){
            List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>();
            Boolean needValid = TypeConst.TYPE_DETAIL.FUSION_TERMINAL.getCode().equals(req.getDetailCode()) || TypeConst.TYPE_DETAIL.SET_TOP_BOX.getCode().equals(req.getDetailCode()) ||
                    TypeConst.TYPE_DETAIL.OPTICAL_MODEM.getCode().equals(req.getDetailCode());
            if (!needValid) {
                return instList;
            }
            if (CollectionUtils.isEmpty(getReq.getMacCodeList())) {
                return instList;
            }

            List<String> mktResInstNbrList = getReq.getMktResInstNbrList();
            List<String> mktResInstMacList = getReq.getMacCodeList();
            List<String> mktResInstSnList = getReq.getSnCodeList();
            List<String> mktResInstCTList = getReq.getCtCodeList();
            ResourceInstsTrackGetReq trackGetReq = new ResourceInstsTrackGetReq();
            trackGetReq.setMacCodeList(getReq.getMacCodeList());
            ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(trackGetReq);
            log.info("ValidAndAddRunableTask.validMacCode resouceInstTrackService.listResourceInstsTrack getReq={}, resp={}", JSON.toJSONString(trackGetReq), JSON.toJSONString(instsTrackvO));
            if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                List<ResouceInstTrackDTO> instTrackDTOList = instsTrackvO.getResultData();
                // 删除的串码可再次导入
                String deleteStatus = ResourceConst.STATUSCD.DELETED.getCode();
                Date now = new Date();
                for (ResouceInstTrackDTO dto : instTrackDTOList) {
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setMktResUploadBatch(batchId);
                    inst.setUploadDate(now);
                    inst.setCreateDate(now);
                    if (!deleteStatus.equals(dto.getStatusCd()) && StringUtils.isNotBlank(dto.getSourceType())) {
                        inst.setResultDesc("MAC码库中已存在");
                        inst.setResult(ResourceConst.CONSTANT_YES);
                    }else{
                        inst.setResult(ResourceConst.CONSTANT_NO);
                    }
                    inst.setMacCode(dto.getMacCode());
                    Integer index = mktResInstMacList.indexOf(dto.getMacCode());
                    inst.setSnCode(mktResInstSnList.get(index));
                    inst.setCtCode(mktResInstCTList.get(index));
                    inst.setMktResInstNbr(mktResInstNbrList.get(index));
                    inst.setCreateStaff(req.getCreateStaff());
                    instList.add(inst);
                    newList.remove(mktResInstNbrList.get(index));
                }
            }
            return instList;
        }

        /**
         * 校验轨迹表CT码的唯一性
         * @return
         */
        private List<ResouceUploadTemp> validCtCode(){
            List<ResouceUploadTemp> instList = new ArrayList<ResouceUploadTemp>();
            Boolean needValid = TypeConst.TYPE_DETAIL.ROUTER.getCode().equals(req.getDetailCode()) || TypeConst.TYPE_DETAIL.INTELLIGENT_TERMINA.getCode().equals(req.getDetailCode());
            if (!needValid) {
                return instList;
            }
            if (CollectionUtils.isEmpty(getReq.getCtCodeList())) {
                return instList;
            }

            List<String> mktResInstNbrList = getReq.getMktResInstNbrList();
            List<String> mktResInstMacList = getReq.getMacCodeList();
            List<String> mktResInstSnList = getReq.getSnCodeList();
            List<String> mktResInstCTList = getReq.getCtCodeList();
            ResourceInstsTrackGetReq trackGetReq = new ResourceInstsTrackGetReq();
            List<String> notNullCtCodeList = getReq.getCtCodeList().stream().filter(t -> StringUtils.isNotBlank(t)).collect(Collectors.toList());
            CopyOnWriteArrayList<String> ctCodeList = new CopyOnWriteArrayList<String>(notNullCtCodeList);
            trackGetReq.setCtCodeList(ctCodeList);
            ResultVO<List<ResouceInstTrackDTO>> instsTrackvO = resouceInstTrackService.listResourceInstsTrack(trackGetReq);
            log.info("ValidAndAddRunableTask.validCtCode resouceInstTrackService.listResourceInstsTrack getReq={}, resp={}", JSON.toJSONString(trackGetReq), JSON.toJSONString(instsTrackvO));
            if (instsTrackvO.isSuccess() && CollectionUtils.isNotEmpty(instsTrackvO.getResultData())) {
                List<ResouceInstTrackDTO> instTrackDTOList = instsTrackvO.getResultData();
                // 删除的串码可再次导入
                String deleteStatus = ResourceConst.STATUSCD.DELETED.getCode();
                Date now = new Date();
                for (ResouceInstTrackDTO dto : instTrackDTOList) {
                    ResouceUploadTemp inst = new ResouceUploadTemp();
                    inst.setMktResUploadBatch(batchId);
                    inst.setUploadDate(now);
                    inst.setCreateDate(now);
                    if (!deleteStatus.equals(dto.getStatusCd()) && StringUtils.isNotBlank(dto.getSourceType())) {
                        inst.setResultDesc("CTEI码库中已存在");
                        inst.setResult(ResourceConst.CONSTANT_YES);
                    }else{
                        inst.setResult(ResourceConst.CONSTANT_NO);
                    }
                    inst.setCtCode(dto.getCtCode());
                    Integer index = mktResInstCTList.indexOf(dto.getCtCode());
                    inst.setSnCode(mktResInstSnList.get(index));
                    inst.setMacCode(mktResInstMacList.get(index));
                    inst.setMktResInstNbr(mktResInstNbrList.get(index));
                    inst.setCreateStaff(req.getCreateStaff());
                    instList.add(inst);
                    newList.remove(mktResInstNbrList.get(index));
                }
            }
            return instList;
        }
    }

    public static void main(String[] args) {
        String reg = "([A-Z]|[0-9]|[-]){12}$";
        Pattern pattern39= Pattern.compile(reg);
        String test = "203911110190";
        Boolean matchs = pattern39.matcher(test).matches();
        System.out.print(matchs);
    }
}

