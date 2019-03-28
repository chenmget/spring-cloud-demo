package com.iwhalecloud.retail.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.partner.dto.PartnerAccountDTO;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.req.PartnerAccountPageReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.partner.service.PartnerAccountService;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/partnerAccount")
public class PartnerAccountController {
	
    @Reference
    private PartnerAccountService partnerAccountService;



    /**
     * 代理商账户列表分页查询接口.
     *
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    @ApiOperation(value = "代理商账户列表分页查询接口", notes = "根据在页面上选择的代理商，状态查询代理商账户列表")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/qryPagelist", method = RequestMethod.POST)
    public ResultVO<Page<PartnerAccountDTO>> qryPartnerAccountPageList(@RequestBody @ApiParam( value = "查询条件", required = true )PartnerAccountPageReq page) {
        ResultVO<Page<PartnerAccountDTO>> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(page));
        Page<PartnerAccountDTO> res = partnerAccountService.qryPartnerAccountPageList(page);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(res);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    /**
     * 代理商账户新增.
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    @ApiOperation(value = "代理商账户新增", notes = "代理商账户新增")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultVO<PartnerAccountDTO> addPartnerAccount(@RequestBody @ApiParam( value = "查询条件", required = true )PartnerAccountDTO dto) {
        ResultVO<PartnerAccountDTO> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(dto));
        PartnerAccountDTO res = partnerAccountService.addPartnerAccount(dto);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(res);
        resultVO.setResultMsg("成功");
        return resultVO;
    }

    /**
     * 代理商账户修改.
     *
     * @param dto
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    @ApiOperation(value = "代理商账户修改", notes = "代理商账户修改")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public ResultVO<PartnerAccountDTO> modifyPartnerAccount(@RequestBody @ApiParam( value = "查询条件", required = true )PartnerAccountDTO dto) {
        ResultVO<PartnerAccountDTO> resultVO = new ResultVO<>();
        log.info("page:{}", JSON.toJSONString(dto));
        PartnerAccountDTO res = partnerAccountService.modifyPartnerAccount(dto);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(res);
        resultVO.setResultMsg("成功");
        return resultVO;
    }
    /**
     * 代理商账户删除.
     *
     * @param accountId
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    @ApiOperation(value = "代理商账户删除", notes = "代理商账户删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "账户ID", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/remove/{accountId}", method = RequestMethod.DELETE)
    public ResultVO<Boolean> removePartnerAccount(@PathVariable String accountId) {
        ResultVO<Boolean> resultVO = new ResultVO<>();
        log.info("accountId:{}", JSON.toJSONString(accountId));
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
//        resultVO.setResultData(partnerAccountService.removePartnerAccount(accountId) > 0);
        resultVO.setResultMsg("成功");
        return resultVO;
    }


}