package com.iwhalecloud.retail.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportStSaleDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportStSaleDaoResp;
import com.iwhalecloud.retail.report.entity.RptStoreOperatingDay;

@Mapper
public interface ReportStoreMapper extends BaseMapper<RptStoreOperatingDay> {
	
 public Page<ReportStSaleDaoResp> ListReportStSale(Page<ReportStSaleDaoResp> page,@Param("req") ReportStSaleDaoReq req);

 public List<ReportStSaleDaoResp> ListReportStSale(@Param("req") ReportStSaleDaoReq req);

}
