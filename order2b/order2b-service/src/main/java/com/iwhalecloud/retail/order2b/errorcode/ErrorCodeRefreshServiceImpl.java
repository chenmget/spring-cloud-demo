package com.iwhalecloud.retail.order2b.errorcode;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.service.ErrorCodeRefreshService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @Description: 异常编码刷新服务类
 * @author: Z
 * @date: 2019/7/2 15:24
 */
@Service
@Slf4j
public class ErrorCodeRefreshServiceImpl implements ErrorCodeRefreshService {

    @Resource
    private ErrorCodeInitHelper errorCodeInitHelper;

    @Override
    public ResultVO<Boolean> refreshErrorCodeCache() {
        log.info(">>fresh before>>");
        errorCodeInitHelper.refreshInitErrorCodes();
        log.info(">>fresh after>>");
        return ResultVO.success(true);
    }
}
