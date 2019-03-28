package com.iwhalecloud.retail.web.controller.goods;


import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.ComplexGoodsGetResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsDetailResp;
import com.iwhalecloud.retail.goods.dto.resp.RecommendGoodsInfoQueryResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsCatService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsRelService;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsService;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/goodsRel")
public class GoodsRelController {

    @Reference
    private ProdGoodsRelService goodsRelService;

    @Reference
    private ProdGoodsService prodGoodsService;

    @Reference
    private ProdGoodsCatService goodsCatsService;

    /**
     * 查询商品关联推荐
     * @param goodsId 商品ID
     */
    @GetMapping("/getRecommendGoodsInfo")
    public ResultVO<List<RecommendGoodsInfoQueryResp>> getRecommendGoodsInfo(@RequestParam(value = "goodsId", required = false) String goodsId,
        @RequestParam(value = "catId", required = false) String catId, @RequestParam(value = "marketEnable", required = false) Integer marketEnable) {
        if (StringUtils.isEmpty(goodsId) && StringUtils.isEmpty(catId)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        List<RecommendGoodsInfoQueryResp> respList = Lists.newArrayList();
        if (!StringUtils.isEmpty(goodsId)) {
            ResultVO resultVO = goodsRelService.getGoodsRelByZGoodsId(goodsId, GoodsConst.GoodsRelType.RECOMMEND);
            if (resultVO.isSuccess() && resultVO.getResultData() != null) {
                List<String> goodsIdList = (List<String>)resultVO.getResultData();
                for (String id :goodsIdList) {
                    buildGoodsDetail(respList, id, marketEnable);
                }
            }
        }
        if (!StringUtils.isEmpty(catId)) {
            ResultVO<List<ComplexGoodsGetResp>> resultVO = goodsCatsService.getComplexGoods(catId);
            if (resultVO.isSuccess() && resultVO.getResultData() != null) {
                List<ComplexGoodsGetResp> complexGoodsGetRespList = resultVO.getResultData();
                for (ComplexGoodsGetResp resp : complexGoodsGetRespList) {
                    String id = resp.getGoodsId();
                    buildGoodsDetail(respList, id, marketEnable);
                }
            }
        }
        return ResultVO.success(respList);
    }

    private void buildGoodsDetail(List<RecommendGoodsInfoQueryResp> respList, String id, Integer marketEnable) {
        ResultVO<ProdGoodsDetailResp> respResultVO = prodGoodsService.queryGoodsDetail(id);
        if (respResultVO.isSuccess() && respResultVO.getResultData() != null) {
            ProdGoodsDetailResp prodGoodsDetailResp = respResultVO.getResultData();
            RecommendGoodsInfoQueryResp resp = new RecommendGoodsInfoQueryResp();
            resp.setGoodsId(prodGoodsDetailResp.getGoodsId());
            resp.setName(prodGoodsDetailResp.getName());
            resp.setRollImageFile(prodGoodsDetailResp.getRollImage());
            resp.setMktPrice(prodGoodsDetailResp.getMktprice());
            resp.setPrice(prodGoodsDetailResp.getPrice());
            if (marketEnable == null) {
                respList.add(resp);
            }
            else if (marketEnable.equals(prodGoodsDetailResp.getMarketEnable())) {
                respList.add(resp);
            }
        }
    }

    /**
     * 合约计划关联套餐
     * @param goodsId 合约计划ID
     */
    @GetMapping(value="getContractOfferByPlanId")
    public ResultVO<List<ProdGoodsDetailResp>> getContractOfferByPlanId(@RequestParam(value = "goodsId") String goodsId,
        @RequestParam(value = "marketEnable", required = false) Integer marketEnable) {
        ResultVO resultVO = goodsRelService.getGoodsRelByZGoodsId(goodsId, GoodsConst.GoodsRelType.CONTRACT_OFFER);
        List<ProdGoodsDetailResp> respList = getProdGoodsDetailResps(resultVO, marketEnable);
        return ResultVO.success(respList);
    }

    private List<ProdGoodsDetailResp> getProdGoodsDetailResps(ResultVO resultVO,Integer marketEnable) {
        List<String> list = (List<String>)resultVO.getResultData();
        List<ProdGoodsDetailResp> respList = Lists.newArrayList();
        if (resultVO.isSuccess() && !CollectionUtils.isEmpty(list)) {
            for (String id : list) {
                ResultVO<ProdGoodsDetailResp> respResultVO = prodGoodsService.queryGoodsDetail(id);
                if (respResultVO.isSuccess() && respResultVO.getResultData() != null) {
                    ProdGoodsDetailResp detailResp = respResultVO.getResultData();
                    if (marketEnable == null) {
                        respList.add(detailResp);
                    }
                    else if (marketEnable.equals(detailResp.getMarketEnable())) {
                        respList.add(detailResp);
                    }
                }
            }
        }
        return respList;
    }

    /**
     * 合约计划关联终端
     * @param goodsId 合约计划ID
     */
    @GetMapping(value="getTerminalByPlanId")
    public ResultVO<List<ProdGoodsDetailResp>> getTerminalByPlanId(@RequestParam(value = "goodsId") String goodsId,
        @RequestParam(value = "marketEnable", required = false) Integer marketEnable) {
        ResultVO resultVO = goodsRelService.getGoodsRelByZGoodsId(goodsId, GoodsConst.GoodsRelType.TERMINAL_PLAN);
        List<ProdGoodsDetailResp> respList = getProdGoodsDetailResps(resultVO, marketEnable);
        return ResultVO.success(respList);
    }

    /**
     * 终端关联合约计划
     * @param goodsId 合约计划ID
     */
    @GetMapping(value="getContractPlanByTerminal")
    public ResultVO<List<ProdGoodsDetailResp>> getContractPlanByTerminal(@RequestParam(value = "goodsId") String goodsId,
        @RequestParam(value = "marketEnable", required = false) Integer marketEnable) {
        ResultVO resultVO = goodsRelService.getContractPlanByAGoodsId(goodsId, GoodsConst.GoodsRelType.TERMINAL_PLAN);
        List<ProdGoodsDetailResp> respList = getProdGoodsDetailResps(resultVO, marketEnable);
        return ResultVO.success(respList);
    }
}