package com.iwhalecloud.retail.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.resp.BrandUrlResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDTO;
import com.iwhalecloud.retail.partner.dto.MerchantRulesDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.MerchantRulesDetailPageResp;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import com.iwhalecloud.retail.web.controller.b2b.partner.response.MerchantRulesImportResp;
import com.iwhalecloud.retail.web.controller.b2b.partner.utils.ExcelToMerchantRulesUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/merchantRules")
public class MerchantRulesController {

    @Reference
    private MerchantRulesService merchantRulesService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private BrandService brandService;

    @Reference
    private ProductService productService;

    @Reference
    private CommonRegionService commonRegionService;

    @Value("${fdfs.suffix.allowUpload}")
    private String allowUploadSuffix;

    /**
     * 新建 商家经营权限规则
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "新建商家权限规则接口", notes = "新建商家权限规则（可以批量）接口(通用）,逻辑：先删除原有的记录，再新建新的记录")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @Transactional
    public ResultVO<Integer> saveMerchantRules(@RequestBody @ApiParam(value = "新建商家权限规则参数", required = true) MerchantRulesSaveReq req) {
        // targetIdList字段为空  只是删除
        // 先删除原先的记录  再插入新的  zhongwenlong 屏蔽掉先删除再添加逻辑
//        MerchantRulesDeleteReq deleteReq = new MerchantRulesDeleteReq();
//        BeanUtils.copyProperties(req, deleteReq);
//        merchantRulesService.deleteMerchantRules(deleteReq);
        if (CollectionUtils.isEmpty(req.getTargetIdList())) {
            return ResultVO.success();
        }
        return merchantRulesService.saveMerchantRules(req);
    }

    /**
     * 删除 商家经营权限规则
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "根据条件（记录ID或记录ID集合（删除多条）、规则类型等）删除商家权限规则接口", notes = "根据条件（记录ID或记录ID集合（删除多条）、规则类型等）删除商家权限规则接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Transactional
    public ResultVO<Integer> deleteMerchantRules(@RequestBody @ApiParam(value = "删除 商家权限规则参数", required = true) MerchantRulesDeleteReq req) {
        // targetIdList字段为空  只是删除
        return merchantRulesService.deleteMerchantRules(req);
    }

    @ApiOperation(value = "根据ID删除单条商家权限规则接口", notes = "根据ID删除单条商家权限规则接口")
    @ApiImplicitParam(
            name = "merchantRuleId", value = "规则ID", paramType = "query", required = true, dataType = "String"
    )
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    @Transactional
    public ResultVO<Integer> deleteMerchantRulesById(@RequestParam(value = "merchantRuleId") String merchantRuleId) {
        MerchantRulesDeleteReq deleteReq = new MerchantRulesDeleteReq();
        deleteReq.setMerchantRuleId(merchantRuleId);
        return merchantRulesService.deleteMerchantRules(deleteReq);
    }


    /**
     * 获取 商家权限规则 列表
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取 商家权限规则 列表接口", notes = "可以根据 商家ID 规则类型、对象类型 条件进行查询（通用接口）")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Transactional
    public ResultVO<List<MerchantRulesDetailDTO>> listMerchantRulesDetail(@RequestBody @ApiParam(value = "查询商家权限规则列表参数", required = true) MerchantRulesDetailListReq req) {
        log.info("MerchantRulesController.listMerchantRulesDetail(), input: MerchantRulesDetailListReq={} ", JSON.toJSONString(req));
        ResultVO<List<MerchantRulesDetailDTO>> resultVO = merchantRulesService.listMerchantRulesDetail(req);
        log.info("MerchantRulesController.listMerchantRulesDetail(), input: MerchantRulesDetailListReq={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    @ApiOperation(value = "获取商家权限规则 接口", notes = "获取商家权限规则 接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/QueryMerchantRules", method = RequestMethod.POST)
    @Transactional
    public ResultVO<List<MerchantRulesDTO>> queryMerchantRules(@RequestBody @ApiParam(value = "获取商家权限规则 接口参数", required = true) MerchantRulesDetailListReq req) {

        return merchantRulesService.listMerchantRules(req);
    }

    @ApiOperation(value = "获取 分页商家权限规则 列表接口", notes = "可以根据 商家ID 规则类型、对象类型 条件进行查询分页（通用接口）")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/listPage", method = RequestMethod.POST)
    @Transactional
    public ResultVO<Page<MerchantRulesDetailDTO>> pageMerchantRules(@RequestBody @ApiParam(value = "分页查询商家权限规则列表参数", required = true) MerchantRulesDetailListReq req) {

        return merchantRulesService.pageMerchantRulesDetail(req);
    }

    /**
     * 获取 商家权限规则 分页
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取 商家权限规则  分页接口", notes = "可以根据 商家ID 规则类型、对象类型 条件进行查询（通用接口）")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @Transactional
    public ResultVO<Page<MerchantRulesDetailPageResp>> pageMerchantRules(@RequestBody @ApiParam(value = "查询商家权限规则列表参数", required = true) MerchantRulesDetailPageReq req) {

        return merchantRulesService.pageMerchantRules(req);
    }

    /**
     * 新建 商家经营权限规则
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "批量新建商家权限规则接口", notes = "新建商家权限规则（可以批量）接口(通用）,逻辑：通过解析excel模板，批量新增")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/saveExcelRules", method = RequestMethod.POST)
    @Transactional
    public ResultVO<Integer> saveExcelMerchantRules(@RequestBody @ApiParam(value = "新建商家权限规则参数", required = true) List<MerchantRuleSaveReq> req) {

        return merchantRulesService.saveExcelMerchantRules(req);
    }


    @ApiOperation(value = "上传并解析Excel文件", notes = "支持xlsx、xls格式")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/uploadExcel", headers = "content-type=multipart/form-data", method = RequestMethod.POST)
    public ResultVO uploadExcel(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (allowUploadSuffix.indexOf(suffix) <= -1) {
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }

        ResultVO resultVO = new ResultVO();
        try (InputStream is = file.getInputStream()){
            List<MerchantRulesImportResp> data = ExcelToMerchantRulesUtils.getData(is);
            for (int i = 0; i < data.size(); i++) {
                MerchantRulesImportResp merchantRulesImportResp = data.get(i);
                ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantByCode(merchantRulesImportResp.getMerchantCode());
                if (merchantDTOResultVO.isSuccess() && merchantDTOResultVO.getResultData() != null) {
                    MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
                    data.get(i).setMerchantName(merchantDTO.getMerchantName());
                    data.get(i).setMerchantType(merchantDTO.getMerchantType());
                    data.get(i).setMerchantId(merchantDTO.getMerchantId());
                } else {
                    return ResultVO.error("商家编码{" + merchantRulesImportResp.getMerchantCode() + "}不存在");
                }
                if (merchantRulesImportResp.getBrandCode() != null) {
                    ResultVO<BrandUrlResp> brandUrlRespResultVO = brandService.getBrandByBrandId(merchantRulesImportResp.getBrandCode());
                    if (brandUrlRespResultVO.isSuccess() && brandUrlRespResultVO.getResultData() != null) {
                        BrandUrlResp brandUrlResp = brandUrlRespResultVO.getResultData();
                        data.get(i).setBrandName(brandUrlResp.getName());
                    }
                } else if (merchantRulesImportResp.getTargetMerchantCode() != null) {
                    MerchantDTO merchantDTO = merchantService.getMerchantByCode(merchantRulesImportResp.getTargetMerchantCode()).getResultData();
                    if (merchantDTO == null) {
                        return ResultVO.error("对象编码{" + merchantRulesImportResp.getTargetMerchantCode() + "}不存在");
                    }
                    data.get(i).setTargetMerchantName(merchantDTO.getMerchantName());
                    data.get(i).setTargetMerchantId(merchantDTO.getMerchantId());
                } else if (merchantRulesImportResp.getRegionId() != null) {
                    CommonRegionDTO commonRegionDTO = commonRegionService.getCommonRegionById(merchantRulesImportResp.getRegionId()).getResultData();
                    if (commonRegionDTO == null) {
                        return ResultVO.error("区域编码{" + merchantRulesImportResp.getRegionId() + "}不存在");
                    }
                    data.get(i).setRegionName(commonRegionDTO.getRegionName());
                } else if (merchantRulesImportResp.getSn() != null) {
                    ProductResp productResp = productService.getProductBySn(merchantRulesImportResp.getSn()).getResultData();
                    if (productResp == null) {
                        return ResultVO.error("机型编码{" + merchantRulesImportResp.getSn() + "}不存在");
                    }
                    data.get(i).setProductId(productResp.getProductId());
                    data.get(i).setUnitName(productResp.getUnitName());
                }
            }
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(data);
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
            log.error("excel解析失败", e);
        }
        return resultVO;
    }

    /**
     * 商家是否有经营权限进行提示
     *
     * @param merchantId
     * @param productBaseId
     * @return
     */
    @ApiOperation(value = "商家是否有经营权限进行提示接口", notes = "商家是否有经营权限进行提示")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/checkProdListRule", method = RequestMethod.GET)
    @Transactional
    public ResultVO<Boolean> checkProdListRule(@RequestParam(value = "merchantId") String merchantId, @RequestParam(value = "productBaseId") String productBaseId) {

        return merchantRulesService.checkProdListRule(merchantId, productBaseId);
    }
}
