package com.iwhalecloud.retail.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportStorePurchaserReq;
import com.iwhalecloud.retail.report.dto.response.ReportStorePurchaserResq;
import com.iwhalecloud.retail.report.entity.RptPartnerOperatingDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportDataInfoMapper extends BaseMapper<RptPartnerOperatingDay> {

	public Page<ReportStorePurchaserResq> getStorePurchaserReport(Page<ReportStorePurchaserResq> page,@Param("req") ReportStorePurchaserReq reportStorePurchaserReq);
	
	public List<ReportStorePurchaserResq> getStorePurchaserReport(@Param("req") ReportStorePurchaserReq reportStorePurchaserReq);

	public List<ReportStorePurchaserResq> getUerRoleForView(@Param("req") ReportStorePurchaserReq reportStorePurchaserReq);

	public String retailerCodeBylegacy(@Param("legacyAccount") String legacyAccount);
	
	public String getretailerCode(@Param("loginName") String loginName);

	public String getMyMktResStoreId(@Param("relCode") String relCode);
}
