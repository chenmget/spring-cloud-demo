package com.iwhalecloud.retail.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.report.dto.request.ReportCityTotalInvReq;
import com.iwhalecloud.retail.report.dto.request.ReportStInvCityDaoReq;
import com.iwhalecloud.retail.report.dto.request.RptPartnerOperatingDay;
import com.iwhalecloud.retail.report.dto.response.ReportCityTotalInvResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author lipeng
 * @date 2019-06-10
 * 地市总进销存报表服务mapper
 */
@Mapper
public interface ReportCityTotalInvMapper extends BaseMapper<ReportCityTotalInvReq> {

    /**
     * 查询地市总进销存报表分页数据列表
     *  @param page
     * @param req
     * @return
     */
    Page<ReportCityTotalInvResp> getCityTotalInvList(Page<ReportCityTotalInvResp> page, @Param("req") ReportCityTotalInvReq req);

    /**
     * 查询地市总进销存报表数据列表
     * @param req
     * @return
     */
    List<ReportCityTotalInvResp> getCityTotalInvList(@Param("req") ReportCityTotalInvReq req);

}
