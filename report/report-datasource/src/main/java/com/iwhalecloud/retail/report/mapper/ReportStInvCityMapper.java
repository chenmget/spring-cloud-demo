package com.iwhalecloud.retail.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportStInvCityDaoReq;
import com.iwhalecloud.retail.report.dto.request.RptPartnerOperatingDay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportStInvCityMapper extends BaseMapper<ReportStInvCityDaoReq> {

    Page<RptPartnerOperatingDay> listStInvCity(Page<ReportStInvCityDaoReq> page, @Param("req") ReportStInvCityDaoReq req);

    List<RptPartnerOperatingDay> listStInvCity(@Param("req") ReportStInvCityDaoReq req);

}
