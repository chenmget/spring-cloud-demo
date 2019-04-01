package com.iwhalecloud.retail.goods2b.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsAddResp;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsOperateResp;
import com.iwhalecloud.retail.goods2b.service.GoodsDockingAbilityService;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatService;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/30.
 */
@Service
public class GoodsDockingAbilityServiceImpl implements GoodsDockingAbilityService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CatService catService;

    @Autowired
    private ProductService productService;

    @Override
    public Map<String, Object> addProdCatByZTAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        CatAddReq req = new CatAddReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<CatAddReq>(){});
            ResultVO<String> resultVO = catService.addProdCatByZT(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateProdCatByZTAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        CatUpdateReq req = new CatUpdateReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<CatUpdateReq>(){});
            ResultVO<Boolean> resultVO = catService.updateProdCatByZT(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteProdCatAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        CatQueryReq req = new CatQueryReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<CatQueryReq>(){});
            ResultVO<Boolean> resultVO = catService.deleteProdCat(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> addProductByZTAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        ProductAddReq req = new ProductAddReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<ProductAddReq>(){});
            ResultVO<String> resultVO = productService.addProductByZT(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateProdProductAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        ProductUpdateReq req = new ProductUpdateReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<ProductUpdateReq>(){});
            ResultVO<Integer> resultVO = productService.updateProdProduct(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteProdProductAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        PrdoProductDeleteReq req = new PrdoProductDeleteReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<PrdoProductDeleteReq>(){});
            ResultVO<Integer> resultVO = productService.deleteProdProduct(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> addGoodsByZTAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        GoodsAddByZTReq req = new GoodsAddByZTReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<GoodsAddByZTReq>(){});
            ResultVO<GoodsAddResp> resultVO = goodsService.addGoodsByZT(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> editGoodsByZTAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        GoodsEditByZTReq req = new GoodsEditByZTReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<GoodsEditByZTReq>(){});
            ResultVO<GoodsOperateResp> resultVO = goodsService.editGoodsByZT(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteGoodsAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        GoodsDeleteReq req = new GoodsDeleteReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<GoodsDeleteReq>(){});
            ResultVO<GoodsOperateResp> resultVO = goodsService.deleteGoods(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }

    @Override
    public Map<String, Object> updateMarketEnableAbility(String str) throws Exception {
        HashMap<String, Object> resultMap=new HashMap<String, Object>();
//        System.out.println(str);
        resultMap.put("code", "0");
        resultMap.put("msg", "调用成功");
        GoodsMarketEnableReq req = new GoodsMarketEnableReq();
        if(str instanceof String){
            req = JSON.parseObject(str, new TypeReference<GoodsMarketEnableReq>(){});
            ResultVO<GoodsOperateResp> resultVO = goodsService.updateMarketEnable(req);
            if(resultVO.isSuccess() && null != resultVO.getResultData()){
                resultMap.put("data",resultVO.getResultData());
                return resultMap;
            }
        }

        resultMap.put("code", "9999");
        resultMap.put("msg", "调用失败");
        return resultMap;
    }
}
