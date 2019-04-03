package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.response.FtpOrderDataResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.FtpOrderDataReq;

import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/29 21:40
 * 将交易数据上传到FTP上
 */
public interface FtpOrderDataService {


    /**
     * 定时任务上传文件到ftp
     * @return
     */
    ResultVO uploadFtpForTask();

    /**
     * 根据条件查询订单数据上传ftp
     * @param req
     * @return
     */
    ResultVO uploadFtp(FtpOrderDataReq req);
}
