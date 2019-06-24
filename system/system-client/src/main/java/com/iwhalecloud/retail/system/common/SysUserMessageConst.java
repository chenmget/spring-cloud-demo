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
    
    /**
     * 0：未读
     * 1：已读
     */
    public static final String READ_FLAG_N = "0";
    public static final String READ_FLAG_Y = "1";


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
}
