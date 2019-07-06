package com.iwhalecloud.retail.partner.constant;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import org.springframework.stereotype.Service;

/**
 * @Description: 合作伙伴异常编码，需要配置sys_error_code表
 * @author: Z
 * @date: 2019/7/2 11:43
 */
@ErrorCodeAnnotation
@Service
public class PartnerErrorCode {

    /**
     * 第一位编码
     */
    public static String ERROR_400000;
}
