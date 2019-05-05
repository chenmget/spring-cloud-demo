package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplySaveDTO;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author wenlong.zhong
 * @date 2019/4/2
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/permissionApply")
@Api(value="商家权限申请:", tags={"商家权限申请:PermissionApplyB2BController"})
public class PermissionApplyB2BController {

    @Reference
    private PermissionApplyService permissionApplyService;

    @ApiOperation(value = "商家权限申请新建接口", notes = "商家权限申请新建接口")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数不全"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="/save")
    @UserLoginToken
    public ResultVO<String> save(@RequestBody @ApiParam(value = "商家权限申请新建入参", required = true) PermissionApplySaveDTO req)throws Exception{
        log.info("PermissionApplyB2BController.save(), input: PermissionApplySaveDTO={} ", JSON.toJSONString(req));
        UserDTO userDTO = UserContext.getUser();
        if (Objects.isNull(userDTO)) {
            return ResultVO.error("用户未登录");
        }
        if (StringUtils.isEmpty(userDTO.getRelCode())) {
            return ResultVO.error("该用户没有绑定商家信息，请确认");
        }
        req.setUserId(userDTO.getUserId());
        req.setMerchantId(userDTO.getRelCode());
        req.setName(userDTO.getUserName());
        ResultVO resp = permissionApplyService.savePermissionApply(req);
        log.info("PermissionApplyB2BController.save(), output: resp={} ", JSON.toJSONString(resp));
        return resp;
    }


}
