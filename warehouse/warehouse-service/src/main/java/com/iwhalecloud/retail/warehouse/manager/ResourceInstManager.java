package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListResp;
import com.iwhalecloud.retail.warehouse.dto.response.ValidResourceInstItemResp;
import com.iwhalecloud.retail.warehouse.dto.response.ValidResourceInstResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInst;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


@Component
@Slf4j
public class ResourceInstManager extends ServiceImpl<ResourceInstMapper, ResourceInst> {
    @Resource
    private ResourceInstMapper resourceInstMapper;

    @Autowired
    private Constant constant;

    /**
     * 添加串码
     *
     * @param t
     * @return
     */
    public Integer addResourceInst(ResourceInst t) {
        return resourceInstMapper.insert(t);
    }

    /**
     * 更新串码状态
     *
     * @param req
     * @return
     */
    public Integer updateResourceInst(ResourceInstUpdateReq req) {
        return resourceInstMapper.updateResourceInst(req);
    }

    /**
     * 更新串码状态
     *
     * @param req
     * @return
     */
    public Integer updateResourceInstByIds(AdminResourceInstDelReq req) {
//        修改一定要注意条件 防范全表更新
        if (req.getMktResInstIds() == null || req.getMktResInstIds().size() < 1) {
            return 0;
        }
        return resourceInstMapper.updateResourceInstByIds(req);
    }

    /**
     * 根据查询条件串码实列
     *
     * @param req
     * @return
     */
    public Page<ResourceInstListResp> getResourceInstList(ResourceInstListReq req) {
        Page<ResourceInstListResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return resourceInstMapper.getResourceInstList(page, req);
    }

    public ResultVO validResourceInst(ValidResourceInstReq req) {
        //step1:供应商——串码如果在供应商的仓库中存在，且状态为可用“1302已领用可销售”；
        // 如果终端串码在供应商仓库中不存在，查询厂商的仓库中是否有此串码，如果有则通过；
        List<ValidResourceInstItemResp> resultList = new ArrayList<ValidResourceInstItemResp>();
        boolean result = true;
        if (req.getMerchantType().equals(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType())) {
            List<ValidResourceInstItem> productIds = req.getProductIds();
            for (ValidResourceInstItem item : productIds) {
                String productId = item.getProductId();
                List<String> mktResInstNbr = item.getMktResInstNbr();
                QueryWrapper<ResourceInst> queryWrapperSuppler = new QueryWrapper<>();
                queryWrapperSuppler.in(ResourceInst.FieldNames.mktResInstNbr.getTableFieldName(), mktResInstNbr);
                List<ResourceInst> resourceInst = resourceInstMapper.selectList(queryWrapperSuppler);
                ResultVO<List<ValidResourceInstItemResp>> validVO = getValidErrorList(resourceInst, mktResInstNbr,productId,req.getMerchantId());
                resultList.addAll(validVO.getResultData());
                if (!validVO.isSuccess()) {
                    result = false;
                }

            }
            if (result) {
                return ResultVO.success();
            } else {
                ResultVO resultVO = ResultVO.error(getValidErrorMsg(resultList));
                ValidResourceInstResp resp = new ValidResourceInstResp();
                resp.setResultCode(ResourceConst.RESULE_CODE_FAIL);
                resp.setValidList(resultList);
                resultVO.setResultData(resp);
                return resultVO;
            }
        }
        //step2:地包商——串码必须供应商的仓库中存在，且状态为可用“1302已领用可销售”
        if (req.getMerchantType().equals(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType())) {
            List<ValidResourceInstItem> productIds = req.getProductIds();
            for (ValidResourceInstItem item : productIds) {
                String productId = item.getProductId();
                List<String> mktResInstNbr = item.getMktResInstNbr();
                QueryWrapper<ResourceInst> queryWrapperSuppler = new QueryWrapper<>();
                queryWrapperSuppler.in(ResourceInst.FieldNames.mktResInstNbr.getTableFieldName(), mktResInstNbr);
                List<ResourceInst> resourceInst = resourceInstMapper.selectList(queryWrapperSuppler);
                ResultVO<List<ValidResourceInstItemResp>> validVO = getValidErrorList(resourceInst, mktResInstNbr,productId,req.getMerchantId());
                resultList.addAll(validVO.getResultData());
                if (!validVO.isSuccess()) {
                    result = false;

                }

            }
            if (result) {
                return ResultVO.success();
            } else {
                ResultVO resultVO = ResultVO.error(getValidErrorMsg(resultList));
                ValidResourceInstResp resp = new ValidResourceInstResp();
                resp.setResultCode(ResourceConst.RESULE_CODE_FAIL);
                resp.setValidList(resultList);
                resultVO.setResultData(resp);
                return resultVO;
            }
        }
        // step3 增加厂商发货 在库可用可以发货
        if (req.getMerchantType().equals(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType())) {
            List<ValidResourceInstItem> productIds = req.getProductIds();
            for (ValidResourceInstItem item : productIds) {
                String productId = item.getProductId();
                List<String> mktResInstNbr = item.getMktResInstNbr();
                QueryWrapper<ResourceInst> queryWrapperSuppler = new QueryWrapper<>();
                queryWrapperSuppler.in(ResourceInst.FieldNames.mktResInstNbr.getTableFieldName(), mktResInstNbr);
                List<ResourceInst> resourceInst = resourceInstMapper.selectList(queryWrapperSuppler);
                ResultVO<List<ValidResourceInstItemResp>> validVO = getValidErrorList(resourceInst, mktResInstNbr,productId,req.getMerchantId());
                resultList.addAll(validVO.getResultData());
                if (!validVO.isSuccess()) {
                    result = false;
                }
            }
            if (result) {
                return ResultVO.success();
            } else {
                ResultVO resultVO = ResultVO.error(getValidErrorMsg(resultList));
                ValidResourceInstResp resp = new ValidResourceInstResp();
                resp.setResultCode(ResourceConst.RESULE_CODE_FAIL);
                resp.setValidList(resultList);
                resultVO.setResultData(resp);
                return resultVO;
            }
        }

        return ResultVO.error(constant.getErrorRequest());
    }
    private String getValidErrorMsg(List<ValidResourceInstItemResp> errorList){
        StringBuffer errorMsg = new StringBuffer();
        if(errorList!=null&&!errorList.isEmpty()){
            for (ValidResourceInstItemResp item : errorList) {
                errorMsg.append(constant.getPesInstErrorsPre()).append(item.getMktResInstNbr()).append(item.getResultMsg()).append(",");
            }
            errorMsg.delete(errorMsg.length()-1,errorMsg.length());
        }

        return errorMsg.toString();

    }

    /**
     * 拿到串码列表后，遍历每个串码，计算其出不合法的原因，并返回错误串码列表
     * @param resourceInstTemp
     * @param mktResInstNbr
     * @param productId
     * @param merchantId
     * @return
     */
    private ResultVO<List<ValidResourceInstItemResp>> getValidErrorList(List<ResourceInst> resourceInstTemp, List<String> mktResInstNbr,String productId,String merchantId) {
        ResultVO<List<ValidResourceInstItemResp>> resultVO = new ResultVO<List<ValidResourceInstItemResp>>();
        List<ValidResourceInstItemResp> errorList = new ArrayList<ValidResourceInstItemResp>();
        List<ValidResourceInstItemResp> sucList = new ArrayList<ValidResourceInstItemResp>();
        //串码集合
        Set<String> allNbSet = new HashSet<String>();
        //与productId和merchantId符合的数据,原来逻辑是根据sql查询的，现在根据代码进行过滤
        Set<String> proNbSet = new HashSet<String>();
        //在库可用的串码
        Set<String> statusUseSet = new HashSet<String>();
        if(resourceInstTemp != null && !resourceInstTemp.isEmpty()){
            for (ResourceInst inst : resourceInstTemp) {
                allNbSet.add(inst.getMktResInstNbr());
                if(inst.getMerchantId().equals(merchantId)&&inst.getMktResId().equals(productId)){
                    proNbSet.add(inst.getMktResInstNbr());
                    if(ResourceConst.STATUSCD.AVAILABLE.getCode().equals(inst.getStatusCd())){
                        statusUseSet.add(inst.getMktResInstNbr());
                    }
                }
            }
        }

        for (String nbr : mktResInstNbr) {
            //串码不存在
            if(!allNbSet.contains(nbr)){
                ValidResourceInstItemResp error = new ValidResourceInstItemResp();
                error.setResultCode(ResourceConst.RESULE_CODE_FAIL);
                error.setMktResInstNbr(nbr);
                error.setResultMsg(constant.getNoResInst());
                errorList.add(error);
                continue;
            }
            //串码存在，与产品对应不上
            if(!proNbSet.contains(nbr)){
                ValidResourceInstItemResp error = new ValidResourceInstItemResp();
                error.setResultCode(ResourceConst.RESULE_CODE_FAIL);
                error.setMktResInstNbr(nbr);
                error.setResultMsg(constant.getPesInstMismatch());
                errorList.add(error);
                continue;
            }
            //串码存在，且对应的上产品,需要判断状态
            if(statusUseSet.contains(nbr)){
                ValidResourceInstItemResp suc = new ValidResourceInstItemResp();
                suc.setResultCode(ResourceConst.RESULE_CODE_SUCCESS);
                suc.setMktResInstNbr(nbr);
                suc.setResultMsg(constant.getPesInstCheckSuc());
                sucList.add(suc);
                continue;
            }

            //其他情况：状态不可用
            ValidResourceInstItemResp error = new ValidResourceInstItemResp();
            error.setResultCode(ResourceConst.RESULE_CODE_FAIL);
            error.setMktResInstNbr(nbr);
            error.setResultMsg(constant.getPesInstInvalid());
            errorList.add(error);

        }
        if (errorList.isEmpty()) {
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(sucList);
            return resultVO;
        }

        //校验失败
        resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
        resultVO.setResultData(errorList);
        return resultVO;

    }

    /**
     * 根据条件查询串码实列
     *
     * @param req
     * @return
     */
    public ResourceInstDTO getResourceInst(ResourceInstGetReq req) {
        return resourceInstMapper.getResourceInst(req);
    }

    /**
     * 根据条件查询串码实列
     *
     * @param req
     * @return
     */
    public List<ResourceInstDTO> getResourceInsts(ResourceInstsGetReq req) {
        return resourceInstMapper.getResourceInsts(req);
    }

    /**
     * 根据主键查询
     *
     * @param mktResInstId
     * @return
     */
    public ResourceInstDTO selectById(String mktResInstId) {
        ResourceInst t = resourceInstMapper.selectById(mktResInstId);
        if (null == t) {
            return null;
        }
        ResourceInstDTO dto = new ResourceInstDTO();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }

    /**
     * 根据查询主键集合串码实列
     *
     * @param idList
     * @return
     */
    public List<ResourceInstDTO> selectByIds(List<String> idList) {
        return resourceInstMapper.selectByIds(idList);
    }

    /**
     * 调拨批量查询
     *
     * @param req
     * @return
     */
    public List<ResourceInstListResp> getBatch(ResourceInstBatchReq req) {
        return resourceInstMapper.getBatch(req);
    }

    /**
     * 修改实例状态
     *
     * @param req
     * @return
     */
    public int batchUpdateInstState(ResourceInstUpdateReq req) {
        return resourceInstMapper.batchUpdateInstState(req);
    }

    /**
     * 根据串码查实例列表
     */
    public List<ResourceInstDTO> listInstsByNbr(String nbr) {
        return resourceInstMapper.listInstsByNbr(nbr);
    }

    /**
     * 根据条件查询出全量的串码实例
     * @param req
     * @return
     */
    public List<ResourceInstListResp> getExportResourceInstList(ResourceInstListReq req){
        return resourceInstMapper.getResourceInstList(req);
    }

    /**
     * 获取主键
     * @return
     */
    public String getPrimaryKey(){
        return resourceInstMapper.getPrimaryKey();
    }


    /**
     * 根据串码查询厂商仓库
     * @param nbr
     * @return
     */
    public String getMerchantStoreIdByNbr(String nbr){
        return resourceInstMapper.getMerchantStoreIdByNbr(nbr);
    }
}
