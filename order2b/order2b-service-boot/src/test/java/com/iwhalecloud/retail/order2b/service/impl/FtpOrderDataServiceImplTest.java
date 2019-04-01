package com.iwhalecloud.retail.order2b.service.impl;

import com.iwhalecloud.retail.order2b.TestBase;
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

}
