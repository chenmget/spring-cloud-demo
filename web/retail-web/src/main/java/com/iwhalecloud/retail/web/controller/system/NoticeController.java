package com.iwhalecloud.retail.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.NoticeDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.service.NoticeService;
import com.iwhalecloud.retail.system.service.NoticeUserService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Reference
    private NoticeService noticeService;

    @Reference
    private NoticeUserService noticeUserService;


    /**
     * 新建通知
     * @param req
     * @return
     */
    @ApiOperation(value = "新建通知接口", notes = "新建通知")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional
    @UserLoginToken
    public ResultVO<NoticeDTO> saveNotice(@RequestBody @ApiParam(value = "新建通知参数", required = true) NoticeSaveReq req) {
        if (UserContext.getUser() != null) {
            req.setCreateUserId(UserContext.getUserId());
        }
        return noticeService.saveNotice(req);
    }

    /**
     * 更新通知
     * @param req
     * @return
     */
    @ApiOperation(value = "更新通知接口", notes = "更新通知")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    @UserLoginToken
    public ResultVO updateNotice(@RequestBody @ApiParam(value = "更新通知参数", required = true) NoticeUpdateReq req) {
        if (UserContext.getUser() != null) {
            req.setUpdateUserId(UserContext.getUserId());
        }
        return noticeService.updateNotice(req);
    }

    /**
     * 更新通知
     * @param
     * @return
     */
    @ApiOperation(value = "更新通知接口", notes = "更新通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeId", value = "通知ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="setHasReadStatus")
//    @RequestMapping(value = "/setHasReadStatus", method = RequestMethod.PUT)
    @Transactional
    @UserLoginToken
    public ResultVO setHasReadStatus(@RequestBody NoticeUpdateReq req) {
        NoticeUserUpdateReq noticeUserUpdateReq = new NoticeUserUpdateReq();
        noticeUserUpdateReq.setNoticeId(req.getNoticeId());
        noticeUserUpdateReq.setUserId(UserContext.getUser().getUserId());
        return noticeUserService.setHasReadStatus(req.getNoticeId(), UserContext.getUser().getUserId());
    }

//    /**
//     * 删除 用户通知 绑定关系
//     * @param id
//     * @return
//     */
//    @ApiOperation(value = "删除 用户通知 绑定关系", notes = "删除 用户通知 绑定关系")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "通知用户关联ID", paramType = "query", required = true, dataType = "String"),
//    })
//    @ApiResponses({
//            @ApiResponse(code=400,message="请求参数没填好"),
//            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
//    })
//    @RequestMapping(value = "/deleteUser", method = RequestMethod.PUT)
//    public ResultVO deleteUser(@RequestParam(value = "id") String id) {
//        return noticeUserService.deleteNoticeUser(id);
//    }

    /**
     * 启用通知
     * @param noticeId
     * @return
     */
    @ApiOperation(value = "启用通知接口", notes = "启用通知接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeId", value = "通知ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    @UserLoginToken
    public ResultVO enableNotice(@RequestParam(value = "noticeId") String noticeId) {
        NoticeUpdateReq updateReq = new NoticeUpdateReq();
        updateReq.setNoticeId(noticeId);
        updateReq.setStatus(SystemConst.NoticeStatusEnum.VALID.getCode());
        if (UserContext.getUser() != null) {
            updateReq.setUpdateUserId(UserContext.getUserId());
        }
        return noticeService.updateNotice(updateReq);
    }



    /**
     * 禁用（使无效）通知
     * @param noticeId
     * @return
     */
    @ApiOperation(value = "禁用（使无效）通知接口", notes = "禁用（使无效）通知接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeId", value = "通知ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public ResultVO disableNotice(@RequestParam(value = "noticeId") String noticeId) {
        NoticeUpdateReq updateReq = new NoticeUpdateReq();
        updateReq.setNoticeId(noticeId);
        updateReq.setStatus(SystemConst.NoticeStatusEnum.INVALID.getCode());
        if (UserContext.getUser() != null) {
            updateReq.setUpdateUserId(UserContext.getUserId());
        }
        return noticeService.updateNotice(updateReq);
    }


    /**
     * 获取通知分页列表
     * @param req
     * @return
     */
    @ApiOperation(value = "获取通知分页列表接口", notes = "可以根据标题、类型、发布类型条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResultVO<Page<NoticeDTO>> pageNotice(@RequestBody @ApiParam(value = "分页参数", required = true) NoticePageReq req) {
        // 判空
        return noticeService.pageNotice(req);
    }

    /**
     * 获取个人的通知分页列表
     * @param noticePageReq
     * @return
     */
    @ApiOperation(value = "获取个人的通知分页列表接口", notes = "可以根据标题、类型、发布类型条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pagePersonalNotice", method = RequestMethod.POST)
    public ResultVO<Page<NoticeDTO>> pagePersonalNotice(@RequestBody @ApiParam(value = "分页参数", required = true) NoticePagePersonalReq noticePageReq) {
//        NoticePageReq noticePageReq = new NoticePageReq();
//        BeanUtils.copyProperties(req, noticePageReq);
        // 设置消息状态
//        noticePageReq.setStatus(SystemConst.NoticeStatusEnum.VALID.getCode());
        if (!UserContext.isUserLogin()) {
            // 未登录 只返回 发布类型是系统类的
            noticePageReq.setPublishType(SystemConst.NoticePublishTypeEnum.SYSTEM.getType());
        } else {
            // 已经登录 返回 发布类型是系统类的  和 是自己的消息
            noticePageReq.setPublishType(SystemConst.NoticePublishTypeEnum.SYSTEM.getType());
            noticePageReq.setUserId(UserContext.getUser().getUserId());
        }

        return noticeService.pagePersonalNotice(noticePageReq);
    }

    /**
     * 获取定向类通知用户关联 列表
     * @param noticeId
     * @return
     */
    @ApiOperation(value = "根据通知ID获取 定向类 通知用户关联 列表", notes = "获取定向类通知用户关联 列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "noticeId", value = "通知ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/listNoticeUser", method = RequestMethod.GET)
    public ResultVO listNoticeUser(
            @RequestParam(value = "noticeId") String noticeId
    ) {
        NoticeUserListReq noticeUserListReq = new NoticeUserListReq();
        noticeUserListReq.setNoticeId(noticeId);
        return noticeUserService.listNoticeUser(noticeUserListReq);
    }

    /**
     * 定向类通知：获取可以选择的用户分页列表
     * @param
     * @return
     */
    @ApiOperation(value = "定向类通知：获取可以选择的用户分页列表接口", notes = "定向类通知：获取可以选择的用户分页列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pageUser", method = RequestMethod.POST)
    public ResultVO<Page<UserDTO>> pageUser(
            @RequestBody @ApiParam(value = "分页参数", required = true) NoticeSelectUserPageReq req
    ) {
        // 判空
        return noticeUserService.pageSelectableUser(req);
    }
}
