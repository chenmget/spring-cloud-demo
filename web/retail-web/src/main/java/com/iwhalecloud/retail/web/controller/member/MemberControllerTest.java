package com.iwhalecloud.retail.web.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.*;
import com.iwhalecloud.retail.member.service.*;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/3/13
 */
@RestController
@RequestMapping("/api/memberTest")
@Slf4j
@Api(value="会员中心接口测试:", tags={"会员中心-对接收钱哆-接口测试"})
public class MemberControllerTest extends BaseController {

    @Reference
    private MemberService memberService;

    @Reference
    private MemberAddressService memberAddressService;

    @Reference
    private GroupService groupService;

    @Reference
    private GroupMerchantService groupMerchantService;

    @Reference
    private MemberGroupService memberGroupService;

    @Reference
    private MemberLevelService memberLevelService;

    @Reference
    private MemberMerchantService memberMerchantService;

    @ApiOperation(value = "会员注册", notes = "会员注册")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultVO getAdminListWithRole(@RequestBody @ApiParam(value = "注册入参", required = true) MemberAddReq req) {
        // 判空
        ResultVO resp = memberService.register(req);
        return successResultVO(resp);
    }

    @ApiOperation(value = "会员信息获取", notes = "会员信息获取")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getMember", method = RequestMethod.POST)
    public ResultVO<MemberDTO> getMember(@RequestBody @ApiParam(value = "会员信息获取入参", required = true) MemberGetReq req) {
        // 判空

        return memberService.getMember(req);
    }

    @ApiOperation(value = "会员地址添加", notes = "会员地址添加")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/address/add", method = RequestMethod.POST)
    public ResultVO<MemberAddressAddResp> addAddress(@RequestBody @ApiParam(value = "会员地址添加入参", required = true) MemberAddressAddReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getMemberId())) {
            return ResultVO.error("会员id不能为空");
        }
        return memberAddressService.addAddress(req);
    }

    @ApiOperation(value = "会员地址修改", notes = "会员地址修改")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/address/update", method = RequestMethod.POST)
    public ResultVO<Integer> addAddress(@RequestBody @ApiParam(value = "会员地址修改入参", required = true) MemberAddressUpdateReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getMemberId())) {
            return ResultVO.error("会员id不能为空");
        }
        return memberAddressService.updateAddress(req);
    }

    @ApiOperation(value = "会员地址删除", notes = "会员地址删除")
    @ApiImplicitParam(
            name = "addressId", value = "地址Id", paramType = "query", required = true, dataType = "String"
    )
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/address/delete", method = RequestMethod.GET)
    public ResultVO<Integer> deleteAddress(@RequestParam(value = "addressId") String addressId) {
        // 判空
        if (StringUtils.isEmpty(addressId)) {
            return ResultVO.error("地址id不能为空");
        }
        return memberAddressService.deleteAddressById(addressId);
    }

    @ApiOperation(value = "会员地址列表查询", notes = "会员地址列表查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/address/list", method = RequestMethod.POST)
    public ResultVO<List<MemberAddressRespDTO>> listAddress(@RequestBody @ApiParam(value = "会员地址列表查询", required = true) MemberAddressListReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getMemberId())) {
            return ResultVO.error("会员id不能为空");
        }
        return memberAddressService.listMemberAddress(req);
    }

    @ApiOperation(value = "新增会员群组", notes = "新增会员群组")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/group/addGroup", method = RequestMethod.POST)
    public ResultVO<Boolean> addGroup(@RequestBody @ApiParam(value = "新增会员群组", required = true) GroupAddReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupName())) {
            return ResultVO.error("群组名称不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getGroupType())) {
            return ResultVO.error("会员群类型不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getTradeName())) {
            return ResultVO.error("商圈不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getCreateStaff())) {
            return ResultVO.error("创建人不能为空");
        }
        return groupService.addGroup(req);
    }

    @ApiOperation(value = "更新会员群组信息", notes = "更新会员群组信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/group/updateGroupById", method = RequestMethod.POST)
    public ResultVO<Boolean> updateGroupById(@RequestBody @ApiParam(value = "更新会员群组信息", required = true) GroupUpdateReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组id不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("更新人员不能为空");
        }
        return groupService.updateGroupById(req);
    }

    @ApiOperation(value = "删除会员群组", notes = "删除会员群组")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/group/deleteGroup", method = RequestMethod.POST)
    public ResultVO<Boolean> deleteGroup(@RequestBody @ApiParam(value = "删除会员群组", required = true) GroupDeleteReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("更新人员不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组id不能为空");
        }
        return groupService.deleteGroup(req);
    }

    @ApiOperation(value = "根据Id查询会员群组信息", notes = "根据Id查询会员群组信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/group/queryGroupById", method = RequestMethod.POST)
    public ResultVO<GroupQueryResp> queryGroupById(@RequestBody @ApiParam(value = "根据Id查询会员群组信息", required = true) GroupQueryDetailReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组id不能为空");
        }
        return groupService.queryGroupById(req);
    }

    @ApiOperation(value = "分页查询会员群组列表", notes = "分页查询会员群组列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/group/queryGroupForPage", method = RequestMethod.POST)
    public ResultVO<Page<GroupQueryResp>> queryGroupForPage(@RequestBody @ApiParam(value = "分页查询会员群组列表", required = true) GroupQueryForPageReq req) {
        return groupService.queryGroupForPage(req);
    }

    @ApiOperation(value = "新增商户会员群", notes = "新增商户会员群")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/groupMerchant/addGroupMerchant", method = RequestMethod.POST)
    public ResultVO<Boolean> addGroupMerchant(@RequestBody @ApiParam(value = "新增商户会员群", required = true) GroupMerchantAddReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchId())) {
            return ResultVO.error("商家ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("创建人不能为空");
        }
        return groupMerchantService.addGroupMerchant(req);
    }

    @ApiOperation(value = "更新商户会员群", notes = "更新商户会员群")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/groupMerchant/updateGroupMerchant", method = RequestMethod.POST)
    public ResultVO<Boolean> updateGroupMerchant(@RequestBody @ApiParam(value = "更新商户会员群", required = true) GroupMerchantUpdateReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchId())) {
            return ResultVO.error("商家ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("创建人不能为空");
        }
        return groupMerchantService.updateGroupMerchant(req);
    }

    @ApiOperation(value = "删除商户会员群", notes = "删除商户会员群")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/groupMerchant/deleteGroupMerchant", method = RequestMethod.POST)
    public ResultVO<Boolean> deleteGroupMerchant(@RequestBody @ApiParam(value = "删除商户会员群", required = true) GroupMerchantDeleteReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchId())) {
            return ResultVO.error("商家ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("创建人不能为空");
        }
        return groupMerchantService.deleteGroupMerchant(req);
    }

    @ApiOperation(value = "分页查询商户会员群", notes = "分页查询商户会员群")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/groupMerchant/queryGroupMerchantForPage", method = RequestMethod.POST)
    public ResultVO<Page<GroupMerchantQueryResp>> queryGroupMerchantForPage(@RequestBody @ApiParam(value = "分页查询商户会员群", required = true) GroupMerchantQueryReq req) {
        return groupMerchantService.queryGroupMerchantForPage(req);
    }

    @ApiOperation(value = "新增群组会员", notes = "新增群组会员")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberGroup/queryGroupMerchantForPage", method = RequestMethod.POST)
    public ResultVO<Boolean> addMemberGroup(@RequestBody @ApiParam(value = "新增群组会员", required = true) MemberGroupAddReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMemId())) {
            return ResultVO.error("会员ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getCreateStaff())) {
            return ResultVO.error("创建人不能为空");
        }
        return memberGroupService.addMemberGroup(req);
    }

    @ApiOperation(value = "更新群组会员信息", notes = "更新群组会员信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberGroup/updateMemberGroupById", method = RequestMethod.POST)
    public ResultVO<Boolean> updateMemberGroupById(@RequestBody @ApiParam(value = "更新群组会员信息", required = true) MemberGroupUpdateReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMemId())) {
            return ResultVO.error("会员ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("创建人不能为空");
        }
        return memberGroupService.updateMemberGroupById(req);
    }

    @ApiOperation(value = "新增群组会员", notes = "新增群组会员")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberGroup/deleteMemberGroup", method = RequestMethod.POST)
    public ResultVO<Boolean> deleteMemberGroup(@RequestBody @ApiParam(value = "新增群组会员", required = true) MemberGroupDeleteReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMemId())) {
            return ResultVO.error("会员ID不能为空");
        }
        return memberGroupService.deleteMemberGroup(req);
    }

    @ApiOperation(value = "根据会员分页查询群组列表", notes = "根据会员分页查询群组列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberGroup/queryGroupByMemberForPage", method = RequestMethod.POST)
    public ResultVO<Page<MemberGroupQueryResp>> queryGroupByMemberForPage(@RequestBody @ApiParam(value = "根据会员分页查询群组列表", required = true) MemberGroupQueryGroupReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getMemId())) {
            return ResultVO.error("群组ID不能为空");
        }
        return memberGroupService.queryGroupByMemberForPage(req);
    }

    @ApiOperation(value = "根据群组分页查询会员列表", notes = "根据群组分页查询会员列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberGroup/queryMemberByGroupForPage", method = RequestMethod.POST)
    public ResultVO<Page<GroupQueryResp>> queryMemberByGroupForPage(@RequestBody @ApiParam(value = "根据群组分页查询会员列表", required = true) MemberGroupQueryMemberReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getGroupId())) {
            return ResultVO.error("群组ID不能为空");
        }
        return memberGroupService.queryMemberByGroupForPage(req);
    }

    @ApiOperation(value = "新增会员等级", notes = "新增会员等级")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberLevel/addMemberLevel", method = RequestMethod.POST)
    public ResultVO<Boolean> addMemberLevel(@RequestBody @ApiParam(value = "新增会员等级", required = true) MemberLevelAddReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getLvName())) {
            return ResultVO.error("等级名称不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchantId())) {
            return ResultVO.error("商家ID不能为空");
        }
        return memberLevelService.addMemberLevel(req);
    }

    @ApiOperation(value = "更新会员等级信息", notes = "更新会员等级信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberLevel/updateMemberLevel", method = RequestMethod.POST)
    public ResultVO<Boolean> updateMemberLevel(@RequestBody @ApiParam(value = "更新会员等级信息", required = true) MemberLevelUpdateReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getLvName())) {
            return ResultVO.error("等级名称不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchantId())) {
            return ResultVO.error("商家ID不能为空");
        }
        return memberLevelService.updateMemberLevel(req);
    }

    @ApiOperation(value = "删除会员等级信息", notes = "删除会员等级信息")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberLevel/deleteMemberLevel", method = RequestMethod.POST)
    public ResultVO<Boolean> deleteMemberLevel(@RequestBody @ApiParam(value = "删除会员等级信息", required = true) MemberLevelDeleteReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getLvId())) {
            return ResultVO.error("等级ID不能为空");
        }
        return memberLevelService.deleteMemberLevel(req);
    }

    @ApiOperation(value = "分页查询会员等级", notes = "分页查询会员等级")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberLevel/queryGroupLevelForPage", method = RequestMethod.POST)
    public ResultVO<Page<MemberLevelQueryResp>> queryGroupLevelForPage(@RequestBody @ApiParam(value = "分页查询会员等级", required = true) MemberLevelQueryReq req) {
        return memberLevelService.queryGroupLevelForPage(req);
    }

    @ApiOperation(value = "新增商户与会员关系", notes = "新增商户与会员关系")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberMerchant/addMemberMerchant", method = RequestMethod.POST)
    public ResultVO<Boolean> addMemberMerchant(@RequestBody @ApiParam(value = "新增商户与会员关系", required = true) MemberMerchantAddReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getMemId())) {
            return ResultVO.error("会员ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchId())) {
            return ResultVO.error("商户ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("创建人不能为空");
        }
        return memberMerchantService.addMemberMerchant(req);
    }

    @ApiOperation(value = "更新商户与会员关系", notes = "更新商户与会员关系")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberMerchant/updateMemberMerchant", method = RequestMethod.POST)
    public ResultVO<Boolean> updateMemberMerchant(@RequestBody @ApiParam(value = "更新商户与会员关系", required = true) MemberMerchantUpdateReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getMemId())) {
            return ResultVO.error("会员ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchId())) {
            return ResultVO.error("商户ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("更新人不能为空");
        }
        return memberMerchantService.updateMemberMerchant(req);
    }

    @ApiOperation(value = "删除商户和会员关系", notes = "删除商户和会员关系")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberMerchant/deleteMemberMerchant", method = RequestMethod.POST)
    public ResultVO<Boolean> deleteMemberMerchant(@RequestBody @ApiParam(value = "删除商户和会员关系", required = true) MemberMerchantDeleteReq req) {
        // 判空
        if (StringUtils.isEmpty(req.getMemId())) {
            return ResultVO.error("会员ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getMerchId())) {
            return ResultVO.error("商户ID不能为空");
        }
        // 判空
        if (StringUtils.isEmpty(req.getUpdateStaff())) {
            return ResultVO.error("更新人不能为空");
        }
        return memberMerchantService.deleteMemberMerchant(req);
    }

    @ApiOperation(value = "查询商户会员关系列表", notes = "查询商户会员关系列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/memberMerchant/queryMemberMerchantForPage", method = RequestMethod.POST)
    public ResultVO<Page<MemberMerchantQueryForPageResp>> queryMemberMerchantForPage(@RequestBody @ApiParam(value = "查询商户会员关系列表", required = true) MemberMerchantQueryForPageReq req) {
        return memberMerchantService.queryMemberMerchantForPage(req);
    }

}
