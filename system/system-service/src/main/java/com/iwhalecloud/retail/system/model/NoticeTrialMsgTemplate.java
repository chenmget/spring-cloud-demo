package com.iwhalecloud.retail.system.model;

import com.ztesoft.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class NoticeTrialMsgTemplate {
    @JSONField(name="UserName")
    private String userName;
    @JSONField(name="ProcessName")
    private String processName;
}
