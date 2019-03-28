package com.iwhalecloud.retail.goods.manager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.common.GoodsConst;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.entity.ProdProduct;
import com.iwhalecloud.retail.goods.mapper.ProdProductMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProdProductManager {
    @Resource
    private ProdProductMapper prodProductMapper;

    public int insert(ProdProduct prodProduct) {
        return prodProductMapper.insert(prodProduct);
    }


    /**
     * 根据产品ID获取产品对象
     * @param productId 产品ID
     * @return
     */
    public ProdProductDTO getProduct(String productId) {

        QueryWrapper<ProdProduct> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("DISABLED", GoodsConst.DisabledEnum.ENABLE.getCode().longValue());
        productQueryWrapper.eq("PRODUCT_ID",productId);
        ProdProduct prodProduct = prodProductMapper.selectOne(productQueryWrapper);

        if (prodProduct==null) {
            return null;
        }
        ProdProductDTO productDto = new ProdProductDTO();
        BeanUtils.copyProperties(prodProduct,productDto);

        return productDto;
    }

    /**
     * 根据商品ID查询产品
     * @param goodsId 商品ID
     * @return
     */
    public List<ProdProductDTO> queryProductByGoodsId(String goodsId)
    {
        QueryWrapper<ProdProduct> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("DISABLED", GoodsConst.DisabledEnum.ENABLE.getCode().longValue());
        productQueryWrapper.eq("GOODS_ID",goodsId);
        List<ProdProduct> prodGoodsList = prodProductMapper.selectList(productQueryWrapper);
        List<ProdProductDTO> prodProductDTOList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(prodGoodsList)) {
            return prodProductDTOList;
        }
        for (ProdProduct product : prodGoodsList) {
            ProdProductDTO productDTO = new ProdProductDTO();
            BeanUtils.copyProperties(product, productDTO);
            prodProductDTOList.add(productDTO);
        }
        return prodProductDTOList;
    }

    public int updateDisableByGoodsId(String goodsId) {
        UpdateWrapper<ProdProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("GOODS_ID",goodsId);
        ProdProduct prodProduct = new ProdProduct();
        prodProduct.setDisabled(GoodsConst.DisabledEnum.DISABLE.getCode().longValue());
        return prodProductMapper.update(prodProduct, updateWrapper);
    }

    public int deleteProdProductByGoodsId(String goodsId) {
        List<ProdProductDTO> productDTOList = queryProductByGoodsId(goodsId);
        if (CollectionUtils.isEmpty(productDTOList)) {
            return 0;
        }
        List<String> ids = productDTOList.stream().map(ProdProductDTO :: getProductId).collect(Collectors.toList());
        return prodProductMapper.deleteBatchIds(ids);
    }
}
