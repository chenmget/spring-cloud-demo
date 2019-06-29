package com.iwhalecloud.retail.report.dto.request;

import java.util.List;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

@Data
public class ReportStInvCityDaoReq extends PageVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String dateStart;//统计起始时间
	private String dateEnd;//统计结束时间
	private List<String> lanIdList; 		//地市
	private List<String> orgName; 		//经营单元

}
