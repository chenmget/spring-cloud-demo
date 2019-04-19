package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.request.AdminResourceInstDelReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstListPageReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstsGetByIdListAndStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstAddResp;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstListPageResp;
import com.iwhalecloud.retail.warehouse.service.AdminResourceInstService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.ResourceInstAddReqDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ResInsExcleImportResp;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.utils.ExcelToNbrUtils;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.List;

/**
 * @author he.sw
 * @date 2018/12/24
 */
@RestController
@RequestMapping("/api/b2b/adminResourceInst")
@Slf4j
public class AdminResourceInstB2BController {

	@Reference
    private AdminResourceInstService resourceInstService;

    @Value("${fdfs.suffix.allowUpload}")
    private String allowUploadSuffix;

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;

    @ApiOperation(value = "管理员串码管理页面", notes = "条件分页查询")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="getResourceInstList")
    public ResultVO<Page<ResourceInstListPageResp>> getResourceInstList(@RequestBody ResourceInstListPageReq req) {
        if (StringUtils.isEmpty(req.getMktResStoreIds())) {
            ResultVO.error("仓库为空");
        }
        return resourceInstService.getResourceInstList(req);
    }


    @ApiOperation(value = "串码入库", notes = "添加操作")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value="addResourceInst")
    @UserLoginToken
    public ResultVO<ResourceInstAddResp> addResourceInst(@RequestBody @Valid ResourceInstAddReqDTO dto) {
        if (StringUtils.isEmpty(dto.getMerchantId())) {
            return ResultVO.error("商家不能为空");
        }
        String userId = UserContext.getUserId();
        ResourceInstAddReq req = new ResourceInstAddReq();
        req.setCreateStaff(userId);
        req.setStatusCd(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setSourceType(ResourceConst.SOURCE_TYPE.MERCHANT.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.PUT_STORAGE.getCode());
        req.setStorageType(ResourceConst.STORAGETYPE.MANUAL_ENTRY.getCode());
        BeanUtils.copyProperties(dto, req);
        return resourceInstService.addResourceInst(req);
    }

    @ApiOperation(value = "删除串码", notes = "传入串码集合删除")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @DeleteMapping(value="delResourceInst")
    @UserLoginToken
    public ResultVO delResourceInst(@RequestBody @Valid ResourceInstsGetByIdListAndStoreIdReq delReq) {

        String userId = UserContext.getUserId();
        AdminResourceInstDelReq req = new AdminResourceInstDelReq();
        req.setUpdateStaff(userId);
        req.setMktResInstIds(delReq.getMktResInstIdList());
        req.setDestStoreId(delReq.getMktResStoreId());
        req.setStatusCd(ResourceConst.STATUSCD.DELETED.getCode());
        req.setEventType(ResourceConst.EVENTTYPE.CANCEL.getCode());

        // 只有可用状态的串码才能删除
        List<String> checkStatusCd = Lists.newArrayList(
                ResourceConst.STATUSCD.DELETED.getCode(),
                ResourceConst.STATUSCD.AUDITING.getCode(),
                ResourceConst.STATUSCD.ALLOCATIONING.getCode(),
                ResourceConst.STATUSCD.RESTORAGEING.getCode(),
                ResourceConst.STATUSCD.RESTORAGED.getCode(),
                ResourceConst.STATUSCD.SALED.getCode());
        req.setCheckStatusCd(checkStatusCd);
        log.info("AdminResourceInstB2BController.delResourceInst req={}", JSON.toJSONString(req));
        return resourceInstService.updateResourceInstByIds(req);
    }

    @ApiOperation(value = "串码还原在库可用", notes = "串码还原在库可用")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PutMapping(value="resetResourceInst")
    @UserLoginToken
    public ResultVO resetResourceInst(@RequestParam(value = "idList") @ApiParam(value = "id列表") List<String> idList
    , @RequestParam(value = "statusCd") @ApiParam(value = "状态") String statusCd) {
        if(CollectionUtils.isEmpty(idList)) {
            return ResultVO.error("id can not be null");
        }
        if(!ResourceConst.STATUSCD.AVAILABLE.getCode().equals(statusCd) && !ResourceConst.STATUSCD.DELETED.getCode().equals(statusCd)) {
            return ResultVO.error("statusCd not rigth");
        }
        String userId = UserContext.getUserId();
        AdminResourceInstDelReq req = new AdminResourceInstDelReq();
        req.setUpdateStaff(userId);
        req.setMktResInstIds(idList);
        req.setStatusCd(statusCd);
        req.setEventType(ResourceConst.EVENTTYPE.RECYCLE.getCode());
        List<String> checkStatusCd = Lists.newArrayList(ResourceConst.STATUSCD.AVAILABLE.getCode());
        req.setCheckStatusCd(checkStatusCd);
        log.info("AdminResourceInstB2BController.delResourceInst req={}", JSON.toJSONString(req));
        return resourceInstService.updateResourceInstByIds(req);
    }

    @ApiOperation(value = "上传串码文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/uploadExcel",headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO uploadExcel(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }

        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
            List<ResInsExcleImportResp> data = ExcelToNbrUtils.getData(is);
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(data);
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
            log.error("excel解析失败",e);
        }
        return resultVO;
    }

}
