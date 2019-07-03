package com.iwhalecloud.retail.order2b.constant;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import org.springframework.stereotype.Service;

/**
 * @Description: 订单模块异常编码，需要配置sys_error_code表
 * @author: Z
 * @date: 2019/7/2 11:43
 */
@ErrorCodeAnnotation
@Service
public class OrderErrorCode {

    /**
     * 第一位编码
     */
    public static String ERROR_200000;
}
