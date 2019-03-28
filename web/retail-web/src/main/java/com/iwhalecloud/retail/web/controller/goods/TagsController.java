package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.goods.dto.ResultVO;
//import com.iwhalecloud.retail.goods.service.dubbo.TagsOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author mzl
 * @date 2018/11/9
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/tags")
public class TagsController {

//    @Reference
//    private TagsOpenService tagsOpenService;
//
//    @GetMapping("/getTagsByGoodsId")
//    public ResultVO getTagsByGoodsId(@RequestParam(value = "goodsId") String goodsId) {
//        return tagsOpenService.getTagsByGoodsId(goodsId);
//    }
}
