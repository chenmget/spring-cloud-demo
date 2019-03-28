package com.iwhalecloud.retail.order2b.dto.resquest.report;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 获取 某个时间段的  订单数量请求
 * 供货商ID 分销商ID 两个不能同时为空
 */
@Data
public class OrderCountOrAmountReq extends OrderRequest implements Serializable {

    private static final long serialVersionUID = 4630086195232573433L;

    private String supplierId; // 供货商ID
    private String partnerId; // 分销商ID
    private List<Integer> statusList; // 状态
    private Date startDate = getDateWithInterval(0);// 开始时间（默认当天）
    private Date endDate = getDateWithInterval(1); // 结束时间（默认当前日期加一）

    /**
     * 获取当前日期
     * @param intervalDays 距离当天日期的间隔天数 正负  表示前后（时分秒都是0）
     * @return
     */
    public Date getDateWithInterval(int intervalDays){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, intervalDays);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
