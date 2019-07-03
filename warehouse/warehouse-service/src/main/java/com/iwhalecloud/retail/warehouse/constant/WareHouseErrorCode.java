package com.iwhalecloud.retail.warehouse.constant;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import com.iwhalecloud.retail.dto.ResultCode;
import org.springframework.stereotype.Service;

/**
 * @Description: 仓库异常异常编码，需要配置sys_error_code表
 * @author: Z
 * @date: 2019/7/1 16:31
 */
@ErrorCodeAnnotation
@Service
public class WareHouseErrorCode {

    /**
     * ERROR_800000 ： 第一位编码
     */
    public static ResultCode ERROR_800000;



}
