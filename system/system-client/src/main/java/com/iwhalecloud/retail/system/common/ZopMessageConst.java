package com.iwhalecloud.retail.system.common;

/**
 * @author wzy
 * @date 2019/7/6
 */
public class ZopMessageConst {

    public static final String PHONENO_NULL = "电话号码为空";
    public static final String VERIFYCODE_NULL = "验证码为空";
    public static final String VERIFYCODE_ERR = "验证码过期或无效";

    /**
     *  '0未验证  1已校验n            2 已验证',
     */
    public static final int UNVERIFIED = 0;
    public static final int VERIFIED = 1;
    //public static final String VERIFIED = "2";

    public enum ZopServiceEnum {
        //发送短信 1.0
        SEND_MESSAGE("order.sms.sendsms", "1.0");

        private String method;
        private String version;

        ZopServiceEnum(String version, String method) {
            this.method = method;
            this.version = version;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public enum SendMsgType {
        SEND_MESSAGE(1,"短信"),
        SEND_EMAIL(2,"邮箱账号");
        private int type;
        private String code;

        SendMsgType(int type, String code) {
            this.type = type;
            this.code = code;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    //ZOP短信能力
    public enum VerifyCode {
        //注册校验嘛
        REGIST_CODE(1,"10101"),
        //登陆校验码
        LOGIN_CODE(3,"10102"),
        //重置密码校验码
        RESET_PASSWD_CODE(2,"10103");
        private int type;
        private String bsid;

        VerifyCode(int type, String bsid) {
            this.type = type;
            this.bsid = bsid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getBsid() {
            return bsid;
        }

        public void setBsid(String bsid) {
            this.bsid = bsid;
        }
    }

    //ZOP短信能力
    public enum NoticeMsg {
        //流程
       NOTICE_MSG_PROCESS("6114","流程短信通知"),
        //订单
        NOTICE_MSG_DD("","");
       private String busId;
       private String desc;

        NoticeMsg(String busId, String desc) {
            this.busId = busId;
            this.desc = desc;
        }

        public String getBusId() {
            return busId;
        }

        public void setBusId(String busId) {
            this.busId = busId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
