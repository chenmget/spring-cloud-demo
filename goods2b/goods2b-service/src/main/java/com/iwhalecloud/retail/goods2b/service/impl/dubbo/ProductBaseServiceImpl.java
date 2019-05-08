package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.*;
import com.iwhalecloud.retail.goods2b.entity.ProductBase;
import com.iwhalecloud.retail.goods2b.manager.ProdFileManager;
import com.iwhalecloud.retail.goods2b.manager.ProductBaseManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductExtService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductFlowService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.utils.DateUtil;
import com.iwhalecloud.retail.goods2b.utils.GenerateCodeUtil;
import com.iwhalecloud.retail.goods2b.utils.ReflectUtils;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class ProductBaseServiceImpl implements ProductBaseService {

    @Autowired
    private ProductBaseManager productBaseManager;
    @Autowired
    private ProdFileManager fileManager;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductExtService productExtService;
    @Reference
    private MerchantService merchantService;

    @Autowired
    private ProductFlowService productFlowService;



    @Override
    public ResultVO<ProductBaseGetResp> getProductBase(ProductBaseGetByIdReq req){
        return ResultVO.success(productBaseManager.getProductBase(req.getProductBaseId()));
    }

    @Override
    public ResultVO<Page<ProductBaseGetResp>> getProductBaseList(ProductBaseListReq req){
        Page<ProductBaseGetResp> pageResp = productBaseManager.getProductBaseList(req);
        List<ProductBaseGetResp> respList = pageResp.getRecords();
        for(ProductBaseGetResp dto : respList){
            MerchantGetReq merchantGetReq = new MerchantGetReq();
            merchantGetReq.setMerchantId(dto.getManufacturerId());
            ResultVO<MerchantDTO> result = merchantService.getMerchant(merchantGetReq);
            MerchantDTO merchantDTO = result.getResultData();
            dto.setManufacturerName(null != merchantDTO ? merchantDTO.getMerchantName() : null);

            Integer snCount = 0;
            ProductGetReq productGetReq = new ProductGetReq();
            productGetReq.setProductBaseId(dto.getProductBaseId());
            ResultVO<Page<ProductDTO>> selectProductVO = productService.selectProduct(productGetReq);
            if (selectProductVO.isSuccess() && null != selectProductVO.getResultData() && !CollectionUtils.isEmpty(selectProductVO.getResultData().getRecords())) {
                snCount = selectProductVO.getResultData().getRecords().size();
            }
            dto.setSnCount(snCount);
        }
        return ResultVO.success(pageResp);
    }


    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    @Override
    public ResultVO<String> addProductBase(ProductBaseAddReq req){
        log.info("ProductBaseServiceImpl.addProductBase,req={}", JSON.toJSONString(req));
        ProductBase t = new ProductBase();
        BeanUtils.copyProperties(req, t);
        Date now = new Date();
        t.setCreateDate(now);
        t.setUpdateDate(now);
        t.setIsDeleted(ProductConst.IsDelete.NO.getCode());
        // 产品编码后端生成
        t.setProductCode(GenerateCodeUtil.generateCode());
        if (null == t.getEffDate()){
            t.setEffDate(now);
        }
        // 默认有效期三年
        if (null == t.getExpDate()){
            t.setExpDate(DateUtil.getNextYearTime(now, 3));
        }
        Integer num = productBaseManager.addProductBase(t);
        String productBaseId = t.getProductBaseId();
        // 添加产品拓展属性
        ProductExtAddReq pear = new ProductExtAddReq();
        BeanUtils.copyProperties(req, pear);
        pear.setProductBaseId(productBaseId);
        productExtService.addProductExt(pear);
        // 添加产品
        List<ProductAddReq> productAddReqs = req.getProductAddReqs();
        String status = "";
        Boolean addResult = true;
        if (null != productAddReqs && !productAddReqs.isEmpty()){
            for (ProductAddReq par : productAddReqs){
                status = par.getStatus();
                String auditState = ProductConst.AuditStateType.UN_SUBMIT.getCode();
                //除了待提交，都是审核中
                if(!ProductConst.StatusType.SUBMIT.getCode().equals(status)){
                    auditState =ProductConst.AuditStateType.AUDITING.getCode();
                    par.setStatus(ProductConst.StatusType.AUDIT.getCode());
                }
                par.setProductBaseId(productBaseId);
                par.setCreateStaff(t.getCreateStaff());
                par.setAuditState(auditState);
                productService.addProduct(par);
            }
        }

        //除了待提交，都是审核中,都要提交审核
        if(!ProductConst.StatusType.SUBMIT.getCode().equals(status) && addResult){
            StartProductFlowReq startProductFlowReq= new StartProductFlowReq();
            startProductFlowReq.setProductBaseId(productBaseId);
            startProductFlowReq.setDealer(t.getCreateStaff());
            startProductFlowReq.setProductName(req.getProductName());
            startProductFlowReq.setProcessId(ProductConst.APP_PRODUCT_FLOW_PROCESS_ID);
            startProductFlowReq.setParamsType(WorkFlowConst.TASK_PARAMS_TYPE.STRING_PARAMS.getCode());
            startProductFlowReq.setParamsValue(t.getBrandId());
            ResultVO flowResltVO = productFlowService.startProductFlow(startProductFlowReq);
            if(!flowResltVO.isSuccess()){
                throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), flowResltVO.getResultMsg());
            }
        }
        return  ResultVO.success(productBaseId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO<Integer> updateProductBase(ProductBaseUpdateReq req) {
        log.info("ProductBaseServiceImpl.updateProductBase,req={}", JSON.toJSONString(req));

        final long startTime = System.currentTimeMillis();
        ProductBaseGetByIdReq req1 = new ProductBaseGetByIdReq();
        req1.setProductBaseId(req.getProductBaseId());
        ResultVO<ProductBaseGetResp> product = this.getProductBase(req1);
        if (product==null||product.getResultData() == null) {
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "产品不存在");
        }
        ProductExtUpdateReq extUpdateReq = req.getProductExtUpdateReq();
        String notCheckField = "productBaseId";
        Boolean isAllFieldNull = ReflectUtils.isAllFieldNull(extUpdateReq, notCheckField);
        ProductExtGetReq productExtGetReq = new ProductExtGetReq();
        productExtGetReq.setProductBaseId(req.getProductBaseId());
        ResultVO<ProductExtGetResp> productExtVO = productExtService.getProductExt(productExtGetReq);
        if (!isAllFieldNull && productExtVO.isSuccess() && null != productExtVO.getResultData()) {
            ResultVO<Integer>  updateVO = productExtService.updateProductExt(extUpdateReq);
            log.info("ProductBaseServiceImpl.updateProductBase productExtService.updateProductExt,req={}", JSON.toJSONString(extUpdateReq));
        }else if(!isAllFieldNull && productExtVO.isSuccess() && null == productExtVO.getResultData()){
            ProductExtAddReq productExtAddReq = new ProductExtAddReq();
            BeanUtils.copyProperties(extUpdateReq, productExtAddReq);
            ResultVO<Integer> addVO = productExtService.addProductExt(productExtAddReq);
            log.info("ProductBaseServiceImpl.updateProductBase productExtService.addProductExt,req={}", JSON.toJSONString(productExtAddReq));
        }
        //原审核状态
        String oldAuditState = "";
        List<ProductUpdateReq> productUpdateReqs = req.getProductUpdateReqs();
        //获取产品
        ProductGetReq productGetReq = new ProductGetReq();
        productGetReq.setProductBaseId(req.getProductBaseId());
        productGetReq.setPageNo(1);
        productGetReq.setPageSize(Integer.MAX_VALUE);
        ResultVO<Page<ProductDTO>> productListResult =  productService.selectProduct(productGetReq);
        if(productListResult==null||!productListResult.isSuccess()||productListResult.getResultData()==null){
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "原产品为空无法获取审核状态");
        }
        Page<ProductDTO> page = productListResult.getResultData();
        if(page==null||page.getRecords()==null||page.getRecords().isEmpty()){
            throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "原产品为空无法获取审核状态");
        }
        oldAuditState = page.getRecords().get(0).getAuditState();
        if(StringUtils.isEmpty(oldAuditState)){
            oldAuditState = ProductConst.AuditStateType.UN_SUBMIT.getCode();
        }
        String newState = "";
        for (ProductUpdateReq productUpdateReq : productUpdateReqs) {
            String productId = productUpdateReq.getProductId();
            String isDeleted = productUpdateReq.getIsDeleted();
            String state = productUpdateReq.getStatus();
            newState = state;
            if (ProductConst.IsDelete.YES.getCode().equals(isDeleted) && StringUtils.isNotBlank(productId)) {
                PrdoProductDeleteReq prdoProductDeleteReq = new PrdoProductDeleteReq();
                prdoProductDeleteReq.setProductId(productId);
                productService.updateProdProductDelete(prdoProductDeleteReq);
                continue;
            }
            if(ProductConst.IsDelete.NO.getCode().equals(isDeleted) && StringUtils.isNotBlank(productId)){
                productUpdateReq.setAuditState(ProductConst.AuditStateType.UN_SUBMIT.getCode());
                productUpdateReq.setStatus(ProductConst.StatusType.SUBMIT.getCode());
                if(!ProductConst.StatusType.SUBMIT.getCode().equals(state)){
                    productUpdateReq.setAuditState(ProductConst.AuditStateType.AUDITING.getCode());
                    productUpdateReq.setStatus(ProductConst.StatusType.AUDIT.getCode());
                }

                productService.updateProdProduct(productUpdateReq);
                continue;
            }
            if(StringUtils.isBlank(productId)){
                ProductAddReq par = new ProductAddReq();
                BeanUtils.copyProperties(productUpdateReq, par);
                par.setProductBaseId(req.getProductBaseId());
                par.setCreateStaff(req.getUpdateStaff());
                par.setAuditState(ProductConst.AuditStateType.UN_SUBMIT.getCode());
                par.setStatus(ProductConst.StatusType.SUBMIT.getCode());
                if(!ProductConst.StatusType.SUBMIT.getCode().equals(state)){
                    par.setAuditState(ProductConst.AuditStateType.AUDITING.getCode());
                    par.setStatus(ProductConst.StatusType.AUDIT.getCode());
                }
                ResultVO<Integer> addResultVO = productService.addProduct(par);
                if (!addResultVO.isSuccess() || addResultVO.getResultData() < 1) {
                    throw new RetailTipException(addResultVO.getResultCode(), addResultVO.getResultMsg());
                }
                continue;
            }
        }
        req.setUpdateDate(new Date());
        int index = productBaseManager.updateProductBase(req);
        //修改成功并且非审核中
        if(index>0&&!ProductConst.AuditStateType.AUDITING.getCode().equals(oldAuditState)){

            //非待提交的都是待审核，需要启动流程
             if(!ProductConst.StatusType.SUBMIT.getCode().equals(newState)){
                 //审核不通过的则重启流程
                 if(ProductConst.AuditStateType.AUDIT_UN_PASS.getCode().equals(oldAuditState)){
                     StartProductFlowReq startProductFlowReq = new StartProductFlowReq();
                     startProductFlowReq.setProductBaseId(req.getProductBaseId());
                     startProductFlowReq.setDealer(req.getUpdateStaff());
                     ResultVO flowResltVO =productFlowService.reStartProductFlow(startProductFlowReq);
                     if(!flowResltVO.isSuccess()){
                         throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), flowResltVO.getResultMsg());
                     }
                 }else if(ProductConst.AuditStateType.UN_SUBMIT.getCode().equals(oldAuditState)
                         || ProductConst.AuditStateType.AUDIT_PASS.getCode().equals(oldAuditState)){
                     //原审核状态为待提交，且新状态为非待提交
                     String processId =ProductConst.APP_PRODUCT_FLOW_PROCESS_ID;
                     if(ProductConst.AuditStateType.AUDIT_PASS.getCode().equals(oldAuditState)){
                         processId =ProductConst.UPDATE_PRODUCT_FLOW_PROCESS_ID;
                     }
                     StartProductFlowReq startProductFlowReq = new StartProductFlowReq();
                     startProductFlowReq.setProductBaseId(req.getProductBaseId());
                     startProductFlowReq.setDealer(req.getUpdateStaff());
                     startProductFlowReq.setProductName(product.getResultData().getProductName());
                     startProductFlowReq.setProcessId(processId);
                     ResultVO flowResltVO =productFlowService.startProductFlow(startProductFlowReq);
                     if(!flowResltVO.isSuccess()){
                         throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), flowResltVO.getResultMsg());
                     }
                 }
             }
        }
        return  ResultVO.success(index);
    }

    @Override
    public ResultVO<Integer> deleteProdProductBase(ProdProductBaseDeleteReq req){
        return ResultVO.success(productBaseManager.deleteProdProductBase(req.getProductBaseId()));
    }


    @Override
    public ResultVO<Integer> softDelProdProductBase(ProdProductBaseSoftDelReq req){
        return ResultVO.success(productBaseManager.softDelProdProductBase(req.getProductBaseId()));
    }

    @Override
    public ResultVO<List<ProductBaseGetResp>> selectProductBase(ProductBaseGetReq req){
        return ResultVO.success(productBaseManager.selectProductBase(req));
    }

    /**
     * 产品详情
     * @param req
     * @return
     */
    @Override
    public ResultVO<ProductDetailResp> getProductDetail(ProductDetailGetByBaseIdReq req){
        ProductDetailResp productDetail = productBaseManager.getProductDetail(req.getProductBaseId());
        if (null == productDetail){
            return ResultVO.success(productDetail);
        }
        ProductGetReq productGetReq = new ProductGetReq();
        productGetReq.setProductBaseId(req.getProductBaseId());
        ResultVO<Page<ProductDTO>> resultVO = productService.selectProduct(productGetReq);
        Page<ProductDTO> page = resultVO.getResultData();
        List<ProductDTO> list = page.getRecords();
        productDetail.setProductAddReqs(list);

        ProductExtGetReq productExtGetReq = new ProductExtGetReq();
        productExtGetReq.setProductBaseId(req.getProductBaseId());
        ResultVO<ProductExtGetResp> productExtVO = productExtService.getProductExt(productExtGetReq);
        if (null != productExtVO && productExtVO.isSuccess() && null != productExtVO.getResultData()) {
            ProductExtGetResp productExtGetResp = productExtVO.getResultData();
            BeanUtils.copyProperties(productExtGetResp, productDetail);
        }

        MerchantGetReq merchantGetReq = new MerchantGetReq();
        merchantGetReq.setMerchantId(productDetail.getManufacturerId());
        ResultVO<MerchantDTO> result = merchantService.getMerchant(merchantGetReq);
        MerchantDTO dto = result.getResultData();
        productDetail.setManufacturerName(null!=dto? dto.getMerchantName():null);

        return ResultVO.success(productDetail);
    }

    @Override
    public ResultVO<ExchangeObjectGetResp> getExchangeObject(ProductExchangeObjectGetReq req) {
        ProductGetByIdReq req1 = new ProductGetByIdReq();
        req1.setProductId(req.getProductId());
        ResultVO<ProductResp> respResultVO = productService.getProduct(req1);
        if (!respResultVO.isSuccess() || respResultVO.getResultData() == null) {
            return ResultVO.error("查询产品信息为空");
        }
        ProductResp productResp = respResultVO.getResultData();
        ProductBaseGetResp productBaseGetResp = productBaseManager.getProductBase(productResp.getProductBaseId());
        if (productBaseGetResp == null) {
            return ResultVO.error("查询产品基本信息为空");
        }
        ExchangeObjectGetResp resp = new ExchangeObjectGetResp();
        resp.setExchangeObject(productBaseGetResp.getExchangeObject());
        resp.setManufacturerId(productBaseGetResp.getManufacturerId());
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<Boolean> updateAvgApplyPrice(ProductBaseUpdateReq req) {
        String productBaseId = req.getProductBaseId();
        if (productBaseId == null || productBaseId == "") {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        Double avgSupplyPrice = productBaseManager.getAvgSupplyPrice(productBaseId);
        log.info("ProductBaseServiceImpl.updateAvgApplyPrice productBaseId={},avgSupplyPrice={}",productBaseId,avgSupplyPrice);
        if (avgSupplyPrice == null) {
            return ResultVO.success(true);
        }
        ProductBaseUpdateReq productBaseUpdateReq = new ProductBaseUpdateReq();
        productBaseUpdateReq.setProductBaseId(productBaseId);
        productBaseUpdateReq.setAvgSupplyPrice(avgSupplyPrice);
        productBaseManager.updateProductBase(productBaseUpdateReq);
        return ResultVO.success(true);
    }

}