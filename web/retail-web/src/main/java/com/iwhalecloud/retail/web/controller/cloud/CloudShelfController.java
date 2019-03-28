package com.iwhalecloud.retail.web.controller.cloud;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.dto.CloudShelfBindUserDTO;
import com.iwhalecloud.retail.oms.dto.CloudShelfDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudShelfPageReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfAddReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfRequestDTO;
import com.iwhalecloud.retail.oms.service.CloudShelfService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import com.iwhalecloud.retail.partner.dto.PartnerShopDTO;
import com.iwhalecloud.retail.partner.service.PartnerShopService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.RegionsGetReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.service.RegionsService;
import com.iwhalecloud.retail.system.service.UserService;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * @Auther: lin.wh
 * @Date: 2018/10/12 14:56
 * @Description: 云货架（增删改查）
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/cloudShelf")
public class CloudShelfController extends BaseController {

    @Reference
    private CloudShelfService cloudShelfService;

    @Reference
    private PartnerShopService partnerShopService;

    @Reference
    private RegionsService regionService;

    @Reference
    private UserService userService;

    @ApiOperation(value = "云货架分页查询接口", notes = "根据在页面上选择的厅店id以及输入的关键字等查询内容")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryCloudShelfPageList", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Page<CloudShelfDTO>> queryCloudShelfPageList(@RequestBody @ApiParam(value = "查询条件", required = true) CloudShelfPageReq page) {
        ResultVO<Page<CloudShelfDTO>> resultVO = new ResultVO<>();
        try {
            String partnerShopId = UserContext.getPartnerShopId();
            if (null == page.getUserId() || "".equals(page.getUserId())){
                page.setUserId(UserContext.getUserId());
            }
            if (null == page.getAdscriptionShopId() || "".equals(page.getAdscriptionShopId())){
                page.setAdscriptionShopId(UserContext.getPartnerShopId());
            }
            Page<CloudShelfDTO> cloudShelfDTO = cloudShelfService.queryCloudShelfList(page);
            for (int i = 0; i < cloudShelfDTO.getRecords().size(); i++) {
                String adscriptionShop = cloudShelfDTO.getRecords().get(i).getAdscriptionShopId();
                if(!StringUtils.isEmpty(adscriptionShop)){
//                    PartnerShopDTO request1 = new PartnerShopDTO();
//                    request1.setPartnerShopId(adscriptionShop);
                    PartnerShopDTO dto = partnerShopService.getPartnerShop(adscriptionShop);
                    if (dto != null) {
                        cloudShelfDTO.getRecords().get(i).setAdscriptionShopName(dto.getName());
                    }
                }
                String adscriptionCity = cloudShelfDTO.getRecords().get(i).getAdscriptionCity();
                RegionsGetReq req = new RegionsGetReq();
                req.setRegionId(adscriptionCity);
                ResultVO<RegionsGetResp> result = regionService.getRegion(req);
                RegionsGetResp region = result.getResultData();
                if (region != null) {
                    String localName = region.getRegionName();
                    cloudShelfDTO.getRecords().get(i).setAdscriptionCityName(localName);
                }
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(cloudShelfDTO);
            resultVO.setResultMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "查询CloudShelfDTO详情", notes = "根据num，查询CloudShelfDTO对象")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/queryCloudShelfListDetails", method = RequestMethod.GET)
//    @UserLoginToken
    public ResultVO<CloudShelfDTO> queryCloudShelfListDetails(@RequestParam String num, @ApiParam(value = "是否查询设备列表", defaultValue = "false") @RequestParam Boolean isQryDevice) {
        ResultVO resultVO = new ResultVO();
        try {
            CloudShelfDTO cloudShelfDTO = cloudShelfService.queryCloudShelfListDetails(num, isQryDevice);
            if (cloudShelfDTO.getCloudShelfBindUsers() != null){
                for (CloudShelfBindUserDTO cloudShelfBindUserDTO : cloudShelfDTO.getCloudShelfBindUsers()){
                    UserDTO userDTO = userService.getUserByUserId(cloudShelfBindUserDTO.getUserId());
                    if (null != userDTO){
                        cloudShelfBindUserDTO.setUserName(userDTO.getLoginName());
                    } else {
                        cloudShelfBindUserDTO.setUserName("NULL");
                        //resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                        //resultVO.setResultMsg("未能在电商平台找到该货架关联的账号！");
                        //return resultVO;
                    }
                }
            }
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(cloudShelfDTO);
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    /**
     * 新增云货架
     *
     * @param request
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    @ApiOperation(value = "新增CloudShelfDTO", notes = "新增云货架  \n"
            + "{  \n"
            + "adscription_shop_id  \n"
            + "：//厅店ID  \n"
            + "：//云货架模板ID  \n"
            + "：//货架名称  \n"
            + "：//货架类目列表  \n"
            + "：//关联设备列表  \n"
            + "}  \n"
            + "云货架id :自增  \n"
            + "云货架NUMBER规则： 时间戳生成  \n"
            + "业务逻辑  \n"
            + "1.从默认默认类目、默认运营位及内容中复制一份数据，生成云货架及详情数据  \n"
            + "2.回写设备表中关联的云货架NUMBER  \n"
            + "3.创建成功后发挥1复制的数据")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/createCloudShelf", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<CloudShelfDTO> createCloudShelf(@RequestBody @ApiParam(value = "新增云货架请求参数", required = true) CloudShelfAddReq request) {
        ResultVO resultVO = new ResultVO();
        try {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
            resultVO.setResultData(cloudShelfService.createCloudShelf(request, UserContext.getUserId()));
            resultVO.setResultMsg("success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "修改CloudShelfDTO", notes = "接口支持根据id修改云货架信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/updateCloudShelfStatus", method = RequestMethod.PUT)
    @UserLoginToken
    public ResultVO updateCloudShelfStatus(@RequestBody CloudShelfDTO dto) {
        ResultVO resultVO = new ResultVO();
        try {
            cloudShelfService.updateCloudShelfStatus(dto);
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

    @ApiOperation(value = "删除CloudShelfDTO", notes = "根据id，进行删除操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteCloudShelf/{id}", method = RequestMethod.DELETE)
    @UserLoginToken
    public ResultVO deleteCloudShelf(@PathVariable Long id) {
        ResultVO resultVO = new ResultVO();
        try {
            CloudShelfDTO dto = new CloudShelfDTO();
            dto.setId(id);
            cloudShelfService.deleteCloudShelf(dto);
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

    @ApiOperation(value = "修改云货架基础数据", notes = "接口支持根据云货架ID,货架名称,货架类目列表,关联设备列表,状态参数修改云货架信息")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/modifyCloudShelfByParam", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO modifyCloudShelfByParam(@RequestBody CloudShelfRequestDTO cloudShelfRequestDTO) {
        return cloudShelfService.modifyCloudShelfByParam(cloudShelfRequestDTO, UserContext.getUserId());
    }

}
