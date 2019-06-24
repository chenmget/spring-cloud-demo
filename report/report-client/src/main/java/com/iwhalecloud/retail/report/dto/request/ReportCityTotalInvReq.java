package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

/**
 * 地市总进销存报表请求实体
 */
@Data
public class ReportCityTotalInvReq extends PageVO {

    private static final long serialVersionUID = 1L;
    /**区县*/
    private String cityId;
    /**地市*/
    private String countyId;
    /**入库起止时间*/
    private String createTimeEnd;
    private String createTimeStart;
    /**出库起止时间*/
    private String outTimeEnd;
    private String outTimeStart;
    /**品牌ID*/
    private String brandId;
    /**机型*/
    private String productBaseId;
    /**零售商编码*/
    private String partnerCode;
    /**零售商名称*/
    private String partnerName;
    /**经营主体*/
    private String businessEntityName;
    /**用户角色*/
    private String userType;
    /**用户Id*/
    private String userId;
    /**地包商编号*/
    private String merchantCode;

}
