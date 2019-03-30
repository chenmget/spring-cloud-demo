package com.iwhalecloud.retail.order2b.dto.resquest.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/30 9:07
 */
@Data
public class FtpOrderDataReq   implements Serializable {
    private String startDate;
    private String endDate;
}
