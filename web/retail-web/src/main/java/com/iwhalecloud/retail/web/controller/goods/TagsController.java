package com.iwhalecloud.retail.web.controller.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.iwhalecloud.retail.goods.service.dubbo.TagsOpenService;

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
