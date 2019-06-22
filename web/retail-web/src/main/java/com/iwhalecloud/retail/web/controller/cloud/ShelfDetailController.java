package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.ShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailUpdateDTO;
import com.iwhalecloud.retail.oms.service.ShelfDetailService;
import com.iwhalecloud.retail.web.controller.BaseController;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/23 22:27
 * @Description:
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/shelfDetail")
public class ShelfDetailController extends BaseController {
    @Reference
    private ShelfDetailService shelfDetailService;

    @RequestMapping(value = "/createShelfDetail", method = RequestMethod.POST)
    public ResultVO createShelfDetail(@RequestBody ShelfDetailDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            shelfDetailService.createShelfDetail(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("新增成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping(value = "/updateShelfDetailStatus", method = RequestMethod.PUT)
    public ResultVO updateShelfDetailStatus(@RequestBody ShelfDetailUpdateDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            shelfDetailService.updateShelfDetailStatus(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("设置成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @RequestMapping(value = "deleteShelfDetail", method = RequestMethod.DELETE)
    public ResultVO deleteShelfDetail(@RequestBody ShelfDetailDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            shelfDetailService.deleteShelfDetail(dto);
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

    @GetMapping(value = "queryCloudShelfDetailProductList")
    public ResultVO<List<ShelfDetailDTO>> queryCloudShelfDetailProductList(@RequestParam(value = "cloudShelfNumber", required = true) String cloudShelfNumber,
                                                                           @RequestParam(value = "shelfCategoryId", required = true) String shelfCategoryId) {
        ResultVO resultVO = new ResultVO();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("cloudShelfNumber", cloudShelfNumber);
        map.put("shelfCategoryId", shelfCategoryId);
        try {
            List<ShelfDetailDTO> shelfDetailDTO = shelfDetailService.queryCloudShelfDetailProductList(map);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(shelfDetailDTO);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "查询内容列表", notes = "根据cloudShelfNumber和shelfCategoryId，查询绑定内容列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cloudShelfNumber", value = "云货架id", paramType = "path", required = true, dataType = "String"),
            @ApiImplicitParam(name = "shelfCategoryId", value = "类目id", paramType = "path", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "/queryCloudShelfDetailContentList/{cloudShelfNumber}/{shelfCategoryId}")
    public ResultVO<List<ShelfDetailDTO>> queryCloudShelfDetailContentList(@PathVariable(value = "cloudShelfNumber", required = true) String cloudShelfNumber,
                                                                           @PathVariable(value = "shelfCategoryId", required = true) String shelfCategoryId) {
        ResultVO resultVO = new ResultVO();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("cloudShelfNumber", cloudShelfNumber);
        map.put("shelfCategoryId", shelfCategoryId);
        try {
            List<ShelfDetailDTO> shelfDetailDTO = shelfDetailService.queryCloudShelfDetailContentList(map);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(shelfDetailDTO);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

}

