package com.iwhalecloud.retail.goods2b.constant;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import com.iwhalecloud.retail.order2b.consts.ResultCode;
import org.springframework.stereotype.Service;

/**
 * @Description: 商品模块异常编码，需要配置sys_error_code表
 * @author: Z
 * @date: 2019/7/2 11:43
 */
@ErrorCodeAnnotation
@Service
public class GoodsErrorCode {

    /**
     * 商品模块错误编码第一位
     */
    public static ResultCode ERROR_300000;
}
