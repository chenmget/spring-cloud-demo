package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.common.GoodsResultCodeEnum;
import com.iwhalecloud.retail.goods2b.dto.*;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.*;
import com.iwhalecloud.retail.goods2b.exception.GoodsRulesException;
import com.iwhalecloud.retail.goods2b.service.AttrSpecService;
import com.iwhalecloud.retail.goods2b.service.dubbo.*;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesListReq;
import com.iwhalecloud.retail.partner.service.MerchantAccountService;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.dto.ActivityProductDTO;
import com.iwhalecloud.retail.promo.dto.req.ActivityProductListReq;
import com.iwhalecloud.retail.promo.service.ActivityProductService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonOrgDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.service.CommonOrgService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.exception.UserNoMerchantException;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.workflow.service.TaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author My
 * @Date 2018/12/26
 **/
@RestController
@RequestMapping("/api/b2b/goods")
@Slf4j
public class GoodsB2BController extends GoodsBaseController {

    @Value("${jwt.secret}")
    private String SECRET;

    @Reference
    private GoodsService goodsService;

    @Reference
    private AttrSpecService attrSpecService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private ActivityProductService activityProductService;

    @Reference
    private CatService catService;

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private MerchantAccountService merchantAccountService;

    @Reference
    private TaskService taskService;

    @Reference
    private ProductBaseService productBaseService;

    @Reference
    private GoodsProductRelService goodsProductRelService;

    @Reference
    private GoodsSaleNumService goodsSaleNumService;

    @Reference
    private CommonOrgService commonOrgService;


    @ApiOperation(value = "添加商品", notes = "添加商品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/addGoods")
    @UserLoginToken
    public ResultVO<GoodsAddResp> addGoods(@RequestBody GoodsAddReq req) throws Exception {
        log.info("GoodsB2BController addGoods req={} ", JSON.toJSON(req));
        UserDTO userDTO = UserContext.getUser();
        String userId = userDTO.getUserId();
        String userName = userDTO.getUserName();
        String lanId = userDTO.getLanId();
        String regionId = userDTO.getRegionId();
        req.setUserId(userId);
        req.setUserName(userName);
        req.setLanId(lanId);
        req.setRegionId(regionId);

        if (GoodsConst.IsAdvanceSale.IS_ADVANCE_SALE.getCode().equals(req.getIsAdvanceSale())) {
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(req.getEntityList())) {
                req.setIsAllot(GoodsConst.IsAllotEnum.IS_ALLOT.getCode());
            } else {
                req.setIsAllot(GoodsConst.IsAllotEnum.IS_NOT_ALLOT.getCode());
            }
            List<GoodsProductRelDTO> goodsProductRelDTOs = req.getGoodsProductRelList();
            //商品产品关联关系上增加预付款金额
            ResultVO attacheAdvancePayAmountResultVO = attacheAdvancePayAmount(goodsProductRelDTOs, req.getGoodsActs());
            if (!attacheAdvancePayAmountResultVO.isSuccess()) {
                return attacheAdvancePayAmountResultVO;
            }
        }

        ResultVO<GoodsAddResp> resultVO = goodsService.addGoods(req);
        if (resultVO.isSuccess() && resultVO.getResultData() != null) {
            String merchantType = req.getMerchantType();
            List<GoodsProductRelDTO> goodsProductRelDTOList = req.getGoodsProductRelList();
            // 更新省包平均供货价
            updateAvgSupplyPrice(merchantType, goodsProductRelDTOList);
        }
        return resultVO;
    }

    private void updateAvgSupplyPrice(String merchantType, List<GoodsProductRelDTO> goodsProductRelDTOList) {
        String productBaseId = null;
        if (!CollectionUtils.isEmpty(goodsProductRelDTOList)) {
            productBaseId = goodsProductRelDTOList.get(0).getProductBaseId();
        }
        boolean isSupplierProvince = PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equals(merchantType);
        if (isSupplierProvince && productBaseId != null) {
            ProductBaseUpdateReq productBaseUpdateReq = new ProductBaseUpdateReq();
            productBaseUpdateReq.setProductBaseId(productBaseId);
            productBaseService.updateAvgApplyPrice(productBaseUpdateReq);
        }
    }

    @ApiOperation(value = "添加商品--中台", notes = "添加商品--中台")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/addGoodsByZT")
    @UserLoginToken
    public ResultVO<GoodsAddResp> addGoodsByZT(@RequestBody GoodsAddByZTReq req) throws GoodsRulesException {
        log.info("GoodsB2BController addGoods req={} ", JSON.toJSON(req));
        String goodsId = req.getGoodsId();

        List<GoodsProductRelDTO> goodsProductRelDTOs = req.getGoodsProductRelList();
        //商品产品关联关系上增加预付款金额
        ResultVO attacheAdvancePayAmountResultVO = attacheAdvancePayAmount(goodsProductRelDTOs, req.getGoodsActs());
        if (!attacheAdvancePayAmountResultVO.isSuccess()) {
            return attacheAdvancePayAmountResultVO;
        }

        if (StringUtils.isEmpty(goodsId)) {
            return goodsService.addGoodsByZT(req);
        } else {
            GoodsEditReq editReq = new GoodsEditReq();
            BeanUtils.copyProperties(req, editReq);
            ResultVO<GoodsOperateResp> respResultVO = goodsService.editGoods(editReq);
            if (respResultVO.isSuccess() && respResultVO.getResultData() != null && respResultVO.getResultData().getResult()) {
                GoodsAddResp resp = new GoodsAddResp();
                resp.setGoodsId(goodsId);
                return ResultVO.success(resp);
            } else {
                ResultVO resultVO = new ResultVO();
                resultVO.setResultCode(respResultVO.getResultCode());
                resultVO.setResultMsg(respResultVO.getResultMsg());
                return resultVO;
            }
        }
    }

    /**
     * 商品与产品的关联关系增加预付款金额
     *
     * @param goodsProductRelDTOs
     * @param goodsActs
     */
    private ResultVO attacheAdvancePayAmount(List<GoodsProductRelDTO> goodsProductRelDTOs, List<GoodsActRelDTO> goodsActs) {

        ResultVO<String> advanceResultVO = getAdvanceAct(goodsActs);
        if (!advanceResultVO.isSuccess()) {
            return advanceResultVO;
        }

        //预售活动ID
        final List<String> actIds = Arrays.asList(advanceResultVO.getResultData());

        //遍历查询产品的预收款
        for (GoodsProductRelDTO goodsProductRelDTO : goodsProductRelDTOs) {
            ActivityProductListReq req = new ActivityProductListReq();
            req.setMarketingActivityIds(actIds);
            req.setProductId(goodsProductRelDTO.getProductId());

            log.info("GoodsB2bController.attacheAdvancePayAmount-->req={}", JSON.toJSONString(req));
            ResultVO<List<ActivityProductDTO>> resultVO = activityProductService.queryActivityProducts(req);
            if (!resultVO.isSuccess()) {
                log.error("GoodsB2bController.attacheAdvancePayAmount-->queryActivityProducts error,resultVO={}", JSON.toJSONString(resultVO));
                return ResultVO.error(resultVO.getResultMsg());
            }

            //如果活动与产品配置的关联关系条数不为1，则直接返回错误
            if (CollectionUtils.isEmpty(resultVO.getResultData()) || resultVO.getResultData().size() != 1) {
                log.error("GoodsB2bController.attacheAdvancePayAmount-->queryActivityProducts error,!!ActivityProducts config error!!resultVO={}", JSON.toJSONString(resultVO));
                return ResultVO.error("预售活动配置异常,productId={}", goodsProductRelDTO.getProductId());
            }

            final Long prePrice = resultVO.getResultData().get(0).getPrePrice();
            if (prePrice == null) {
                log.error("GoodsB2bController.attacheAdvancePayAmount-->queryActivityProducts error,!! config error prePrice is null!!resultVO={}", JSON.toJSONString(resultVO));
                return ResultVO.error("预售活动配置异常，获取预存款为空,productId={}", goodsProductRelDTO.getProductId());
            }
            goodsProductRelDTO.setAdvancePayAmount(prePrice);
        }
        return ResultVO.success();
    }

    /**
     * 获取预售活动ID
     *
     * @param goodsActs
     * @return
     */
    private ResultVO<String> getAdvanceAct(List<GoodsActRelDTO> goodsActs) {
        //如果活动信息不为1（预售商品只能参与一个活动）
        if (CollectionUtils.isEmpty(goodsActs)) {
            log.error("GoodsB2bController.getAdvanceAct-->advance goods allow config only one activity,goodsActs={}", JSON.toJSONString(goodsActs));
            return ResultVO.error("预售商品必须配置一个预售活动");
        }

        //过滤出预售的活动
        List<GoodsActRelDTO> advanceActs = goodsActs.stream()
                .filter((GoodsActRelDTO goodsActRelDTO) -> PromoConst.ACTIVITYTYPE.BOOKING.getCode().equals(goodsActRelDTO.getActType()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(advanceActs) || goodsActs.size() != 1) {
            log.error("GoodsB2bController.getAdvanceAct-->advance goods allow config only one activity,advanceActs={}", JSON.toJSONString(advanceActs));
            return ResultVO.error("预售商品有且需要配置一个预售活动");
        }

        return ResultVO.success(advanceActs.get(0).getActId());
    }

    @ApiOperation(value = "编辑商品", notes = "编辑商品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/editGoods")
    @UserLoginToken
    public ResultVO<GoodsOperateResp> editGoods(@RequestBody GoodsEditReq req) throws Exception {
        log.info("GoodsB2BController editGoods req={} ", JSON.toJSON(req));
        UserDTO userDTO = UserContext.getUser();
        String userId = userDTO.getUserId();
        String userName = userDTO.getUserName();
        String lanId = userDTO.getLanId();
        String regionId = userDTO.getRegionId();
        req.setUserId(userId);
        req.setUserName(userName);
        req.setLanId(lanId);
        req.setRegionId(regionId);

        //如果是预售商品，强制变更是否分货为否
        if (GoodsConst.IsAdvanceSale.IS_ADVANCE_SALE.getCode().equals(req.getIsAdvanceSale())) {
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(req.getEntityList())) {
                req.setIsAllot(GoodsConst.IsAllotEnum.IS_ALLOT.getCode());
            } else {
                req.setIsAllot(GoodsConst.IsAllotEnum.IS_NOT_ALLOT.getCode());
            }
            List<GoodsProductRelDTO> goodsProductRelDTOs = req.getGoodsProductRelList();
            //商品产品关联关系上增加预付款金额
            ResultVO attacheAdvancePayAmountResultVO = attacheAdvancePayAmount(goodsProductRelDTOs, req.getGoodsActs());
            if (!attacheAdvancePayAmountResultVO.isSuccess()) {
                return attacheAdvancePayAmountResultVO;
            }
        }

        ResultVO<GoodsOperateResp> resultVO = goodsService.editGoods(req);
        if (resultVO.isSuccess() && resultVO.getResultData() != null) {
            String merchantType = req.getMerchantType();
            List<GoodsProductRelDTO> goodsProductRelDTOList = req.getGoodsProductRelList();
            // 更新省包平均供货价
            updateAvgSupplyPrice(merchantType, goodsProductRelDTOList);
        }
        return resultVO;
    }

    @ApiOperation(value = "编辑商品-中台", notes = "编辑商品-中台")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/editGoodsByZT")
    public ResultVO<GoodsOperateResp> editGoodsByZT(@RequestBody GoodsEditByZTReq req) {
        log.info("GoodsB2BController editGoods req={} ", JSON.toJSON(req));
        return goodsService.editGoodsByZT(req);
    }

    /**
     * 商品分页查询
     * <p>
     * 增加一个商品展示规则（零售商），优先地包
     * 1. ，地包排序在省包前。如果地包价格高于省包平均供货价的3%，则不展示地包商品；
     * 2. 1599及以下机型优先地包供货，如果地包无库存，则展示省包商品
     * 3. 有前置补贴的机型优先地包供货，即使地包无库存，也不展示省包
     * <p>
     * 地包商只展示省包商品，省包商不展示商品
     */
    @ApiOperation(value = "根据条件进行商品分页查询（PC交易端）", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/queryGoodsForPage")
    public ResultVO<Page<GoodsForPageQueryResp>> queryGoodsForPage(
            @RequestParam(value = "pageNo") Integer pageNo,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "ids", required = false) List<String> ids,
            @RequestParam(value = "catIdList", required = false) List<String> catIdList,
            @RequestParam(value = "brandIdList", required = false) List<String> brandIdList,
            @RequestParam(value = "searchKey", required = false) String searchKey,
            @RequestParam(value = "startPrice", required = false) Long startPrice,
            @RequestParam(value = "endPrice", required = false) Long endPrice,
            @RequestParam(value = "attrSpecValues", required = false) String attrSpecValues,
            @RequestParam(value = "isHaveStock", required = false) Integer isHaveStock,
            @RequestParam(value = "sortType", required = false) Integer sortType) throws UserNoMerchantException {
        GoodsForPageQueryReq req = new GoodsForPageQueryReq();
        // 组装req参数
        setReqParam(pageNo, pageSize, ids, catIdList, brandIdList, searchKey, startPrice, endPrice, isHaveStock, req, catService);
        // 如果登陆用户是地包商则过滤所有省包商品
        Integer userFounder = null;
        if (UserContext.isUserLogin()) {
            // 设置分货规则过滤条件
            setTarGetCodeList(req);
            req.setSortType(GoodsConst.SortTypeEnum.DELIVERY_PRICE_ASC.getValue());
            // 设置当前登录商家区域和商家ID作为商品发布区域和发布对象查询过滤条件
            UserDTO userDTO = UserContext.getUser();
            String lanId = userDTO.getLanId();
            String regionId = userDTO.getRegionId();
            req.setLanId(lanId);
            req.setRegionId(regionId);
            String merchantId = UserContext.getMerchantId();
            req.setTargetId(merchantId);
            log.info("GoodsB2BController.queryGoodsForPage lanId={},regionId={},merchantId={}", lanId, regionId, merchantId);
            // 如果用户是零售商，先查地包商品，没有地包商品才能查省包商品
            userFounder = UserContext.getUser().getUserFounder();
            if (null == userFounder) {
                throw new UserNoMerchantException(ResultCodeEnum.ERROR.getCode(), "用户没有商家类型，请确认");
            } else if (userFounder == SystemConst.USER_FOUNDER_3) {
                req.setSortType(GoodsConst.SortTypeEnum.DELIVERY_PRICE_ASC_MERCHANT_TYPE_ASC.getValue());

                // 设置零售商的组织路径编码 zhong.wenlong 2019.06.13
                req.setOrgPathCode(getOrgPathCode(merchantId));

            } else if (userFounder == SystemConst.USER_FOUNDER_5) {
                String merchantType = PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType();
                // 商家类型为省包供应商
                // 如果用户是地包供应商，只能查到省包商品
                req.setMerchantType(merchantType);

                // 设置地包商的组织路径编码 zhong.wenlong 2019.06.13
                req.setOrgPathCode(getOrgPathCode(merchantId));

            } else {
                // 省包供应商查询不到任何商品
                req.setSourceFrom("-1");
            }
        } else {
            req.setSortType(GoodsConst.SortTypeEnum.MKTPRICE_ASC.getValue());
        }
        if (!StringUtils.isEmpty(attrSpecValues)) {
            try {
                Gson gson = new Gson();
                List<AttrSpecValueReq> attrSpecValueList = gson.fromJson(attrSpecValues, new TypeToken<List<AttrSpecValueReq>>() {
                }.getType());
                // 设置规格属性查询条件
                req.setAttrSpecValueList(attrSpecValueList);
            } catch (Exception ex) {
                log.error("GoodsB2BController.queryGoodsForPage json transform error ex={}", ex);
                return ResultVO.error(GoodsResultCodeEnum.DESERIALIZE_ERROR);
            }
        }
        // 设置排序类型
        if (sortType != null) {
            String sortTypeValue = GoodsConst.SortTypeEnum.getValueByKey(sortType);
            if (sortTypeValue != null) {
                req.setSortType(sortTypeValue);
            }
        }
        req.setUserFounder(userFounder);
        log.info("GoodsB2BController.queryGoodsForPage req={}", JSON.toJSONString(req));
        long start = System.currentTimeMillis();
        ResultVO<Page<GoodsForPageQueryResp>> pageResultVO = goodsService.queryGoodsForPage(req);
        long end = System.currentTimeMillis();
        long cost = (end - start);
        System.out.println("queryGoodsForPage cost time:" + cost);
        return pageResultVO;
    }

    /**
     * zhong.wenlong
     * 获取零售商的 组织路径 OrgPathCode
     *
     * @param merchantId
     */
    private String getOrgPathCode(String merchantId) {
        // 根据商家所属组织，过滤商品发布区域
        String orgPathCode = null;
        MerchantDTO merchantDTO = merchantService.getMerchantById(merchantId).getResultData();
        log.info("GoodsB2BController.getOrgPathCode() merchantService.getMerchantById() input: merchantId={}, output: merchantDTO ={}", merchantId, JSON.toJSONString(merchantDTO));
        if (Objects.nonNull(merchantDTO)) {
            // 判断 组织ID 是否为空
            if (StringUtils.isNotEmpty(merchantDTO.getParCrmOrgId())) {
                CommonOrgDTO commonOrgDTO = commonOrgService.getCommonOrgById(merchantDTO.getParCrmOrgId()).getResultData();
                log.info("GoodsB2BController.getOrgPathCode() commonOrgService.getCommonOrgById() input: orgId={}, output: CommonOrgDTO ={}", merchantDTO.getParCrmOrgId(), JSON.toJSONString(commonOrgDTO));
                if (Objects.nonNull(commonOrgDTO) && StringUtils.isNotEmpty(commonOrgDTO.getPathCode())) {
                    orgPathCode = commonOrgDTO.getPathCode();
                }
            }
        }
        return orgPathCode;
    }

    private void setRegionIdAndTargetId(GoodsForPageQueryReq req) {
        // 根据商家所属地市，过滤商品发布区域
        String merchantId = UserContext.getMerchantId();
        ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(merchantId);
        log.info("GoodsB2BController.queryGoodsForPage merchantId={},merchantDTOResultVO={}", merchantId,
                JSON.toJSONString(merchantDTOResultVO));
        if (merchantDTOResultVO.isSuccess() && merchantDTOResultVO.getResultData() != null) {
            MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
            req.setRegionId(merchantDTO.getCity());
        }
        // 根据商家id过滤发布对象类型
        req.setTargetId(merchantId);
    }

    private ResultVO<Page<GoodsForPageQueryResp>> getPageResultVO(GoodsForPageQueryReq req) {
        // 查询省包商品
        ResultVO<Page<GoodsForPageQueryResp>> pageResultVO;
        MerchantListReq merchantList = new MerchantListReq();
        // 类型为省包供应商
        merchantList.setMerchantType(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType());
        log.info("GoodsB2BController.getPageResultVO merchantListReq={}", JSON.toJSONString(merchantList));
        ResultVO<List<MerchantDTO>> resultVO = merchantService.listMerchant(merchantList);
        if (resultVO.isSuccess() && !CollectionUtils.isEmpty(resultVO.getResultData())) {
            List<MerchantDTO> merchantDTOList = resultVO.getResultData();
            List<String> supplierIdList = merchantDTOList.stream().map(MerchantDTO::getMerchantId).collect(Collectors.toList());
            req.setSupplierIdList(supplierIdList);
        }
        log.info("GoodsB2BController.getPageResultVO req={}", JSON.toJSONString(req));
        pageResultVO = goodsService.queryGoodsForPage(req);
        return pageResultVO;
    }

    private void setTarGetCodeList(GoodsForPageQueryReq req) throws UserNoMerchantException {
        req.setIsLogin(true);
        req.setUserId(UserContext.getUser().getUserId());
        List<String> targetCodeList = Lists.newArrayList();
        Integer userFounder = UserContext.getUser().getUserFounder();
        setTargetCode(targetCodeList, userFounder);
        if (!CollectionUtils.isEmpty(targetCodeList)) {
            req.setTargetCodeList(targetCodeList);
        }
        log.info("GoodsB2BController.setTarGetCodeList userFounder={},targetCodeList={}", userFounder, JSON.toJSONString(targetCodeList));
    }

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryGoodsDetail", method = RequestMethod.GET)
    public ResultVO<GoodsDetailResp> queryGoodsDetail(@RequestParam String goodsId) {
        log.info("GoodsB2BController queryGoodsDetail req={} ", goodsId);
        if (StringUtils.isEmpty(goodsId)) {
            return ResultVO.error("goodsId is must not be null");
        }
        Boolean isLogin = UserContext.isUserLogin();
        GoodsQueryReq req = new GoodsQueryReq();
        req.setGoodsId(goodsId);
        req.setIsLogin(isLogin);
        req.setUserId(UserContext.getUserId());
        return goodsService.queryGoodsDetail(req);
    }

    @ApiOperation(value = "查询指定产品ID的商品详情", notes = "查询指定产品ID的商品详情")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryGoodsDetailByProductId", method = RequestMethod.GET)
    public ResultVO<GoodsDetailResp> queryGoodsDetailByProductId(@RequestParam String goodsId, @RequestParam String productId) {
        log.info("GoodsB2BController queryGoodsDetail req goodsId={},productId={} ", goodsId, productId);
        if (StringUtils.isEmpty(goodsId) || StringUtils.isEmpty(productId)) {
            return ResultVO.error("goodsId or ProductId is must not be null");
        }
        Boolean isLogin = UserContext.isUserLogin();
        GoodsQueryReq req = new GoodsQueryReq();
        req.setGoodsId(goodsId);
        req.setIsLogin(isLogin);
        req.setUserId(UserContext.getUserId());
        req.setProductId(productId);
        return goodsService.queryGoodsDetail(req);
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/deleteGoods")
    public ResultVO<GoodsOperateResp> deleteGoods(@RequestBody GoodsDeleteReq req) {
        log.info("GoodsB2BController deleteGoods req={} ", JSON.toJSON(req));
        String goodsId = req.getGoodsId();
        GoodsIdListReq goodsIdListReq = new GoodsIdListReq();
        List<String> list = Lists.newArrayList();
        list.add(goodsId);
        goodsIdListReq.setGoodsIdList(list);
        ResultVO<List<GoodsDTO>> listResultVO = goodsService.listGoods(goodsIdListReq);
        if (!listResultVO.isSuccess() || CollectionUtils.isEmpty(listResultVO.getResultData())) {
            return ResultVO.error("商品不存在");
        }
        GoodsDTO goodsDTO = listResultVO.getResultData().get(0);
        String merchantType = goodsDTO.getMerchantType();
        ResultVO<List<GoodsProductRelDTO>> resultVO = goodsProductRelService.listGoodsProductRel(goodsId);
        if (!resultVO.isSuccess() || CollectionUtils.isEmpty(resultVO.getResultData())) {
            return ResultVO.error("产商品关联不存在");
        }
        GoodsProductRelDTO goodsProductRelDTO = resultVO.getResultData().get(0);
        ResultVO<GoodsOperateResp> respResultVO = goodsService.deleteGoods(req);
        if (respResultVO.isSuccess() && respResultVO.getResultData() != null) {
            List<GoodsProductRelDTO> goodsProductRelDTOList = Lists.newArrayList();
            goodsProductRelDTOList.add(goodsProductRelDTO);
            // 更新省包平均供货价
            updateAvgSupplyPrice(merchantType, goodsProductRelDTOList);
        }
        return respResultVO;
    }

    @ApiOperation(value = "商品上下架", notes = "商品上下架")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @PostMapping(value = "/updateMarketEnable")
    public ResultVO<GoodsOperateResp> updateMarketEnable(@RequestBody UpdateMarketEnableReq req) {
        log.info("GoodsB2BController updateMarketEnable req={}", JSON.toJSONString(req));
        boolean flag = GoodsConst.MARKET_ENABLE.equals(req.getMarketEnable());
        if (flag) {
            MerchantAccountListReq merchantAccountListReq = new MerchantAccountListReq();
            GoodsSupplierIDGetReq goodsSupplierIDGetReq = new GoodsSupplierIDGetReq();
            goodsSupplierIDGetReq.setGoodsId(req.getGoodsId());
            ResultVO<com.iwhalecloud.retail.goods2b.dto.MerchantDTO> merchantDTOResultVO = goodsService.
                    querySupplierIdByGoodsId(goodsSupplierIDGetReq);
            if (merchantDTOResultVO.isSuccess() && merchantDTOResultVO.getResultData() != null) {
                String merchantId = merchantDTOResultVO.getResultData().getMerchantId();
                log.info("GoodsB2BController updateMarketEnable req={},merchantId={}", JSON.toJSONString(req), merchantId);

                merchantAccountListReq.setMerchantId(merchantId);
            } else {
                return ResultVO.error("供应商为空");
            }
            try {
                log.info("GoodsB2BController updateMarketEnable req={},merchantAccountListReq={}", JSON.toJSONString
                                (req),
                        JSON.toJSONString(merchantAccountListReq));
                ResultVO<List<MerchantAccountDTO>> resultVO = merchantAccountService.listMerchantAccount(merchantAccountListReq);
                log.info("GoodsB2BController updateMarketEnable req={},merchantAccountListReq={},resultVO={}", JSON.toJSONString(req),
                        JSON.toJSONString(merchantAccountListReq), JSON.toJSONString(resultVO));
                if (resultVO.isSuccess() && CollectionUtils.isEmpty(resultVO.getResultData())) {
                    log.info("GoodsB2BController updateMarket Enable req={},merchantAccountListReq={}", JSON.toJSONString(req),
                            JSON.toJSONString(merchantAccountListReq));
                    return ResultVO.errorEnum(GoodsResultCodeEnum.MERCHANT_ACCOUNT_IS_EMPTY);
                }
            } catch (Exception ex) {
                log.error("GoodsB2BController updateMarketEnable invoke partner listMerchantAccount exception req={},ex={}", JSON.toJSONString(req), JSON.toJSONString(ex));
                return ResultVO.errorEnum(GoodsResultCodeEnum.INVOKE_PARTNER_SERVICE_EXCEPTION);
            }
        }
        GoodsIdListReq goodsIdListReq = new GoodsIdListReq();
        List<String> list = Lists.newArrayList();
        list.add(req.getGoodsId());
        goodsIdListReq.setGoodsIdList(list);
        ResultVO<List<GoodsDTO>> listResultVO = goodsService.listGoods(goodsIdListReq);
        if (!listResultVO.isSuccess() || CollectionUtils.isEmpty(listResultVO.getResultData())) {
            return ResultVO.error("商品不存在");
        }
        GoodsDTO goodsDTO = listResultVO.getResultData().get(0);
        String merchantType = goodsDTO.getMerchantType();
        ResultVO<List<GoodsProductRelDTO>> resultVO = goodsProductRelService.listGoodsProductRel(req.getGoodsId());
        if (!resultVO.isSuccess() || CollectionUtils.isEmpty(resultVO.getResultData())) {
            return ResultVO.error("产商品关联不存在");
        }
        GoodsProductRelDTO goodsProductRelDTO = resultVO.getResultData().get(0);
        GoodsMarketEnableReq req1 = new GoodsMarketEnableReq();
        req1.setGoodsId(req.getGoodsId());
        req1.setMarketEnable(req.getMarketEnable());
        ResultVO<GoodsOperateResp> respResultVO = goodsService.updateMarketEnable(req1);
        if (respResultVO.isSuccess() && respResultVO.getResultData() != null) {
            List<GoodsProductRelDTO> goodsProductRelDTOList = Lists.newArrayList();
            goodsProductRelDTOList.add(goodsProductRelDTO);
            // 更新省包平均供货价
            updateAvgSupplyPrice(merchantType, goodsProductRelDTOList);
        }
        return respResultVO;
    }

    @ApiOperation(value = "修改商品审核状态", notes = "修改商品审核状态")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/updateAuditState")
    @UserLoginToken
    public ResultVO<GoodsOperateResp> updateAuditState(@RequestBody UpdateAuditStateReq req) {
        log.info("GoodsB2BController updateAuditState req={}", req);
        GoodsAuditStateReq req1 = new GoodsAuditStateReq();
        req1.setGoodsId(req.getGoodsId());
        req1.setAuditState(req.getAuditState());
        return goodsService.updateAuditState(req1);
    }

    @ApiOperation(value = "根据条件进行商品分页查询（管理端）", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "/queryPageByConditionAdmin")
    @UserLoginToken
    public ResultVO<Page<GoodsPageResp>> queryPageByConditionAdmin(@RequestBody GoodsPageReq req) {
        log.info("GoodsController queryPageByConditionAdmin req={}", req);
        Boolean isAdminType = UserContext.isAdminType();
        if (!isAdminType) {
            String merchantId = UserContext.getMerchantId();
            List<String> supplierIds = req.getSupplierIds();
            if (null == supplierIds) {
                supplierIds = Lists.newArrayList(merchantId);
            } else {
                supplierIds.add(merchantId);
            }
            req.setSupplierIds(supplierIds);
        }

        return goodsService.queryPageByConditionAdmin(req);
    }

    @ApiOperation(value = "查询商品分类条件", notes = "查询商品分类条件")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/queryGoodsFilters")
    public ResultVO<List<GoodsFilterResp>> queryGoodsFilters(@RequestParam(value = "typeId") String typeId) {
        List<GoodsFilterResp> goodsFilterRespList = Lists.newArrayList();
        ResultVO<List<AttrSpecDTO>> resultVO = attrSpecService.queryAttrSpecList(typeId);
        List<AttrSpecDTO> attrSpecDTOList = resultVO.getResultData();
        if (!resultVO.isSuccess() || CollectionUtils.isEmpty(attrSpecDTOList)) {
            return ResultVO.success(goodsFilterRespList);
        }
        // 根据属性ID进行分组
        Map<String, List<AttrSpecDTO>> attrSpecMap = attrSpecDTOList.stream().collect(Collectors.groupingBy(AttrSpecDTO::getAttrId));
        for (Map.Entry<String, List<AttrSpecDTO>> entry : attrSpecMap.entrySet()) {
            String attrId = entry.getKey();
            List<AttrSpecDTO> attrSpecDTOS = entry.getValue();
            if (!"1".equals(attrSpecDTOS.get(0).getIsFilter())) {
                continue;
            }
            GoodsFilterResp goodsFilterResp = new GoodsFilterResp();
            goodsFilterResp.setAttrId(attrId);
            goodsFilterResp.setCname(attrSpecDTOS.get(0).getCname());
            goodsFilterResp.setOrder(attrSpecDTOS.get(0).getFilterOrder());
            List<String> values = Lists.newArrayList();
            // 获取展示在前端页面的属性名称
            for (AttrSpecDTO item : attrSpecDTOS) {
                if (StringUtils.isEmpty(item.getValueScope())) {
                    continue;
                }
                String[] valueArr = item.getValueScope().split(",");
                for (String value : valueArr) {
                    values.add(value);
                }
            }
            goodsFilterResp.setValueList(values);
            goodsFilterRespList.add(goodsFilterResp);
        }
        return ResultVO.success(goodsFilterRespList);
    }

    @ApiOperation(value = "根据商品id查询供应商信息", notes = "根据商品id查询供应商信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/queryMerchantByGoodsId")
    ResultVO<com.iwhalecloud.retail.goods2b.dto.MerchantDTO> queryMerchantByGoodsId(@RequestParam(value = "goodsId") String goodsId) {
        log.info("GoodsController queryMerchantByGoodsId goodsId={}", goodsId);
        GoodsSupplierIDGetReq goodsSupplierIDGetReq = new GoodsSupplierIDGetReq();
        goodsSupplierIDGetReq.setGoodsId(goodsId);
        com.iwhalecloud.retail.goods2b.dto.MerchantDTO merchantDTO = new com.iwhalecloud.retail.goods2b.dto.MerchantDTO();
        ResultVO<com.iwhalecloud.retail.goods2b.dto.MerchantDTO> merchantDTOResultVO = goodsService.querySupplierIdByGoodsId(goodsSupplierIDGetReq);
        if (merchantDTOResultVO.isSuccess()) {
            merchantDTO = merchantDTOResultVO.getResultData();
        }
        return ResultVO.success(merchantDTO);

    }

    @ApiOperation(value = "根据Key查询商品排行榜", notes = "根据Key查询商品排行榜")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/queryGoodsSaleOrder")
    ResultVO<List<GoodsSaleNumDTO>> queryGoodsSaleOrder(@RequestParam(value = "cacheKey") String cacheKey) {
        log.info("GoodsController queryGoodsSaleOrder goodsId={}", cacheKey);
        List<GoodsSaleNumDTO> list = new ArrayList<>();
        ResultVO<List<GoodsSaleNumDTO>> listResultVO = goodsSaleNumService.getGoodsSaleOrder(cacheKey);
        if (listResultVO.isSuccess()) {
            list = listResultVO.getResultData();
        }
        return ResultVO.success(list);

    }

    @ApiOperation(value = "根据商品ID和产品id省包推荐商品", notes = "根据商品ID和产品id省包推荐商品")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/querySupplierGoods")
    ResultVO<List<SupplierGoodsDTO>> querySupplierGoods(@RequestParam(value = "goodsId") String goodsId, @RequestParam(value = "productId") String productId) {
        log.info("GoodsController querySupplierGoods goodsId={},productId={}", goodsId, productId);
        List<SupplierGoodsDTO> supplierGoodsDTOs = new ArrayList<>();
        List<SupplierGoodsDTO> supplierGoodsDTOs1 = goodsService.querySupplierGoods(goodsId, productId);
        if (!CollectionUtils.isEmpty(supplierGoodsDTOs1)) {
            supplierGoodsDTOs = supplierGoodsDTOs1;
        }
        return ResultVO.success(supplierGoodsDTOs);

    }
}
