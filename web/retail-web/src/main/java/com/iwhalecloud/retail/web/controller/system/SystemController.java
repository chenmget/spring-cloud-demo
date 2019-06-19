package com.iwhalecloud.retail.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.*;
import com.iwhalecloud.retail.system.dto.request.MenuListReq;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.dto.request.RoleGetReq;
import com.iwhalecloud.retail.system.dto.request.RolePageReq;
import com.iwhalecloud.retail.system.service.*;
import com.iwhalecloud.retail.web.controller.system.request.*;
import com.iwhalecloud.retail.web.controller.system.response.RoleMenuResp;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/sys")
@Slf4j
@Api(value = "系统模块：菜单（资源）、角色、区域、配置等", tags = {"系统模块：菜单（资源）、角色、区域、配置等"})
public class SystemController {

    @Reference
    MenuService menuService;

    @Reference
    RoleService roleService;

    @Reference
    RoleMenuService roleMenuService;

    @Reference
    UserRoleService userRoleService;

    @Reference
    OrganizationService organizationService;

    @Reference
    ConfigInfoService configInfoService;

    @Reference
    PublicDictService publicDictService;

    /**
     * 根据类型type获取对应的 字典列表
     *
     * @param type
     * @return
     */
    @ApiOperation(value = "根据类型type获取对应的 字典列表", notes = "传入类型，进行获取操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/getPublicDictListByType")
    public ResultVO<List<PublicDictDTO>> getPublicDictListByType(
            @RequestParam(value = "type", required = true) String type
    ) {

        if (StringUtils.isEmpty(type)) {
            return ResultVO.error("类型不能为空!");
        }

        List<PublicDictDTO> publicDictDTOList = publicDictService.queryPublicDictListByType(type);

        // 失败统一返回
        if (CollectionUtils.isEmpty(publicDictDTOList)) {
            return ResultVO.error("没有获取到数据，请检查类型!");
        }

        return ResultVO.success(publicDictDTOList);
    }

    /**
     * 根据ID获取配置值
     *
     * @param configInfoId
     * @return
     */
    @ApiOperation(value = "根据配置ID获取配置值", notes = "传入配置ID，进行获取操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "configInfoId", value = "configInfoId", paramType = "path", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/getConfigInfoById", method = RequestMethod.GET)
    public ResultVO<String> getConfigInfoById(
            @RequestParam(value = "configInfoId", required = true) String configInfoId
    ) {

        if (StringUtils.isEmpty(configInfoId)) {
            return ResultVO.error("配置ID不能为空!");
        }

        ConfigInfoDTO configInfoDTO = configInfoService.getConfigInfoById(configInfoId);

        // 失败统一返回
        if (configInfoDTO == null) {
            return ResultVO.error("获取配置值失败!");
        }

        return ResultVO.success(configInfoDTO.getCfValue());
    }

    @ApiOperation(value = "插入菜单", notes = "")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/saveMenu", method = RequestMethod.POST)
    public ResultVO saveMenu(@RequestBody @ApiParam(value = "插入菜单", required = true) SaveMenuReq request) {
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(request, menuDTO);
        return menuService.saveMenu(menuDTO);
    }

    @ApiOperation(value = "查询菜单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", value = "平台类型:  1 交易平台   2管理平台", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称（模糊查询）", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/listMenu")
    public ResultVO listMenu(
            @RequestParam(value = "platform") String platform,
            @RequestParam(value = "menuName", required = false) String menuName
    ) {
        ResultVO listMenuResp = new ResultVO();
        MenuListReq menuListReq = new MenuListReq();
        menuListReq.setPlatform(platform);
        menuListReq.setMenuName(menuName);
        ResultVO list = menuService.listMenu(menuListReq);
        listMenuResp.setResultCode(list.getResultCode());
        listMenuResp.setResultMsg(list.getResultMsg());
        listMenuResp.setResultData(list.getResultData());
        return listMenuResp;
    }

    @ApiOperation(value = "查询单个菜单", notes = "")
    @GetMapping(value = "/getMenuById")
    public ResultVO<MenuDTO> getMenuById(
            @RequestParam(value = "menuId", required = true) String menuId
    ) {
        if (StringUtils.isEmpty(menuId)) {
            return ResultVO.error("菜单ID不能为空");
        }
        return menuService.getMenuByMenuId(menuId);
    }

    @ApiOperation(value = "删除菜单", notes = "")
    @RequestMapping(value = "/deleteMenu/{menuId}", method = RequestMethod.DELETE)
    public ResultVO deleteMenu(@PathVariable("menuId") String menuId) {
        ResultVO deleteMenuRes = new ResultVO();
        ResultVO resultVO = menuService.deleteMenu(menuId);
        deleteMenuRes.setResultMsg(resultVO.getResultMsg());
        deleteMenuRes.setResultCode(resultVO.getResultCode());
        return deleteMenuRes;
    }

    @ApiOperation(value = "修改菜单", notes = "")
    @RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
    public ResultVO updateMenu(@RequestBody @ApiParam(value = "修改菜单", required = true) UpdateMenuReq updateMenuReq) {
        ResultVO updateMenuRes = new ResultVO();
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(updateMenuReq, menuDTO);
        ResultVO resultVO = menuService.updateMenu(menuDTO);
        updateMenuRes.setResultMsg(resultVO.getResultMsg());
        updateMenuRes.setResultCode(resultVO.getResultCode());
        return updateMenuRes;
    }

    @ApiOperation(value = "插入角色", notes = "")
    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    public ResultVO saveRole(@RequestBody @ApiParam(value = "插入角色", required = true) SaveRoleReq req) {
        //判空
        if (StringUtils.isEmpty(req.getRoleName())) {
            return ResultVO.error("角色名称不能为空");
        }
        //判断是否存在同名的角色
        RoleGetReq roleGetReq = new RoleGetReq();
        roleGetReq.setRoleName(req.getRoleName());
        RoleDTO roleDTO = roleService.getRole(roleGetReq);
        if (roleDTO != null) {
            return ResultVO.error("已存在同名角色");
        }

        roleDTO = new RoleDTO();
        BeanUtils.copyProperties(req, roleDTO);
        ResultVO resultVO = roleService.saveRole(roleDTO);
        return resultVO;
    }

    @ApiOperation(value = "查询角色", notes = "")
    @GetMapping(value = "/listRole")
    public ResultVO listRole() {
        ResultVO listRoleResp = new ResultVO();
        ResultVO list = roleService.listRole();
        listRoleResp.setResultCode(list.getResultCode());
        listRoleResp.setResultMsg(list.getResultMsg());
        listRoleResp.setResultData(list.getResultData());
        return listRoleResp;
    }

    @ApiOperation(value = "查询角色列表，带条件和分页", notes = "")
    @GetMapping(value = "/queryRolePage")
    public ResultVO queryRoleForPage(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "roleId", required = false) String roleId) {
        RolePageReq req = new RolePageReq();
        if (!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)) {
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
        } else {
            req.setPageNo(0);
            req.setPageSize(10);
        }
        req.setRoleName(roleName);
        req.setRoleId(roleId);
        return roleService.queryRolePage(req);
    }

    @ApiOperation(value = "删除角色", notes = "")
    @RequestMapping(value = "/deleteRole/{roleId}", method = RequestMethod.DELETE)
    public ResultVO deleteRole(@PathVariable("roleId") String roleId) {
        ResultVO deleteRoleRes = new ResultVO();
        ResultVO resultVO = roleService.deleteRole(roleId);
        deleteRoleRes.setResultMsg(resultVO.getResultMsg());
        deleteRoleRes.setResultCode(resultVO.getResultCode());
        return deleteRoleRes;
    }

    @ApiOperation(value = "修改角色", notes = "")
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public ResultVO updateRole(@RequestBody @ApiParam(value = "修改角色", required = true) UpdateRoleReq updateRoleReq) {
        ResultVO updateRoleRes = new ResultVO();
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(updateRoleReq, roleDTO);
        ResultVO resultVO = roleService.updateRole(roleDTO);
        updateRoleRes.setResultMsg(resultVO.getResultMsg());
        updateRoleRes.setResultCode(resultVO.getResultCode());
        return updateRoleRes;
    }

    @ApiOperation(value = "插入角色菜单", notes = "")
    @RequestMapping(value = "/saveRoleMenu", method = RequestMethod.POST)
    public ResultVO saveRoleMenu(@RequestBody @ApiParam(value = "插入角色", required = true) SaveRoleMenuReq request) {
        ResultVO savaRoleMenuResponse = new ResultVO();
        RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
        BeanUtils.copyProperties(request, roleMenuDTO);
        ResultVO resultVO = roleMenuService.saveRoleMenu(roleMenuDTO);
        savaRoleMenuResponse.setResultMsg(resultVO.getResultMsg());
        savaRoleMenuResponse.setResultCode(resultVO.getResultCode());
        return savaRoleMenuResponse;
    }

    @ApiOperation(value = "查询角色菜单", notes = "")
    @GetMapping(value = "/listRoleMenu")
    public ResultVO listRoleMenu() {
        ResultVO listRoleResp = new ResultVO();
        ResultVO list = roleMenuService.listRoleMenu();
        listRoleResp.setResultCode(list.getResultCode());
        listRoleResp.setResultMsg(list.getResultMsg());
        listRoleResp.setResultData(list.getResultData());
        return listRoleResp;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "platform", value = "平台类型:  1 交易平台   2管理平台", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "menuName", value = "菜单名称（模糊查询）", paramType = "query", required = false, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @ApiOperation(value = "在全菜单列表里勾选当前角色拥有的菜单", notes = "")
    @GetMapping(value = "/listRoleMenuByRoleId")
    public ResultVO listRoleMenuByRoleId(
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "menuName", required = false) String menuName
    ) {
        ResultVO listRoleResp = new ResultVO();
        ResultVO roleMenuResult = roleMenuService.listRoleMenuByRoleId(roleId);
        if (roleMenuResult.isSuccess()) {
            List<RoleMenuDTO> roleMenuDTOs = (List<RoleMenuDTO>) roleMenuResult.getResultData();
            HashMap hashMap = new HashMap(256);
            for (RoleMenuDTO roleMenuDTO : roleMenuDTOs) {
                hashMap.put(roleMenuDTO.getMenuId(), roleMenuDTO.getId());
            }
            MenuListReq menuListReq = new MenuListReq();
            menuListReq.setPlatform(platform);
            menuListReq.setMenuName(menuName);
            ResultVO menuDTOResult = menuService.listMenu(menuListReq);
            if (menuDTOResult.isSuccess()) {
                List<MenuDTO> menuDTOS = (List<MenuDTO>) menuDTOResult.getResultData();
                List<RoleMenuResp> roleMenuResps = new ArrayList<>();
                for (MenuDTO menuDTO : menuDTOS) {
                    RoleMenuResp roleMenuResp = new RoleMenuResp();
                    BeanUtils.copyProperties(menuDTO, roleMenuResp);
                    if (hashMap.containsKey(menuDTO.getMenuId())) {
                        roleMenuResp.setIsChecked(true);
                        roleMenuResp.setRoleMenuId((String) hashMap.get(menuDTO.getMenuId()));
                    } else {
                        roleMenuResp.setIsChecked(false);
                    }
                    roleMenuResps.add(roleMenuResp);
                }
                return ResultVO.success(roleMenuResps);
            } else {
                return ResultVO.error();
            }
        } else {
            return ResultVO.error();
        }
    }

    @ApiOperation(value = "删除角色菜单", notes = "")
    @RequestMapping(value = "/deleteRoleMenu/{id}", method = RequestMethod.DELETE)
    public ResultVO deleteRoleMenu(@PathVariable("id") String id) {
        ResultVO deleteRoleMenuRes = new ResultVO();
        ResultVO resultVO = roleMenuService.deleteRoleMenu(id);
        deleteRoleMenuRes.setResultMsg(resultVO.getResultMsg());
        deleteRoleMenuRes.setResultCode(resultVO.getResultCode());
        return deleteRoleMenuRes;
    }

    @ApiOperation(value = "修改角色菜单", notes = "")
    @RequestMapping(value = "/updateRoleMenu", method = RequestMethod.POST)
    public ResultVO updateRoleMenu(@RequestBody @ApiParam(value = "修改角色菜单", required = true) UpdateRoleMenuReq updateRoleMenuReq) {
        ResultVO updateRoleMenuRes = new ResultVO();
        RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
        BeanUtils.copyProperties(updateRoleMenuReq, roleMenuDTO);
        ResultVO resultVO = roleMenuService.updateRoleMenu(roleMenuDTO);
        updateRoleMenuRes.setResultMsg(resultVO.getResultMsg());
        updateRoleMenuRes.setResultCode(resultVO.getResultCode());
        return updateRoleMenuRes;
    }

    @ApiOperation(value = "插入用户角色", notes = "")
    @RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
    public ResultVO saveUserRole(@RequestBody @ApiParam(value = "插入用户角色", required = true) SaveUserRoleReq request) {
        ResultVO saveRoleMenuResponse = new ResultVO();
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        BeanUtils.copyProperties(request, userRoleDTO);
        ResultVO resultVO = userRoleService.saveUserRole(userRoleDTO);
        saveRoleMenuResponse.setResultMsg(resultVO.getResultMsg());
        saveRoleMenuResponse.setResultCode(resultVO.getResultCode());
        return saveRoleMenuResponse;
    }
//
//    @ApiOperation(value = "查询用户角色", notes = "")
//    @GetMapping(value = "/listUserRole")
//    public ResultVO listUserRole(){
//        ResultVO listUserRoleResp = new ResultVO();
//        ResultVO list = userRoleService.listUserRole();
//        listUserRoleResp.setResultCode(list.getResultCode());
//        listUserRoleResp.setResultMsg(list.getResultMsg());
//        listUserRoleResp.setResultData(list.getResultData());
//        return listUserRoleResp;
//    }

//    @ApiOperation(value = "删除用户角色", notes = "")
//    @RequestMapping(value = "/deleteUserRole/{id}",method = RequestMethod.DELETE)
//    public ResultVO deleteUserRole(@PathVariable("id") String id){
//        ResultVO deleteUserRoleRes = new ResultVO();
//        ResultVO resultVO = userRoleService.deleteUserRole(id);
//        deleteUserRoleRes.setResultMsg(resultVO.getResultMsg());
//        deleteUserRoleRes.setResultCode(resultVO.getResultCode());
//        return deleteUserRoleRes;
//    }
//
//    @ApiOperation(value = "修改用户角色", notes = "")
//    @RequestMapping(value = "/updateUserRole", method = RequestMethod.POST)
//    public ResultVO updateUserRole(@RequestBody @ApiParam(value = "修改用户角色", required = true) UpdateUserRoleReq updateUserRoleReq){
//        ResultVO updateUserRoleRes = new ResultVO();
//        UserRoleDTO userRoleDTO = new UserRoleDTO();
//        BeanUtils.copyProperties(updateUserRoleReq, userRoleDTO);
//        ResultVO resultVO = userRoleService.updateUserRole(userRoleDTO);
//        updateUserRoleRes.setResultMsg(resultVO.getResultMsg());
//        updateUserRoleRes.setResultCode(resultVO.getResultCode());
//        return updateUserRoleRes;
//    }


    @ApiOperation(value = "插入组织架构", notes = "")
    @RequestMapping(value = "/saveOrganization", method = RequestMethod.POST)
    public ResultVO saveOrganization(@RequestBody @ApiParam(value = "插入组织架构", required = true) SaveOrganizationReq request) {
        ResultVO savaOrganizationResponse = new ResultVO();
        OrganizationDTO OrganizationDTO = new OrganizationDTO();
        BeanUtils.copyProperties(request, OrganizationDTO);
        ResultVO resultVO = organizationService.saveOrganization(OrganizationDTO);
        savaOrganizationResponse.setResultMsg(resultVO.getResultMsg());
        savaOrganizationResponse.setResultCode(resultVO.getResultCode());
        return savaOrganizationResponse;
    }

    @ApiOperation(value = "查询组织架构", notes = "")
    @GetMapping(value = "/listOrganization/{parentId}")
    public ResultVO listOrganization(@PathVariable("parentId") String parentId) {
        ResultVO listOrganizationResp = new ResultVO();
        ResultVO list = organizationService.listOrganization(parentId);
        listOrganizationResp.setResultCode(list.getResultCode());
        listOrganizationResp.setResultMsg(list.getResultMsg());
        listOrganizationResp.setResultData(list.getResultData());
        return listOrganizationResp;
    }

    @ApiOperation(value = "查询组织架构详情", notes = "")
    @GetMapping(value = "/getOrganization/{orgId}")
    public ResultVO<OrganizationDTO> getOrganization(@PathVariable("orgId") String orgId) {
        return organizationService.getOrganization(orgId);
    }

    /**
     * 组织列表查询
     */
    @ApiOperation(value = "查询组织架构，带条件和分页", notes = "")
    @GetMapping(value = "queryOrganizationsForPage")
    public ResultVO queryOrganizationsForPage(
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "orgId", required = false) String orgId,
            @RequestParam(value = "orgName", required = false) String orgName,
            @RequestParam(value = "lanId", required = false) String lanId) {
        OrganizationsQueryReq req = new OrganizationsQueryReq();
        if (!StringUtils.isEmpty(pageNo) && !StringUtils.isEmpty(pageSize)) {
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
        } else {
            req.setPageNo(0);
            req.setPageSize(10);
        }
        req.setOrgId(orgId);
        req.setOrgName(orgName);
        req.setLanId(lanId);
        return organizationService.queryOrganizationsForPage(req);
    }

    @ApiOperation(value = "删除组织架构", notes = "")
    @RequestMapping(value = "/deleteOrganization/{orgId}", method = RequestMethod.DELETE)
    public ResultVO deleteOrganization(@PathVariable("orgId") String orgId) {
        ResultVO deleteOrganizationRes = new ResultVO();
        ResultVO resultVO = organizationService.deleteOrganization(orgId);
        deleteOrganizationRes.setResultMsg(resultVO.getResultMsg());
        deleteOrganizationRes.setResultCode(resultVO.getResultCode());
        return deleteOrganizationRes;
    }

    @ApiOperation(value = "修改组织架构", notes = "")
    @RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
    public ResultVO updateOrganization(@RequestBody @ApiParam(value = "修改组织架构", required = true) UpdateOrganizationReq updateOrganizationReq) {
        ResultVO updateOrganizationRes = new ResultVO();
        OrganizationDTO OrganizationDTO = new OrganizationDTO();
        BeanUtils.copyProperties(updateOrganizationReq, OrganizationDTO);
        ResultVO resultVO = organizationService.updateOrganization(OrganizationDTO);
        updateOrganizationRes.setResultMsg(resultVO.getResultMsg());
        updateOrganizationRes.setResultCode(resultVO.getResultCode());
        return updateOrganizationRes;
    }
}
