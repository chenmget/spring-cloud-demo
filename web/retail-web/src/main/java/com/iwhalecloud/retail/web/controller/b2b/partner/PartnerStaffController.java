package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.PartnerStaffDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffAddReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffPageReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerStaffUpdateReq;
import com.iwhalecloud.retail.partner.service.PartnerStaffService;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserAddReq;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.b2b.partner.request.PartnerStaffQryByPartnerIdReq;
import com.iwhalecloud.retail.web.controller.b2b.partner.response.PartnerStaffQryByPartnerIdResp;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/partnerStaff")
public class PartnerStaffController extends BaseController {

    @Reference
    private PartnerStaffService partnerStaffService;
    @Reference
    private UserService userService;

    @ApiOperation(value = "新增分销商店员", notes = "传入PartnerStaffAddReq对象，进行添加操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/add")
    public ResultVO<PartnerStaffDTO> addPartnerStaff(@RequestBody @ApiParam(value = "PartnerStaffAddReq", required = true) PartnerStaffAddReq req) {

        if (StringUtils.isEmpty(req.getPartnerShopId())) {
            return ResultVO.error("厅店ID不能为空！");
        }
        if (StringUtils.isEmpty(req.getStaffCode())) {
            return ResultVO.error("员工编码不能为空！");
        }
        if (StringUtils.isEmpty(req.getStaffName())) {
            return ResultVO.error("员工名称不能为空！");
        }

        // 取当前登录人的一些信息（如 orgid\regionid 等等）赋值给新增的账号

        //1、获取当前登录的是否  是分销商
        UserDTO curLoginUserDTO = UserContext.getUser();
        if(curLoginUserDTO == null){
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        if(curLoginUserDTO.getUserFounder() != SystemConst.USER_FOUNDER_3){
            return failResultVO("你不是分销商用户，不能新建店员");
        }
        // 2、判断 登录账号是否已经存在
        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setLoginName(req.getStaffCode());
        UserDTO userDTO = userService.getUser(userGetReq);
        if (userDTO != null){
            return failResultVO("已存在同名账号，请换一个登录账号");
        }
        // 3、新增店员信息
        PartnerStaffDTO newStaff = partnerStaffService.addPartnerStaff(req);
        if (newStaff == null){
            return ResultVO.error("添加员工信息失败");
        }

        // 4、新增系统账号
        UserAddReq userAddReq = new UserAddReq();
        userAddReq.setLoginName(newStaff.getStaffCode());
        userAddReq.setLoginPwd(newStaff.getStaffCode()); // 密码默认登录名
        userAddReq.setUserName(newStaff.getStaffName());
        userAddReq.setRelCode(curLoginUserDTO.getRelCode()); // 关联分销商ID
        userAddReq.setRelNo(newStaff.getStaffId()); // 关联店员ID
        userAddReq.setCreateStaff(curLoginUserDTO.getUserId());
        userAddReq.setRegionId(curLoginUserDTO.getRegionId());
        userAddReq.setLanId(curLoginUserDTO.getLanId());
        userAddReq.setOrgId(curLoginUserDTO.getOrgId());
        userAddReq.setUserFounder(SystemConst.USER_FOUNDER_6);
        userAddReq.setRelType(SystemConst.REL_TYPE_PARTNER);

        ResultVO<UserDTO> addUserResultVO = userService.addUser(userAddReq);
        UserDTO newUser = addUserResultVO.getResultData();
        if (newUser == null){
            return ResultVO.error(addUserResultVO.getResultMsg());
        }


        return ResultVO.success(newStaff);


//        PartnerStaffDTO partnerStaffDTO = partnerStaffService.addPartnerStaff(req);
//        if (partnerStaffDTO == null)
//            return ResultVO.error("添加员工信息失败");
//        return ResultVO.success(partnerStaffDTO);
    }

    @ApiOperation(value = "编辑分销商店员", notes = "传入PartnerStaffUpdateReq对象，进行编辑操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/edit")
    public ResultVO<Integer> editPartnerStaff(@RequestBody @ApiParam(value = "PartnerStaffUpdateReq", required = true) PartnerStaffUpdateReq req) {

        //TODO 要限定哪些字段不能改
        if (StringUtils.isEmpty(req.getStaffId())) {
            return ResultVO.error("员工ID不能为空！");
        }
        int result = partnerStaffService.editPartnerStaff(req);
        if (result == 0) {
            return ResultVO.error("编辑员工信息失败");
        }
        return ResultVO.success(result);
    }

    @ApiOperation(value = "批量删除分销商店员", notes = "传入PartnerStaffDeleteReq对象的staffIds，进行批量删除操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/delete")
    public ResultVO<Integer> deletePartnerStaffs(@RequestBody @ApiParam(value = "PartnerStaffDeleteReq", required = true) PartnerStaffDeleteReq req) {

        if (req.getStaffIds() == null
                || req.getStaffIds().size() == 0) {
            return ResultVO.error("员工ID不能为空！");
        }
        try {
            int result = partnerStaffService.deletePartnerStaff(req);
            if (result == 0) {
                return ResultVO.error("删除员工信息失败");
            }
            return ResultVO.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());
        }
    }

    @ApiOperation(value = "查询店员列表（带出店名的）", notes = "可以根据分销商ID、员工姓名、店名条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/qryList")
    public ResultVO<Page<PartnerStaffDTO>> qryPartnerStaffList(
            @RequestBody @ApiParam(value = "PartnerStaffPageReq", required = true) PartnerStaffPageReq req) {
        try {
            Page<PartnerStaffDTO> page = partnerStaffService.getPartnerStaffList(req);
            return ResultVO.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(e.getMessage());

        }
    }

    @ApiOperation(value = "查询分销商的店员列表）", notes = "根据分销商ID查询分销商的店员列表")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/qryListByPartnerId")
    public ResultVO<List<PartnerStaffQryByPartnerIdResp>> qryPartnerStaffListByPartnerId(
            @RequestBody @ApiParam(value = "PartnerStaffPageReq", required = true) PartnerStaffQryByPartnerIdReq req) {
        if (StringUtils.isEmpty(req.getPartnerId())) {
            return ResultVO.error("分销商ID不能为空！");
        }
        //1、获取分销商的用户列表
        UserListReq userListReq = new UserListReq();
        userListReq.setRelCode(req.getPartnerId());
        List<UserDTO> userDTOList = userService.getUserList(userListReq);
        //2、找出每个用户对应的 厅店员工信息
        List<PartnerStaffQryByPartnerIdResp> resultList = new ArrayList<>();
        for (UserDTO userDTO : userDTOList){
            if (StringUtils.isEmpty(userDTO.getRelNo())){
                continue;
            }
            PartnerStaffDTO partnerStaffDTO = partnerStaffService.getPartnerStaff(userDTO.getRelNo());
            if(partnerStaffDTO != null){
                PartnerStaffQryByPartnerIdResp resp = new PartnerStaffQryByPartnerIdResp();
                BeanUtils.copyProperties(partnerStaffDTO, resp);
                resp.setUserId(userDTO.getUserId());
                resultList.add(resp);
            }
        }
        return ResultVO.success(resultList);
    }
}
