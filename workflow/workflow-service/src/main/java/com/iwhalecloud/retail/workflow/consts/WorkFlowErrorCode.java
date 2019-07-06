package com.iwhalecloud.retail.workflow.consts;

import com.iwhalecloud.retail.annotation.ErrorCodeAnnotation;
import com.iwhalecloud.retail.dto.ResultCode;
import org.springframework.stereotype.Service;

/**
 * @Description: 工作流异常编码，需要配置sys_error_code表
 * @author: Z
 * @date: 2019/7/3 14:43
 */
@ErrorCodeAnnotation
@Service
public class WorkFlowErrorCode {

    /**
     * 第一位编码
     */
    public static ResultCode ERROR_900000;
}
