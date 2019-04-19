package com.iwhalecloud.retail.web.controller.system;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/cache")
@Api(value="缓存清空", tags={"缓存清空"})
public class CacheController extends BaseController {

    @ApiOperation(value = "清空sys_public_dict（公共字典）表缓存", notes = "清空sys_public_dict（公共字典）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCachePublicDict", method = RequestMethod.GET)
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_PUBLIC_DICT, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCachePublicDict() {
        log.info("CacheController.cleanCachePublicDict clean sys_public_dict table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空sys_user（用户）表缓存", notes = "清空sys_user（用户）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheUser", method = RequestMethod.GET)
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_USER, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheUser() {
        log.info("CacheController.cleanCacheUser clean sys_user table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空sys_user_role（用户-角色）表缓存", notes = "清空sys_user_role（用户-角色）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheUserRole", method = RequestMethod.GET)
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_USER_ROLE, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheUserRole() {
        log.info("CacheController.cleanCacheUserRole clean sys_user_role table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空sys_role_menu（角色-菜单）表缓存", notes = "清空sys_role_menu（角色-菜单）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheRoleMenu", method = RequestMethod.GET)
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_ROLE_MENU, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheRoleMenu() {
        log.info("CacheController.cleanCacheRoleMenu clean sys_role_menu table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空sys_menu（菜单）表缓存", notes = "清空sys_menu（菜单）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheMenu", method = RequestMethod.GET)
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_MENU, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheMenu() {
        log.info("CacheController.cleanCacheMenu clean sys_menu table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空sys_config_info（配置）表缓存", notes = "清空sys_config_info（配置）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheConfigInfo", method = RequestMethod.GET)
    @CacheEvict(value = SystemConst.CACHE_NAME_SYS_CONFIG_INFO, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheConfigInfo() {
        log.info("CacheController.cleanCacheConfigInfo clean sys_config_info table cache success!!!");
        return ResultVO.success(true);
    }

    /**
     * 这个方法会清除两个缓存
     */
    @ApiOperation(value = "清空sys_common_region(本地网区域）表缓存", notes = "清空sys_common_region（本地网区域）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheCommonRegion", method = RequestMethod.GET)
    @CacheEvict(value = {SystemConst.CACHE_NAME_SYS_COMMON_REGION, SystemConst.CACHE_NAME_SYS_COMMON_REGION_LIST}, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheCommonRegion() {
        log.info("CacheController.cleanCacheCommonRegion clean sys_common_region table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空par_merchant(商家）表缓存", notes = "清空par_merchant(商家）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheMerchant", method = RequestMethod.GET)
    @CacheEvict(value = PartnerConst.CACHE_NAME_PAR_MERCHANT, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheMerchant() {
        log.info("CacheController.cleanCacheMerchant clean par_merchant table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空par_business_entity(经营主体）表缓存", notes = "清空par_business_entity(经营主体）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheBusinessEntity", method = RequestMethod.GET)
    @CacheEvict(value = PartnerConst.CACHE_NAME_PAR_BUSINESS_ENTITY, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheBusinessEntity() {
        log.info("CacheController.cleanCacheBusinessEntity clean par_merchant table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空prod_file(文件表）表缓存", notes = "清空prod_file(文件表）表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheProdFile", method = RequestMethod.GET)
    @CacheEvict(value = GoodsConst.CACHE_NAME_PROD_FILE, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheProdFile() {
        log.info("CacheController.cleanCacheProdFile clean prod_file table cache success!!!");
        return ResultVO.success(true);
    }

    @ApiOperation(value = "清空wf_route表缓存", notes = "清空wf_route表缓存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/cleanCacheNameWfRoute", method = RequestMethod.GET)
    @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_ROUTE, allEntries = true, beforeInvocation = true)
    public ResultVO<Boolean> cleanCacheNameWfRoute() {
        log.info("CacheController.cleanCacheNameWfRoute clean wf_route table cache success!!!");
        return ResultVO.success(true);
    }
}
