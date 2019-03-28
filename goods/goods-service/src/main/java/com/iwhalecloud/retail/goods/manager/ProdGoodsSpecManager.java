package com.iwhalecloud.retail.goods.manager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods.entity.ProdGoods;
import com.iwhalecloud.retail.goods.entity.ProdGoodsSpec;
import com.iwhalecloud.retail.goods.entity.ProdProduct;
import com.iwhalecloud.retail.goods.entity.ProdSpecValues;
import com.iwhalecloud.retail.goods.mapper.ProdGoodsSpecMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProdGoodsSpecManager{
    @Resource
    private ProdGoodsSpecMapper prodGoodsSpecMapper;

    public void insertProdGoodsSpec(ProdGoods prodGoods, List<ProdSpecValues> prodSpecValuesList, ProdProduct prodProduct) {
        // 插入商品规格关联表
        for (ProdSpecValues prodSpecValues : prodSpecValuesList) {
            ProdGoodsSpec prodGoodsSpec = new ProdGoodsSpec();
            prodGoodsSpec.setGoodsId(prodGoods.getGoodsId());
            prodGoodsSpec.setProductId(prodProduct.getProductId());
            prodGoodsSpec.setSpecId(prodSpecValues.getSpecId());
            prodGoodsSpec.setSpecValueId(prodSpecValues.getSpecValueId());
            prodGoodsSpecMapper.insert(prodGoodsSpec);
        }
    }

    public List<ProdGoodsSpec> selectGoodsSpecByGoodsId(String goodsId) {
        QueryWrapper<ProdGoodsSpec> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("GOODS_ID", goodsId);
        List<ProdGoodsSpec> prodGoodsSpecList = prodGoodsSpecMapper.selectList(queryWrapper);
        return prodGoodsSpecList;
    }

    public int deleteGoodsSpecByGoodsId(String goodsId) {
        List<ProdGoodsSpec> goodsSpecList = selectGoodsSpecByGoodsId(goodsId);
        if (CollectionUtils.isEmpty(goodsSpecList)) {
            return 0;
        }
        List<String> relIdList = goodsSpecList.stream().map(ProdGoodsSpec::getRelId).collect(Collectors.toList());
        return prodGoodsSpecMapper.deleteBatchIds(relIdList);
    }
}
