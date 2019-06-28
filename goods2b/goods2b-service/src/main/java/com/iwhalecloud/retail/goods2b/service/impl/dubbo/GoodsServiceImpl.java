package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.AttrSpecConst;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.common.GoodsResultCodeEnum;
import com.iwhalecloud.retail.goods2b.dto.*;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.*;
import com.iwhalecloud.retail.goods2b.entity.*;
import com.iwhalecloud.retail.goods2b.exception.GoodsRulesException;
import com.iwhalecloud.retail.goods2b.helper.AttrSpecHelper;
import com.iwhalecloud.retail.goods2b.manager.*;
import com.iwhalecloud.retail.goods2b.mapper.GoodsProductRelMapper;
import com.iwhalecloud.retail.goods2b.service.dubbo.*;
import com.iwhalecloud.retail.goods2b.utils.CurrencyUtil;
import com.iwhalecloud.retail.goods2b.utils.ResultVOUtils;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.partner.service.SupplierService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActivityProductDTO;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityQueryByGoodsReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingReliefActivityQueryResp;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.UserCollectionDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserCollectionJudgeReq;
import com.iwhalecloud.retail.system.dto.request.UserCollectionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.system.service.UserCollectionService;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.warehouse.dto.request.GetProductQuantityByMerchantReq;
import com.iwhalecloud.retail.warehouse.dto.request.ProductQuantityItem;
import com.iwhalecloud.retail.warehouse.dto.response.GetProductQuantityByMerchantResp;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.NextRouteAndReceiveTaskReq;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component("goodsService")
@Service(parameters = {"queryGoodsForPage.timeout", "300000", "queryPageByConditionAdmin.timeout", "200000"})
public class GoodsServiceImpl implements GoodsService {

    @Value("${goodsRule.goodsMktPrice}")
    private Double GOODS_MKT_PRICE;

    @Value("${goodsRule.avgSupllyPrice}")
    private Double AVG_SUPPLY_PRICE;

    @Autowired
    private GoodsManager goodsManager;

    @Autowired
    private GoodsRegionRelManager goodsRegionRelManager;

    @Autowired
    private ProdFileManager prodFileManager;

    @Autowired
    private GoodsActRelManager goodsActRelManager;

    @Autowired
    private GoodsProductRelManager goodsProductRelManager;

    @Autowired
    private GoodsActRelManager getGoodsActRelManager;

    @Reference
    private SupplierService supplierService;

    @Autowired
    private AttrSpecManager attrSpecManager;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private GoodsRegionRelManager getGoodsRegionRelManager;

    @Autowired
    private GoodsTargetManager goodsTargetManager;

    @Autowired
    private CatManager catManager;

    @Autowired
    private TagRelService tagRelService;

    @Autowired
    private BrandManager brandManager;

    @Autowired
    private TagsService tagsService;

    @Reference
    private MerchantService merchantService;

    @Autowired
    private ProdFileManager fileManager;

    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @Reference
    private UserCollectionService userCollectionService;

    @Autowired
    private ProductBaseManager productBaseManager;

    @Autowired
    private TagsManager tagsManager;

    @Reference
    private TaskService taskService;

    @Reference
    private ProductBaseService productBaseService;

    @Reference
    private CommonRegionService commonRegionService;

    @Reference
    private MarketingActivityService marketingActivityService;

    @Reference
    private ActivityProductService activityProductService;

    @Autowired
    private GoodsRulesService goodsRulesService;

    @Reference
    private UserService userService;

    @Autowired
    private TypeManager typeManager;

    @Autowired
    private GoodsProductRelMapper goodsProductRelMapper;

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 添加商品发布地市关联记录
     *
     * @param goodsId
     * @param regionReq
     */
    private void saveGoodsRegionRel(String goodsId, RegionReq regionReq) {
        // 添加商品发布地市关联记录(增加 org_id、org_name两个字段后）
        GoodsRegionRel goodsRegionRel = new GoodsRegionRel();
        BeanUtils.copyProperties(regionReq, goodsRegionRel);
        goodsRegionRel.setGoodsId(goodsId);
        goodsRegionRelManager.saveGoodsRegionRel(goodsRegionRel);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<GoodsAddResp> addGoods(GoodsAddReq req) throws GoodsRulesException {
        log.info("GoodsServiceImpl.addGoods req={}", JSON.toJSONString(req));
        Goods goods = new Goods();
        BeanUtils.copyProperties(req, goods);
        if (GoodsConst.AuditStateEnum.WAIT_SUBMIT.getCode().equals(req.getAuditState())) {
            goods.setAuditState(GoodsConst.AuditStateEnum.WAIT_SUBMIT.getCode());
        } else {
            if (GoodsConst.IsAllotEnum.IS_ALLOT.getCode().equals(req.getIsAllot())) {
                goods.setAuditState(GoodsConst.AuditStateEnum.AUDITING.getCode());
            } else {
                goods.setAuditState(GoodsConst.AuditStateEnum.AUDITED.getCode());
            }
        }
        log.info("GoodsServiceImpl.addGoods goods={}", JSON.toJSONString(goods));
        goodsManager.addGoods(goods);

        //添加分货规则
        if (GoodsConst.IsAllotEnum.IS_ALLOT.getCode().equals(req.getIsAllot()) &&
                CollectionUtils.isNotEmpty(req.getEntityList())) {
            ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
            prodGoodsRuleEditReq.setGoodsRulesDTOList(req.getEntityList());
            ResultVO checkResult = goodsRulesService.checkGoodsRules(req.getEntityList(), req.getGoodsProductRelList(), req.getSupplierId());
            log.info("GoodsServiceImpl.addGoods   checkResult={}", checkResult);
            if (checkResult.isSuccess()) {
                try {
                    goodsRulesService.addProdGoodsRuleBatch(prodGoodsRuleEditReq);
                } catch (Exception e) {
                    log.info("添加分货规则失败");
                }
            } else {
                List<String> errors = new ArrayList<String>(1);
                errors.add(checkResult.getResultMsg());
                throw new GoodsRulesException(errors);
            }
            log.info("添加分货规则成功");
        }

        String goodsId = goods.getGoodsId();
        String targetType = req.getTargetType();
        if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_REGION.equals(targetType)) {
            List<RegionReq> regionList = req.getRegionList();
            if (CollectionUtils.isNotEmpty(regionList)) {
                for (RegionReq regionReq : regionList) {
                    // 添加商品发布地市关联记录
//                    goodsRegionRelManager.addGoodsRegionRel(goodsId, regionReq.getRegionId(), regionReq.getRegionName(), regionReq.getLanId());
                    // 添加商品发布地市关联记录(增加 org_id、org_name两个字段后）
                    saveGoodsRegionRel(goodsId, regionReq);
                }
            }
        } else if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_TARGET.equals(targetType)) {
            List<GoodsTargetReq> goodsTargetReqList = req.getTargetList();
            if (CollectionUtils.isNotEmpty(goodsTargetReqList)) {
                for (GoodsTargetReq goodsTargetReq : goodsTargetReqList) {
                    // 添加商品发布对象关联记录
                    goodsTargetManager.addGoodsTargerRel(goodsId, goodsTargetReq.getTargetId());
                }
            }
        }
        log.info("添加商品关联关系成功");

        // 添加产商品关联
        List<GoodsProductRelDTO> goodsProductRelDTOList = req.getGoodsProductRelList();
        if (CollectionUtils.isNotEmpty(goodsProductRelDTOList)) {
            for (GoodsProductRelDTO item : goodsProductRelDTOList) {
                GoodsProductRel goodsProductRel = new GoodsProductRel();
                BeanUtils.copyProperties(item, goodsProductRel);
                goodsProductRel.setGoodsId(goodsId);
                goodsProductRelManager.addGoodsProductRel(goodsProductRel);
            }
        }
        log.info("添加产商品关联关系成功");
        // 添加图片
        List<FileAddReq> fileAddReqList = req.getFileAddReqList();
        addImageFile(goodsId, fileAddReqList);
        // 添加零售商标签
//        List<String> tagList = req.getTagList();
//        if (CollectionUtils.isNotEmpty(tagList)) {
//            TagRelBatchAddReq relBatchAddReq = new TagRelBatchAddReq();
//            relBatchAddReq.setGoodsId(goodsId);
//            relBatchAddReq.setTagList(tagList);
//            tagRelService.batchAddTagRel(relBatchAddReq);
//        }
        log.info("添加图片、标签成功");

        //如果有配置活动信息
        if (!CollectionUtils.isEmpty(req.getGoodsActs())) {
            goodsActRelManager.addGoodsActRel(goodsId, req.getGoodsActs());
        }
        log.info("添加活动信息成功");

        GoodsAddResp resp = new GoodsAddResp();
        resp.setGoodsId(goodsId);

        if (GoodsConst.IsAllotEnum.IS_ALLOT.getCode().equals(req.getIsAllot())
                && !GoodsConst.AuditStateEnum.WAIT_SUBMIT.getCode().equals(req.getAuditState())) {
            ProcessStartReq processStartDTO = new ProcessStartReq();
            processStartDTO.setFormId(goodsId);
            processStartDTO.setTitle("分货商品审核流程");
            processStartDTO.setProcessId("3");
            processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1130.getTaskSubType());
            processStartDTO.setTitle("商品审核");
            processStartDTO.setApplyUserId(req.getUserId());
            processStartDTO.setApplyUserName(req.getUserName());
            ResultVO<CommonRegionDTO> resultVOLan = ResultVO.error();
            ResultVO<CommonRegionDTO> resultVORegion = ResultVO.error();
            if (req.getLanId() != null) {
                resultVOLan = commonRegionService.getCommonRegionById(req.getLanId());
            }
            if (req.getRegionId() != null) {
                resultVORegion = commonRegionService.getCommonRegionById(req.getRegionId());
            }
            String lanName = "";
            String regionName = "";
            if (resultVOLan.isSuccess() && resultVOLan.getResultData() != null) {
                lanName = resultVOLan.getResultData().getRegionName();
            }
            if (resultVORegion.isSuccess() && resultVORegion.getResultData() != null) {
                regionName = resultVORegion.getResultData().getRegionName();
            }
            String extends1 = lanName + "" + regionName;
            processStartDTO.setExtends1(extends1);
            ResultVO taskServiceRV = new ResultVO();
            try {
                taskServiceRV = taskService.startProcess(processStartDTO);
            } catch (Exception e) {
                return ResultVO.error();
            } finally {
                log.info("GoodsServiceImpl.addGoods req={},resp={}",
                        JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
            }
        }

        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<GoodsAddResp> addGoodsByZT(GoodsAddByZTReq req) {
        log.info("GoodsServiceImpl.addGoodsByZT req={}", JSON.toJSONString(req));
        Goods goods = new Goods();
        BeanUtils.copyProperties(req, goods);
        log.info("GoodsServiceImpl.addGoodsByZT goods={}", JSON.toJSONString(goods));
        goodsManager.addGoods(goods);
        String goodsId = goods.getGoodsId();
        String targetType = req.getTargetType();
        if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_REGION.equals(targetType)) {
            List<RegionReq> regionList = req.getRegionList();
            if (CollectionUtils.isNotEmpty(regionList)) {
                for (RegionReq regionReq : regionList) {
                    // 添加商品发布地市关联记录
//                    goodsRegionRelManager.addGoodsRegionRel(goodsId, regionReq.getRegionId(), regionReq.getRegionName(), regionReq.getLanId());
                    // 添加商品发布地市关联记录(增加 org_id、org_name两个字段后）
                    saveGoodsRegionRel(goodsId, regionReq);
                }
            }
        } else if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_TARGET.equals(targetType)) {
            List<GoodsTargetReq> goodsTargetReqList = req.getTargetList();
            if (CollectionUtils.isNotEmpty(goodsTargetReqList)) {
                for (GoodsTargetReq goodsTargetReq : goodsTargetReqList) {
                    // 添加商品发布对象关联记录
                    goodsTargetManager.addGoodsTargerRel(goodsId, goodsTargetReq.getTargetId());
                }
            }
        }

        // 添加产商品关联
        List<GoodsProductRelDTO> goodsProductRelDTOList = req.getGoodsProductRelList();
        if (CollectionUtils.isNotEmpty(goodsProductRelDTOList)) {
            for (GoodsProductRelDTO item : goodsProductRelDTOList) {
                GoodsProductRel goodsProductRel = new GoodsProductRel();
                BeanUtils.copyProperties(item, goodsProductRel);
                goodsProductRel.setGoodsId(goodsId);
                goodsProductRelManager.addGoodsProductRel(goodsProductRel);
            }
        }
        // 添加图片
        List<FileAddReq> fileAddReqList = req.getFileAddReqList();
        addImageFile(goodsId, fileAddReqList);
        // 添加零售商标签
//        List<String> tagList = req.getTagList();
//        if (CollectionUtils.isNotEmpty(tagList)) {
//            TagRelBatchAddReq relBatchAddReq = new TagRelBatchAddReq();
//            relBatchAddReq.setGoodsId(goodsId);
//            relBatchAddReq.setTagList(tagList);
//            tagRelService.batchAddTagRel(relBatchAddReq);
//        }

        //如果有配置活动信息
        if (!CollectionUtils.isEmpty(req.getGoodsActs())) {
            goodsActRelManager.addGoodsActRel(goodsId, req.getGoodsActs());
        }

        GoodsAddResp resp = new GoodsAddResp();
        resp.setGoodsId(goodsId);
        return ResultVO.success(resp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<GoodsOperateResp> editGoods(GoodsEditReq req) throws GoodsRulesException {
        log.info("GoodsServiceImpl.editGoods req={}", JSON.toJSONString(req));
        Goods goods = new Goods();
        BeanUtils.copyProperties(req, goods);
        // 修改商品表记录
        if (GoodsConst.IsAllotEnum.IS_NOT_ALLOT.getCode().equals(req.getIsAllot())
                && !GoodsConst.AuditStateEnum.WAIT_SUBMIT.getCode().equals(req.getAuditState())) {
            goods.setAuditState(GoodsConst.AuditStateEnum.AUDITED.getCode());
        } else if (GoodsConst.IsAllotEnum.IS_NOT_ALLOT.getCode().equals(req.getIsAllot())
                && GoodsConst.AuditStateEnum.WAIT_SUBMIT.getCode().equals(req.getAuditState())) {
            goods.setAuditState(null);
        } else if (GoodsConst.IsAllotEnum.IS_ALLOT.getCode().equals(req.getIsAllot())
                && !GoodsConst.AuditStateEnum.WAIT_SUBMIT.getCode().equals(req.getAuditState())) {
            goods.setAuditState(GoodsConst.AuditStateEnum.AUDITING.getCode());
        } else {
            goods.setAuditState(null);
        }
        goodsManager.updateByPrimaryKey(goods);
        String goodsId = req.getGoodsId();
        String targetType = req.getTargetType();

        //添加分货规则
        if (GoodsConst.IsAllotEnum.IS_ALLOT.getCode().equals(req.getIsAllot()) &&
                CollectionUtils.isNotEmpty(req.getEntityList())) {
            ProdGoodsRuleEditReq prodGoodsRuleEditReq = new ProdGoodsRuleEditReq();
            prodGoodsRuleEditReq.setGoodsRulesDTOList(req.getEntityList());
            ResultVO checkResult = goodsRulesService.checkGoodsRules(req.getEntityList(), req.getGoodsProductRelList(), req.getSupplierId());
            log.info("GoodsServiceImpl.editGoods   checkResult={}", checkResult);
            if (checkResult.isSuccess()) {
                try {
                    goodsRulesService.addProdGoodsRuleBatch(prodGoodsRuleEditReq);
                } catch (Exception e) {
                    log.info("添加分货规则失败");
                }
            } else {
                List<String> errors = new ArrayList<String>(1);
                errors.add(checkResult.getResultMsg());
                throw new GoodsRulesException(errors);
            }
        }
        log.info("添加分货规则成功");


        if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_REGION.equals(targetType)) {
            List<RegionReq> regionList = req.getRegionList();
            if (CollectionUtils.isNotEmpty(regionList)) {
                // 删除该商品发布地市关联记录
                goodsRegionRelManager.delGoodsRegionRelByGoodsId(goodsId);
                goodsTargetManager.deleteGoodsTargerRel(goodsId);
                for (RegionReq regionReq : regionList) {
                    // 添加商品发布地市关联记录
//                    goodsRegionRelManager.addGoodsRegionRel(goodsId, regionReq.getRegionId(), regionReq.getRegionName(), regionReq.getLanId());
                    // 添加商品发布地市关联记录(增加 org_id、org_name两个字段后）
                    saveGoodsRegionRel(goodsId, regionReq);
                }
            }
        } else if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_TARGET.equals(targetType)) {
            List<GoodsTargetReq> goodsTargetReqList = req.getTargetList();
            if (CollectionUtils.isNotEmpty(goodsTargetReqList)) {
                goodsRegionRelManager.delGoodsRegionRelByGoodsId(goodsId);
                goodsTargetManager.deleteGoodsTargerRel(goodsId);
                for (GoodsTargetReq goodsTargetReq : goodsTargetReqList) {
                    // 添加商品发布对象关联记录
                    goodsTargetManager.addGoodsTargerRel(goodsId, goodsTargetReq.getTargetId());
                }
            }
        }
        log.info("添加发布地市关联关系成功");

        // 修改产商品关联
        List<GoodsProductRelDTO> goodsProductRelDTOList = req.getGoodsProductRelList();
        if (CollectionUtils.isNotEmpty(goodsProductRelDTOList)) {
            goodsProductRelManager.delGoodsProductRelByGoodsId(goodsId);
            for (GoodsProductRelDTO item : goodsProductRelDTOList) {
                GoodsProductRel goodsProductRel = new GoodsProductRel();
                BeanUtils.copyProperties(item, goodsProductRel);
                goodsProductRelManager.addGoodsProductRel(goodsProductRel);
            }
        }
        log.info("添加产商品关联关系成功");

        // 修改商品图片前先删除再新增
        prodFileManager.deleteByGoodsId(goodsId);
        // 添加图片
        List<FileAddReq> fileAddReqList = req.getFileAddReqList();
        addImageFile(goodsId, fileAddReqList);
        // 添加零售商标签前先删除再新增
//        List<String> tagList = req.getTagList();
//        if (CollectionUtils.isNotEmpty(tagList)) {
//            TagRelDeleteByGoodsIdReq relDeleteByGoodsIdReq = new TagRelDeleteByGoodsIdReq();
//            relDeleteByGoodsIdReq.setGoodsId(goodsId);
//            tagRelService.deleteTagRelByGoodsId(relDeleteByGoodsIdReq);
//            TagRelBatchAddReq relBatchAddReq = new TagRelBatchAddReq();
//            relBatchAddReq.setTagList(tagList);
//            relBatchAddReq.setGoodsId(goodsId);
//            tagRelService.batchAddTagRel(relBatchAddReq);
//        }
        log.info("添加商品图片成功");

        //如果有配置活动信息
        if (!CollectionUtils.isEmpty(req.getGoodsActs())) {
            goodsActRelManager.editGoodsActRel(goodsId, req.getGoodsActs());
        }
        log.info("添加配置活动信息成功");

        GoodsOperateResp resp = new GoodsOperateResp();
        resp.setResult(true);

        GoodsQueryReq goodsQueryReq = new GoodsQueryReq();
        goodsQueryReq.setGoodsId(req.getGoodsId());
        goodsQueryReq.setIsLogin(true);
        goodsQueryReq.setUserId(req.getUserId());
        ResultVO<GoodsDetailResp> resultVO = queryGoodsDetail(goodsQueryReq);
        GoodsDetailResp goodsDetailResp = resultVO.getResultData();
        if (!GoodsConst.AuditStateEnum.WAIT_SUBMIT.getCode().equals(req.getAuditState())) {

            if (GoodsConst.AuditStateEnum.AUDITING.getCode().equals(goodsDetailResp.getAuditState()) && GoodsConst.IsAllotEnum.IS_ALLOT.getCode().equals(req.getIsAllot())) {
                ProcessStartReq processStartDTO = new ProcessStartReq();
                processStartDTO.setFormId(goodsId);
                processStartDTO.setTitle("分货商品审核流程");
                processStartDTO.setProcessId("3");
                processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_1130.getTaskSubType());
                processStartDTO.setTitle("商品审核测试");
                processStartDTO.setApplyUserId(req.getUserId());
                processStartDTO.setApplyUserName(req.getUserName());
                ResultVO<CommonRegionDTO> resultVOLan = ResultVO.error();
                ResultVO<CommonRegionDTO> resultVORegion = ResultVO.error();
                if (req.getLanId() != null) {
                    resultVOLan = commonRegionService.getCommonRegionById(req.getLanId());
                }
                if (req.getRegionId() != null) {
                    resultVORegion = commonRegionService.getCommonRegionById(req.getRegionId());
                }
                String lanName = "";
                String regionName = "";
                if (resultVOLan.isSuccess() && resultVOLan.getResultData() != null) {
                    lanName = resultVOLan.getResultData().getRegionName();
                }
                if (resultVORegion.isSuccess() && resultVORegion.getResultData() != null) {
                    regionName = resultVORegion.getResultData().getRegionName();
                }
                String extends1 = lanName + "" + regionName;
                processStartDTO.setExtends1(extends1);
                ResultVO taskServiceRV = new ResultVO();
                try {
                    taskServiceRV = taskService.startProcess(processStartDTO);
                } catch (Exception e) {
                    return ResultVO.error();
                } finally {
                    log.info("GoodsServiceImpl.editGoods req={},resp={}",
                            JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
                }
            } else if (GoodsConst.AuditStateEnum.NOT_AUDITED.getCode().equals(req.getAuditState()) && GoodsConst.IsAllotEnum.IS_ALLOT.getCode().equals(req.getIsAllot())) {
                NextRouteAndReceiveTaskReq nextRouteAndReceiveTaskReq = new NextRouteAndReceiveTaskReq();
                nextRouteAndReceiveTaskReq.setFormId(req.getGoodsId());
                nextRouteAndReceiveTaskReq.setHandlerUserId(req.getUserId());
                nextRouteAndReceiveTaskReq.setHandlerMsg("商品驳回审核");
                GoodsAuditStateReq goodsAuditStateReq = new GoodsAuditStateReq();
                goodsAuditStateReq.setGoodsId(req.getGoodsId());
                goodsAuditStateReq.setAuditState(GoodsConst.AuditStateEnum.AUDITING.getCode());
                updateAuditState(goodsAuditStateReq);
                ResultVO taskServiceRV = new ResultVO();
                try {
                    taskServiceRV = taskService.nextRouteAndReceiveTask(nextRouteAndReceiveTaskReq);
                } catch (Exception e) {
                    return ResultVO.error();
                } finally {
                    log.info("GoodsServiceImpl.editGoods req={},resp={}",
                            JSON.toJSONString(nextRouteAndReceiveTaskReq), JSON.toJSONString(taskServiceRV));
                }
            }
        }
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<GoodsOperateResp> editGoodsByZT(GoodsEditByZTReq req) {
        log.info("GoodsServiceImpl.editGoods req={}", JSON.toJSONString(req));
        Goods goods = new Goods();
        BeanUtils.copyProperties(req, goods);
        // 修改商品表记录
        goodsManager.updateByPrimaryKey(goods);
        String goodsId = req.getGoodsId();
        String targetType = req.getTargetType();
        if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_REGION.equals(targetType)) {
            List<RegionReq> regionList = req.getRegionList();
            if (CollectionUtils.isNotEmpty(regionList)) {
                // 删除该商品发布地市关联记录
                goodsRegionRelManager.delGoodsRegionRelByGoodsId(goodsId);
                for (RegionReq regionReq : regionList) {
                    // 添加商品发布地市关联记录
//                    goodsRegionRelManager.addGoodsRegionRel(goodsId, regionReq.getRegionId(), regionReq.getRegionName(), regionReq.getLanId());
                    // 添加商品发布地市关联记录(增加 org_id、org_name两个字段后）
                    saveGoodsRegionRel(goodsId, regionReq);
                }
            }
        } else if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_TARGET.equals(targetType)) {
            List<GoodsTargetReq> goodsTargetReqList = req.getTargetList();
            if (CollectionUtils.isNotEmpty(goodsTargetReqList)) {
                goodsTargetManager.deleteGoodsTargerRel(goodsId);
                for (GoodsTargetReq goodsTargetReq : goodsTargetReqList) {
                    // 添加商品发布对象关联记录
                    goodsTargetManager.addGoodsTargerRel(goodsId, goodsTargetReq.getTargetId());
                }
            }
        }

        // 修改产商品关联
        List<GoodsProductRelDTO> goodsProductRelDTOList = req.getGoodsProductRelList();
        if (CollectionUtils.isNotEmpty(goodsProductRelDTOList)) {
            goodsProductRelManager.delGoodsProductRelByGoodsId(goodsId);
            for (GoodsProductRelDTO item : goodsProductRelDTOList) {
                GoodsProductRel goodsProductRel = new GoodsProductRel();
                BeanUtils.copyProperties(item, goodsProductRel);
                goodsProductRelManager.addGoodsProductRel(goodsProductRel);
            }
        }
        // 修改商品图片前先删除再新增
        List<FileAddReq> fileAddReqList = req.getFileAddReqList();
        if (CollectionUtils.isNotEmpty(fileAddReqList)) {
            prodFileManager.deleteByGoodsId(goodsId);
            // 添加图片
            addImageFile(goodsId, fileAddReqList);
        }

        // 添加零售商标签前先删除再新增
//        List<String> tagList = req.getTagList();
//        if (CollectionUtils.isNotEmpty(tagList)) {
//            TagRelDeleteByGoodsIdReq relDeleteByGoodsIdReq = new TagRelDeleteByGoodsIdReq();
//            relDeleteByGoodsIdReq.setGoodsId(goodsId);
//            tagRelService.deleteTagRelByGoodsId(relDeleteByGoodsIdReq);
//            TagRelBatchAddReq relBatchAddReq = new TagRelBatchAddReq();
//            relBatchAddReq.setTagList(tagList);
//            relBatchAddReq.setGoodsId(goodsId);
//            tagRelService.batchAddTagRel(relBatchAddReq);
//        }

        //如果有配置活动信息
        if (!CollectionUtils.isEmpty(req.getGoodsActs())) {
            goodsActRelManager.editGoodsActRel(goodsId, req.getGoodsActs());
        }

        GoodsOperateResp resp = new GoodsOperateResp();
        resp.setResult(true);
        return ResultVO.success(resp);
    }

    private void addImageFile(String goodsId, List<FileAddReq> fileAddReqList) {
        if (CollectionUtils.isNotEmpty(fileAddReqList)) {
            for (FileAddReq fileAddReq : fileAddReqList) {
                String fileUrl = fileAddReq.getFileUrl();
                // 型号视频
                if (FileConst.SubType.MODEL_VIDIO_SUB.getType().equals(fileAddReq.getSubType())) {
                    String thumbnailUrl = fileAddReq.getThumbnailUrl();
                    prodFileManager.addGoodsImage(goodsId, FileConst.SubType.MODEL_VIDIO_SUB, fileUrl, thumbnailUrl);
                }
                // 商品图片
                else if (FileConst.SubType.ROLL_SUB.getType().equals(fileAddReq.getSubType())) {
                    prodFileManager.addGoodsImage(goodsId, FileConst.SubType.ROLL_SUB, fileUrl);
                }
                // 缩略图图片
                else if (FileConst.SubType.THUMBNAILS_SUB.getType().equals(fileAddReq.getSubType())) {
                    prodFileManager.addGoodsImage(goodsId, FileConst.SubType.THUMBNAILS_SUB, fileUrl);
                }
                // 详情图片
                else if (FileConst.SubType.DETAIL_SUB.getType().equals(fileAddReq.getSubType())) {
                    prodFileManager.addGoodsImage(goodsId, FileConst.SubType.DETAIL_SUB, fileUrl);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<GoodsOperateResp> deleteGoods(GoodsDeleteReq req) {
        log.info("GoodsServiceImpl.deleteGoods req={}", JSON.toJSONString(req));
        int result = goodsManager.deleteGoodsByGoodsId(req.getGoodsId());
        GoodsOperateResp resp = new GoodsOperateResp();
        resp.setResult(result > 0);
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<Page<GoodsForPageQueryResp>> queryGoodsForPage(GoodsForPageQueryReq req) {
        log.info("GoodsServiceImpl.queryGoodsForPage req={}", JSON.toJSONString(req));
        List<AttrSpecValueReq> attrSpecValueReqList = req.getAttrSpecValueList();
        // 获取并设置属性字段名称F
        getFiledName(attrSpecValueReqList);
        Page<GoodsForPageQueryResp> goodsForPageQueryRespPage = goodsManager.queryGoodsForPage(req);
        // 按照零售商商品展示规则过滤
        long start = System.currentTimeMillis();

        // 新逻辑(如果用户是零售商，只能查到地包商品, 用户是地包商，只查省包商的商品 在controller层加这条件)：
        // 去掉商品过滤逻辑 zhongwenlong2019.06.28
//        filterGoods(req, goodsForPageQueryRespPage);


        long end = System.currentTimeMillis();
        log.info("filterGoods costTime={}:", end - start);
        List<GoodsForPageQueryResp> goodsDTOList = goodsForPageQueryRespPage.getRecords();
        if (CollectionUtils.isEmpty(goodsDTOList)) {
            Page<GoodsForPageQueryResp> page = new Page<>();
            page.setRecords(Lists.newArrayList());
            return ResultVO.success(page);
        }
        // 查询并设置供应商名称
        ResultVO resultVO = setSupplierName(goodsDTOList);
        if (!resultVO.isSuccess()) {
            return resultVO;
        }
        List<String> goodsIdList = goodsDTOList.stream().map(GoodsForPageQueryResp::getGoodsId).collect(Collectors.toList());
        // 查询并设置商品标签
        for (GoodsForPageQueryResp goods : goodsDTOList) {
            List<TagsDTO> tagsDTOList = tagsManager.getTagsByGoodsId(goods.getGoodsId());
            goods.setTagList(tagsDTOList);
        }
        // 查询并设置商品缩略图
        setGoodsImageUrl(goodsDTOList, goodsIdList);
        if (req.getIsLogin() != null && req.getIsLogin()) {
            // 查询并设置是否收藏及提货价
            setIsCollectionAndDeliveryPrice(req, goodsDTOList, goodsIdList);

        } else {
            // 用户未登录设置提货价为null
            goodsDTOList.forEach(item -> item.setDeliveryPrice(null));
        }
        return ResultVO.success(goodsForPageQueryRespPage);
    }

    /**
     * 1、零售价1599或以下，只展示地包商品，不展示省包商品。
     * 2、零售价1599以上的地包商品在哪些条件下不展示： 地包商品供货价大于省包平均供货价的3%
     * 3、零售价1599以上的省包商品在哪些条件下展示：
     * 1）所有地包商品供货价大于省包平均供货价的3%；
     * 2）所有供货价小于省包平均供货价3%的地包商品都没有上架量，且这些地包商品都没有配置前置补贴活动；
     * 省包商品满足条件1）或条件2）时展示。
     */
    private void filterGoods(GoodsForPageQueryReq req, Page<GoodsForPageQueryResp> goodsForPageQueryRespPage) {
        List<GoodsForPageQueryResp> goodsForPageQueryRespList = goodsForPageQueryRespPage.getRecords();
        // 按零售商商品展示规则过滤
        Iterator<GoodsForPageQueryResp> it = goodsForPageQueryRespList.iterator();
        while (it.hasNext()) {
            GoodsForPageQueryResp item = it.next();
            String goodsId = item.getGoodsId();
            Integer userFounder = req.getUserFounder();
            // 非零售商用户不进行商品过滤
            if (userFounder == null || userFounder != SystemConst.USER_FOUNDER_3) {
                log.info("GoodsServiceImpl.filterGoods() 非零售商用户 查询商品分页 跳过 商品过滤 userFounder={}", userFounder);
                continue;
            }
            // 是否省包商品标识
            boolean supplierProvinceFlag = PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(item.getMerchantType());
            if (item.getMktprice() > GOODS_MKT_PRICE) {
                boolean supplierGroundFlag = PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equals(item.getMerchantType());
                // 零售价1599以上的地包商品
                if (supplierGroundFlag) {
                    Double deliveryPrice = item.getDeliveryPrice();
                    String productBaseId = item.getProductBaseId();
                    ProductBaseGetResp productBaseGetResp = productBaseManager.getProductBase(productBaseId);
                    // 获取平均供货价
                    Double avgSupplyPrice = productBaseGetResp.getAvgSupplyPrice();
                    log.info("GoodsServiceImpl.filterGoods goodsId={} avgSupplyPrice={}", goodsId, avgSupplyPrice);
                    // 如果地包供货价高于省包平均供货价的3%，则不展示该地包商品
                    if (isAbove3Per(deliveryPrice, avgSupplyPrice)) {
                        log.info("GoodsServiceImpl.filterGoods filter avgSupplyPrice goodsId={}", goodsId);
                        it.remove();
                    }
                }
                // 省包商品：存在该机型的地包商品供货价小于省包平均供货价的3%且地包（有上架数量或者参与前置补贴活动）则不展示省包
                else if (supplierProvinceFlag) {
                    filterProvinceGoods(it, item, goodsId);
                }
            } else {
                // 零售价1599及以下机型只展示地包供货
                if (supplierProvinceFlag) {
                    it.remove();
                }
            }
        }
    }

    private void filterProvinceGoods(Iterator<GoodsForPageQueryResp> it, GoodsForPageQueryResp item, String goodsId) {
        String productBaseId = item.getProductBaseId();
        // 查询该机型包含的所有地包商品
        List<SupplierGroundGoodsDTO> supplierGroundGoodsDTOList = goodsManager.listSupplierGroundRelative(productBaseId);
        if (CollectionUtils.isEmpty(supplierGroundGoodsDTOList)) {
            return;
        }
        ProductBaseGetResp productBaseGetResp = productBaseManager.getProductBase(productBaseId);
        // 获取平均供货价
        Double avgSupplyPrice = productBaseGetResp.getAvgSupplyPrice();
        // 通过goodsId进行分组
        Map<String, List<SupplierGroundGoodsDTO>> map = supplierGroundGoodsDTOList.stream().collect(Collectors.groupingBy(
                SupplierGroundGoodsDTO::getGoodsId));
        for (Map.Entry<String, List<SupplierGroundGoodsDTO>> entry : map.entrySet()) {
            // 获取goodsId
            String goodsIdKey = entry.getKey();
            List<SupplierGroundGoodsDTO> supplierGroundGoodsDTOS = entry.getValue();
            List<String> productIdList = Lists.newArrayList();
            List<Long> supplyNumList = Lists.newArrayList();
            List<Double> deliveryPriceList = Lists.newArrayList();
            List<String> supplierIdList = Lists.newArrayList();
            supplierGroundGoodsDTOS.forEach(s -> {
                productIdList.add(s.getProductId());
                supplyNumList.add(s.getSupplyNum());
                deliveryPriceList.add(s.getDeliveryPrice());
                supplierIdList.add(s.getSupplierId());
            });
            Double minDeliveryPrice = Collections.min(deliveryPriceList);
            // 地包供货价低于省包平均供货价3%
            if (!isAbove3Per(minDeliveryPrice, avgSupplyPrice)) {
                boolean isSupplyNumAboveZero = false;
                for (Long supplyNum : supplyNumList) {
                    if (supplyNum > 0) {
                        isSupplyNumAboveZero = true;
                        break;
                    }
                }
                // 供货价小于省包平均供货价3%的地包商品上架数量大于0则不展示省包商品
                if (isSupplyNumAboveZero) {
                    log.info("GoodsServiceImpl.filterGoods filter isSupplyNumAboveZero filterGoodsId={},becauseGoodsId={}", goodsId, goodsIdKey);
                    it.remove();
                    return;
                }

                // 供货价小于省包平均供货价3%的地包商品配置了前置补贴活动则不展示省包
                boolean isPresubsidyGoodsFlag = filterPresubsidyGoods(productIdList);
                if (isPresubsidyGoodsFlag) {
                    log.info("GoodsServiceImpl.filterGoods filter activity goodsId={}", item.getGoodsId());
                    it.remove();
                    return;
                }
            }
        }
    }

    private boolean isAbove3Per(Double deliveryPrice, Double avgSupplyPrice) {
        return avgSupplyPrice != null && deliveryPrice > avgSupplyPrice && (deliveryPrice - avgSupplyPrice) / avgSupplyPrice >
                AVG_SUPPLY_PRICE;
    }

    private boolean filterPresubsidyGoods(List<String> productIdList) {
        boolean isPresubsidyGoodsFlag = false;
        if (!CollectionUtils.isEmpty(productIdList)) {
            for (String productId : productIdList) {
                String code = PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode();
                log.info("GoodsServiceImpl.filterPresubsidyGoods productId={}", productId);
                ResultVO<List<MarketingActivityDTO>> resultVO = marketingActivityService.queryActivityByProductId(productId, code);
                log.info("GoodsServiceImpl.filterPresubsidyGoods resultVO={}", JSON.toJSONString(resultVO.getResultData()));
                if (resultVO.isSuccess() && resultVO.getResultData().size() > 0) {
                    isPresubsidyGoodsFlag = true;
                    break;
                }
            }
        }
        return isPresubsidyGoodsFlag;
    }

    private void setIsCollectionAndDeliveryPrice(GoodsForPageQueryReq req, List<GoodsForPageQueryResp> goodsDTOList,
                                                 List<String> goodsIdList) {
        UserCollectionListReq userCollectionListReq = new UserCollectionListReq();
        userCollectionListReq.setUserId(req.getUserId());
        userCollectionListReq.setObjIds(goodsIdList);
        userCollectionListReq.setObjType(SystemConst.ObjTypeEnum.B2B_GOODS.getCode());

        // 获取卖家编码
        String merchantCode = "";
        log.info("GoodsServiceImpl.queryGoodsForPage getUserByUserId req={}", req.getUserId());
        UserDTO userDTO = userService.getUserByUserId(req.getUserId());
        if (null != userDTO) {
            merchantCode = userDTO.getRelCode();
//            MerchantGetReq merchantGetReq = new MerchantGetReq();
//            merchantGetReq.setMerchantId(userDTO.getRelCode());
//            log.info("GoodsServiceImpl.queryGoodsForPage getMerchant merchantGetReq={}", merchantGetReq);
//            ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchant(merchantGetReq);
//            if (merchantDTOResultVO.isSuccess() && null != merchantDTOResultVO.getResultData()) {
//                merchantCode = merchantDTOResultVO.getResultData().getMerchantCode();
//            }
        }
        // 查询商品是否收藏
        log.info("GoodsServiceImpl.queryGoodsForPage queryUserCollection userCollectionListReq={}", userCollectionListReq);
        ResultVO<List<UserCollectionDTO>> listResultVO = userCollectionService.queryUserCollection(userCollectionListReq);
        log.info("GoodsServiceImpl.queryGoodsForPage queryUserCollection listResultVO={}", listResultVO);
        // 查询展示的前置补贴价格
        List<UserCollectionDTO> userCollectionDTOList = listResultVO.getResultData();
        long startTime = System.currentTimeMillis();
        for (GoodsForPageQueryResp goods : goodsDTOList) {
            goods.setIsCollection(false);
            // 设置是否有收藏
            if (listResultVO.isSuccess() && CollectionUtils.isNotEmpty(userCollectionDTOList)) {
                for (UserCollectionDTO userCollection : userCollectionDTOList) {
                    if (goods.getGoodsId().equals(userCollection.getObjId())) {
                        goods.setIsCollection(true);
                        break;
                    }
                }
            }
            // 设置前置补贴价格
            goods.setDeliveryPrice(goods.getDeliveryPrice());
            goods.setIsPresubsidy(false);
            if (null != goods.getIsSubsidy()) {
                int isSubsidy = goods.getIsSubsidy();
                if (GoodsConst.IsSubsidy.IS_SUBSIDY.getCode() == isSubsidy) {
                    this.setPresubsidyPrice(goods.getProductId(), goods.getSupplierId(), merchantCode, goods);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("setPresubsidyPrice costTime={}:", endTime - startTime);
    }

    private void setGoodsImageUrl(List<GoodsForPageQueryResp> goodsDTOList, List<String> goodsIdList) {
        // 查询商品默认图片
        List<ProdFileDTO> prodFileDTOList = prodFileManager.queryGoodsImage(goodsIdList, FileConst.SubType.THUMBNAILS_SUB);

        if (CollectionUtils.isNotEmpty(prodFileDTOList)) {
            for (GoodsForPageQueryResp goods : goodsDTOList) {
                // 设置缩略图
                for (ProdFileDTO prodFileDTO : prodFileDTOList) {
                    if (goods.getGoodsId().equals(prodFileDTO.getTargetId())) {
                        ProdFileDTO prodFileDTOHD = prodFileManager.queryGoodsImageHD(goods.getGoodsId());//查看这个商品有没有活动图片
                        if (prodFileDTOHD != null) {
                            goods.setImageUrl(prodFileDTOHD.getFileUrl());
                            break;
                        } else {
                            goods.setImageUrl(prodFileDTO.getFileUrl());
                            break;
                        }
                    }
                }
            }
        }
    }

    private void setPresubsidyPrice(String productId, String supplierCode, String merchantCode, GoodsForPageQueryResp goods) {
        if (!StringUtils.isEmpty(productId) && !StringUtils.isEmpty(supplierCode)) {
            MarketingActivityQueryByGoodsReq marketingActivityQueryByGoodsReq = new MarketingActivityQueryByGoodsReq();
            marketingActivityQueryByGoodsReq.setProductId(productId);
            marketingActivityQueryByGoodsReq.setSupplierCode(supplierCode);
            marketingActivityQueryByGoodsReq.setMerchantCode(merchantCode);
            marketingActivityQueryByGoodsReq.setActivityType(PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode());
            log.info("GoodsServiceImpl.queryGoodsForPage listGoodsMarketingReliefActivitys marketingActivityQueryByGoodsReq={}", marketingActivityQueryByGoodsReq);
            long startTime = System.currentTimeMillis();
            ResultVO<List<MarketingReliefActivityQueryResp>> resultVO1 = marketingActivityService.listGoodsMarketingReliefActivitys(marketingActivityQueryByGoodsReq);
            long endTime = System.currentTimeMillis();
            log.info("setPresubsidyPrice costTime={}:", endTime - startTime);
            if (resultVO1.isSuccess() && CollectionUtils.isNotEmpty(resultVO1.getResultData())) {
                goods.setIsPresubsidy(true);
                List<MarketingReliefActivityQueryResp> marketingReliefActivityQueryResps = resultVO1.getResultData();
                Double deliveryPrice = goods.getDeliveryPrice();
                Double promotionPrice = 0.00;
                for (MarketingReliefActivityQueryResp marketingReliefActivityQueryResp : marketingReliefActivityQueryResps) {
                    promotionPrice = CurrencyUtil.add(promotionPrice, Double.valueOf(marketingReliefActivityQueryResp.getPromotionPrice()));
                }
                int i = deliveryPrice.compareTo(promotionPrice);
                if (i >= 0) {
                    goods.setPresubsidyPrice(CurrencyUtil.sub(deliveryPrice, promotionPrice));
                } else {
                    goods.setPresubsidyPrice(0.00);
                }
            }
        }
    }

    private void getFiledName(List<AttrSpecValueReq> attrSpecValueReqList) {
        if (CollectionUtils.isNotEmpty(attrSpecValueReqList)) {
            List<String> attrIds = attrSpecValueReqList.stream().map(AttrSpecValueReq::getAttrId).collect(Collectors.toList());
            List<AttrSpecDTO> attrSpecDTOList = attrSpecManager.selectBatchByIds(attrIds);
            for (AttrSpecDTO dto : attrSpecDTOList) {
                for (AttrSpecValueReq item : attrSpecValueReqList) {
                    if (dto.getAttrId().equals(item.getAttrId())) {
                        item.setFiledName(dto.getFiledName());
                        break;
                    }
                }
            }
        }
    }

    private ResultVO setSupplierName(List<GoodsForPageQueryResp> goodsDTOList) {
        List<String> supplierIds = goodsDTOList.stream().map(GoodsForPageQueryResp::getSupplierId).collect(Collectors.toList());
        try {
            MerchantListReq merchantListReq = new MerchantListReq();
            merchantListReq.setMerchantIdList(supplierIds);
            log.info("GoodsServiceImpl.queryGoodsForPage listMerchant merchantListReq={}", merchantListReq);
            ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchant(merchantListReq);
            log.info("GoodsServiceImpl.queryGoodsForPage listMerchant listResultVO={}", listResultVO);
            if (listResultVO.isSuccess() && !CollectionUtils.isEmpty(listResultVO.getResultData())) {
                List<MerchantDTO> merchantDTOList = listResultVO.getResultData();
                for (GoodsForPageQueryResp goods : goodsDTOList) {
                    for (MerchantDTO merchantDTO : merchantDTOList) {
                        if (merchantDTO.getMerchantId().equals(goods.getSupplierId())) {
                            goods.setSupplierName(merchantDTO.getMerchantName());
                            goods.setSupplierCode(merchantDTO.getMerchantCode());
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("GoodsServiceImpl.queryGoodsForPage getSupplierListByIds throw exception exception ex={}", ex);
            return ResultVO.error(GoodsResultCodeEnum.INVOKE_PARTNER_SERVICE_EXCEPTION);
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO<GoodsOperateResp> updateMarketEnable(GoodsMarketEnableReq goodsMarketEnableReq) {
        String goodsId = goodsMarketEnableReq.getGoodsId();
        Integer marketEnable = goodsMarketEnableReq.getMarketEnable();
        log.info("GoodsServiceImpl.updateMarketEnable goodsId={},marketEnable={}", goodsId, marketEnable);
        Goods goods = goodsManager.queryGoods(goodsId);
        if (goods == null) {
            return ResultVO.error("查询商品为空");
        }
        // 商品上架需要进行库存校验
        boolean flag = GoodsConst.MARKET_ENABLE.equals(marketEnable);
        if (marketEnable.equals(goods.getMarketEnable())) {
            if (flag) {
                return ResultVO.error("商品已经上架，请不要重复操作");
            } else {
                return ResultVO.error("商品已经下架，请不要重复操作");
            }
        }
        //预售商品
        boolean isAdvanceGoods = GoodsConst.IsAdvanceSale.IS_ADVANCE_SALE.getCode().equals(goods.getIsAdvanceSale());
        //预售商品不校验库存
        if (flag && !isAdvanceGoods) {
            GetProductQuantityByMerchantReq req = new GetProductQuantityByMerchantReq();
            req.setMerchantId(goods.getSupplierId());
            List<ProductQuantityItem> itemList = Lists.newArrayList();
            List<GoodsProductRel> goodsProductRelList = goodsProductRelManager.listGoodsProductRel(goodsId);
            if (CollectionUtils.isEmpty(goodsProductRelList)) {
                return ResultVO.error("查询产商品关联为空");
            }
            for (GoodsProductRel goodsProductRel : goodsProductRelList) {
                ProductQuantityItem item = new ProductQuantityItem();
                item.setProductId(goodsProductRel.getProductId());
                item.setNum(1L);
                itemList.add(item);
            }
            req.setItemList(itemList);
            try {
                log.info("GoodsServiceImpl.updateMarketEnable getProductQuantityByMerchant req={}", JSON.toJSONString(req));

                ResultVO<GetProductQuantityByMerchantResp> booleanResultVO = resourceInstStoreService.getProductQuantityByMerchant(req);
                log.info("GoodsServiceImpl.updateMarketEnable booleanResultVO={}", JSON.toJSONString(booleanResultVO));
                GetProductQuantityByMerchantResp merchantResp = booleanResultVO.getResultData();
                if (!booleanResultVO.isSuccess() || merchantResp == null || !merchantResp.getInStock()) {
//                    return ResultVO.error(GoodsResultCodeEnum.NOT_HAS_STOCK);
                } else {
                    GetProductQuantityByMerchantResp resultData = booleanResultVO.getResultData();
                    List<ProductQuantityItem> productQuantityItemList = resultData.getItemList();
                    if (productQuantityItemList == null && CollectionUtils.isEmpty(productQuantityItemList)) {
//                    return ResultVO.error(GoodsResultCodeEnum.NOT_HAS_STOCK);
                    } else {
                        // 更新该商品有库存字段
                        for (ProductQuantityItem item : productQuantityItemList) {
                            goodsProductRelManager.updateIsHaveStock(goods.getGoodsId(), item.getProductId(), item.getIsEnough());
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("GoodsServiceImpl.updateMarketEnable getProductQuantityByMerchant throw exception ex={}", ex);
                return ResultVO.error(GoodsResultCodeEnum.INVOKE_WAREHOUSE_SERVICE_EXCEPTION);
            }
        }
        boolean isSupplierProvinceFlag = PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(goods.getMerchantType());
        if (isSupplierProvinceFlag) {
            List<GoodsProductRel> goodsProductRelList = goodsProductRelManager.listGoodsProductRel(goodsId);
            if (CollectionUtils.isEmpty(goodsProductRelList)) {
                return ResultVO.error("查询产商品关联为空");
            }
            String productBaseId = goodsProductRelList.get(0).getProductBaseId();
            if (productBaseId == null) {
                return ResultVO.error("产品基本信息为空");
            }
        }
        int result = goodsManager.updateMarketEnableGoodsId(goodsId, marketEnable);
        GoodsOperateResp resp = new GoodsOperateResp();
        resp.setResult(result > 0);
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<GoodsOperateResp> updateAuditState(GoodsAuditStateReq goodsAuditStateReq) {
        String goodsId = goodsAuditStateReq.getGoodsId();
        String auditState = goodsAuditStateReq.getAuditState();
        log.info("GoodsServiceImpl.updateMarketEnable goodsId={},auditState={}", goodsId, auditState);
        int result = goodsManager.updateAuditStateByGoodsId(goodsId, auditState);
        GoodsOperateResp resp = new GoodsOperateResp();
        resp.setResult(result > 0);
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<Page<GoodsPageResp>> queryPageByConditionAdmin(GoodsPageReq req) {
        log.info("GoodsServiceImpl.queryPageByConditionAdmin req={}", req);
        // 筛选供应商
        if (StringUtils.isNotEmpty(req.getSupplierName()) || StringUtils.isNotEmpty(req.getSupplierLanId())) {
            MerchantListReq merchantListReq = new MerchantListReq();
            merchantListReq.setMerchantName(req.getSupplierName());
            merchantListReq.setLanId(req.getSupplierLanId());
            ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchant(merchantListReq);
            if (listResultVO.isSuccess() && null != listResultVO.getResultData()) {
                if (listResultVO.getResultData().size() > 0) {
                    List<String> supplierMerchantIds = listResultVO.getResultData().stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
                    req.setSupplierIds(supplierMerchantIds);
                    log.info("GoodsServiceImpl.queryPageByConditionAdmin 调用 merchantService.listMerchant() 查要过滤的供应商的结果 req={}， idList:",
                            JSON.toJSONString(req), JSON.toJSONString(supplierMerchantIds));
//                    String supplierId = listResultVO.getResultData().get(0).getMerchantId();
//                    if (StringUtils.isNotEmpty(supplierId)) {
//                        req.setSupplierId(supplierId);
//                    }
                } else {
                    Page<GoodsPageResp> pageRespPage = new Page<GoodsPageResp>();
                    return ResultVO.success(pageRespPage);
                }
            }
        }
        Page<GoodsPageResp> respPage = goodsManager.queryPageByConditionAdmin(req);
        List<GoodsPageResp> respList = respPage.getRecords();
        for (GoodsPageResp resp : respList) {
            GoodsProductRel goodsProductRel = goodsProductRelManager.queryGoodsProductRel(resp.getGoodsId());
            log.info("GoodsServiceImpl.queryPageByConditionAdmin queryGoodsProductRel rsp={},goodsProductRel={}", resp.getGoodsId(), goodsProductRel);
            if (null != goodsProductRel) {
//                ProductBaseGetReq productBaseGetReq = new ProductBaseGetReq();
                ProductDetailResp productDetail = productBaseManager.getProductDetail(goodsProductRel.getProductBaseId());
                if (null != productDetail) {
                    resp.setTypeId(productDetail.getTypeId());
                    resp.setTypeName(productDetail.getTypeName());
                    List<AttrSpecDTO> attrSpecDTOS = attrSpecManager.queryAttrSpecList(productDetail.getTypeId());
                    if (CollectionUtils.isNotEmpty(attrSpecDTOS)) {
                        resp.setAttrSpecDTOs(attrSpecDTOS);
                    }
                }
            }
            // 添加营销活动名称
            List<String> actNames = goodsActRelManager.queryActNameByGoodsId(resp.getGoodsId());
            resp.setActNames(actNames);
            // 添加产品最低零售价至商品
            Double lowestPrice = goodsProductRelManager.getLowestPriceByGoodsId(resp.getGoodsId());
            resp.setGoodsLowestPrice(lowestPrice);
            String targetType = resp.getTargetType();
            if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_REGION.equals(targetType)) {
                List<GoodsRegionRel> goodsRegionRels = goodsRegionRelManager.queryGoodsRegionRel(resp.getGoodsId());
                if (CollectionUtils.isNotEmpty(goodsRegionRels)) {
                    List<GoodsRegionRelDTO> goodsRegionRelDTOs = new ArrayList<>();
                    for (GoodsRegionRel goodsRegionRel : goodsRegionRels) {
                        GoodsRegionRelDTO goodsRegionRelDTO = new GoodsRegionRelDTO();
                        BeanUtils.copyProperties(goodsRegionRel, goodsRegionRelDTO);
                        goodsRegionRelDTOs.add(goodsRegionRelDTO);
                    }
                    resp.setGoodsRegionRels(goodsRegionRelDTOs);
                }
            } else if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_TARGET.equals(targetType)) {
                List<GoodsTargetRel> goodsTargetRels = goodsTargetManager.queryGoodsTargerRel(resp.getGoodsId());
                if (CollectionUtils.isNotEmpty(goodsTargetRels)) {
                    List<String> merchantList = new ArrayList<>();
                    for (GoodsTargetRel goodsTargetRel : goodsTargetRels) {
                        if (StringUtils.isEmpty(goodsTargetRel.getTargetId())) {
                            merchantList.add(goodsTargetRel.getTargetId());
                        }
                    }
                    MerchantListReq merchantListReq = new MerchantListReq();
                    merchantListReq.setMerchantIdList(merchantList);
                    ResultVO<List<MerchantDTO>> resultVO = merchantService.listMerchant(merchantListReq);
                    List<String> goodsTargetRelLists = new ArrayList<>();
                    if (resultVO.isSuccess() && null != resultVO.getResultData()) {
                        List<MerchantDTO> merchantDTOs = resultVO.getResultData();
                        if (CollectionUtils.isNotEmpty(merchantDTOs)) {
                            for (MerchantDTO merchantDTO : merchantDTOs) {
                                goodsTargetRelLists.add(merchantDTO.getMerchantName());
                            }
                        }
                    }
                    resp.setGoodsTargetRels(goodsTargetRelLists);
                }
            }

            // 补充供应商名称 、供应商地市名称 及等级
            resp.setImagesUrl(getDefaultPicUrl(resp.getGoodsId()));
            try {
                ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(resp.getSupplierId());
                if (merchantDTOResultVO.isSuccess() && merchantDTOResultVO.getResultData() != null) {
                    MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
                    resp.setSupplierName(merchantDTO.getMerchantName());
                    resp.setSupplierType(merchantDTO.getMerchantType());
                    resp.setSupplierId(merchantDTO.getLanId());
                    resp.setSupplierLanName(getCommonRegionName(merchantDTO.getLanId()));
                }
            } catch (Exception ex) {
                log.error("GoodsServiceImpl.queryPageByConditionAdmin getMerchantById throw exception ex={}", ex);
                return ResultVO.error(GoodsResultCodeEnum.INVOKE_PARTNER_SERVICE_EXCEPTION);
            }
        }
        return ResultVOUtils.genQueryResultVO(respPage);
    }

    /**
     * 根据ID 获取区域信息
     * @param regionId
     * @return
     */
    private String getCommonRegionName(String regionId) {
        if (StringUtils.isEmpty(regionId)) {
            return null;
        }
        CommonRegionDTO commonRegionDTO = commonRegionService.getCommonRegionById(regionId).getResultData();
        if (Objects.nonNull(commonRegionDTO)) {
            return commonRegionDTO.getRegionName();
        }
        return null;
    }

    /**
     * 获取商品默认图片，输入商品ID即可
     *
     * @param targetId
     * @return
     */
    private String getDefaultPicUrl(String targetId) {
        String targetType = FileConst.TargetType.GOODS_TARGET.getType();
        String defaultType = FileConst.SubType.THUMBNAILS_SUB.getType();
        List<ProdFileDTO> images = fileManager.getFile(targetId, targetType, defaultType);
        if (null != images && images.size() > 0) {
            StringBuffer urlSb = new StringBuffer();
            for (int i = 0; i < images.size(); i++) {
                ProdFileDTO file = images.get(i);
                if (i == (images.size() - 1)) {
                    urlSb.append(file.getFileUrl());
                } else {
                    urlSb.append(file.getFileUrl()).append(",");
                }
            }
            return urlSb.toString();
        }
        return null;
    }

    @Override
    public ResultVO<GoodsDetailResp> queryGoodsDetail(GoodsQueryReq req) {
        log.info("GoodsServiceImpl.queryGoodsDetail GoodsQueryReq={}", req);
        String goodsId = req.getGoodsId();
        GoodsDetailResp resp = new GoodsDetailResp();
        //查询商品
        Goods goods = goodsManager.queryGoods(goodsId);
        if (null == goods) {
            return ResultVO.successMessage("商品为空");
        }
        BeanUtils.copyProperties(goods, resp);
        if (req.getIsLogin()) {
            // 判断该商品是否被收藏
            UserCollectionJudgeReq judgeReq = new UserCollectionJudgeReq();
            judgeReq.setUserId(req.getUserId());
            judgeReq.setObjType(SystemConst.ObjTypeEnum.B2B_GOODS.getCode());
            judgeReq.setObjId(req.getGoodsId());
            //判断该供应商是否能被收藏
            UserCollectionJudgeReq collectionJudgeReq = new UserCollectionJudgeReq();
            collectionJudgeReq.setUserId(req.getUserId());
            collectionJudgeReq.setObjType(SystemConst.ObjTypeEnum.B2B_SUPPLIER.getCode());
            collectionJudgeReq.setObjId(goods.getSupplierId());
            ResultVO<Boolean> goodsResultVO = userCollectionService.booCollection(judgeReq);
            log.info("GoodsServiceImpl.queryGoodsDetail userCollectionService.booCollectio --> goodsResultVO={}", JSON.toJSONString(goodsResultVO));
            ResultVO<Boolean> suppliResultVO = userCollectionService.booCollection(collectionJudgeReq);
            log.info("GoodsServiceImpl.queryGoodsDetail userCollectionService.booCollectio --> suppliResultVO={}", JSON.toJSONString(suppliResultVO));
            if (null != goodsResultVO) {
                resp.setGoodsIsCollection(goodsResultVO.getResultData());
            }
            if (null != suppliResultVO) {
                resp.setSuppliIsCollection(suppliResultVO.getResultData());
            }
        }

        // 查询品牌名称
        String brandId = goods.getBrandId();
        if (brandId != null) {
            List<String> brandIdList = Lists.newArrayList();
            brandIdList.add(brandId);
            List<Brand> brandList = brandManager.listBrand(brandIdList);
            log.info("GoodsServiceImpl.queryGoodsDetail brandManager.listBrand --> brandList={}", JSON.toJSONString(brandList));
            if (CollectionUtils.isNotEmpty(brandList)) {
                resp.setBrandName(brandList.get(0).getName());
            }
        }
        // 根据商品ID查询零售商标签
        TagGetByGoodsIdReq req1 = new TagGetByGoodsIdReq();
        req1.setGoodsId(goodsId);
        ResultVO<List<TagsDTO>> listResultVO = tagsService.getTagsByGoodsId(req1);
        log.info("GoodsServiceImpl.queryGoodsDetail tagsService.getTagsByGoodsId --> listResultVO={}", JSON.toJSONString(listResultVO));
        List<TagsDTO> tagsDTOS = listResultVO.getResultData();
        if (listResultVO.isSuccess() && CollectionUtils.isNotEmpty(tagsDTOS)) {
            resp.setTagList(tagsDTOS);
        }
        //查询产商品关联表获取提货价
        List<GoodsProductRel> list = goodsProductRelManager.listGoodsProductRel(goodsId);
        log.info("GoodsServiceImpl.queryGoodsDetail goodsProductRelManager.listGoodsProductRel --> list={}", JSON.toJSONString(list));
        if (CollectionUtils.isEmpty(list)) {
            return ResultVO.successMessage("查询产商品列表为空");
        }
        //查询指定productId
        String productId = req.getProductId();
        if (StringUtils.isNotEmpty(productId)) {
            List<GoodsProductRel> list2 = new ArrayList<>();
            for (GoodsProductRel goodsProductRel : list) {
                if (productId.equals(goodsProductRel.getProductId())) {
                    list2.add(goodsProductRel);
                }
            }
            if (CollectionUtils.isNotEmpty(list2)) {
                list = list2;
            }
        }
        // 查询产商品关联信息
        List<GoodsProductRelDTO> goodsProductRelDTOList = Lists.newArrayList();
        for (GoodsProductRel goodsProductRel : list) {
            GoodsProductRelDTO goodsProductRelDTO = new GoodsProductRelDTO();
            BeanUtils.copyProperties(goodsProductRel, goodsProductRelDTO);

            goodsProductRelDTOList.add(goodsProductRelDTO);
        }
        resp.setGoodsProductRelList(goodsProductRelDTOList);
        //去产品参数的ID，产品与产品参数表是一对多，故取一个。此处不会出现大量的数据
        GoodsProductRel goodsProductRel = list.get(0);
        //获取地市名
        List<GoodsRegionRel> goodsRegionRels = getGoodsRegionRelManager.queryGoodsRegionRel(goodsId);
        log.info("GoodsServiceImpl.queryGoodsDetail getGoodsRegionRelManager.queryGoodsRegionRel --> goodsRegionRels={}", JSON.toJSONString(goodsRegionRels));
        List<String> regionNames = Lists.newLinkedList();
        if (CollectionUtils.isNotEmpty(goodsRegionRels)) {
            List<RegionReq> regionList = Lists.newArrayList();
            for (GoodsRegionRel regionRel : goodsRegionRels) {
                regionNames.add(regionRel.getRegionName());
                RegionReq regionReq = new RegionReq();
//                regionReq.setRegionId(regionRel.getRegionId());
//                regionReq.setRegionName(regionRel.getRegionName());
//                regionReq.setLanId(regionRel.getLanId());
                // zhongwenlong 2019.06.13
                BeanUtils.copyProperties(regionRel, regionReq);
                regionList.add(regionReq);
            }
            resp.setRegionList(regionList);
        }
        resp.setRegionNames(regionNames);

        List<GoodsTargetReq> targetList = Lists.newLinkedList();
        String targetType = goods.getTargetType();
        if (!StringUtils.isEmpty(targetType) && GoodsConst.TARGET_TYPE_TARGET.equals(targetType)) {
            List<GoodsTargetRel> goodsTargetRels = goodsTargetManager.queryGoodsTargerRel(goodsId);
            if (CollectionUtils.isNotEmpty(goodsTargetRels)) {
                for (GoodsTargetRel goodsTargetRel : goodsTargetRels) {
                    GoodsTargetReq goodsTargetReq = new GoodsTargetReq();
                    goodsTargetReq.setTargetId(goodsTargetRel.getTargetId());
                    ResultVO<MerchantDTO> merResultVO = merchantService.getMerchantById(goodsTargetRel.getTargetId());
                    log.info("GoodsServiceImpl.queryGoodsDetail merchantService.getMerchantById --> merResultVO={}", JSON.toJSONString(merResultVO));
                    MerchantDTO merchantDTO = merResultVO.getResultData();
                    if (merResultVO.isSuccess() && merResultVO.getResultData() != null) {
                        goodsTargetReq.setTargetCode(merchantDTO.getMerchantCode());
                        goodsTargetReq.setTargetName(merchantDTO.getMerchantName());
                    }
                    targetList.add(goodsTargetReq);
                }
            }
        }
        resp.setTargetList(targetList);

        //查询供应商名称
        String merchantId = goods.getSupplierId();
        ResultVO<MerchantDTO> merResultVO = merchantService.getMerchantById(merchantId);
        log.info("GoodsServiceImpl.queryGoodsDetail merchantService.getMerchantById --> merResultVO={}", JSON.toJSONString(merResultVO));
        MerchantDTO merchantDTO = merResultVO.getResultData();
        if (merResultVO.isSuccess() && merResultVO.getResultData() != null) {
            resp.setSupplierName(merchantDTO.getMerchantName());
            resp.setMerchantType(merchantDTO.getMerchantType());
            // 查询商品归属商家所在地市
//            resp.setCityName(merchantDTO.getCityName());
            // zhongwenlong 2019.06.24 改为本地网的名称
            resp.setCityName(merchantDTO.getLanName());
        }
        //查询类别名称
        Cat cat = catManager.queryProdCat(goods.getGoodsCatId());
        log.info("GoodsServiceImpl.queryGoodsDetail catManager.queryProdCat --> cat={}", JSON.toJSONString(cat));
        if (null != cat) {
            resp.setCatName(cat.getCatName());
        }
        //查询营销活动的列表
        List<GoodActRelResp> relResps = getGoodActRelResps(goodsId);
        resp.setGoodActRelResps(relResps);

        //查询活动的图片
        List<ProdFileDTO> prodFileDTOListHD = prodFileManager.queryGoodsImageHDdetail(goodsId);//查出是活的的图片
        if (prodFileDTOListHD == null) {
            prodFileDTOListHD = new ArrayList<ProdFileDTO>();
        }
        // 查询商品图片和视频
        List<ProdFileDTO> prodFileDTOList = prodFileManager.queryGoodsImage(goodsId);//查询不是活动的图片

        for (int i = 0; i < prodFileDTOList.size(); i++) {//为了活动图片展示在普通的图片前面
            prodFileDTOListHD.add(prodFileDTOList.get(i));
        }

        if (CollectionUtils.isNotEmpty(prodFileDTOListHD)) {
            resp.setProdFiles(prodFileDTOListHD);
        }
        // 查询产品基本信息
        String productBaseId = goodsProductRel.getProductBaseId();
        ProductBaseGetResp productBaseGetResp = productBaseManager.getProductBase(productBaseId);
        //查询产品列表
//        List<ProductResp> productResps = getProductResps(list, req.getIsLogin());
        List<ProductResp> productResps = getProductResps(list, req, goods);

        if (productBaseGetResp != null && !CollectionUtils.isEmpty(productResps)) {
            resp.setSallingPoint(productBaseGetResp.getSallingPoint());
            for (ProductResp productResp : productResps) {
                productResp.setUnitType(productBaseGetResp.getUnitType());
                productResp.setPurchaseType(productBaseGetResp.getPurchaseType());
                productResp.setBrandId(productResp.getBrandId());
                productResp.setBrandName(resp.getBrandName());
                if (StringUtils.isNotEmpty(productResp.getTypeId())) {
                    Type type = typeManager.selectById(productResp.getTypeId());
                    if (null != type) {
                        productResp.setTypeName(type.getTypeName());
                    }
                }
            }
        }
        //查询产品属性列表
        resp.setProductResps(productResps);

        String typeId = productBaseGetResp.getTypeId();
        List<AttrSpecDTO> attrSpecDTOList = attrSpecManager.queryAttrSpecList(typeId);
        ProductDetailGetByBaseIdReq detailGetByBaseIdReq = new ProductDetailGetByBaseIdReq();
        detailGetByBaseIdReq.setProductBaseId(productBaseId);
        ResultVO<ProductDetailResp> detailRespResultVO = productBaseService.getProductDetail(detailGetByBaseIdReq);
        ProductDetailResp productDetailResp = null;
        if (detailRespResultVO.isSuccess() && detailRespResultVO.getResultData() != null) {
            productDetailResp = detailRespResultVO.getResultData();
        }
        if (!CollectionUtils.isEmpty(attrSpecDTOList) && productDetailResp != null) {
            List<GoodsAttrResp> goodsAttrRespList = Lists.newArrayList();
            List<GoodsParamResp> goodsParamRespList = Lists.newArrayList();
            for (AttrSpecDTO attrSpecDto : attrSpecDTOList) {
                String fileName = attrSpecDto.getFiledName();
                if (fileName == null || fileName == "") {
                    continue;
                }
                if (AttrSpecConst.SPEC_TYPE.equals(attrSpecDto.getAttrType())) {
                    // 获取属性数据 param_value
                    if (CollectionUtils.isNotEmpty(productResps)) {
                        for (ProductResp productDto : productResps) {
                            String paramValue = getFieldValueByFieldName(fileName, productDto);
                            GoodsParamResp goodsParamResp = new GoodsParamResp();
                            goodsParamResp.setProductId(productDto.getProductId());
                            goodsParamResp.setParamName(attrSpecDto.getAttrName());
                            goodsParamResp.setParamValue(paramValue);
                            goodsParamResp.setAttrGroupId(attrSpecDto.getAttrGroupId());
                            goodsParamResp.setIsDisplay(attrSpecDto.getIsDisplay());
                            goodsParamRespList.add(goodsParamResp);
                        }
                    }
                } else if (AttrSpecConst.ATTR_TYPE.equals(attrSpecDto.getAttrType())) {
                    // 获取规格数据 attr_value
                    String attrValue = getFieldValueByFieldName(fileName, productDetailResp);
                    GoodsAttrResp goodsAttrResp = new GoodsAttrResp();
                    goodsAttrResp.setAttrName(attrSpecDto.getAttrName());
                    goodsAttrResp.setAttrValue(attrValue);
                    goodsAttrResp.setIsDisplay(attrSpecDto.getIsDisplay());
                    goodsAttrRespList.add(goodsAttrResp);
                }
            }
            resp.setAttrRespList(goodsAttrRespList);
            resp.setParamRespList(goodsParamRespList);
        }
        return ResultVO.success(resp);
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            //将下划线转为驼峰，并将首字母大写
            String methodName = "get" + org.springframework.util.StringUtils.capitalize(AttrSpecHelper.underlineToUpper(fieldName));
            Method method = object.getClass().getMethod(methodName);
            log.info("GoodsServiceImpl.getFieldValueByFieldName,className={},methodName={}", object.getClass().getName(), methodName);
            Object invokeValue = method.invoke(object);
            if (invokeValue == null) {
                return "";
            }
            return String.valueOf(invokeValue);
        } catch (IllegalAccessException e) {
            log.error("AttrSpecManager.getInstValue IllegalAccessException", e);
            throw new RuntimeException("获取属性实例失败，非法访问");
        } catch (InvocationTargetException e) {
            log.error("AttrSpecManager.getInstValue InvocationTargetException", e);
            throw new RuntimeException("获取属性实例失败，内部方法异常");
        } catch (NoSuchMethodException e) {
            log.error("AttrSpecManager.getInstValue InvocationTargetException", e);
            throw new RuntimeException("获取属性实例失败，方法不存在");
        }
    }

    /**
     * 营销活动列表过滤数据
     *
     * @param goodsId
     * @return
     */
    private List<GoodActRelResp> getGoodActRelResps(String goodsId) {
        List<GoodsActRel> goodsActRels = getGoodsActRelManager.queryGoodActRel(goodsId);
        List<GoodActRelResp> relResps = Lists.newLinkedList();
        if (CollectionUtils.isEmpty(goodsActRels)) {
            return relResps;
        }
        for (GoodsActRel goodsActRel : goodsActRels) {
            GoodActRelResp relResp = new GoodActRelResp();
            relResp.setActId(goodsActRel.getActId());
            relResp.setActName(goodsActRel.getActName());
            relResps.add(relResp);
        }
        return relResps;
    }

    /**
     * 获取商品规格
     *
     * @param typeId
     * @param productBaseId
     * @return
     */
    private List<AttrSpecResp> getAttrSpecResps(String typeId, String productBaseId) {

        List<AttrSpecResp> attrSpecResps = Lists.newLinkedList();
        //获取属性值列表
        List<AttrSpecDTO> attrSpecDTOs = attrSpecManager.queryAttrSpecList(typeId);
        log.info("GoodsServiceImpl queryGoodsDetail attrSpecDTOs={}", JSON.toJSONString(attrSpecDTOs));
        Map<String, List<AttrSpecDTO>> map = new HashedMap();
        map = attrSpecDTOs.stream().collect(Collectors.groupingBy(AttrSpecDTO::getAttrGroupName));
        List<AttrSpecResp.GoodsSpec> goodsSpecs = Lists.newLinkedList();

        for (Map.Entry<String, List<AttrSpecDTO>> entry : map.entrySet()) {
            AttrSpecResp resp = new AttrSpecResp();
            resp.setAttrGroupName(entry.getKey());
            List<AttrSpecDTO> attrSpecDTOS = entry.getValue();

            for (AttrSpecDTO attrSpecDTO : attrSpecDTOS) {
                //获取产品扩展表的属性
                if (ProductExt.TNAME.equals(attrSpecDTO.getTableName())) {
                    AttrSpecResp.GoodsSpec goodsSpec = new AttrSpecResp.GoodsSpec();
                    String tableFileName = ProductExt.FieldNames.productBaseId.getTableFieldName();
                    String specValue = getInstValue(attrSpecDTO.getTableName(), tableFileName, attrSpecDTO.getFiledName(), productBaseId);
                    goodsSpec.setAttrName(attrSpecDTO.getCname());
                    goodsSpec.setInstValue(specValue);
                    goodsSpecs.add(goodsSpec);
                    //获取产品基本信息表的属性
                } else if (ProductBase.TNAME.equals(attrSpecDTO.getTableName())) {
                    AttrSpecResp.GoodsSpec goodsSpec = new AttrSpecResp.GoodsSpec();
                    String tableFileName = ProductBase.FieldNames.productBaseId.getTableFieldName();
                    String specValue = getInstValue(attrSpecDTO.getTableName(), tableFileName, attrSpecDTO.getFiledName(), productBaseId);
                    goodsSpec.setAttrName(attrSpecDTO.getCname());
                    goodsSpec.setInstValue(specValue);
                    goodsSpecs.add(goodsSpec);
                }
            }
            resp.setGoodsSpecList(goodsSpecs);
            attrSpecResps.add(resp);
        }
        return attrSpecResps;
    }

    /**
     * 查询产品列表
     * 增加逻辑： 判断商品是不是参加啦前置补贴活动，是：要查出产品是否符合活动，符合要查出强制的统一供货价
     *
     * @param list
     * @return
     */
    private List<ProductResp> getProductResps(List<GoodsProductRel> list, GoodsQueryReq req, Goods goods) {
//        private List<ProductResp> getProductResps(List<GoodsProductRel> list, boolean isLogin) {
        List<ProductResp> productResps = Lists.newLinkedList();

        // 判断商品是否是 参加前置补贴
        MarketingActivityQueryByGoodsReq activityQueryByGoodsReq = new MarketingActivityQueryByGoodsReq();
        if (GoodsConst.IsSubsidy.IS_SUBSIDY.getCode().equals(goods.getIsSubsidy())) {
            // 构造请求判断 当前用户是否参加

            // 前置补贴活动类型
            activityQueryByGoodsReq.setActivityType(PromoConst.ACTIVITYTYPE.PRESUBSIDY.getCode());
            activityQueryByGoodsReq.setSupplierCode(goods.getSupplierId());
            // 买家的商家ID
            UserDTO userDTO = userService.getUserByUserId(req.getUserId());
            if (Objects.nonNull(userDTO) && !StringUtils.isEmpty(userDTO.getRelCode())) {
                activityQueryByGoodsReq.setMerchantCode(userDTO.getRelCode());
            } else {
                log.info("GoodsServiceImpl.getProductResps() 根据userid 获取merchantid失败");
            }
        }

        for (GoodsProductRel goodsProductRel : list) {
            ProductResp dto = productManager.getProduct(goodsProductRel.getProductId());
            if (dto == null) {
                return null;
            }
            ProductResp resp = new ProductResp();
            BeanUtils.copyProperties(dto, resp);
            List<ProdFileDTO> files = prodFileManager.getFile(dto.getProductId(), FileConst.TargetType.PRODUCT_TARGET.getType(), FileConst.SubType.THUMBNAILS_SUB.getType());
            if (!CollectionUtils.isEmpty(files)) {
                resp.setImg(files.get(0).getFileUrl());
            }
            resp.setMaxNum(goodsProductRel.getMaxNum());
            resp.setMinNum(goodsProductRel.getMinNum());
            resp.setSupplyNum(goodsProductRel.getSupplyNum());
            resp.setSpecName(goodsProductRel.getSpecName());
            resp.setAdvancePayAmount(goodsProductRel.getAdvancePayAmount());
            resp.setInitialPrice(goodsProductRel.getInitialPrice());
            if (req.getIsLogin()) {
                resp.setDeliveryPrice(goodsProductRel.getDeliveryPrice());
            }

            // 判断商品是否是 参加前置补贴
            if (GoodsConst.IsSubsidy.IS_SUBSIDY.getCode().equals(goods.getIsSubsidy())) {
                // 构造请求判断 当前用户是否参加 前置补贴活动
                activityQueryByGoodsReq.setProductId(goodsProductRel.getProductId());
                ResultVO<ActivityProductDTO> resultVO = marketingActivityService.getActivityProduct(activityQueryByGoodsReq);
                if (resultVO.isSuccess() && Objects.nonNull(resultVO.getResultData())) {
                    ActivityProductDTO activityProductDTO = resultVO.getResultData();
                    // 设置需要的值
                    // 前置补贴活动的统一货价(转换为Double)
                    resp.setDeliveryPrice(activityProductDTO.getPrice() * 1D);
                } else {
                    log.info("GoodsServiceImpl.getProductResps() 调用服务 marketingActivityService.getActivityProduct 返回结果为空");
                }
            }

            productResps.add(resp);
        }
        return productResps;
    }

    private String getInstValue(String tableName, String tableFileName, String filedName, String goodsId) {
        try {
            Object objValue = dynimcQuery(tableName, tableFileName, goodsId);
            if (objValue == null) {
                log.info("AttrSpecManager.getInstValue 未查询到实例数据,tableName={},filedName={},goodsId={}", tableName, filedName, goodsId);
                return "";
            }
            log.info("AttrSpecManager.getInstValue objValue={}", JSON.toJSON(objValue));
            String filedObj = AttrSpecHelper.underlineToUpper(filedName);

            //将下划线转为驼峰，并将首字母大写
            String methodName = "get" + org.springframework.util.StringUtils.capitalize(AttrSpecHelper.underlineToUpper(filedObj));
            Method method = objValue.getClass().getMethod(methodName);
            log.info("AttrSpecManager.getInstValue,className={},methodName={}", objValue.getClass().getName(), methodName);
            Object invokeValue = method.invoke(objValue);
            if (invokeValue == null) {
                return "";
            }
            return String.valueOf(invokeValue);
        } catch (IllegalAccessException e) {
            log.error("AttrSpecManager.getInstValue IllegalAccessException", e);
            throw new RuntimeException("获取属性实例失败，非法访问");
        } catch (InvocationTargetException e) {
            log.error("AttrSpecManager.getInstValue InvocationTargetException", e);
            throw new RuntimeException("获取属性实例失败，内部方法异常");
        } catch (NoSuchMethodException e) {
            log.error("AttrSpecManager.getInstValue InvocationTargetException", e);
            throw new RuntimeException("获取属性实例失败，方法不存在");
        }
    }

    private Object dynimcQuery(String tableName, String tableFileName, String queryStr) {

        BaseMapper mapper = AttrSpecHelper.getMapperBean(tableName);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(tableFileName, queryStr);
        Object objValue = mapper.selectOne(queryWrapper);

        return objValue;
    }

    @Override
    public ResultVO<List<GoodsDTO>> listGoods(GoodsIdListReq goodsIdListReq) {
        List<String> goodsIdList = goodsIdListReq.getGoodsIdList();
        log.info("GoodsServiceImpl listGoods goodsIdList={}", JSON.toJSONString(goodsIdList));
        if (CollectionUtils.isEmpty(goodsIdList)) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        List<Goods> goodsList = goodsManager.listGoods(goodsIdList);
        List<GoodsDTO> goodsDTOList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(goodsList)) {
            for (Goods goods : goodsList) {
                GoodsDTO goodsDTO = new GoodsDTO();
                BeanUtils.copyProperties(goods, goodsDTO);
                List<GoodsProductRel> list = goodsProductRelManager.listGoodsProductRel(goodsDTO.getGoodsId());
                if(CollectionUtils.isNotEmpty(list)){
                    GoodsProductRel goodsProductRel = list.get(0);
                    String productBaseId = goodsProductRel.getProductBaseId();
                    ProductBaseGetResp productBaseGetResp = productBaseManager.getProductBase(productBaseId);
                    if(null!=productBaseGetResp){
                        goodsDTO.setSallingPoint(productBaseGetResp.getSallingPoint());
                    }
                }

                goodsDTOList.add(goodsDTO);
            }
        }
        return ResultVO.success(goodsDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<Boolean> updateBuyCountById(GoodsBuyCountByIdReq req) {
        // 更新商品的购买数量和该商品关联产品的供货数量
        List<UpdateBuyCountByIdReq> list = req.getUpdateBuyCountByIdReqList();
        if (com.alibaba.dubbo.common.utils.CollectionUtils.isEmpty(list)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        for (UpdateBuyCountByIdReq item : list) {
            // 查询购买数量
            String goodsId = item.getGoodsId();
            String productId = item.getProductId();
            Goods goods = goodsManager.queryGoods(goodsId);
            if (goods == null) {
                throw new RuntimeException("商品为空");
            }
            Long buyCountQuery = goods.getBuyCount() == null ? 0 : goods.getBuyCount();
            log.info("GoodsServiceImpl.updateBuyCountById before goodsId={},buyCountQuery={}", goodsId, buyCountQuery);
            buyCountQuery += item.getBuyCount();
            // 更新商品购买数量
            goodsManager.updateBuyCountById(item.getGoodsId(), buyCountQuery);
            log.info("GoodsServiceImpl.updateBuyCountById after goodsId={},buyCountQuery={}", goodsId, buyCountQuery);
            // 更新产商品供货量（上架数量）
            GoodsProductRel goodsProductRel = goodsProductRelManager.getGoodsProductRel(goodsId, productId);
            if (goodsProductRel != null) {
                Long supplyNum = goodsProductRel.getSupplyNum();
                log.info("GoodsServiceImpl.updateBuyCountById update before goodsId={},productId={},supplyNum={}",
                        goodsId, productId, supplyNum);
                supplyNum += -item.getBuyCount();
                goodsProductRelManager.updateSupplyNumStock(goodsId, productId, supplyNum);
                boolean isHaveStock = supplyNum > 0;
                goodsProductRelManager.updateIsHaveStock(goodsId, productId, isHaveStock);
                log.info("GoodsServiceImpl.updateBuyCountById update before goodsId={},productId={},supplyNum={},isHaveStock={}",
                        goodsId, productId, supplyNum, isHaveStock);
            }
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<com.iwhalecloud.retail.goods2b.dto.MerchantDTO> querySupplierIdByGoodsId(GoodsSupplierIDGetReq goodsSupplierIDGetReq) {
        String goodsId = goodsSupplierIDGetReq.getGoodsId();
        com.iwhalecloud.retail.goods2b.dto.MerchantDTO merchantDTO = new com.iwhalecloud.retail.goods2b.dto.MerchantDTO();
        if (StringUtils.isEmpty(goodsId)) {
            return ResultVO.error("入参goodsId为空");
        }

        Goods goods = goodsManager.queryGoods(goodsId);
        if (goods == null) {
            return ResultVO.error("查询商品为空");
        }
        String supplierId = goods.getSupplierId();
        if (!StringUtils.isEmpty(supplierId)) {
            ResultVO<MerchantDTO> merResultVO = merchantService.getMerchantById(supplierId);
            if (merResultVO.isSuccess() && merResultVO.getResultData() != null) {
                MerchantDTO merchantDTO1 = merResultVO.getResultData();
                BeanUtils.copyProperties(merchantDTO1, merchantDTO);
                return ResultVO.success(merchantDTO);
            }
        }
        return ResultVO.success(merchantDTO);
    }

    @Override
    public ResultVO<Boolean> updateGoodsActTypeByGoodsIdList(GoodsUpdateActTypeByGoodsIdsReq req) {
        log.info("GoodsServiceImpl.updateGoodsActTypeByGoodsIdList req={}", JSON.toJSON(req));
        boolean updateFlag = false;
        if (CollectionUtils.isNotEmpty(req.getGoodsIds())) {
            int updateNum = goodsManager.updateGoodsActTypeByGoodsIdList(req);
            updateFlag = updateNum > 0;
        }
        log.info("GoodsServiceImpl.updateGoodsActTypeByGoodsIdList req={}", updateFlag);
        return ResultVO.success(updateFlag);
    }

    @Override
    public List<SupplierGoodsDTO> querySupplierGoods(String goodsId, String productId) {
        log.info("GoodsServiceImpl.querySupplierGoods req goodsId={},goodsId={}", goodsId, productId);
        List<SupplierGoodsDTO> supplierGoodsDTOs = new ArrayList<>();
        Goods goods = goodsManager.queryGoods(goodsId);
        if (null == goods) {
            return supplierGoodsDTOs;
        } else {
            Double mktprice = goods.getMktprice();
            Integer isSubsidy = 0;
            if (null != goods.getIsSubsidy()) {
                isSubsidy = goods.getIsSubsidy();
            }
            if (1 == isSubsidy || mktprice <= 1599) {
                return supplierGoodsDTOs;
            }
            if (PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(goods.getMerchantType())) {
                return supplierGoodsDTOs;
            }
        }

        //先根据商家类型查询地包商是否有货
        List<SupplierGoodsDTO> supplierGoodsDTOs1 = goodsManager.listSupplierGoodsByType(productId, "2");
        log.info("GoodsServiceImpl.querySupplierGoods listSupplierGoodsByType 地包商 supplierGoodsDTOs={}", supplierGoodsDTOs1);
        if (CollectionUtils.isNotEmpty(supplierGoodsDTOs1)) {
            String avgPriceStr = goodsManager.getAvgPrice(productId);
            ProductResp productResp = productManager.getProductInfo(productId);
            GoodsDetailDTO goodsDetailDTO = goodsProductRelMapper.qryGoodsByProductIdAndGoodsId(productId,goodsId);
            if(StringUtils.isNotEmpty(avgPriceStr) && null!=goodsDetailDTO && null!=productResp){
                Double deliveryPrice = goodsDetailDTO.getDeliveryPrice();
                Double avgPrice = Double.valueOf(avgPriceStr);
                String isFixedLine = productResp.getIsFixedLine();
                if((StringUtils.isEmpty(isFixedLine) || "0".equals(isFixedLine)) && deliveryPrice / avgPrice > 1.03){
                    supplierGoodsDTOs = this.getSupplierGoods(productId);
                }
            }
//            return supplierGoodsDTOs;
        }else{
            supplierGoodsDTOs = this.getSupplierGoods(productId);
            //先根据商家类型查询省包商是否有货
//            List<SupplierGoodsDTO> supplierGoodsDTOs2 = goodsManager.listSupplierGoodsByType(productId, "3");
//            log.info("GoodsServiceImpl.querySupplierGoods listSupplierGoodsByType 省包商 supplierGoodsDTOs={}", supplierGoodsDTOs2);
//            if (CollectionUtils.isNotEmpty(supplierGoodsDTOs2)) {
//                for (SupplierGoodsDTO supplierGoodsDTO : supplierGoodsDTOs2) {
//                    List<ProdFileDTO> prodFileDTOs = fileManager.getFile(supplierGoodsDTO.getGoodsId(), FileConst.TargetType.GOODS_TARGET.getType(), FileConst.SubType.THUMBNAILS_SUB.getType());
//                    if (CollectionUtils.isNotEmpty(prodFileDTOs)) {
//                        supplierGoodsDTO.setImageUrl(prodFileDTOs.get(0).getFileUrl());
//                    }
//                }
//                supplierGoodsDTOs = supplierGoodsDTOs2;
//            }
        }
        log.info("GoodsServiceImpl.querySupplierGoods listSupplierGoodsByType  resp={}", supplierGoodsDTOs);
        return supplierGoodsDTOs;
    }

    private List<SupplierGoodsDTO> getSupplierGoods(String productId){
        List<SupplierGoodsDTO> supplierGoodsDTOs2 = goodsManager.listSupplierGoodsByType(productId, "3");
        log.info("GoodsServiceImpl.querySupplierGoods listSupplierGoodsByType 省包商 supplierGoodsDTOs={}", supplierGoodsDTOs2);
        if (CollectionUtils.isNotEmpty(supplierGoodsDTOs2)) {
            for (SupplierGoodsDTO supplierGoodsDTO : supplierGoodsDTOs2) {
                List<ProdFileDTO> prodFileDTOs = fileManager.getFile(supplierGoodsDTO.getGoodsId(), FileConst.TargetType.GOODS_TARGET.getType(), FileConst.SubType.THUMBNAILS_SUB.getType());
                if (CollectionUtils.isNotEmpty(prodFileDTOs)) {
                    supplierGoodsDTO.setImageUrl(prodFileDTOs.get(0).getFileUrl());
                }
            }
        }
        return supplierGoodsDTOs2;
    }
}