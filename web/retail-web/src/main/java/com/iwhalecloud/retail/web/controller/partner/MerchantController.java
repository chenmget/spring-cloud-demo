package com.iwhalecloud.retail.web.controller.partner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.TagsConst;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.service.dubbo.MerchantTagRelService;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantDetailDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.dto.resp.FactoryMerchantDTO;
import com.iwhalecloud.retail.partner.dto.resp.MerchantPageResp;
import com.iwhalecloud.retail.partner.dto.resp.RetailMerchantDTO;
import com.iwhalecloud.retail.partner.dto.resp.SupplyMerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.dto.request.UserPageReq;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.partner.utils.ExcelToMerchantListUtils;
import com.iwhalecloud.retail.web.controller.partner.utils.MerchantColumn;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/merchant")
public class MerchantController {

    @Reference
    private MerchantService merchantService;

    @Reference
    private MerchantTagRelService merchantTagRelService;

    @Reference
    private UserService userService;

    @Reference
    private MerchantRulesService merchantRulesService;

    private final Integer EXPORT_PAGE_NO = 1;
    // 导出数据量控制在1万条
    private final Integer EXPORT_PAGE_SIZE = 10000;

    /**
     * 获取商家分页列表
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取商家分页列表接口", notes = "可以根据商家名称、编码、类型条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResultVO<Page<MerchantPageResp>> pageMerchant(@RequestBody MerchantPageReq req) {
        log.info("MerchantController.pageMerchant() input: MerchantPageReq={}", JSON.toJSONString(req));
        ResultVO<Page<MerchantPageResp>> pageResultVO = merchantService.pageMerchant(req);
        // 追加用户登录帐号
        if (null != pageResultVO && pageResultVO.isSuccess() && null != pageResultVO.getResultData()) {
            List<MerchantPageResp> merchantDTOS = pageResultVO.getResultData().getRecords();

            if (!CollectionUtils.isEmpty(merchantDTOS)) {
                merchantDTOS.forEach(merchantDTO -> {
                    merchantDTO.setLoginName(getLoginName(merchantDTO.getMerchantId()));
                });
            }
        }
        return pageResultVO;
    }

    /**
     * 获取商家分页列表
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取商家分页列表接口", notes = "前置条件：只有政企供货标签的供应商可以做政企供货，可以根据商家名称、编码、类型条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pageWithRule", method = RequestMethod.POST)
    public ResultVO<Page<MerchantPageResp>> pageMerchantWithRule(@RequestBody MerchantPageReq req) {
        MerchantTagRelListReq merchantTagRelListReq = new MerchantTagRelListReq();
        merchantTagRelListReq.setTagId(TagsConst.GOVERNMENT_ENTERPRISE_SUPPLIERS);
        ResultVO<List<MerchantTagRelDTO>> listResultVO = merchantTagRelService.listMerchantTagRel(merchantTagRelListReq);
        log.info("MerchantController.pageMerchantWithRule() merchantTagRelService.listMerchantTagRel merchantTagRelListReq={}, listResultVO={}", JSON.toJSONString(merchantTagRelListReq), JSON.toJSONString(listResultVO));
        if (null == listResultVO || !listResultVO.isSuccess() || CollectionUtils.isEmpty(listResultVO.getResultData())) {
            return ResultVO.error("只有政企供货标签的供应商可以做政企供货");
        }
        List<String> merchantIds = Lists.newArrayList();
        for (MerchantTagRelDTO merchantTagRelDTO : listResultVO.getResultData()) {
            merchantIds.add(merchantTagRelDTO.getMerchantId());
        }
        if (CollectionUtils.isEmpty(merchantIds)) {
            return ResultVO.error("没有政企供货标签的供应商可以做政企供货");
        }
        if (StringUtils.isNotEmpty(req.getMerchantId())) {
            if (!merchantIds.contains(req.getMerchantId())) {
                return ResultVO.error("没有政企供货标签的供应商可以做政企供货");
            }
        } else {
            req.setMerchantIdList(merchantIds);
        }
        log.info("MerchantController.pageMerchantWithRule() input: MerchantPageReq={}", JSON.toJSONString(req));
        ResultVO<Page<MerchantPageResp>> pageResultVO = merchantService.pageMerchant(req);
        // 追加用户登录帐号
        if (null != pageResultVO && pageResultVO.isSuccess() && null != pageResultVO.getResultData()) {
            List<MerchantPageResp> merchantDTOS = pageResultVO.getResultData().getRecords();

            if (!CollectionUtils.isEmpty(merchantDTOS)) {
                merchantDTOS.forEach(merchantDTO -> {
                    merchantDTO.setLoginName(getLoginName(merchantDTO.getMerchantId()));
                });
            }
        }
        return pageResultVO;
    }

    @ApiOperation(value = "商家列表导出", notes = "商家列表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accountId", value = "商家账户ID", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })//lws
    @RequestMapping(value = "/exportMerchantList", method = RequestMethod.POST)
    public void exportMerchantList(@RequestBody MerchantPageReq req, HttpServletResponse response) {
        log.info("MerchantController.exportMerchantList() input: MerchantPageReq={}", JSON.toJSONString(req));
        req.setPageNo(EXPORT_PAGE_NO);
        //数据量控制在1万条
        req.setPageSize(EXPORT_PAGE_SIZE);
        ResultVO<Page<MerchantPageResp>> resultVO = merchantService.pageMerchant(req);
        if (null != resultVO && resultVO.isSuccess() && null != resultVO.getResultData()) {
            List<MerchantPageResp> merchantDTOS = resultVO.getResultData().getRecords();

            if (!CollectionUtils.isEmpty(merchantDTOS)) {
                merchantDTOS.forEach(merchantDTO -> {
                    merchantDTO.setLoginName(getLoginName(merchantDTO.getMerchantId()));
                });
            }
        }
        List<ExcelTitleName> excelTitleNames = MerchantColumn.merchantColumn();
        OutputStream output = null;
        try {
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "商家列表";
            ExcelToMerchantListUtils.builderOrderExcel(workbook, resultVO.getResultData().getRecords(), excelTitleNames);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        } catch (Exception e) {
            log.error("商家列表导出失败 error={}", e);
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "获取零售商类型的商家分页列表接口", notes = "")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pageRetailMerchant", method = RequestMethod.POST)
    public ResultVO<Page<RetailMerchantDTO>> pageRetailMerchant(@RequestBody @ApiParam(value = "分页参数", required = true) RetailMerchantPageReq req) {
        log.info("MerchantController.pageRetailMerchant() input: RetailMerchantPageReq={}", JSON.toJSONString(req));
        ResultVO<Page<RetailMerchantDTO>> pageResultVO = merchantService.pageRetailMerchant(req);

        // 追加用户登录帐号
        if (null != pageResultVO && pageResultVO.isSuccess() && null != pageResultVO.getResultData()) {
            List<RetailMerchantDTO> retailDTOS = pageResultVO.getResultData().getRecords();

            if (!CollectionUtils.isEmpty(retailDTOS)) {
                retailDTOS.forEach(retailDTO -> {
                    retailDTO.setLoginName(getLoginName(retailDTO.getMerchantId()));
                });
            }
        }

        return pageResultVO;
    }

    @ApiOperation(value = "零售商列表导出", notes = "零售商类型的商家列表导出")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })//lws
    @RequestMapping(value = "/exportRetailMerchantList", method = RequestMethod.POST)
    public void exportRetailMerchantList(@RequestBody RetailMerchantPageReq req, HttpServletResponse response) {
        log.info("MerchantController.exportMerchantList() input: MerchantPageReq={}", JSON.toJSONString(req));
        req.setPageNo(EXPORT_PAGE_NO);
        // 数据量控制在1万条
        req.setPageSize(EXPORT_PAGE_SIZE);
        ResultVO<Page<RetailMerchantDTO>> resultVO = merchantService.pageRetailMerchant(req);
        List<RetailMerchantDTO> merchantDTOS = resultVO.getResultData().getRecords();

        if (!CollectionUtils.isEmpty(merchantDTOS)) {
            merchantDTOS.forEach(merchantDTO -> {
                merchantDTO.setLoginName(getLoginName(merchantDTO.getMerchantId()));
            });
        }
        List<ExcelTitleName> excelTitleNames = MerchantColumn.retailMerchantFields();
        OutputStream output = null;
        try {
            // 创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "零售商列表";
            ExcelToMerchantListUtils.builderOrderExcel(workbook, resultVO.getResultData().getRecords(), excelTitleNames);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        } catch (Exception e) {
            log.error("零售商列表导出失败 error={}", e);
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @ApiOperation(value = "获取供应商类型的商家分页列表接口", notes = "")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pageSupplyMerchant", method = RequestMethod.POST)
    public ResultVO<Page<SupplyMerchantDTO>> pageSupplyMerchant(@RequestBody @ApiParam(value = "分页参数", required = true) SupplyMerchantPageReq req) {
        log.info("MerchantController.pageSupplyMerchant() input: SupplyMerchantPageReq={}", JSON.toJSONString(req));
        ResultVO<Page<SupplyMerchantDTO>> pageResultVO = merchantService.pageSupplyMerchant(req);

        // 追加用户登录帐号
        if (null != pageResultVO && pageResultVO.isSuccess() && null != pageResultVO.getResultData()) {
            List<SupplyMerchantDTO> supplyDTOS = pageResultVO.getResultData().getRecords();

            if (!CollectionUtils.isEmpty(supplyDTOS)) {
                supplyDTOS.forEach(supplyDTO -> {
                    supplyDTO.setLoginName(getLoginName(supplyDTO.getMerchantId()));
                });
            }
        }

        return pageResultVO;
    }


    /**
     * 获取用户登录名称
     *
     * @param merchantId 商家ID
     * @return
     */
    private String getLoginName(String merchantId) {
        if (StringUtils.isEmpty(merchantId)) {
            return "";
        }
        UserGetReq userGetReq = new UserGetReq();
        userGetReq.setRelCode(merchantId);
        UserDTO user = userService.getUser(userGetReq);
        if (!Objects.isNull(user)) {
            return user.getLoginName();
        }
        return "";
    }

    @ApiOperation(value = "供应商列表导出", notes = "供应商列表导出")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/exportSupplyMerchantList", method = RequestMethod.POST)
    public void exportSupplyMerchantList(@RequestBody SupplyMerchantPageReq req, HttpServletResponse response) {
        log.info("MerchantController.exportSupplyMerchantList() input: req={}", JSON.toJSONString(req));
        req.setPageNo(EXPORT_PAGE_NO);
        req.setPageSize(EXPORT_PAGE_SIZE);
        ResultVO<Page<SupplyMerchantDTO>> resultVO = merchantService.pageSupplyMerchant(req);
        if (null != resultVO && resultVO.isSuccess() && null != resultVO.getResultData()) {
            List<SupplyMerchantDTO> merchantDTOS = resultVO.getResultData().getRecords();
            if (!CollectionUtils.isEmpty(merchantDTOS)) {
                merchantDTOS.forEach(merchantDTO -> {
                    merchantDTO.setLoginName(getLoginName(merchantDTO.getMerchantId()));
                });
            }
        }
        
        List<ExcelTitleName> excelTitleNames = MerchantColumn.merchantColumn();
        excelTitleNames = MerchantColumn.complementSupplyMerchantFileds(excelTitleNames);
        OutputStream output = null;
        try {
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "供应商列表";
            ExcelToMerchantListUtils.builderOrderExcel(workbook, resultVO.getResultData().getRecords(), excelTitleNames);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        } catch (Exception e) {
            log.error("供应商列表导出失败 error={}", e);
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "获取厂商类型的商家分页列表接口", notes = "")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pageFactoryMerchant", method = RequestMethod.POST)
    public ResultVO<Page<FactoryMerchantDTO>> pageFactoryMerchant(@RequestBody @ApiParam(value = "分页参数", required = true) FactoryMerchantPageReq req) {
        log.info("MerchantController.pageFactoryMerchant() input: FactoryMerchantPageReq={}", JSON.toJSONString(req));
        ResultVO<Page<FactoryMerchantDTO>> pageResultVO = merchantService.pageFactoryMerchant(req);
        // 追加用户登录帐号
        if (null != pageResultVO && pageResultVO.isSuccess() && null != pageResultVO.getResultData()) {
            List<FactoryMerchantDTO> factoryDTOS = pageResultVO.getResultData().getRecords();

            if (!CollectionUtils.isEmpty(factoryDTOS)) {
                factoryDTOS.forEach(factoryDTO -> {
                    factoryDTO.setLoginName(getLoginName(factoryDTO.getMerchantId()));
                });
            }
        }

        return pageResultVO;
    }


    @ApiOperation(value = "厂商列表导出", notes = "厂商列表导出")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/exportFactoryMerchantList", method = RequestMethod.POST)
    public void exportFactoryMerchantList(@RequestBody FactoryMerchantPageReq req, HttpServletResponse response) {
        log.info("MerchantController.exportFactoryMerchantList() input: req={}", JSON.toJSONString(req));
        req.setPageNo(EXPORT_PAGE_NO);
        req.setPageSize(EXPORT_PAGE_SIZE);
        ResultVO<Page<FactoryMerchantDTO>> resultVO = merchantService.pageFactoryMerchant(req);
        if (null != resultVO && resultVO.isSuccess() && null != resultVO.getResultData()) {
            List<FactoryMerchantDTO> merchantDTOS = resultVO.getResultData().getRecords();
            if (!CollectionUtils.isEmpty(merchantDTOS)) {
                merchantDTOS.forEach(merchantDTO -> {
                    merchantDTO.setLoginName(getLoginName(merchantDTO.getMerchantId()));
                });
            }
        }
        List<ExcelTitleName> excelTitleNames = MerchantColumn.merchantColumn();
        OutputStream output = null;
        try {
            //创建Excel
            Workbook workbook = new HSSFWorkbook();
            String fileName = "厂商列表";
            ExcelToMerchantListUtils.builderOrderExcel(workbook, resultVO.getResultData().getRecords(), excelTitleNames);
            output = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            workbook.write(output);
        } catch (Exception e) {
            log.error("厂商列表导出失败 error={}", e);
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取商家详情
     *
     * @param merchantId
     * @return
     */
    @ApiOperation(value = "获取商家详情", notes = "获取商家详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantId", value = "商家ID", paramType = "query", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultVO<MerchantDetailDTO> getMerchantDetail(@RequestParam(value = "merchantId") String merchantId) {
        log.info("MerchantController.getMerchantDetail() input: merchantId={}", JSON.toJSONString(merchantId));
        MerchantGetReq req = new MerchantGetReq();
        req.setMerchantId(merchantId);
        return merchantService.getMerchantDetail(req);
    }


    @ApiOperation(value = "更新商家接口", notes = "更新商家接口: 更新状态、类型、绑定的用户")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
//    @RequestMapping(value = "/updateType", method = RequestMethod.POST)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultVO<Integer> update(@RequestBody @ApiParam(value = "更新参数", required = true) MerchantUpdateReq req) {
        log.info("MerchantController.update() input: MerchantUpdateReq={}", JSON.toJSONString(req));
        return merchantService.updateMerchant(req);
    }

    @ApiOperation(value = "批量更新商家接口", notes = "批量更新商家接口: 更新状态、类型、绑定的用户")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/updateBatch", method = RequestMethod.POST)
    public ResultVO<Integer> updateBatch(@RequestBody @ApiParam(value = "更新参数", required = true) MerchantUpdateBatchReq req) {
        log.info("MerchantController.updateBatch() input: req={}", JSON.toJSONString(req));
        return merchantService.updateMerchantBatch(req);
    }

    /**
     * 绑定用户时的  查询用户列表分页
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "绑定用户时的 查询用户列表分页接口", notes = "绑定用户时的  查询用户列表分页")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/pageUser", method = RequestMethod.POST)
    public ResultVO<Page<UserDTO>> pageUser(@RequestBody @ApiParam(value = "分页参数", required = true) UserPageReq req) {
        // 设置用户类型 查rel_code 字段为空的
//        req.setUserFounder();
        req.setIsRelCodeNull(true);
        return ResultVO.success(userService.pageUser(req));
    }

    /**
     * 绑定用户
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "绑定用户接口", notes = "绑定用户接口")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/bindUser", method = RequestMethod.POST)
    public ResultVO<Integer> bindUser(@RequestBody @ApiParam(value = "绑定用户参数", required = true) MerchantBindUserReq req) {

        try {
            return merchantService.bindUser(req);
        } catch (Exception e) {
            return ResultVO.error("绑定用户失败！");
        }
    }

    /**
     * 保存商家标签
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "保存商家标签接口", notes = "保存商家标签，可以批量保存，,逻辑：先删除原有的记录，再新建新的记录")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/saveTag", method = RequestMethod.POST)
    public ResultVO<Integer> saveTag(@RequestBody @ApiParam(value = "保存参数", required = true) MerchantTagRelSaveReq req) {
        // tagIdList字段为空  只是删除
        // 先删除原先的记录  再插入新记录
        MerchantTagRelDeleteReq deleteReq = new MerchantTagRelDeleteReq();
        deleteReq.setMerchantId(req.getMerchantId());
        merchantTagRelService.deleteMerchantTagRel(deleteReq);
        if (CollectionUtils.isEmpty(req.getTagIdList())) {
            return ResultVO.success();
        }
        // 插入
        return merchantTagRelService.saveMerchantTagRel(req);
    }

    /**
     * 保存商家标签
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "批量保存商家标签接口", notes = "批量保存商家标签接口，逻辑：先删除原有的记录，再新建新的记录")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/saveTagBatch", method = RequestMethod.POST)
    public ResultVO<Integer> saveTagBatch(@RequestBody @ApiParam(value = "保存参数", required = true) MerchantTagRelSaveBatchReq req) {
        // 先删除原先的记录  再插入新记录
        MerchantTagRelDeleteBatchReq deleteReq = new MerchantTagRelDeleteBatchReq();
        deleteReq.setMerchantIdList(req.getMerchantIdList());
        merchantTagRelService.deleteMerchantTagRelBatch(deleteReq);
        if (CollectionUtils.isEmpty(req.getTagIdList())) {
            // tagIdList字段为空，只做删除，然后返回成功
            return ResultVO.success();
        }
        // 插入
        return merchantTagRelService.saveMerchantTagRelBatch(req);
    }

    /**
     * 获取商家标签列表
     *
     * @param merchantId
     * @return
     */
    @ApiOperation(value = "获取商家标签列表接口", notes = "获取商家标签列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantId", value = "商家ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/listTag", method = RequestMethod.GET)
    public ResultVO<List<MerchantTagRelDTO>> listTag(@RequestParam(value = "merchantId") String merchantId) {
        MerchantTagRelListReq req = new MerchantTagRelListReq();
        req.setMerchantId(merchantId);
        return merchantTagRelService.listMerchantTagRel(req);
    }

    /**
     * 获取商家标签列表
     *
     * @return
     */
    @ApiOperation(value = "获取商家标签列表接口", notes = "获取商家是否含有政企供应商标签列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "merchantId", value = "商家ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/governmentEnterpriseSuppliers", method = RequestMethod.GET)
    public ResultVO<Boolean> governmentEnterpriseSuppliers() {
        UserDTO curLoginUserDTO = UserContext.getUser();
        if( curLoginUserDTO == null ){
            return ResultVO.errorEnum(ResultCodeEnum.NOT_LOGIN);
        }
        MerchantTagRelListReq req = new MerchantTagRelListReq();
        req.setMerchantId(curLoginUserDTO.getRelCode());
        req.setTagId(TagsConst.GOVERNMENT_ENTERPRISE_STORES);
        ResultVO<List<MerchantTagRelDTO>> listResultVO = merchantTagRelService.listMerchantTagRel(req);
        log.info("MerchantController.governmentEnterpriseSuppliers() merchantTagRelService.listMerchantTagRel merchantTagRelListReq={}, listResultVO={}", JSON.toJSONString(req), JSON.toJSONString(listResultVO));
        if (null != listResultVO && listResultVO.isSuccess() && !CollectionUtils.isEmpty(listResultVO.getResultData())) {
            return ResultVO.success(true);
        }
        return ResultVO.success(false);
    }

    /**
     * 删除商家标签
     *
     * @param relId
     * @return
     */
    @ApiOperation(value = "删除商家标签", notes = "获取商家标签接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "relId", value = "商家-标签关联ID", paramType = "query", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/deleteSingleTag", method = RequestMethod.GET)
    public ResultVO<Integer> deleteSingleTag(@RequestParam(value = "relId") String relId) {
        MerchantTagRelDeleteReq req = new MerchantTagRelDeleteReq();
        req.setRelId(relId);
        return merchantTagRelService.deleteMerchantTagRel(req);
    }

    /**
     * 获取商家分页列表
     *
     * @param req
     * @return
     */
    @ApiOperation(value = "获取商家权限分页列表接口", notes = "可以根据商家名称、编码、类型条件进行筛选查询")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @RequestMapping(value = "/rightsPageMerchant", method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Page<MerchantPageResp>> rightsPageMerchant(@RequestBody @ApiParam(value = "分页参数", required = true) MerchantPageReq req) {

        if (!UserContext.isUserLogin()) {
            // 没有登陆，直接返回不能查到数据
            return ResultVO.success(new Page<MerchantPageResp>());
        }

        Boolean isAdminType = UserContext.isAdminType();
        MerchantRulesCommonReq commonReq = new MerchantRulesCommonReq();
        commonReq.setRuleType(PartnerConst.MerchantRuleTypeEnum.BUSINESS.getType());
        if (UserContext.getUserOtherMsg() != null && UserContext.getUserOtherMsg().getMerchant() != null && !isAdminType) {
            String merchantId = UserContext.getUserOtherMsg().getMerchant().getMerchantId();
            commonReq.setMerchantId(merchantId);
            ResultVO<List<String>> permissionVO = merchantRulesService.getRegionAndMerchantPermission(commonReq);
            if (permissionVO.isSuccess() && !CollectionUtils.isEmpty(permissionVO.getResultData())) {
                req.setMerchantIdList(permissionVO.getResultData());
            }
        } else if (isAdminType && StringUtils.isNotBlank(req.getMerchantId())) {
            // 管理员登陆且指定商家
            commonReq.setMerchantId(req.getMerchantId());
            ResultVO<List<String>> permissionVO = merchantRulesService.getRegionAndMerchantPermission(commonReq);
            if (permissionVO.isSuccess() && !CollectionUtils.isEmpty(permissionVO.getResultData())) {
                req.setMerchantIdList(permissionVO.getResultData());
            }
        }
        return merchantService.pageMerchant(req);
    }


}
