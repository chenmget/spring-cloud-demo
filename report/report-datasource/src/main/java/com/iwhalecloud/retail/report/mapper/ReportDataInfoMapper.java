package com.iwhalecloud.retail.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportDeSaleDaoReq;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ProductListAllResp;
import com.iwhalecloud.retail.report.dto.response.ReportDeSaleDaoResq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.entity.RptPartnerOperatingDay;
import com.iwhalecloud.retail.report.entity.RptSupplierOperatingDay;

@Mapper
public interface ReportDataInfoMapper extends BaseMapper<RptPartnerOperatingDay> {

	public Page<ReportStorePurchaserResq> getStorePurchaserReport(Page<ReportStorePurchaserResq> page,@Param("req") ReportStorePurchaserReq reportStorePurchaserReq);
	
	public List<ReportStorePurchaserResq> getStorePurchaserReport(@Param("req") ReportStorePurchaserReq reportStorePurchaserReq);

	public List<ReportStorePurchaserResq> getUerRoleForView(@Param("req") ReportStorePurchaserReq reportStorePurchaserReq);
}
