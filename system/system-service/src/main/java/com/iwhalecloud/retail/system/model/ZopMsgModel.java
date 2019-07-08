package com.iwhalecloud.retail.system.model;

import lombok.Data;

@Data
public class ZopMsgModel {
    //必填

    /**
     * 本地网id
     */
    private String latnId;
    /**
     * 电话号码
     */
    private String toTel;
    /**
     * 发送内容，内容采用HEX编码
     * 如果不采用模板的话，内容不可为空
     */
    private String sentContent;
    /**
     * 模板所需要的参数，参数要符合JSON格式
     */
    private String params;
    /**
     * 业务id
     */
    private String businessId;

}
