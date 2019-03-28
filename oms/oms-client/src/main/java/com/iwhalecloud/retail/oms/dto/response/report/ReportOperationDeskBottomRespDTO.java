package com.iwhalecloud.retail.oms.dto.response.report;

import com.iwhalecloud.retail.oms.dto.ReportEventShopRankDTO;
import com.iwhalecloud.retail.oms.dto.ReportEventTimeIntervalDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ReportOperationDeskBottomRespDTO implements Serializable {

    // 厅店前七排行榜列表
    private List shopRankList;

    // 报告数据列表
    private List reportDataList;

    //备用报告数据列表
    // private List<Map<String,Object>> repoertDataListSpare;
}
