package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.dto.PageVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/30 9:07
 */
@Data
public class FtpOrderDataReq extends PageVO implements Serializable {
    private String startDate;
    private String endDate;
    private String ftpPath;
}
