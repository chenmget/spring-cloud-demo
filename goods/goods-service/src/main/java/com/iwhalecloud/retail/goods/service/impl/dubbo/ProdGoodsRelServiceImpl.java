package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.entity.ProdGoodsRel;
import com.iwhalecloud.retail.goods.manager.ProdGoodsRelManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsRelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("prodGoodsRelService")
@Service
public class ProdGoodsRelServiceImpl implements ProdGoodsRelService {

    @Autowired
    private ProdGoodsRelManager prodGoodsRelManager;


    @Override
    public ResultVO<List<String>> getGoodsRelByZGoodsId(String goodsId, GoodsConst.GoodsRelType goodsRelType) {
        if (StringUtils.isEmpty(goodsId) || goodsRelType == null) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        List<String> goodsIdList = Lists.newArrayList();
        List<ProdGoodsRel> prodGoodsRelList = prodGoodsRelManager.getGoodsRelByZGoodsId(goodsId, goodsRelType.getValue());
        if (CollectionUtils.isEmpty(prodGoodsRelList)) {
            return ResultVO.success(goodsIdList);
        }
        goodsIdList = prodGoodsRelList.stream().map(ProdGoodsRel :: getAGoodsId).collect(Collectors.toList());
        return ResultVO.success(goodsIdList);
    }

    @Override
    public ResultVO<List<String>> getContractPlanByAGoodsId(String goodsId, GoodsConst.GoodsRelType goodsRelType) {
        if (StringUtils.isEmpty(goodsId) || goodsRelType == null) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        List<String> goodsIdList = Lists.newArrayList();
        List<ProdGoodsRel> prodGoodsRelList = prodGoodsRelManager.getGoodsRelByAGoodsId(goodsId, goodsRelType.getValue());
        if (CollectionUtils.isEmpty(prodGoodsRelList)) {
            return ResultVO.success(goodsIdList);
        }
        goodsIdList = prodGoodsRelList.stream().map(ProdGoodsRel :: getZGoodsId).collect(Collectors.toList());
        return ResultVO.success(goodsIdList);
    }
}