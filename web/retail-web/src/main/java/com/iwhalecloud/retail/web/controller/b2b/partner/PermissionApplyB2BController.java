package com.iwhalecloud.retail.web.controller.b2b.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplySaveDTO;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO<String> save(@RequestBody @ApiParam(value = "商家权限申请新建入参", required = true) PermissionApplySaveDTO req)throws Exception{
        log.info("PermissionApplyB2BController.save(), input: PermissionApplySaveDTO={} ", JSON.toJSONString(req));
        ResultVO resp = permissionApplyService.savePermissionApply(req);
        log.info("PermissionApplyB2BController.save(), output: resp={} ", JSON.toJSONString(resp));
        return resp;
    }


}
