package com.iwhalecloud.retail.system.model;

import com.ztesoft.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 验证码模板
 */
@Data
public class SmsVerificationtemplate {
    @JSONField(name="SmsVerificationCode")
    private String smsVerificationCode;
}
