package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.TestBase;
import com.iwhalecloud.retail.order2b.dto.resquest.order.FtpOrderDataReq;
import com.iwhalecloud.retail.order2b.service.FtpOrderDataService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 吴良勇
 * @date 2019/3/30 10:51
 */
public class FtpOrderDataServiceImplTest extends TestBase {
    @Autowired
    private FtpOrderDataService ftpOrderDataService;

    @Test
    public void uploadFtpForTask() {
        ftpOrderDataService.uploadFtpForTask();
    }
    @Test
    public void uploadFtp() {
        FtpOrderDataReq req = new FtpOrderDataReq();
        req.setFtpPath("/tmp/order/ago");
        req.setStartDate("2019-02-01");
        req.setEndDate("2019-02-15");
        ResultVO resultVO = ftpOrderDataService.uploadFtp(req);
    }

}
