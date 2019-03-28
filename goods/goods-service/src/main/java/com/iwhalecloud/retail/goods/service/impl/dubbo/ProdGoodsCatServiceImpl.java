package com.iwhalecloud.retail.goods.service.impl.dubbo;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.ProdCatComplexAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsCatsListByIdReq;
import com.iwhalecloud.retail.goods.dto.resp.ComplexGoodsGetResp;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsCatListResp;
import com.iwhalecloud.retail.goods.manager.ProdCatComplexManager;
import com.iwhalecloud.retail.goods.manager.ProdGoodsCatManager;
import com.iwhalecloud.retail.goods.manager.ProdGoodsManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsCatService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component("prodGoodsCatService")
@Service
public class ProdGoodsCatServiceImpl implements ProdGoodsCatService {

	@Autowired
    private ProdGoodsManager prodGoodsManager;
    @Autowired
    private ProdGoodsCatManager prodGoodsCatManager;
    @Autowired
    private ProdCatComplexManager prodCatComplexManager;

    @Override
    public ResultVO<List<ProdGoodsCatListResp>> listCats(ProdGoodsCatsListByIdReq req) {
        log.info("GoodsCatsServiceImpl.listCats req={}", req);
        ResultVO<List<ProdGoodsCatListResp>> resultVo = new ResultVO<List<ProdGoodsCatListResp>>();
    	resultVo.setResultMsg("查询成功");
    	resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        List<ProdGoodsCatListResp> complexGoodsGetResp = prodGoodsCatManager.listCats(req);
        resultVo.setResultData(complexGoodsGetResp);
        
        return resultVo;
    }

    @Override
    public ResultVO<List<ComplexGoodsGetResp>> getComplexGoods(String catId) {
    	ResultVO<List<ComplexGoodsGetResp>> resultVo = new ResultVO<List<ComplexGoodsGetResp>>();
    	resultVo.setResultMsg("查询成功");
    	resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
    	List<ComplexGoodsGetResp> list = prodGoodsManager.getComplexGoodsByCatId(catId);
    	resultVo.setResultData(list);
        return resultVo;
    }

    @Override
    @Transactional
    public ResultVO<Integer> addCatComplex(List<ProdCatComplexAddReq> req) {

        if (req==null || req.size()<=0) {
            log.info("ProdGoodsCatServiceImpl.addCatComplex req is null ");
            return ResultVO.error("推荐的分类商品为空");
        }

        log.info("ProdGoodsCatServiceImpl.addCatComplex req = {}", JSON.toJSONString(req));
        ResultVO<Integer> resultVo = new ResultVO<Integer>();
        resultVo.setResultMsg("添加成功");
        resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());

        //1、删除已有分类的推荐商品
        prodCatComplexManager.delete(req.get(0).getCatId());

        //2、增加分类的推荐商品
        Integer num = 0;
        for (ProdCatComplexAddReq prodCatComplexAddReq : req) {
            num += prodCatComplexManager.insert(prodCatComplexAddReq);
        }
        resultVo.setResultData(num);
        return resultVo;
    }

}