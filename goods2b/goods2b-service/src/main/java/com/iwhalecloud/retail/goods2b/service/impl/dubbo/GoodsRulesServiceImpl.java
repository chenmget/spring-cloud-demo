package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.busiservice.GoodsRulesProductService;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.common.GoodsResultCodeEnum;
import com.iwhalecloud.retail.goods2b.common.GoodsRulesConst;
import com.iwhalecloud.retail.goods2b.dto.*;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsRuleByExcelFileReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsRuleEditReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductObjReq;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsRulesExcelResp;
import com.iwhalecloud.retail.goods2b.entity.Goods;
import com.iwhalecloud.retail.goods2b.manager.GoodsManager;
import com.iwhalecloud.retail.goods2b.manager.GoodsRulesManager;
import com.iwhalecloud.retail.goods2b.manager.ProductManager;
import com.iwhalecloud.retail.goods2b.dto.req.CheckRuleReq;
import com.iwhalecloud.retail.goods2b.reference.BusinessEntityReference;
import com.iwhalecloud.retail.goods2b.reference.MerchantReference;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRulesService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.utils.ResultVOUtils;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Service
public class GoodsRulesServiceImpl implements GoodsRulesService {
    @Autowired
    private GoodsRulesManager goodsRulesManager;

    @Autowired
    private ProductManager productManager;

    @Resource
    private BusinessEntityReference businessEntityReference;

    @Resource
    private MerchantReference merchantReference;

    @Autowired
    private ProductService productService;

    @Resource
    private GoodsManager goodsManager;

    @Autowired
    private GoodsRulesProductService goodsRulesProductService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Override
    public ResultVO getGoodsRulesByExcel(ProdGoodsRuleByExcelFileReq prodGoodsRuleByExcelFileReq) throws Exception{
        byte[] excelFileBytes = prodGoodsRuleByExcelFileReq.getExcelFileBytes();
        InputStream excelFileIs = new ByteArrayInputStream(excelFileBytes);
        GoodsRulesExcelResp resp = new GoodsRulesExcelResp();
        List<GoodsRulesDTO> dtoList = genGoodsRulesByExcel(null,excelFileIs,resp);
        for (GoodsRulesDTO entity :dtoList){
            goodsRulesProductService.supplyTargetInfo(entity);
            supplyProductInfo(entity);
        }
        return ResultVO.success(dtoList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<GoodsRulesExcelResp> addProdGoodsRuleByExcelFile(ProdGoodsRuleByExcelFileReq prodGoodsRuleByExcelFileReq) throws Exception {
        String goodsId = prodGoodsRuleByExcelFileReq.getGoodsId();
        byte[] excelFileBytes = prodGoodsRuleByExcelFileReq.getExcelFileBytes();
        InputStream excelFileIs = new ByteArrayInputStream(excelFileBytes);
        GoodsRulesExcelResp resp = new GoodsRulesExcelResp();
        List<GoodsRulesDTO> dtoList = genGoodsRulesByExcel(goodsId,excelFileIs,resp);
        for (GoodsRulesDTO entity :dtoList){
            goodsRulesProductService.supplyTargetInfo(entity);
            supplyProductInfo(entity);
        }

        //校验配置的分货规则对象是否存在交叉记录
        ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq.setGoodsRulesDTOList(dtoList);
        ResultVO<Boolean> validResultVO = overlappingRecordsValid(prodGoodsRuleEditReq);
        if (!validResultVO.isSuccess() || !validResultVO.getResultData()) {
            resp.setOperateMessage(validResultVO.getResultMsg());
            resp.setOperateResult(false);
            return ResultVOUtils.genQueryResultVO(resp);
        }

        int effRow = goodsRulesManager.addOrUpdateBatch(dtoList);
        resp.setEffectRow(effRow);
        if (resp.getTotalRow().equals(resp.getEffectRow())){
            resp.setOperateResult(true);
        } else {
            resp.setOperateResult(false);
        }
        return ResultVOUtils.genQueryResultVO(resp);
    }

    /**
     * 通过excel生成商品分货规则DTO
     * @param goodsId
     * @param excelFileIs
     * @param resp
     * @return
     * @throws Exception
     */
    private List<GoodsRulesDTO> genGoodsRulesByExcel(String goodsId,InputStream excelFileIs,GoodsRulesExcelResp resp) throws Exception {
        List<GoodsRulesDTO> dtoList = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(excelFileIs);
        Sheet sheet = workbook.getSheetAt(0);
        int rowNumTotal = sheet.getLastRowNum();
        resp.setTotalRow(rowNumTotal);
        boolean isQualified;
        //String lineSeparator = System.getProperty("line.separator");
        StringBuffer errorMsgSb = new StringBuffer();
        for (int i=1;i<=rowNumTotal;i++){// 从第二行开始获取
            Row row = sheet.getRow(i);
            GoodsRulesDTO entity = new GoodsRulesDTO();
            entity.setGoodsId(goodsId);
            isQualified = true;
            for (int j=0;j<row.getLastCellNum();j++){
                Cell cell = row.getCell(j);
                if (null == cell){
                    break;
                }
                cell.setCellType(CellType.STRING);
                String value = cell.getStringCellValue();
                if (0 == j){
                    if (!value.equals(GoodsRulesConst.Stockist.BUSINESS_ENTITY_TYPE.getValue())
                            && !value.equals(GoodsRulesConst.Stockist.PARTNER_IN_SHOP_TYPE.getValue())){
                        //setValidateWarning(workbook,cell);
                        errorMsgSb.append(genErrorMsg(i,j,"不支持该类型目标对象"));
                        isQualified = false;
                        break;
                    }
                    entity.setTargetType(value);
                } else if (1 == j){
                    entity.setTargetCode(value);
                    if (!goodsRulesProductService.supplyTargetInfo(entity)){
                        //setValidateWarning(workbook,cell);
                        errorMsgSb.append(genErrorMsg(i,j,"该目标对象不存在"));
                        isQualified = false;
                        break;
                    }
                } else if (2 == j){
                    entity.setProductCode(value);
                    if (!supplyProductInfo(entity)){
                        //setValidateWarning(workbook,cell);
                        errorMsgSb.append(genErrorMsg(i,j,"该产品不存在"));
                        isQualified = false;
                        break;
                    }
                } else if (3 == j){
                    try{
                        entity.setMarketNum(Long.parseLong(value));
                    } catch(NumberFormatException e){
                        //setValidateWarning(workbook,cell);
                        errorMsgSb.append(genErrorMsg(i,j,"所输入的数量非法"));
                        isQualified = false;
                        break;
                    }
                }
            }
            if (isQualified){// 存在不合格的列不进行插入
                dtoList.add(entity);
            }
        }
        resp.setOperateMessage(errorMsgSb.toString());
        return dtoList;
    }

    /**
     * 生成错误信息
     * @param row
     * @param column
     * @param errorReason
     * @return
     */
    private String genErrorMsg(int row,int column,String errorReason){
        StringBuffer sb = new StringBuffer();
        sb.append("第" + row + "行,");
        sb.append("第" + column + "列,");
        sb.append(errorReason + ";");
        return sb.toString();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<GoodsRulesExcelResp> addProdGoodsRuleBatch(ProdGoodsRuleEditReq prodGoodsRuleEditReq ){
        log.info("gs_10010_addProdGoodsRuleBatch req={}",prodGoodsRuleEditReq);
        List<GoodsRulesDTO> entityList = prodGoodsRuleEditReq.getGoodsRulesDTOList();

        /**
         * 补充产品信息，分货对象信息
         */
        QueryProductObjReq queryReq=new QueryProductObjReq();
        queryReq.setDtoList(entityList);
        queryReq.setAssignedType(prodGoodsRuleEditReq.getAssignType());
        queryReq.setProductIds(prodGoodsRuleEditReq.getProductIds());
        goodsRulesProductService.queryProductObj(queryReq);

        GoodsRulesExcelResp resp = new GoodsRulesExcelResp();
        //校验配置的分货规则对象是否存在交叉记录
        ProdGoodsRuleEditReq prodGoodsRuleEditReq1 = new ProdGoodsRuleEditReq();
        prodGoodsRuleEditReq1.setGoodsRulesDTOList(entityList);
        prodGoodsRuleEditReq1.setAssignType(prodGoodsRuleEditReq.getAssignType());
        ResultVO<Boolean> validResultVO = overlappingRecordsValid(prodGoodsRuleEditReq1);
        if (!validResultVO.isSuccess() || !validResultVO.getResultData()) {
            ResultVO<GoodsRulesExcelResp> rv = new ResultVO<GoodsRulesExcelResp>();
            rv.setResultMsg(validResultVO.getResultMsg());
            rv.setResultCode(validResultVO.getResultCode());
            resp.setOperateMessage(validResultVO.getResultMsg());
            resp.setOperateResult(false);
            return rv;
        }

        int effRow = goodsRulesManager.addOrUpdateBatch(entityList);
        resp.setTotalRow(null!=entityList?entityList.size():0);
        resp.setEffectRow(effRow);
        if (resp.getTotalRow().equals(resp.getEffectRow())){
            resp.setOperateResult(true);
        } else {
            resp.setOperateResult(false);
        }
        return ResultVOUtils.genQueryResultVO(resp);
    }

    @Override
    public ResultVO addProdGoodsRule(GoodsRulesDTO entity){
        goodsRulesProductService.supplyTargetInfo(entity);
        supplyProductInfo(entity);
        return ResultVOUtils.genAduResultVO(goodsRulesManager.addOrUpdateOne(entity));
    }

    @Override
    public ResultVO deleteProdGoodsRuleBatch( ProdGoodsRuleEditReq prodGoodsRuleEditReq) {
        List<String> idList = prodGoodsRuleEditReq.getIdList();
        return ResultVOUtils.genAduResultVO(goodsRulesManager.deleteBatch(idList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO deleteProdGoodsRule(ProdGoodsRuleEditReq prodGoodsRuleEditReq) {
        String id = prodGoodsRuleEditReq.getId();
        GoodsRulesDTO goodsRulesDTO = goodsRulesManager.queryById(id);
        if (goodsRulesDTO != null) {
            Goods goods = new Goods();
            goods.setGoodsId(goodsRulesDTO.getGoodsId());
            goods.setIsAllot(GoodsConst.IsAllotEnum.IS_NOT_ALLOT.getCode());
            goodsManager.updateByPrimaryKey(goods);
        }
        return ResultVOUtils.genAduResultVO(goodsRulesManager.deleteOne(id));
    }

    @Override
    public ResultVO deleteProdGoodsRuleByCondition(GoodsRulesDTO condition) {
        return ResultVOUtils.genAduResultVO(goodsRulesManager.deleteByCondition(condition));
    }

    @Override
    public ResultVO updateProdGoodsRuleByCondition(GoodsRulesDTO condition){
        goodsRulesProductService.supplyTargetInfo(condition);
        supplyProductInfo(condition);
        condition.setGoodsRuleId(null);// 不允许更改主键ID
        return ResultVOUtils.genAduResultVO(goodsRulesManager.updateByCondtion(condition));
    }

    @Override
    public ResultVO updateProdGoodsRuleById(GoodsRulesDTO condition){
        goodsRulesProductService.supplyTargetInfo(condition);
        supplyProductInfo(condition);
        return ResultVOUtils.genAduResultVO(goodsRulesManager.updateById(condition));
    }

    @Override
    public ResultVO<GoodsRulesDTO> queryProdGoodsRuleById(ProdGoodsRuleEditReq prodGoodsRuleEditReq) {
        String id = prodGoodsRuleEditReq.getId();
        return ResultVOUtils.genQueryResultVO(goodsRulesManager.queryById(id));
    }

    @Override
    public ResultVO<List<GoodsRulesProductDTO>> queryProdGoodsRuleByCondition(GoodsRulesDTO condition) {
        List<GoodsRulesDTO> list=goodsRulesManager.listByConditon(condition);
        List<GoodsRulesProductDTO> ruleList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            QueryProductObjReq req=new QueryProductObjReq();
            req.setProductBaseId(list.get(0).getProductBaseId());
            req.setDtoList(list);
            ruleList.addAll(goodsRulesProductService.queryProductObj(req));
        }
        return ResultVOUtils.genQueryResultVO(ruleList);
    }

    /**
     * 购买时的分货规则校验
     * @param prodGoodsRuleEditReq
     * @return
     */
    @Override
    public ResultVO<Boolean> checkGoodsProdRule( ProdGoodsRuleEditReq prodGoodsRuleEditReq) {
       List<GoodsRulesOperateDTO> conditionList = prodGoodsRuleEditReq.getGoodsRulesOperateDTOList();
        log.info("GoodsRulesServiceImpl.checkGoodsProdRule conditionList={}", JSON.toJSONString(conditionList));
        if (conditionList == null) {
            return ResultVO.success(true);
        }

        for (GoodsRulesOperateDTO dto : conditionList) {
             String goodsRuleTargetType=null;
            //1、如果是零售商，先根据零售商校验分货规则
            if (PartnerConst.MerchantTypeEnum.PARTNER.getType().equals(dto.getMerchantType())) {
                goodsRuleTargetType = GoodsRulesConst.Stockist.PARTNER_IN_SHOP_TYPE.getValue();
             //地包商校验权限
            }else if(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(dto.getMerchantType())){
                goodsRuleTargetType = GoodsRulesConst.Stockist.DB_CODE.getValue();
            }
            List<GoodsRulesDTO> goodsRulesDTOs = goodsRulesManager.queryGoodsRules(dto.getGoodsId(), null, goodsRuleTargetType, dto.getMerchantId());
            ResultVO<Boolean> checkRuleResultVO = validGoodsRule(goodsRulesDTOs,dto.getDrawNum());
            if (!checkRuleResultVO.isSuccess() || !checkRuleResultVO.getResultData()) {
                return checkRuleResultVO;
            }

            //2、根据商家归属的经营主体校验规则
            MerchantDTO merchantDTO =  merchantReference.getMerchantById(dto.getMerchantId());
            if (merchantDTO == null) {
                log.warn("GoodsRulesServiceImpl.checkGoodsProdRule get business entity error,MerchantId={}",dto.getMerchantId());
                ResultVO resultVO = new ResultVO();
                resultVO.setResultCode(GoodsResultCodeEnum.GOODS_RULE_VALID_FAIL.getCode());
                resultVO.setResultMsg("获取经营主体异常");
                return ResultVO.success(false);
            }
            String businessEntityId = merchantDTO.getBusinessEntityId();
            if (StringUtils.isEmpty(businessEntityId)) {
                log.error("GoodsRulesServiceImpl.checkGoodsProdRule-->merchantReference.getMerchantById businessEntityId is null,merchantId={},merchantDTO={}"
                        , dto.getMerchantId(), JSON.toJSONString(merchantDTO));
                continue;
            }
            goodsRuleTargetType = GoodsRulesConst.Stockist.BUSINESS_ENTITY_TYPE.getValue();
            goodsRulesDTOs = goodsRulesManager.queryGoodsRules(dto.getGoodsId(), null, goodsRuleTargetType, businessEntityId);
            ResultVO<Boolean> resultVO = validGoodsRule(goodsRulesDTOs,dto.getDrawNum());
            if (!resultVO.isSuccess()) {
                return resultVO;
            }
        }

        return ResultVO.success(true);
    }


    /**
     * 校验分货规则
     * @param goodsRulesDTOs
     * @param buyCount
     * @return
     */
    private ResultVO<Boolean> validGoodsRule(List<GoodsRulesDTO> goodsRulesDTOs,Long buyCount) {
        for (GoodsRulesDTO goodsRulesDTO : goodsRulesDTOs) {

            final Long markerNum = goodsRulesDTO.getMarketNum()==null?0L:goodsRulesDTO.getMarketNum();
            final Long purchasedNum = goodsRulesDTO.getPurchasedNum()==null?0L:goodsRulesDTO.getPurchasedNum();
            //如果当前购买数量大于 （分货规则减去已提货的数量）
            if (buyCount > (markerNum-purchasedNum)) {
                log.info("GoodsRulesServiceImpl.checkGoodsProdRule goodsRulesDTO={}",JSON.toJSONString(goodsRulesDTO));
                String tips = String.format("%s商品超出最大购买数量，当前购买数量%s,分货规则配置数量%s，已购买数量%s",goodsRulesDTO.getProductName(),buyCount
                        ,goodsRulesDTO.getMarketNum(),goodsRulesDTO.getPurchasedNum());

                log.info(tips);
                ResultVO resultVO = new ResultVO();
                resultVO.setResultCode(GoodsResultCodeEnum.GOODS_RULE_VALID_FAIL.getCode());
                resultVO.setResultMsg(tips);
                resultVO.setResultData(false);
                return resultVO;
            }
        }

        return ResultVO.success(true);
    }

    @Override
    public ResultVO<Boolean> raiseMarketNumDiffer(ProdGoodsRuleEditReq prodGoodsRuleEditReq){
        List<GoodsRulePurchaseDTO> goodsRulePurchaseDTOs = prodGoodsRuleEditReq.getGoodsRulePurchaseDTOs();
        if (CollectionUtils.isEmpty(goodsRulePurchaseDTOs)) {
            return ResultVO.success(true);
        }

        for (GoodsRulePurchaseDTO dto : goodsRulePurchaseDTOs) {
            Goods goods = goodsManager.queryGoods(dto.getGoodsId());
            //如果不是分货商品
            if (goods==null || goods.getIsAllot()!=1) {
                continue;
            }

            //1、根据零售商，或者地包商 修改提货数量
            final String targetTypePartner = GoodsRulesConst.Stockist.PARTNER_IN_SHOP_TYPE.getValue();
            goodsRulesManager.updatePurchaseNum(dto.getGoodsId(),null,null,dto.getMerchantId(),dto.getPurchasedNum());

            //2、根据经营主体修改提货数量
            MerchantDTO merchantDTO = merchantReference.getMerchantById(dto.getMerchantId());
            if (merchantDTO == null) {
                log.error("raiseMarketNumDiffer-->merchantReference.getMerchantById merchantDTO is null,merchantId={},merchantDTO={}"
                        , dto.getMerchantId(), JSON.toJSONString(merchantDTO));
                continue;
            }
            final String businessEntityId = merchantDTO.getBusinessEntityId();
            if (StringUtils.isEmpty(businessEntityId)) {
                log.error("raiseMarketNumDiffer-->merchantReference.getMerchantById businessEntityId is null,merchantId={},merchantDTO={}"
                        , dto.getMerchantId(), JSON.toJSONString(merchantDTO));
                continue;
            }
            final String targetTypeBusinessEntity = GoodsRulesConst.Stockist.BUSINESS_ENTITY_TYPE.getValue();
            goodsRulesManager.updatePurchaseNum(dto.getGoodsId(),null,targetTypeBusinessEntity,businessEntityId,dto.getPurchasedNum());
        }

        return ResultVO.success(true);
    }

    @Override
    public ResultVO deleteGoodsRuleByGoodsId(ProdGoodsRuleEditReq prodGoodsRuleEditReq) {
        String goodsId = prodGoodsRuleEditReq.getGoodsId();
        GoodsRulesDTO condition = new GoodsRulesDTO();
        condition.setGoodsId(goodsId);
        return ResultVOUtils.genAduResultVO(goodsRulesManager.deleteByCondition(condition));
    }

    /**
     * 补充产品信息
     * @param entity
     */
    private boolean supplyProductInfo(GoodsRulesDTO entity){
        String code = entity.getProductCode();
        ProductGetReq req = new ProductGetReq();
        req.setSn(code);
        Page<ProductDTO> resultPage = productService.selectProduct(req).getResultData();
        List<ProductDTO> resultList = null!=resultPage ? resultPage.getRecords():null;
        if (null != resultList && resultList.size()>0){
            entity.setProductId(resultList.get(0).getProductId());
            entity.setProductName(resultList.get(0).getUnitName());
            return true;
        }
        return false;
    }

    /**
     * 校验分货规则配置对象的经营主体和零售商是否存在交叉记录，比如配置了经营主体又同时配置了经营主体下的零售商
     * @param prodGoodsRuleEditReq
     * @return
     */
    @Override
    public ResultVO<Boolean> overlappingRecordsValid(ProdGoodsRuleEditReq prodGoodsRuleEditReq) {
        List<GoodsRulesDTO> goodsRulesDTOs = prodGoodsRuleEditReq.getGoodsRulesDTOList();
        if (goodsRulesDTOs==null || goodsRulesDTOs.size()<=0) {
            return ResultVO.success(true);
        }

        Map<String,List<GoodsRulesDTO>> productGroupDtos=null;
        if(GoodsConst.DIS_PRODUCT_TYPE_1.equals(prodGoodsRuleEditReq.getAssignType())){
           productGroupDtos =  goodsRulesDTOs.stream().collect(Collectors.groupingBy(GoodsRulesDTO::getProductBaseId));

        }else{
           productGroupDtos =  goodsRulesDTOs.stream().collect(Collectors.groupingBy(GoodsRulesDTO::getProductCode));
        }
        Iterator<String> it = productGroupDtos.keySet().iterator();
        while (it.hasNext()) {
            String productBaseId = it.next();
            List<GoodsRulesDTO> productGoodsRulesDto = productGroupDtos.get(productBaseId);
            ResultVO<Boolean> rv = overlappingRecordsValidByProductCode(productBaseId,productGoodsRulesDto);
            if (!rv.isSuccess() || !rv.getResultData()) {
                return rv;
            }
        }


        return ResultVO.success(true);
    }


    /**
     * 根据产品编码校验分货规则
     * @param productCode
     * @param goodsRulesDTOs
     * @return
     */
    private ResultVO<Boolean> overlappingRecordsValidByProductCode(String productCode,List<GoodsRulesDTO> goodsRulesDTOs) {

        //获取经营主体的code集合
        List<String> businessEntityCodes = goodsRulesDTOs.stream()
                .filter(a -> GoodsRulesConst.Stockist.BUSINESS_ENTITY_TYPE.getValue().equals(a.getTargetType()))
                .map(GoodsRulesDTO::getTargetCode).collect(Collectors.toList());

        //获取零售商的code集合
        List<String> merchantCodes = goodsRulesDTOs.stream()
                .filter(a -> GoodsRulesConst.Stockist.PARTNER_IN_SHOP_TYPE.getValue().equals(a.getTargetType()))
                .map(GoodsRulesDTO::getTargetCode).collect(Collectors.toList());

        //如果上传的只有经营主体或者只有零售商类型，则不用验证
        if (CollectionUtils.isEmpty(businessEntityCodes) || CollectionUtils.isEmpty(merchantCodes)) {
            return ResultVO.success(true);
        }

        //根据商家编码集合和经营主体集合查询记录
        ResultVO<List<MerchantDTO>> merchantRV = merchantReference.queryMerchant(merchantCodes, businessEntityCodes);
        if (!merchantRV.isSuccess()) {
            ResultVO<Boolean> resultVO = new ResultVO<Boolean>();
            resultVO.setResultCode(merchantRV.getResultCode());
            resultVO.setResultMsg(merchantRV.getResultMsg());
            resultVO.setResultData(false);
            return resultVO;
        }

        //如果查询到有记录则说明存在交叉记录
        if (!CollectionUtils.isEmpty(merchantRV.getResultData())) {
            ResultVO<Boolean> resultVO = new ResultVO<Boolean>();
            resultVO.setResultCode(GoodsResultCodeEnum.GOODS_RULE_VALID_FAIL_EXIST_OVERLAPPING_RECORDS.getCode());
            final String tips = "产品编码["+ productCode +"]" + GoodsResultCodeEnum.GOODS_RULE_VALID_FAIL_EXIST_OVERLAPPING_RECORDS.getDesc();
            log.info("GoodsRulesServiceImpl.overlappingRecordsValidByProductCode tips={},goodsRulesDTOs={}",tips,JSON.toJSONString(goodsRulesDTOs));
            resultVO.setResultMsg(tips);
            resultVO.setResultData(false);

            return resultVO;
        }
        return ResultVO.success(true);
    }

    /**
     * 分货规则入库校验
     * @return
     */
    @Override
    public ResultVO checkGoodsRules(CheckRuleReq req){
        log.info("gs_10010_checkGoodsRules,req={}",JSON.toJSONString(req));
        if (CollectionUtils.isEmpty(req.getEntityList())) {
            //每个产品规格必须有一条分货规则
            return ResultVO.error("每个产品规格必须有一条分货规则");
        }

        /**
         * 校验同一个商品，不能存在两个一样的分货对象
         */
        Map<String,List<GoodsRulesDTO>> productGroupDtos = req.getEntityList().stream().collect(Collectors.groupingBy(GoodsRulesDTO::getTargetCode));
        Iterator<String> objRule = productGroupDtos.keySet().iterator();
        while (objRule.hasNext()) {
            String objCode = objRule.next();
            List<GoodsRulesDTO> productGoodsRulesDto = productGroupDtos.get(objCode);
            if(productGoodsRulesDto.size()>1){
                return ResultVO.error("不能存在两个一样的分货对象,Code="+objCode);
            }
        }

        ResultVO resultVO;
        if (req.getAssignType() == null) {
            req.setAssignType(GoodsConst.DIS_PRODUCT_TYPE_2);
        }

        switch (req.getAssignType()) {
            //按商品校验
            case GoodsConst.DIS_PRODUCT_TYPE_1:
                resultVO = goodsRulesProductService.checkRuleByGoods(req);
                break;
            //默认按规格校验
            default:
                resultVO = goodsRulesProductService.checkRuleByProduct(req);
                break;
        }
        return resultVO;
    }

    @Override
    public ResultVO<List<GoodsRulesProductDTO>> queryProductObj(QueryProductObjReq req) {

        List<GoodsRulesProductDTO> passGoodsLsit=goodsRulesProductService.queryProductObj(req);
        if(CollectionUtils.isEmpty(passGoodsLsit)){
            ResultVO.error("未查询到产品数据");
        }
        return ResultVO.success(passGoodsLsit);
    }

    @Override
    public ResultVO checkObj(CheckRuleReq req) {
        if(CollectionUtils.isEmpty(req.getTargetList()) && StringUtils.isEmpty(req.getSupplierId())){
            ResultVO.error("对象和供应商id不能为空");
        }
        return goodsRulesProductService.checkMerchant(req.getSupplierId(),req.getTargetList());
    }


}
