package com.iwhalecloud.retail.web.controller.cms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.ContentPublishDTO;
import com.iwhalecloud.retail.oms.service.ContentBaseService;
import com.iwhalecloud.retail.oms.service.ContentPublishService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contentPublish")
public class ContentPublishController {

    @Reference
    private ContentPublishService contentPublishService;
    @Reference
    private ContentBaseService contentBaseService;

    // 内容发布/变更
    @RequestMapping(value = "/createContentPublish", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO createContentPublish(@RequestBody ContentPublishDTO dto) {
        //List传入发布对象 若为编辑 则第一个为旧对象 第二个为修改后对象
        //新增和删除只传长度为1的List
        ResultVO resultVO = new ResultVO();
        String userId = UserContext.getUserId();
        String tag = dto.getTag();
        Date date = new Date();
        try {
            if(!tag.equals("delete")){
                dto.setOprid(userId);
                dto.setPublishdate(date);
            }

            switch (tag){
                case "add":
                    contentBaseService.contentBaseStatusChange(dto.getContentid(), OmsConst.ContentStatusEnum.HAVE_PUBLISH.getCode());
                    contentPublishService.createContentPublish(dto);
                    resultVO.setResultMsg("内容发布成功");
                    break;
                case "edit":
                    contentPublishService.deleteContentPublish(dto);
                    contentPublishService.createContentPublish(dto);
                    resultVO.setResultMsg("内容修改成功");
                    break;
                case "delete":
                    contentBaseService.contentBaseStatusChange(dto.getContentid(),OmsConst.ContentStatusEnum.PASS_AUDIT.getCode());
                    contentPublishService.deleteContentPublish(dto);
                    resultVO.setResultMsg("内容删除成功");
                    break;
                default:
                    resultVO.setResultMsg("操作类型不明确");
                    resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                    return resultVO;
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultMsg("success");

        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    public boolean judgeContentPublishNull(ContentPublishDTO contentPublish){
        Long contentId = contentPublish.getContentid();
        String area = contentPublish.getArea();
        String wayType = contentPublish.getWaytype();
        if(contentId != null && !StringUtils.isEmpty(area) && wayType != null){
            //三个属性若都不为空
            return true;
        }else {
            //三个属性若有为空
            return false;
        }
    }

    // 内容发布查询
    @GetMapping(value = "queryContentPublishList")
    @UserLoginToken
    public ResultVO<List<ContentPublishDTO>> queryContentPublishList(@RequestParam(value = "contentId", required = true) String contentId) {
        ResultVO resultVO = new ResultVO();
        try {
            List<ContentPublishDTO> contentPublish = contentPublishService.queryContentPublishList(contentId);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(contentPublish);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    // 内容上下架
    @RequestMapping(value = "/updateContentPublishStatus", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO updateContentPublishStatus(@RequestBody ContentBaseDTO dto) {
        ResultVO resultVO = new ResultVO();

        try {
            if (dto.getTag() == "putOn") {
                contentBaseService.contentBaseStatusChange(dto.getContentId(), OmsConst.ContentStatusEnum.UP_SHELVE.getCode());
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData("上架成功");
                resultVO.setResultMsg("success");
            } else if (dto.getTag() == "pullOff") {
                contentBaseService.contentBaseStatusChange(dto.getContentId(), OmsConst.ContentStatusEnum.OFF_SHELVE.getCode());
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData("下架成功");
                resultVO.setResultMsg("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }
}