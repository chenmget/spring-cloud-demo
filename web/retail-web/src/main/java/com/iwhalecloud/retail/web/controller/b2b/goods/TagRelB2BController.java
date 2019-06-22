package com.iwhalecloud.retail.web.controller.b2b.goods;


import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelDeleteByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelUpdateReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagRelService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/24
 */
@RestController
@RequestMapping("/api/b2b/tagRel")
@Slf4j
public class TagRelB2BController {

	@Reference
    private TagRelService tagRelService;

    @ApiOperation(value = "根据ID查询标签商品关系", notes = "传入标签商品关系ID，进行查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relId", value = "relId", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="getTagRel")
    public ResultVO<TagRelDTO> getTagRel(@RequestParam(value = "relId", required = true) String relId) {
        if (StringUtils.isEmpty(relId)) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        TagRelGetByIdReq relGetByIdReq = new TagRelGetByIdReq();
        relGetByIdReq.setRelId(relId);
        return tagRelService.getTagRel(relGetByIdReq);
    }

    @ApiOperation(value = "查询所有标签商品关系", notes = "查询所有操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value="listAll")
    public ResultVO<List<TagRelDTO>> listAll() {
        return tagRelService.listAll();
    }

    @ApiOperation(value = "添加标签商品关系", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addTagRel")
    public ResultVO<Integer> addTagRel(@RequestBody TagRelAddReq req) {
        return tagRelService.addTagRel(req);

    }

    @ApiOperation(value = "更新标签商品关系", notes = "更新操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="updateTagRel")
    public ResultVO<Integer> updateTagRel(@RequestBody TagRelUpdateReq req) {
        return tagRelService.updateTagRel(req);

    }

    @ApiOperation(value = "删除标签商品关系", notes = "删除操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relId", value = "relId", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="deleteTagRel")
    public ResultVO<Integer> deleteTagRel(@RequestParam(value = "relId", required = true) String relId) {
        TagRelDeleteByIdReq req = new TagRelDeleteByIdReq();
        req.setRelId(relId);
        return tagRelService.deleteTagRel(req);
    }


}
