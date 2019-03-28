package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.TagTypeDTO;
import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdTagDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagGetByGoodsIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagGetByTagReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mzl
 * @date 2018/12/26
 */
@RestController
@RequestMapping("/api/b2b/tags")
@Slf4j
public class TagsB2BController {

    @Reference
    private TagsService tagsService;

    @ApiOperation(value = "根据商品ID查询标签", notes = "根据商品ID查询标签")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getTagsByGoodsId")
    public ResultVO<List<TagsDTO>> getTagsByGoodsId(@RequestParam(value = "goodsId") String goodsId){
        log.info("TagsB2BController getTagsByGoodsId goodsId={} ", goodsId);
        TagGetByGoodsIdReq req = new TagGetByGoodsIdReq();
        req.setGoodsId(goodsId);
        return tagsService.getTagsByGoodsId(req);
    }

    @ApiOperation(value = "查询标签列表", notes = "查询标签列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listProdTags")
    public ResultVO<List<TagsDTO>> listProdTags(){
        return tagsService.listProdTags();
    }

    @ApiOperation(value = "添加标签", notes = "添加标签")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/addProdTags")
    public ResultVO<String> addProdTags(@RequestBody TagsDTO req){
        log.info("TagsB2BController addProdTags req={} ", JSON.toJSON(req));
        return tagsService.addProdTags(req);
    }

    @ApiOperation(value = "编辑标签", notes = "编辑标签")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/editProdTags")
    public ResultVO<Boolean> editProdTags(@RequestBody TagsDTO req){
        log.info("TagsB2BController editProdTags req={} ", JSON.toJSON(req));
        return tagsService.editProdTags(req);
    }

    @ApiOperation(value = "删除标签", notes = "删除标签")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/deleteProdTags")
    public ResultVO<Boolean> deleteProdTags(@RequestParam(value = "tagsId") String tagsId){
        log.info("TagsB2BController deleteProdTags tagsId={} ", tagsId);
        ProdTagDeleteReq req = new ProdTagDeleteReq();
        req.setTagId(tagsId);
        return tagsService.deleteProdTags(req);
    }

    @ApiOperation(value = "根据标签ID查询标签", notes = "根据标签ID查询标签")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getTagById")
    public ResultVO<TagsDTO> getTagById(@RequestParam(value = "tagsId") String tagsId){
        log.info("TagsB2BController deleteProdTags tagsId={} ", tagsId);
        TagGetByIdReq req = new TagGetByIdReq();
        req.setTagId(tagsId);
        return tagsService.getTagById(req);
    }


    @ApiOperation(value = "根据标签类型和名称查询标签", notes = "根据标签类型和名称查询标签")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/getTags")
    public ResultVO<List<TagsDTO>> getTags(@RequestParam(value = "tagsType", required = false) String tagsType,
        @RequestParam(value = "tagsName", required = false) String tagsName){
        log.info("TagsB2BController deleteProdTags tagsType={},tagsName={} ", tagsType, tagsName);
        TagGetByTagReq req = new TagGetByTagReq();
        req.setTagType(tagsType);
        req.setTagName(tagsName);
        return tagsService.getTags(req);
    }

    @ApiOperation(value = "查询标签类型", notes = "查询标签类型")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="/listTagType")
    public ResultVO<List<TagTypeDTO>> listTagType(){
        List<TagTypeDTO> tagTypeDTOList =Lists.newArrayList();
        GoodsConst.TagTypeEnum[] tagTypeEnums = GoodsConst.TagTypeEnum.values();
        for (GoodsConst.TagTypeEnum tagType :
                tagTypeEnums) {
            TagTypeDTO tagTypeDTO = new TagTypeDTO();
            tagTypeDTO.setTagTypeCode(tagType.getCode());
            tagTypeDTO.setTagTypeName(tagType.getValue());
            tagTypeDTOList.add(tagTypeDTO);
        }
       return ResultVO.success(tagTypeDTOList);
    }
}
