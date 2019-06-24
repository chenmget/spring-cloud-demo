package com.iwhalecloud.retail.report.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.SummarySaleBySupplierPageReq;
import com.iwhalecloud.retail.report.dto.response.SummarySaleBySupplierPageResp;
import com.iwhalecloud.retail.report.entity.SupplierOperatingDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: SupplierOperatingDayMapper
 * @author autoCreate
 */
@Mapper
public interface SupplierOperatingDayMapper extends BaseMapper<SupplierOperatingDay> {

    /**
     * 地包进销存 数据 汇总 分页查询（按地包商的维度）
     * @param page
     * @param req
     * @return
     */
    Page<SummarySaleBySupplierPageResp> pageSummarySaleBySupplier(Page<SummarySaleBySupplierPageResp> page, @Param("req") SummarySaleBySupplierPageReq req);

}