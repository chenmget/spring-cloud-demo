package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.dto.ProdTagsDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.service.dubbo.ProdTagsService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mzl
 * @date 2018/11/9
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/tags")
public class GoodsTagsController {

    @Reference
    private ProdTagsService prodTagsService;
    @ApiOperation(value = "查询标签", notes = "通过goodsId查询标签")
    @GetMapping("/getTagsByGoodsId")
    public ResultVO getTagsByGoodsId(@RequestParam(value = "goodsId") String goodsId) {
        if(StringUtils.isEmpty(goodsId)){
            return ResultVO.error("goodsId is must not be null");
        }
        List<ProdTagsDTO> list =prodTagsService.getTagsByGoodsId(goodsId);
        if(CollectionUtils.isEmpty(list)){
            return ResultVO.error("list is  null");
        }
        return ResultVO.success(list);
    }

    @ApiOperation(value = "查询标签列表", notes = "查询标签列表")
    @GetMapping("/listProdTags")
    public ResultVO listProdTags() {
        List<ProdTagsDTO> list =prodTagsService.listProdTags();
        if(CollectionUtils.isEmpty(list)){
            return ResultVO.success(Lists.newArrayList());
        }
        return ResultVO.success(list);
    }
}
