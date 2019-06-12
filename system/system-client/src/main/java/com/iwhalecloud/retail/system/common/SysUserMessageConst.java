package com.iwhalecloud.retail.system.common;

/**
 * @author li.yulong
 * @date 2019/3/26 17:58
 */
public class SysUserMessageConst {

    /**
     * 1：业务通知类（如待发货、待支付提醒）
     * 2：业务告警类（如库存预警）
     * 3：业务异常类（如订单异常等）
     */
    public static final String MESSAGE_TYPE_REMIND = "1";
    public static final String MESSAGE_TYPE_WARN = "2";
    public static final String MESSAGE_TYPE_ABERRANT = "3";

    public static  final String NOTIFY_ACTIVITY_ORDER_DELIVERY_TITLE = "发货预警";
    public static  final String NOTIFY_ACTIVITY_ORDER_DELIVERY_CONTENT = ",离发货日期还有%s天,您有订单没有发货,请尽快完成订单发货处理。";
    public static  final String NOTIFY_ACTIVITY_ORDER_DELIVERY_CONTENT_NEW = "您还有《%s》营销活动订单未完成发货，本次活动将于%s结束，请在活动结束前完成串码上传，逾期将不能上传串码 。";
    // 营销活动发货预警提前天数
    public static  final String DELIVER_END_TIME_NOTIFY_DAYS = "DELIVER_END_TIME_NOTIFY_DAYS";

    //ZOP短信能力
    public static final String REGIST_VERIFY_BSID = "2691";
    public static final String MODFIFY_PASSWD_BSID = "2691";
    public static final String LOGIN_VERIFY_BSID = "2691";

    public static final String PHONENO_NULL = "电话号码为空";
    public static final String VERIFYCODE_NULL = "验证码为空";
    public static final String VERIFYCODE_EFF = "验证码过期";
    public static final String VERIFYCODE_ERR = "验证码有误";

    /**
     * 0：未读
     * 1：已读
     */
    public static final String READ_FLAG_N = "0";
    public static final String READ_FLAG_Y = "1";

    /**
     *  '0未验证  1已校验n            2 已验证',
     */
    public static final int UNVERIFIED = 0;
    public static final int VERIFIED = 1;
    //public static final String VERIFIED = "2";


    /**
     * 通知状态枚举
     */
    public enum MessageStatusEnum{
        VALID("1", "有效"),
        INVALID("0","无效");
        private String value;
        private String code;
        MessageStatusEnum(String code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

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
}
