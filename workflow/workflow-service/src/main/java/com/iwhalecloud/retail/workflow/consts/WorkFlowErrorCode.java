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
     * 这是一个普通提示
     */
    public static ResultCode ERROR_900000;

    /**
     * 这是一个包含两个占位符的提示%s-%s
     */
    public static ResultCode ERROR_900001;
}
