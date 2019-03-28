package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsSpecListResp;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsSpecService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mzl
 * @date 2018/11/30
 */
@RestController
@RequestMapping("/api/goodsSpec")
@Slf4j
public class GoodsSpecController {

    @Reference
    private ProdGoodsSpecService prodGoodsSpecService;

    /**
     * 商品列表查询
     * @param goodsId
     * @return
     */
    @GetMapping(value="listProdGoodsSpec")
    public ResultVO<ProdGoodsSpecListResp> listProdGoodsSpec(@RequestParam(value = "goodsId") String goodsId) {
        return prodGoodsSpecService.listProdGoodsSpec(goodsId);

    }
}
