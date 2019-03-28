package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ProdProductDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.manager.ProdProductManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Component("prodProductService")
@Service
public class ProdProductServiceImpl implements ProdProductService {

    @Autowired
    private ProdProductManager prodProductManager;


    @Override
    public ProdProductDTO getProduct(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw new IllegalArgumentException("产品ID不能为空");
        }
        return prodProductManager.getProduct(productId);
    }

    @Override
    public ResultVO<List<ProdProductDTO>> queryProductByGoodsId(String goodsId) {

        if (StringUtils.isEmpty(goodsId)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        return ResultVO.success(prodProductManager.queryProductByGoodsId(goodsId));
    }
}