package com.iwhalecloud.retail.web.controller.cms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import com.iwhalecloud.retail.oms.service.ContentMenuService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/25 11:26
 * @Description:
 */

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/contentMenu")
public class ContentMenuController extends BaseController {
    @Reference
    private ContentMenuService contentMenuService;

    @ApiOperation(value = "新增目录", notes = "传入TagDTO对象，进行新增操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/createContentMenu", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO createContentMenu(@RequestBody CataLogDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            String userId = UserContext.getUserId();
            dto.setOprId(userId);
            List<CataLogDTO> dtoes = contentMenuService.queryMenuByParentAndName(dto);
            if (dtoes.size() > 0){
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                resultVO.setResultMsg("该父目录下已存在同名目录！");
                return resultVO;
            }
            contentMenuService.createContentMenu(dto);
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

    @ApiOperation(value = "删除目录", notes = "传入TagDTO对象，进行删除操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "deleteContentMenu", method = RequestMethod.DELETE)
    @UserLoginToken
    public ResultVO deleteContentMenu(@RequestBody CataLogDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            CataLogDTO cataLogDTO = contentMenuService.queryContentMenuDetail(dto);
            int canDel = cataLogDTO.getCanDel();
            JSONObject container = new JSONObject();
            if (canDel == 0) {
                container.put("success", false);
                container.put("message", "当前目录的“是否可删除”属性为“不可删除”，无法删除该目录");
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData(container);
                resultVO.setResultMsg("");
                return resultVO;
            }
            List<CataLogDTO> cataLogDTO1 = contentMenuService.queryContentMenuList(dto);
            if (cataLogDTO1.size() > 0) {
                container.put("success", false);
                container.put("message", "待删除目录下还有子目录，无法删除该目录");
                resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                resultVO.setResultData(container);
                resultVO.setResultMsg("");
                return resultVO;
            }
            contentMenuService.deleteContentMenu(dto);
            contentMenuService.updateContentBase(dto);
            container.put("success", true);
            container.put("message", "删除成功");
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(container);
            resultVO.setResultMsg("");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "修改目录", notes = "传入TagDTO对象，进行修改操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/editContentMenu", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO editContentMenu(@RequestBody CataLogDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            String userId = UserContext.getUserId();
            dto.setOprId(userId);
            contentMenuService.editContentMenu(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("修改成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "查询目录", notes = "传入TagDTO，进行查询操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryContentMenuList", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<List<CataLogDTO>> queryContentMenuList(@RequestBody CataLogDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            String name = dto.getName();
            if (!StringUtils.isEmpty(name)) {
                dto.setName("%" + name + "%");
            }
            List<CataLogDTO> cataLogDTO = contentMenuService.queryContentMenuList(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(cataLogDTO);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "移动目录", notes = "传入cataId、parentCataId，进行更新目录结构操作")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/moveContentMenu", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO moveContentMenu(@RequestBody CataLogDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            String userId = UserContext.getUserId();
            dto.setOprId(userId);
            CataLogDTO cataLogDTO1 = contentMenuService.queryContentMenuDetail(dto);
            String name = cataLogDTO1.getName();
            Long cataId = dto.getCataid();
            Long parentCataId = dto.getParentCataId();
            dto.setCataid(parentCataId);
            List<CataLogDTO> cataLogDTO = contentMenuService.queryContentMenuList(dto);
            if (!CollectionUtils.isEmpty(cataLogDTO)) {
                for (int i = 0; i < cataLogDTO.size(); i++) {
                    if (cataLogDTO.get(i).getName().equals(name)) {
                        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
                        resultVO.setResultData("同层级目录已有名称相同的目录");
                        resultVO.setResultMsg("");
                        return resultVO;
                    }
                    dto.setCatalogLevel(cataLogDTO.get(1).getCatalogLevel());
                }
                dto.setCataid(cataId);
            }
            contentMenuService.moveContentMenu(dto);
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData("移动目录成功");
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }
}

