package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.ShelfTemplatesReq;
import com.iwhalecloud.retail.oms.service.ShelfTemplatesService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/16 14:28
 * @Description: 云货架模版（增删改查）
 */

@RestController
@RequestMapping("/api/shelfTemplates")
@Slf4j
public class ShelfTemplatesController extends BaseController {
    @Reference
    private ShelfTemplatesService shelfTemplatesService;

    /**
     * 新增云货架模版.
     *
     * @param request
     * @return
     * @author Ji.kai
     * @date 2018/11/12 15:27
     */
    @ApiOperation(value = "新增云货架模版", notes = "根据tempNumber shelfTemplatesName shelfTemplatesDesc imgUrl等等，新增云货架模版")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO createShelfTemplates(@RequestBody @ApiParam(value = "新增云货架请求参数", required = true) ShelfTemplatesReq request) {
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(shelfTemplatesService.createShelfTemplates(request, UserContext.getUserId()));
        resultVO.setResultMsg("success");
        return resultVO;
    }

    /**
     * 更新云货架模版.
     *
     * @param request
     * @return
     * @author Ji.kai
     * @date 2018/11/12 15:27
     */
    @ApiOperation(value = "更新云货架模版", notes = "根据id，更新、、、的数据")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO updateShelfTemplatesStatus(@RequestBody @ApiParam(value = "更新云货架模版", required = true) ShelfTemplatesReq request) {
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(shelfTemplatesService.updateShelfTemplatesStatus(request, UserContext.getUserId()));
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "分页查询云货架模版", notes = "根据pageNo pageSize，查询云货架模版分页信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryShelfTemplates", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO queryShelfTemplates(@RequestBody ShelfTemplatesDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            Page<ShelfTemplatesDTO> shelfTemplatesDTO = shelfTemplatesService.queryShelfTemplates(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(shelfTemplatesDTO);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "删除云货架模版", notes = "根据id，修改云货架模版表对应id的is_deleted状态改为1")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteShelfTemplates", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO deleteShelfTemplates(@RequestBody ShelfTemplatesDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            shelfTemplatesService.deleteShelfTemplates(dto);
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
}

