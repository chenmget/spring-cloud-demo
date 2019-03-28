package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwhalecloud.retail.goods.common.FileConst;
import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.*;
import com.iwhalecloud.retail.goods.dto.req.*;
import com.iwhalecloud.retail.goods.dto.resp.*;
import com.iwhalecloud.retail.goods.entity.*;
import com.iwhalecloud.retail.goods.manager.*;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsCommentsService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mzl
 * @date 2018/11/27
 */
@Slf4j
@Component("prodGoodsService")
@Service
public class ProdGoodsServiceImpl implements ProdGoodsService {

    @Autowired
    private ProdGoodsManager prodGoodsManager;

    @Autowired
    private ProdProductManager prodProductManager;

    @Autowired
    private ProdGoodsSpecManager prodGoodsSpecManager;

    @Autowired
    private ProdSpecValuesManager prodSpecValuesManager;

    @Autowired
    private ProdFileManager prodFileManager;

    @Autowired
    private ProdGoodsRelManager prodGoodsRelManager;

    @Autowired
    private ProdGoodsContractManager prodGoodsContractManager;

    @Autowired
    private GoodsCommentsService goodsCommentsService;

    @Autowired
    private ProdTagRelManager prodTagRelManager;

    @Autowired
    private ProdTagsManager prodTagsManager;

    @Autowired
    private AttrSpecManager attrSpecManager;

    @Autowired
    private ProdGoodsCatManager prodGoodsCatManager;

    @Autowired
    private GoodsGroupRelManager goodsGroupRelManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<ProdGoodsAddResp> addGoods(ProdGoodsAddReq req) {
        log.info("ProdGoodsServiceImpl.addGoods req={}", JSON.toJSONString(req));
        String[] specValues = req.getSpecValues();
        Map<String,List<ProdSpecValues>> specValuesMap = Maps.newConcurrentMap();
        boolean haveSpec = true;
        if (specValues == null || specValues.length == 0 ) {
            haveSpec= false;
        }
        String[] stores = req.getStores();
        String[] specPrices = req.getSpecPrices();
        if (haveSpec) {
            // 校验规格值存在，需要校验规格库存和规格价格的数组长度是否和规格值数组长度一致
            if (stores == null || specPrices == null || specValues.length != stores.length || specValues.length != specPrices.length) {
                return ResultVO.errorEnum(ResultCodeEnum.LENGTH_UNEQUAL);
            }
            // 获取规格值记录并判断规格值是否数据库中存在
            boolean specValueNotExist = getSpecValues(specValues, specValuesMap);
            if (specValueNotExist) {
                return ResultVO.errorEnum(ResultCodeEnum.SPEC_VALUE_NOT_EXIST);
            }
        }
        // 添加ProdGoods表记录
        ProdGoods prodGoods = new ProdGoods();
        BeanUtils.copyProperties(req, prodGoods);
        prodGoodsManager.insert(prodGoods, req.getCatName(), req.getBrandName());
        inertProductAndGoodsSpec(specValuesMap, haveSpec, prodGoods, req);
        // 添加图片
        String detailImageUrl = req.getDetailImageFile();
        if (StringUtils.isNotEmpty(detailImageUrl)) {
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.DETAIL_SUB,detailImageUrl );
        }
        String rollImageUrl  = req.getRollImageFile();
        if (StringUtils.isNotEmpty(rollImageUrl)) {
            // 添加轮播图
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.ROLL_SUB,rollImageUrl );
            String[] imageUrlArr = rollImageUrl.split(",");
            // 默认图片取轮播图第一张
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.DEFAULT_SUB,imageUrlArr[0] );
        }
        // 添加轮播视频
        String rollVideo = req.getRollVideo();
        if (StringUtils.isNotEmpty(rollVideo)) {
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.ROLL_VIDIO_SUB,rollVideo );
        }
        // 添加商品关联推荐
        String[] recommendArr = req.getRecommendList();
        prodGoodsRelManager.insertProdGoodsRel(prodGoods, recommendArr, GoodsConst.GoodsRelType.RECOMMEND.getValue());
        // 添加合约机关联终端
        String[] terminalArr = req.getTerminalList();
        prodGoodsRelManager.insertProdGoodsRel(prodGoods, terminalArr, GoodsConst.GoodsRelType.TERMINAL_PLAN.getValue());
        // 添加合约机关联套餐
        String[] offerList = req.getOfferList();
        prodGoodsRelManager.insertProdGoodsRel(prodGoods, offerList, GoodsConst.GoodsRelType.CONTRACT_OFFER.getValue());
        // 添加合约计划信息
        if (req.getContractPeriod() != null) {
            prodGoodsContractManager.insert(prodGoods.getGoodsId(), req.getContractPeriod());
        }
        // 添加商品标签
        if (req.getTagId() != null) {
            prodTagRelManager.addTagRel(req.getTagId(),prodGoods.getGoodsId());
        }
        // 返回商品id
        ProdGoodsAddResp prodGoodsAddResp = new ProdGoodsAddResp();
        prodGoodsAddResp.setGoodsId(prodGoods.getGoodsId());
        return ResultVO.success(prodGoodsAddResp);
    }

    private boolean getSpecValues(String[] specValues, Map<String, List<ProdSpecValues>> specValuesMap) {
        for (String values : specValues) {
            String[] valueArr = values.split(",");
            List<ProdSpecValues> prodSpecValuesLists = Lists.newArrayList();
            for (String specValue : valueArr) {
                ProdSpecValues prodSpecValues = prodSpecValuesManager.selectSpecValuesByValueName(specValue);
                if (prodSpecValues == null) {
                    log.warn("ProdGoodsServiceImpl.addGoods SPEC_VALUE_NOT_EXIST specValue={}", specValue);
                    return true;
                }
                prodSpecValuesLists.add(prodSpecValues);
            }
            specValuesMap.put(values, prodSpecValuesLists);
        }
        return false;
    }

    private void insertProdProduct(ProdGoodsAddReq req, ProdGoods prodGoods, int index, ProdProduct prodProduct) {
        prodProduct.setGoodsId(prodGoods.getGoodsId());
        prodProduct.setName(prodGoods.getName());
        if (StringUtils.isNotEmpty(prodGoods.getSn())) {
            prodProduct.setSn(prodGoods.getSn() + "_" + index);
        }
        String store = req.getStores() == null ? GoodsConst.DEFULT_STORE : req.getStores()[index];
        // 设置规格库存
        prodProduct.setStore(Long.valueOf(store));
        String specPrice = req.getSpecPrices() == null ? (req.getPrice() == null ? GoodsConst.DEFAULT_SPEC_PRICE :
        req.getPrice().toString()) : req.getSpecPrices()[index];
        // 设置规格价格
        prodProduct.setPrice(Double.valueOf(specPrice));
        prodProduct.setDisabled(GoodsConst.DisabledEnum.ENABLE.getCode().longValue());
        prodProductManager.insert(prodProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<ProdGoodsEditResp> editGoods(ProdGoodsEditReq req) {
        log.info("ProdGoodsServiceImpl.editGoods req={}", JSON.toJSONString(req));
        String goodsId = req.getGoodsId();
        // 判断商品是否存在，不存在则返回商品已删除 TODO
        ProdGoodsDTO prodGoodsDTO = prodGoodsManager.queryGoods(goodsId);
        if (prodGoodsDTO == null || StringUtils.isEmpty(prodGoodsDTO.getGoodsId())) {
            return ResultVO.successMessage("商品不存在或者已经删除");
        }
        String[] specValues = req.getSpecValues();
        Map<String, List<ProdSpecValues>> specValuesMap = Maps.newConcurrentMap();
        boolean haveSpec = true;
        if (specValues == null || specValues.length == 0) {
            haveSpec = false;
        }
        String[] stores = req.getStores();
        String[] specPrices = req.getSpecPrices();
        if (haveSpec) {ResultVO<ProdGoodsEditResp> x = checkSpecValueExist(specValues, specValuesMap, stores, specPrices);
            if (x != null) {
                return x;
            }
        }
        // 修改商品表记录
        ProdGoods prodGoods = new ProdGoods();
        BeanUtils.copyProperties(req, prodGoods);
        prodGoods.setMarketEnable(GoodsConst.MARKET_ENABLE_ZERO);
        prodGoodsManager.updateByPrimaryKey(prodGoods);
        // 修改商品规格先把之前的商品规格和产品表记录删除
        prodGoodsSpecManager.deleteGoodsSpecByGoodsId(goodsId);
        prodProductManager.deleteProdProductByGoodsId(goodsId);
        ProdGoodsAddReq goodsAddReq = new ProdGoodsAddReq();
        BeanUtils.copyProperties(req, goodsAddReq);
        // 插入产品表记录和商品规格关联表记录
        inertProductAndGoodsSpec(specValuesMap, haveSpec, prodGoods, goodsAddReq);
        // 修改商品图片前先删除再新增
        prodFileManager.deleteByGoodsId(goodsId);
        String detailImageUrl = req.getDetailImageFile();
        if (StringUtils.isNotEmpty(detailImageUrl)) {
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.DETAIL_SUB,detailImageUrl );
        }
        String rollImageUrl  = req.getRollImageFile();
        if (StringUtils.isNotEmpty(rollImageUrl)) {
            // 添加轮播图
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.ROLL_SUB,rollImageUrl );
            String[] imageUrlArr = rollImageUrl.split(",");
            // 默认图片取轮播图第一张
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.DEFAULT_SUB,imageUrlArr[0] );
        }
        // 添加轮播视频
        String rollVideo = req.getRollVideo();
        if (StringUtils.isNotEmpty(rollVideo)) {
            prodFileManager.addGoodsImage(prodGoods.getGoodsId(), FileConst.SubType.ROLL_VIDIO_SUB,rollVideo );
        }
        // 修改商品关联表记录前先删除再新增
        prodGoodsRelManager.deleteGoodsRelByZGoodsId(goodsId);
        // 添加商品关联推荐
        String[] recommendArr = req.getRecommendList();
        prodGoodsRelManager.insertProdGoodsRel(prodGoods, recommendArr, GoodsConst.GoodsRelType.RECOMMEND.getValue());
        // 添加合约机关联终端
        String[] terminalArr = req.getTerminalList();
        prodGoodsRelManager.insertProdGoodsRel(prodGoods, terminalArr, GoodsConst.GoodsRelType.TERMINAL_PLAN.getValue());
        // 添加合约机关联套餐
        String[] offerList = req.getOfferList();
        prodGoodsRelManager.insertProdGoodsRel(prodGoods, offerList, GoodsConst.GoodsRelType.CONTRACT_OFFER.getValue());
        // 修改合约计划前先删除再新增
        if (req.getContractPeriod() != null) {
            prodGoodsContractManager.delGoodsContractByGoodsId(goodsId);
            prodGoodsContractManager.insert(goodsId, req.getContractPeriod());
        }
        // 修改商品标签前先删除再新增
        if (req.getTagId() != null) {
            prodTagRelManager.deleteTagRelByGoodsId(goodsId);
            prodTagRelManager.addTagRel(req.getTagId(),prodGoods.getGoodsId());
        }
        ProdGoodsEditResp resp = new ProdGoodsEditResp();
        resp.setResult(true);
        return ResultVO.success(resp);
    }

    private void inertProductAndGoodsSpec(Map<String, List<ProdSpecValues>> specValuesMap, boolean haveSpec, ProdGoods prodGoods, ProdGoodsAddReq goodsAddReq) {
        int index = 0;
        if (haveSpec) {
            // 循环遍历规格列表，添加ProdProduct表记录 同时添加ProdGoodsSpec表记录
            for (Map.Entry<String, List<ProdSpecValues>> entry : specValuesMap.entrySet()) {
                ProdProduct prodProduct = new ProdProduct();
                prodProduct.setSpecs(entry.getKey());
                // 添加产品表记录
                insertProdProduct(goodsAddReq, prodGoods, index, prodProduct);
                // 添加商品规格关联记录
                List<ProdSpecValues> prodSpecValuesList = entry.getValue();
                prodGoodsSpecManager.insertProdGoodsSpec(prodGoods, prodSpecValuesList, prodProduct);
                index += 1;
            }
        } else {
            ProdProduct prodProduct = new ProdProduct();
            // 添加产品表记录
            insertProdProduct(goodsAddReq, prodGoods, index, prodProduct);
        }
    }

    private ResultVO<ProdGoodsEditResp> checkSpecValueExist(String[] specValues, Map<String, List<ProdSpecValues>>
            specValuesMap, String[] stores, String[] specPrices) {
        // 校验规格值存在，需要校验规格库存和规格价格的数组长度是否和规格值数组长度一致
        if (stores == null || specPrices == null || specValues.length != stores.length || specValues.length != specPrices.length) {
            return ResultVO.errorEnum(ResultCodeEnum.LENGTH_UNEQUAL);
        }
        // 获取规格值记录并判断规格值是否数据库中存在
        boolean specValueNotExist = getSpecValues(specValues, specValuesMap);
        if (specValueNotExist) {
            return ResultVO.errorEnum(ResultCodeEnum.SPEC_VALUE_NOT_EXIST);
        }
        return null;
    }

    @Override
    public ResultVO<ProdGoodsDeleteResp> deleteGoods(ProdGoodsDeleteReq req) {
        log.info("ProdGoodsServiceImpl.deleteGoods req={}", JSON.toJSONString(req));
        String goodsId = req.getGoodsId();
        if (StringUtils.isEmpty(goodsId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        int result = prodGoodsManager.deleteGoodsById(goodsId);
        if (result > 0) {
            result = prodProductManager.updateDisableByGoodsId(goodsId);
        }
        ProdGoodsDeleteResp prodGoodsDeleteResp = new ProdGoodsDeleteResp();
        prodGoodsDeleteResp.setResult(result > 0);
        return ResultVO.success(prodGoodsDeleteResp);
    }

    @Override
    public ResultVO<Page<ProdGoodsDTO>> queryGoodsForPage(ProdGoodsQueryReq req) {
        if (req.getPageNo() == null || req.getPageSize() == null) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        Page<ProdGoodsDTO> prodGoodsDTOPage = prodGoodsManager.queryGoodsForPage(req);
        List<ProdGoodsDTO> prodGoodsDTOList = prodGoodsDTOPage.getRecords();
        if (prodGoodsDTOPage == null  || CollectionUtils.isEmpty(prodGoodsDTOList)) {
            Page<ProdGoodsDTO> page = new Page<>();
            page.setRecords(Lists.newArrayList());
            return ResultVO.success(page);
        }
        // 查询商品默认图片
        List<String> goodsIdList = prodGoodsDTOList.stream().map(ProdGoodsDTO :: getGoodsId).collect(Collectors.toList());
        for (ProdGoodsDTO prodGoods : prodGoodsDTOList) {
            List<ProdFileDTO> prodFileDTOList = prodFileManager.queryGoodsImage(prodGoods.getGoodsId(),FileConst.SubType.DEFAULT_SUB);
            if (CollectionUtils.isNotEmpty(prodFileDTOList)) {
                prodGoods.setDefaultImage(prodFileDTOList.get(0).getFileUrl());
            }
        }
        ResultVO<List<CommentsGoodsRateReqDTO>>  listResultVO = goodsCommentsService.queryCommentsGoodsRate(goodsIdList);
        if (!listResultVO.isSuccess() || CollectionUtils.isEmpty(listResultVO.getResultData())) {
            return ResultVO.success(prodGoodsDTOPage);
        }
        List<CommentsGoodsRateReqDTO> reqDTOList = listResultVO.getResultData();
        // 查询商品评论数和好评率
        for (ProdGoodsDTO prodGoods : prodGoodsDTOList) {
            for (CommentsGoodsRateReqDTO rateReq : reqDTOList) {
                if (prodGoods.getGoodsId().equals(rateReq.getGoodsId())) {
                    prodGoods.setGoodsCommentsNum(rateReq.getCommentsNum());
                    prodGoods.setGoodsCommentsRate(rateReq.getGoodsCommentsRate());
                    break;
                }
            }
        }
        return ResultVO.success(prodGoodsDTOPage);
    }

    @Override
    public ResultVO<List<ProdGoodsDTO>> listGoods(ProdGoodsListReq req) {

        List<ProdGoodsDTO> prodGoodsList = prodGoodsManager.listGoods(req);
        // 查询商品默认图片
        for (ProdGoodsDTO prodGoods : prodGoodsList) {
            List<ProdFileDTO> prodFileDTOList = prodFileManager.queryGoodsImage(prodGoods.getGoodsId(),FileConst.SubType.DEFAULT_SUB);
            if (CollectionUtils.isNotEmpty(prodFileDTOList)) {
                prodGoods.setDefaultImage(prodFileDTOList.get(0).getFileUrl());
            }
        }
        return ResultVO.success(prodGoodsList);
    }

    @Override
    public ResultVO<ProdGoodsDetailResp> queryGoodsDetail(String goodsId) {
        if (StringUtils.isEmpty(goodsId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        // 查询商品信息
        ProdGoodsDTO prodGoodsDTO = prodGoodsManager.queryGoods(goodsId);
        if (prodGoodsDTO == null) {
            return ResultVO.successMessage("商品为空");
        }
        ProdGoodsDetailResp resp = new ProdGoodsDetailResp();
        BeanUtils.copyProperties(prodGoodsDTO, resp);
        // 查询商品图片和视频
        List<ProdFileDTO> prodFileDTOList = prodFileManager.queryGoodsImage(goodsId);
        if (CollectionUtils.isNotEmpty(prodFileDTOList)) {
            for (ProdFileDTO profileDto : prodFileDTOList) {
                if (FileConst.SubType.DEFAULT_SUB.getType().equals(profileDto.getSubType())) {
                    resp.setDefaultImage(profileDto.getFileUrl());
                }
                else if (FileConst.SubType.DETAIL_SUB.getType().equals(profileDto.getSubType())) {
                    resp.setDetailImage(profileDto.getFileUrl());
                }
                else if (FileConst.SubType.ROLL_SUB.getType().equals(profileDto.getSubType())) {
                    resp.setRollImage(profileDto.getFileUrl());
                }
                else if (FileConst.SubType.ROLL_VIDIO_SUB.getType().equals(profileDto.getSubType())) {
                    resp.setRollVideo(profileDto.getFileUrl());
                }
            }
        }
        List<ProdGoodsRel> goodRelList = prodGoodsRelManager.getGoodsRelByZGoodsId(goodsId);
        // 查询商品关联推荐
        List<String> recommendList = Lists.newArrayList();
        // 合约计划关联终端
        List<String> terminalList = Lists.newArrayList();
        // 合约计划关套餐
        List<String> contractOfferList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(goodRelList)) {
            for (ProdGoodsRel goodRel : goodRelList) {
                if (GoodsConst.GoodsRelType.RECOMMEND.getValue().equals(goodRel.getRelType())) {
                    recommendList.add(goodRel.getAGoodsId());
                }
                if (GoodsConst.GoodsRelType.TERMINAL_PLAN.getValue().equals(goodRel.getRelType())) {
                    terminalList.add(goodRel.getAGoodsId());
                }
                if (GoodsConst.GoodsRelType.CONTRACT_OFFER.getValue().equals(goodRel.getRelType())) {
                    contractOfferList.add(goodRel.getAGoodsId());
                }
            }
        }
        resp.setRecommendList(recommendList);
        resp.setTerminalList(terminalList);
        resp.setOfferList(contractOfferList);
        resp.setAttrSpecList(attrSpecManager.queryAttrSpecWithInstValue(prodGoodsDTO.getCatId(),goodsId));
        // 合约期
        ProdGoodsContract goodsContract = prodGoodsContractManager.getGoodsContractByGoodsId(goodsId);
        if (goodsContract != null) {
            resp.setContractPeriod(goodsContract.getContractPeriod());
        }
        // 商品标签
        List<ProdTagsDTO> tagsDTOList = prodTagsManager.getTagsByGoodsId(goodsId);
        if (CollectionUtils.isNotEmpty(tagsDTOList)) {
            resp.setTagList(tagsDTOList);
        } else {
            resp.setTagList(Lists.newArrayList());
        }
        // 分类名称
        if (StringUtils.isNotEmpty(resp.getCatId())) {
            ProdGoodsCatsListByIdReq dto = new ProdGoodsCatsListByIdReq();
            dto.setCatId(resp.getCatId());
            List<ProdGoodsCatListResp> catRespList = prodGoodsCatManager.listCats(dto);
            if (CollectionUtils.isNotEmpty(catRespList)) {
                resp.setCatName(catRespList.get(0).getName());
            }
        }
        List<ProdProductDTO> prodProductDTOList = prodProductManager.queryProductByGoodsId(goodsId);
        resp.setProductList(prodProductDTOList);
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<QryGoodsByProductIdResp> qryGoodsByProductId(String productId) {
        ProdProductDTO prodProductDTO = prodProductManager.getProduct(productId);
        if (prodProductDTO == null) {
            return ResultVO.successMessage("查询为空");
        }
        ResultVO<ProdGoodsDetailResp> respResultVO = queryGoodsDetail(prodProductDTO.getGoodsId());
        if (!respResultVO.isSuccess() || respResultVO.getResultData() == null) {
            return ResultVO.successMessage("查询为空");
        }
        QryGoodsByProductIdResp qryGoodsByProductIdResp = new QryGoodsByProductIdResp();
        BeanUtils.copyProperties(respResultVO.getResultData(),qryGoodsByProductIdResp);
        qryGoodsByProductIdResp.setSpecs(prodProductDTO.getSpecs());
        return ResultVO.success(qryGoodsByProductIdResp);
    }

    @Override
    public ResultVO<Boolean> updateMarketEnableByPrimaryKey(String goodsId, Integer marketEnable) {
        int result = prodGoodsManager.updateMarketEnableByPrimaryKey(goodsId,marketEnable);
        boolean flag = result > 0;
        if (flag && GoodsConst.MARKET_ENABLE_ZERO.equals(marketEnable)) {
            goodsGroupRelManager.deleteGoodsGroupRel(goodsId);
        }
        return ResultVO.success(flag);
    }

    @Override
    public ResultVO<ProdGoodsQueryByIdReq> queryGoods(String goodsId) {
        if (StringUtils.isEmpty(goodsId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        // 查询商品信息
        ProdGoodsDTO prodGoodsDTO = prodGoodsManager.queryGoods(goodsId);
        if (prodGoodsDTO == null) {
            return ResultVO.successMessage("查询为空");
        }
        ProdGoodsQueryByIdReq resp = new ProdGoodsQueryByIdReq();
        BeanUtils.copyProperties(prodGoodsDTO, resp);
        return ResultVO.success(resp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<Boolean> updateBuyCountById(List<UpdateBuyCountByIdReq> req) {
        if (CollectionUtils.isEmpty(req)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        for (UpdateBuyCountByIdReq item : req) {
            // 查询购买数量
            ProdGoodsDTO prodGoodsDTO = prodGoodsManager.queryGoods(item.getGoodsId());
            if (prodGoodsDTO == null) {
                return ResultVO.error("商品为空");
            }
            Long buyCountQuery = prodGoodsDTO.getBuyCount() == null ? 0 : prodGoodsDTO.getBuyCount();
            buyCountQuery += item.getBuyCount();
            prodGoodsManager.updateBuyCountById(item.getGoodsId(), buyCountQuery);
        }
        return ResultVO.success(true);
    }

}
