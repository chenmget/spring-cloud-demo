package com.iwhalecloud.retail.system.dto.request;

import com.ztesoft.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class NoticeTrialMsgTemplate implements Serializable{
    @JSONField(name="UserName")
    private String userName;
    @JSONField(name="ProcessName")
    private String processName;
}
