package com.iwhalecloud.retail.report.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.SupplierOperatingDayDTO;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.dto.request.SupplierOperatingDayPageReq;
import com.iwhalecloud.retail.report.dto.response.SummarySaleBySupplierPageResp;
import com.iwhalecloud.retail.report.entity.SupplierOperatingDay;
import com.iwhalecloud.retail.report.mapper.SupplierOperatingDayMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


@Component
public class SupplierOperatingDayManager {
    @Resource
    private SupplierOperatingDayMapper supplierOperatingDayMapper;

    /**
     * 地包进销存 数据 分页查询
     * @return
     */
//    public Page<SupplierOperatingDayDTO> page(SupplierOperatingDayPageReq req) {
//        IPage<SupplierOperatingDay> page =  new Page<SupplierOperatingDay>(req.getPageNo(), req.getPageSize());
//        QueryWrapper<SupplierOperatingDay> queryWrapper = new QueryWrapper<SupplierOperatingDay>();
//        // eq 字段的
//        if (!StringUtils.isEmpty(req.getSupplierId())) {
//            queryWrapper.eq(SupplierOperatingDay.FieldNames.supplierId.getTableFieldName(), req.getSupplierId());
//        }
//        if (!StringUtils.isEmpty(req.getSupplierCode())) {
//            queryWrapper.eq(SupplierOperatingDay.FieldNames.supplierCode.getTableFieldName(), req.getSupplierCode());
//        }
//        if (!StringUtils.isEmpty(req.getCityId())) {
//            queryWrapper.eq(SupplierOperatingDay.FieldNames.cityId.getTableFieldName(), req.getCityId());
//        }
//        if (!StringUtils.isEmpty(req.getCountyId())) {
//            queryWrapper.eq(SupplierOperatingDay.FieldNames.countyId.getTableFieldName(), req.getCountyId());
//        }
//        if (!StringUtils.isEmpty(req.getBrandId())) {
//            queryWrapper.eq(SupplierOperatingDay.FieldNames.brandId.getTableFieldName(), req.getBrandId());
//        }
//        if (!StringUtils.isEmpty(req.getProductBaseId())) {
//            queryWrapper.eq(SupplierOperatingDay.FieldNames.productBaseId.getTableFieldName(), req.getProductBaseId());
//        }
//        if (!StringUtils.isEmpty(req.getProductId())) {
//            queryWrapper.eq(SupplierOperatingDay.FieldNames.productId.getTableFieldName(), req.getProductId());
//        }
//
//        // like 字段的
//        if (!StringUtils.isEmpty(req.getSupplierName())) {
//            queryWrapper.like(SupplierOperatingDay.FieldNames.supplierName.getTableFieldName(), req.getSupplierName());
//        }
//
//        // 时间区间
//        if (!Objects.nonNull(req.getStartDate())) {
//            queryWrapper.ge(SupplierOperatingDay.FieldNames.createDate.getTableFieldName(), req.getStartDate());
//        }
//        if (!Objects.nonNull(req.getEndDate())) {
//            queryWrapper.le(SupplierOperatingDay.FieldNames.createDate.getTableFieldName(), req.getEndDate());
//        }
//
//
//        page = supplierOperatingDayMapper.selectPage(page, queryWrapper);
//        Page<SupplierOperatingDayDTO> respPage = new Page<>();
//        BeanUtils.copyProperties(page, respPage);
//        List<SupplierOperatingDay> entityList = page.getRecords();
//        List<SupplierOperatingDayDTO> dtoList = Lists.newArrayList();
//        if (!CollectionUtils.isEmpty(entityList)) {
//            entityList.forEach(entity -> {
//                SupplierOperatingDayDTO dto = new SupplierOperatingDayDTO();
//                BeanUtils.copyProperties(entity, dto);
//                dtoList.add(dto);
//            });
//        }
//        respPage.setRecords(dtoList);
//
//        return respPage;
//    }


    /**
     * 地包进销存 数据 汇总 分页查询（按地包商的维度）
     * @return
     */
    public Page<SummarySaleBySupplierPageResp> pageSummarySaleBySupplier(SummarySaleBySupplierPageReq req) {
        Page<SummarySaleBySupplierPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<SummarySaleBySupplierPageResp> respPage = supplierOperatingDayMapper.pageSummarySaleBySupplier(page, req);
        return respPage;
    }
}
