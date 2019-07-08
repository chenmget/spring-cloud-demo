package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.system.dto.PublicDictDTO;
import com.iwhalecloud.retail.system.service.PublicDictService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.FtpDTO;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;
import com.iwhalecloud.retail.warehouse.entity.ResouceEvent;
import com.iwhalecloud.retail.warehouse.mapper.MktResItmsSyncRecMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResouceEventMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import com.iwhalecloud.retail.warehouse.runable.ResourceInstStoreRunableTask;
import com.iwhalecloud.retail.warehouse.service.MktResItmsSyncRecService;
import com.iwhalecloud.retail.warehouse.util.FtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class MktResItmsSyncRecServiceImpl implements MktResItmsSyncRecService {

    private static String[] brands = {"10350143", "10350148"};
    private static String[][] types = {{"1001", "1003", "1004"}, {"1002"}, {"1007", "1009"}};
    private static String[][] isItms = {{"1","3"},{"2","3"}};

    private static final String CONF_STR = "ITMS_PUSH_MKT_NB";

    @Autowired
    private ResourceInstMapper resourceInstMapper;

    @Autowired
    private ResouceEventMapper resouceEventMapper;

    @Autowired
    private MktResItmsSyncRecMapper mktResItmsSyncRecMapper;

    @Autowired
    private ResourceInstStoreRunableTask resourceInstStoreRunableTask;

    @Reference
    private PublicDictService publicDictService;
    /**
     * 根据地市生成相关的文件
     * 光猫：10350143，IPTV：10350148
     * 新增：1001,1003,1004 修改：1002 删除：1007,1009
     * 统计时间和序列号都存放在SYS_CONFIG_INFO表中，
     * 其中统计时间的CF_ID是CONF_STR，一字符串的方式存储；
     *
     * Created by jiyou on 2019/4/24.
     * updated by xieqi on 2019/5/15
     */
    @Override
    public void syncMktToITMS() {
        log.info("MktResItmsSyncRecServiceImpl.syncMktToITMS start.....");
        //获取ftp信息
        FtpDTO ftpInfo = getftpDTO();
        //获取itms上传ftp服务器保存路径信息
        List<PublicDictDTO> publicDictDTOS = publicDictService.queryPublicDictListByType(ResourceConst.DICT_TYPE_FTP_ITMS_PATH);
        if (null != ftpInfo && null != publicDictDTOS) {
            //备份文件
            try{
                this.backItmsFile(ftpInfo, publicDictDTOS);
            }catch (Exception e){
                log.error("备份上ITMS数据失败{}",e.getMessage());
            }
            // 开始时间
            String startStr = resourceInstMapper.findCfValueByCfId(CONF_STR);
            // 结束时间
            Date newStartStr = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < brands.length; i++) {
                for (int j = 0; j < types.length; j++) {
                    //查询事件表数据
                    List<ResouceEvent> evenList = resouceEventMapper.selectMktResEventList(types[j],startStr,sdf.format(newStartStr));
                    if(null != evenList && evenList.size() != 0){
                        this.syncMktToITMS(brands[i], types[j], evenList, ftpInfo, publicDictDTOS);
                    }
                }
            }
            if (null == startStr) {
                resourceInstMapper.initConfig(sdf.format(newStartStr));
            } else {
                resourceInstMapper.updateCfValueByCfId(CONF_STR, sdf.format(newStartStr));
            }
            log.info("MktResItmsSyncRecServiceImpl.syncMktToITMS end.....");
        }else{
            log.error("MktResItmsSyncRecServiceImpl.syncMktToITMS 获取ftp配置错误,执行失败");
        }

    }


    public void syncMktToITMS(String brand, String[] ops, List<ResouceEvent> evenList, FtpDTO ftpInfo,List<PublicDictDTO> publicDictDTOS) {

        List<String> files = new ArrayList<String>();
        // 查询所有地市
        List<String> lanIdList = resourceInstMapper.findAllLanID();
        //全省id
        lanIdList.add("999");
        //上传路径
        String sendDir = null;
        //清空临时文件
        this.clearDir(ftpInfo);

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String time = sdf.format(new Date());
        PrintWriter pw = null;
        for (int i = 0; i < lanIdList.size(); i++) {
            List<MktResItmsSyncRec> mktList = new ArrayList<>();
            String lanId = lanIdList.get(i);

            String[] getIsItms = getIsItms(brand);
            //插入数据到MktResItmsSyncRec表
            List<MktResItmsSyncRec> list = insertDateToMRISR(lanId,getIsItms,evenList);
            if(!CollectionUtils.isEmpty(list)){
                //获取ftp存储文件路径
                String pKey = getPKey(brand, ops);
                sendDir = getSendDir(publicDictDTOS, pKey);
                String seqStr = mktResItmsSyncRecMapper.getSeqBysendDir(sendDir+"/"+lanId);
                String resStr = "0";
                if(seqStr != null && seqStr.length() != 0){
                    resStr = seqStr.substring(seqStr.length()-3,seqStr.length());
                }
                int seqNb = Integer.parseInt(resStr);

                //根据PER_NUM配置条数控制每个输出文件输出条数
                Integer excutorNum = list.size()%ResourceConst.PER_NUM == 0 ? list.size()/ResourceConst.PER_NUM : (list.size()/ResourceConst.PER_NUM + 1);
                for (Integer j = 0; j < excutorNum; j++) {

                    Integer maxNum = ResourceConst.PER_NUM * (j + 1) > list.size() ? list.size() : ResourceConst.PER_NUM * (j + 1);
                    List<MktResItmsSyncRec> subList = list.subList(ResourceConst.PER_NUM * j, maxNum);
                    seqNb++;
                    String seq = getSeqStr(seqNb);
                    String batchId = time + seq;
                    String destFileName = lanId.concat("ITMS").concat(batchId).concat(".txt");
                    File destFile = new File(ftpInfo.getFilePath() + File.separator + destFileName);
                    files.add(destFileName);
                    pw = getPrintWriter(destFile);
                    StringBuilder buil = new StringBuilder();
                    String syncFileName = sendDir.concat("/").concat(destFileName);
                    for(MktResItmsSyncRec mktResItmsSyncRec : subList){
                        //拼接输出字符串
                        buil.append(mktResItmsSyncRec.getMktResInstNbr()).append(",").append(mktResItmsSyncRec.getBrandName())
                                .append(mktResItmsSyncRec.getUnitType()).append(",2,").append(mktResItmsSyncRec.getOrigLanId()).append(System.lineSeparator());

                        MktResItmsSyncRec req = new MktResItmsSyncRec();
                        req.setMktResChngEvtDetailId(mktResItmsSyncRec.getMktResChngEvtDetailId());
                        req.setSyncFileName(syncFileName);
                        req.setSyncBatchId(batchId);
                        mktList.add(req);
                    }
                    pw.write(buil.toString());
                    pw.close();
                }
                //调用线程更新
                if(!CollectionUtils.isEmpty(mktList)){
                    resourceInstStoreRunableTask.exceutorUpdateMktResItmsSyncRec(mktList);
                }
            }
        }
        if(pw != null){
            pw.close();
        }
        //上传文件到ftp服务器
        try{
            this.sendFileToFtp(ftpInfo,sendDir,files);
            files.clear();
        }catch (Exception e){
            log.error("syncMktToITMS 传文件到ftp服务器失败");
        }
    }

    private String[] getIsItms(String brand){
        String[] itms = new String[2];
        //光猫
        if(brand.equals(brands[0])){
            itms = isItms[1];
        }
        //IPTV
        else if(brand.equals(brands[1])){
            itms = isItms[0];
        }
        return itms;
    }

    /**
     * 将序列号转换成字符串
     *
     * @param seqNb
     * @return
     */
    private String getSeqStr(int seqNb) {
        String seq = "";
        if (seqNb < 10) {
            seq = "00" + seqNb;
        } else if (seqNb < 100) {
            seq = "0" + seqNb;
        } else {
            seq = "" + seqNb;
        }
        return seq;
    }

    /**
     * 初始化配置
     *
     * @return
     */
    private JSONObject initConfJsonStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ops_time", sdf.format(new Date()));

        for (int i = 0; i < brands.length; i++) {
            for (int j = 0; j < types.length; j++) {
                jsonObject.put("ITMS_" + brands[i] + "_" + types[j], 1);
            }
        }

        return jsonObject;
    }

    /**
     * 获取输出对象
     *
     * @param file
     * @return
     */
    private PrintWriter getPrintWriter(File file) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"), true);
        } catch (UnsupportedEncodingException e) {
            log.error("串码入库，与ITMS集成" + e.getMessage(), e);
        } catch (FileNotFoundException e) {
            log.error("串码入库，与ITMS集成" + e.getMessage(), e);
        }
        return pw;
    }

    /**
     * 数据插入到MKT_RES_ITMS_SYNC_REC表
     *
     * @return
     * @throws
     */
    private List<MktResItmsSyncRec> insertDateToMRISR(String lanId, String[] isItms,List<ResouceEvent> evenList) {
        List<MktResItmsSyncRec> mktResItmsSyncRecList = new ArrayList<MktResItmsSyncRec>();
        for(ResouceEvent resouceEvent:evenList){
            List<MktResItmsSyncRec> mktResItmsSyncRecs = mktResItmsSyncRecMapper.findDateMKTInfoByParams(lanId,resouceEvent.getEventType(),isItms,resouceEvent);
            if(!CollectionUtils.isEmpty(mktResItmsSyncRecs)){
                mktResItmsSyncRecList.addAll(mktResItmsSyncRecs);
            }
        }
        if(mktResItmsSyncRecList.size()!=0){
            ArrayList<List<MktResItmsSyncRec>> mktList = this.mktListSplit(mktResItmsSyncRecList);
            //批量插入
            for(int i = 0; i < mktList.size(); i++){
                mktResItmsSyncRecMapper.batchAddMKTInfo(mktList.get(i));
            }
            log.info("成功插入到 MktResItmsSyncRec表数据。");
        }
        return mktResItmsSyncRecList;
    }

    private String getPKey(String brand, String[] ops){
//        String baseDir = "/home/itsm_y/itmsfile";
        String pKey = "";
        if (brands[0].equals(brand)) {
            // 光猫
            if (Arrays.equals(types[0],ops)) {
                pKey = ResourceConst.TERMINAL_ADD;
            }
            if (Arrays.equals(types[1],ops)) {
                pKey = ResourceConst.TERMINAL_MODIFY;
            }
            if (Arrays.equals(types[2],ops)) {
                pKey = ResourceConst.TERMINAL_DELETE;
            }
        } else {
            // iptv
            if (Arrays.equals(types[0],ops)) {
                pKey = ResourceConst.IPTV_ADD;
            }
            if (Arrays.equals(types[1],ops)) {
                pKey = ResourceConst.IPTV_MODIFY;
            }
            if (Arrays.equals(types[2],ops)) {
                pKey = ResourceConst.IPTV_DELETE;
            }
        }
        return pKey;
    }

    /**
     * 将list以每num条拆分为多个list
     * @param lineList
     * @return
     */
    private ArrayList<List<MktResItmsSyncRec>> mktListSplit(List<MktResItmsSyncRec> lineList){
        ArrayList<List<MktResItmsSyncRec>> allLineLists = new ArrayList<>();
        int num = ResourceConst.EVERY_PART_NUM;
        int number;
        if(lineList.size() > num){
            //判断要分割的个数
            if(lineList.size() % num > 0){
                number = lineList.size() / num + 1;
            }else{
                number = lineList.size() / num;
            }
            // 循环把分割的数组存入 userIdLists 中
            for (int i = 0; i < number; i++) {
                ArrayList<MktResItmsSyncRec> list = new ArrayList<>();
                // 最后一个数组长度不定
                if (i != number - 1) {
                    for (int j = 0; j < num; j++) {
                        list.add(lineList.get(i * num + j));
                    }
                } else {
                    for (int j = 0; j < lineList.size() - (i * num); j++) {
                        list.add(lineList.get(i * num + j));
                    }
                }
                allLineLists.add(list);
            }
        }else {
            allLineLists.add(lineList);
        }
        return allLineLists;
    }

    /**
     * 备份传给ITMS的数据
     * @param ftpInfo
     * @throws Exception
     */
    private void backItmsFile(FtpDTO ftpInfo, List<PublicDictDTO> publicDictDTOS) throws Exception{
        log.info("backItmsFile 开始备份上传ITMS数据");
        FTPClient ftpClient = FtpUtils.connectFtp(ftpInfo);
        try{
            List<String> fileList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(publicDictDTOS)){
                for(PublicDictDTO publicDictDTO : publicDictDTOS){
                    //原路径
                    String filePath =  publicDictDTO.getCodec().concat("/");
                    fileList = FtpUtils.fileList(ftpClient, filePath, publicDictDTO.getCodeb());
                    ftpClient.changeWorkingDirectory(filePath);
                    if(fileList != null && !fileList.isEmpty()){
                        for(int j=0; j<fileList.size(); j++){
                            String fileName = fileList.get(j);
                            //目标路径+文件名
                            String newFilePath = publicDictDTO.getCoded().concat("/").concat(fileName);
                            FtpUtils.copyFile(ftpClient,newFilePath,fileName);
                        }
                    }

                }
            }
            log.info("backItmsFile 备份上传ITMS数据成功");
        }catch (Exception e){
            log.error("backItmsFile 备份上传ITMS文件失败"+e.getMessage());
        }finally {
            FtpUtils.closeServer(ftpClient);
        }
    }

    /**
     * 获取ftp信息
     * @return
     */
    public FtpDTO getftpDTO(){
        FtpDTO ftpDTO = new FtpDTO();
        List<PublicDictDTO> publicDictDTOS = publicDictService.queryPublicDictListByType(ResourceConst.DICT_TYPE_FTP);
        if (CollectionUtils.isEmpty(publicDictDTOS)) {
            log.error("ResourceInstStoreServiceImpl.getftpDTO 获取ftp信息失败");
            return ftpDTO;
        }
        //pkey值为1的数据为生产在用数据
        for(PublicDictDTO publicDictDTO : publicDictDTOS){
            if("1".equals(publicDictDTO.getPkey())){
                ftpDTO.setIpAddr(publicDictDTO.getPname());
                ftpDTO.setPort(publicDictDTO.getCodea());
                ftpDTO.setUserName(publicDictDTO.getCodeb());
                ftpDTO.setPassword(publicDictDTO.getCodec());
                ftpDTO.setFilePath(publicDictDTO.getCoded());
            }
        }
        return ftpDTO;
    }

    /**
     * 清空本地目录
     * @param ftpInfo
     * @return
     */
    private Boolean clearDir(FtpDTO ftpInfo){
        File dir = new File(ftpInfo.getFilePath());
        try{
            //删除文件
            FtpUtils.delTempChild(dir);
            log.info("MktResItmsSyncRecServiceImpl.clearDir basePath:{} exists:{} isDirectory:{}",ftpInfo.getFilePath(),dir.exists(),dir.isDirectory());
            if (!dir.exists() || !dir.isDirectory()) {
                log.info("MktResItmsSyncRecServiceImpl.syncMktToITMS mkdirs start.....");
                dir.mkdirs();
                log.info("MktResItmsSyncRecServiceImpl.syncMktToITMS mkdirs end.....");
            }
        }catch (Exception e){
            log.error("MktResItmsSyncRecServiceImpl.syncMktToITMS mkdirs{}",e.getMessage());
        }
        return true;
    }

    public String getSendDir(List<PublicDictDTO> publicDictDTOS, String pKey){
        String sendDir = null;
        for(PublicDictDTO PublicDictDTO : publicDictDTOS){
            if(pKey.equals(PublicDictDTO.getPkey())){
                sendDir = PublicDictDTO.getCodec();
            }
        }
        return sendDir;
    }

    private Boolean sendFileToFtp(FtpDTO ftpInfo, String sendDir, List<String> files) throws Exception{
        Boolean flag = true;
        FTPClient ftpClient = FtpUtils.connectFtp(ftpInfo);
        try {
            FtpUtils.sendFileToFtp(ftpClient,sendDir,files, ftpInfo.getFilePath());
        } catch (Exception e) {
            flag = false;
            log.error("MktResItmsSyncRecServiceImpl.sendFileToFtp{}",e.getMessage());
        }finally {
            FtpUtils.closeServer(ftpClient);
        }
        return flag;
    }
}
