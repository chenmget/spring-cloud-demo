package com.iwhalecloud.retail.web.controller.cms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.SelectTagListDTO;
import com.iwhalecloud.retail.oms.dto.TagDTO;
import com.iwhalecloud.retail.oms.dto.resquest.TagPageReq;
import com.iwhalecloud.retail.oms.service.TagService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/contentTag")
public class ContentTagController {
    @Reference
    private TagService tagService;

    @ApiOperation(value = "新增TagDTO", notes = "传入TagDTO对象，进行保存操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/createTag", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO createCloudDevice(@RequestBody @ApiParam(value = "TagDTO", required = true) TagDTO request) {
        ResultVO resultVO = new ResultVO();
        try {
            String tagName = request.getTagName();
            TagDTO tagDTO = tagService.queryTagDetailByTagName(tagName);
            if (tagDTO != null) {
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("已存在同名标签");
            } else {
                tagService.createTag(request);
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData("新增成功");
                resultVO.setResultMsg("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "删除TagDTO", notes = "根据标签id，进行删除操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagId", value = "标签ID", paramType = "path", required = true, dataType = "Int")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteTag/{tagId}", method = RequestMethod.DELETE)
    @UserLoginToken
    public ResultVO deleteCloudDevice(@PathVariable Long tagId) {
        ResultVO resultVO = new ResultVO();
        try {
            TagDTO request = new TagDTO();
            request.setTagId(tagId);
            tagService.deleteTag(request);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("删除成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "修改TagDTO", notes = "接口支持根据tagId修改标签id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/editTag", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO updateCloudDeviceStatus(@RequestBody TagDTO dto) {
        ResultVO resultVO = new ResultVO();
        /*try {
            String tagName = dto.getTagName();
            TagDTO tagDTO = tagService.queryTagDetailByTagName(tagName);
            if (tagDTO != null) {
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("已存在同名标签");
            } else {
                tagService.editTag(dto);
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData("修改成功");
                resultVO.setResultMsg("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }*/
        int effRowNum = tagService.editTag(dto);
        if (effRowNum > 0) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("修改成功");
            resultVO.setResultMsg("success");
        } else {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("记录变更行数：" + effRowNum + "行");
        }
        return resultVO;
    }

    @ApiOperation(value = "查询TagDTO", notes = "根据tagName，查询SelectTagListDTO对象")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryTagList", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Page<SelectTagListDTO>> queryTagList(@RequestBody @ApiParam(value = "查询条件", required = true) TagPageReq page) {
        ResultVO<Page<SelectTagListDTO>> resultVO = new ResultVO<>();
        String tagName = page.getTagName();
        if (StringUtils.isEmpty(tagName)) {
            page.setTagName(null);
        } else {
            page.setTagName("%" + tagName + "%");
        }
        try {
            Page<SelectTagListDTO> res = tagService.queryTagList(page);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(res);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }
}