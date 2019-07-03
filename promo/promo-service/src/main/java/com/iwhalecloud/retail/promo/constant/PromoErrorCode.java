package com.iwhalecloud.retail.promo.constant;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import com.iwhalecloud.retail.order2b.consts.ResultCode;
import org.springframework.stereotype.Service;

/**
 * @Description: 促销模块异常编码，需要配置sys_error_code表
 * @author: Z
 * @date: 2019/7/2 11:43
 */
@ErrorCodeAnnotation
@Service
public class PromoErrorCode {

    /**
     * 第一位编码
     */
    public static ResultCode ERROR_600000;
}
