package com.iwhalecloud.retail.system.constant;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import com.iwhalecloud.retail.dto.ResultCode;
import org.springframework.stereotype.Service;

/**
 * @Description: 系统模块异常编码，需要配置sys_error_code表
 * @author: Z
 * @date: 2019/7/2 11:43
 */
@ErrorCodeAnnotation
@Service
public class SystemErrorCode {

    /**
     * 取消收藏请求对象不能为空
     */
    public static ResultCode ERROR_100000;
}
