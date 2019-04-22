package com.iwhalecloud.retail.report.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class ReportStInvCityDaoReq extends PageVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String createTimeEnd;//入库起止时间
    private String createTimeStart;
    private String outTimeEnd;//出库起止时间
    private String outTimeStart;
    private String warningStatus;//库存预警状态
    private String city;//区县
    private String lanId;//地市

}
