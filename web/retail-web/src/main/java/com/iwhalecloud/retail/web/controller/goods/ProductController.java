package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.service.dubbo.ProdProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mzl
 * @date 2018/12/6
 */
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    @Reference
    private ProdProductService prodProductService;

    /**
     * 根据商品ID查询产品
     */
    @GetMapping(value="queryProductByGoodsId")
    public ResultVO queryProductByGoodsId(@RequestParam(value = "goodsId") String goodsId) {
        log.info("ProductController.queryProductByGoodsId goodsId={}", goodsId);
        return prodProductService.queryProductByGoodsId(goodsId);
    }
}
