package com.iwhalecloud.retail.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.entity.RptSupplierOperatingDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper extends BaseMapper<RptSupplierOperatingDay> {
 public Page<ReportDeSaleDaoResq> ListReportDeSale(Page<ReportDeSaleDaoResq> page,@Param("req") ReportDeSaleDaoReq reportDeSaleDaoReq);
 
 public List<ProductListAllResp> listProductAll(@Param("brandId")String brandId );
 
 public List<ReportDeSaleDaoResq> reportDeSaleExport(@Param("req") ReportDeSaleDaoReq reportDeSaleDaoReq);


}
