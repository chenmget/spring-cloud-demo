package com.iwhalecloud.retail.pay.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单支付流水
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ORDER_PAY, 对应实体OrderPay类")
public class SaveOrderPayRequestDTO implements java.io.Serializable  {

        private static final long serialVersionUID = 1L;

        /**
         * 状态:插入_1000;支付成功_1100;支付失败_1200 :
         */
        @ApiModelProperty(value = "状态:插入_1000;支付成功_1100;支付失败_1200 :")
        private java.lang.String status;

        /**
         * 支付渠道编码(支付平台分配）
         BEST：翼支付
         ALI：支付宝
         WX：微信
         TEN: 财付通
         JT ： 集团电渠
         说明：如需使用该字段，需向电渠支付网关管理负责人申请开通。
         */
        @ApiModelProperty(value = "支付渠道编码(支付平台分配）BEST：翼支付 ALI：支付宝 WX：微信 TEN: 财付通 JT ： 集团电渠 说明：如需使用该字段，需向电渠支付网关管理负责人申请开通。 ")
        private java.lang.String payChannel;

        /**
         * 由业务渠道提供，建议：yyyyMMddhhmmss + 6位随机数
         */
        @ApiModelProperty(value = "由业务渠道提供，建议：yyyyMMddhhmmss + 6位随机数")
        private java.lang.String requestSeq;

        /**
         * 支付类型编码（支付平台定义）WEB：web网关
         WAP：wap网关
         NATIVE：扫码
         JSAPI : 微信公众号
         MWEB：微信H5
         INST：分期支付
         */
        @ApiModelProperty(value = "支付类型编码（支付平台定义）WEB：web网关 WAP：wap网关 NATIVE：扫码 JSAPI : 微信公众号 MWEB：微信H5 INST：分期支付")
        private java.lang.String payType;

        /**
         * 请求时间
         */
        @ApiModelProperty(value = "请求时间")
        private java.util.Date requestTime;

        /**
         * 支付金额 单位：分
         */
        @ApiModelProperty(value = "支付金额 单位：分")
        private java.lang.Long payAmount;

        /**
         * 前台返回地址:用于接收交易返回的前台url。支付类型为WEB,WAP时此字段必填
         */
        @ApiModelProperty(value = "前台返回地址:用于接收交易返回的前台url。支付类型为WEB,WAP时此字段必填")
        private java.lang.String returnUrl;

        /**
         * 后台返回地址:用于接收交易返回的后台url
         */
        @ApiModelProperty(value = "后台返回地址:用于接收交易返回的后台url")
        private java.lang.String notifyUrl;

        /**
         * 银行编码:此参数对应银行编码表
         */
        @ApiModelProperty(value = "银行编码:此参数对应银行编码表")
        private java.lang.String bankId;

        /**
         * 订单ID
         */
        @ApiModelProperty(value = "订单ID")
        private java.lang.String orderId;

        /**
         * 分账明细 分账填写,不分账为空。格式：account1:800|account2:100|account3:100
         */
        @ApiModelProperty(value = "分账明细 分账填写,不分账为空。格式：account1:800|account2:100|account3:100")
        private java.lang.String divDetails;

        /**
         * 分期数 只有选择银行分期支付时，此项才会校验
         中国工商银行:分期期数：3，6，9，12，18，24
         中国银行: 分期期数：3，6，12，24

         */
        @ApiModelProperty(value = "分期数 只有选择银行分期支付时，此项才会校验 中国工商银行:分期期数：3，6，9，12，18，24 中国银行: 分期期数：3，6，12，24")
        private java.lang.String pedCnt;

        /**
         * 客户端IP 用户IP
         */
        @ApiModelProperty(value = "客户端IP 用户IP")
        private java.lang.String clientIp;

        /**
         * 指定支付方式
         */
        @ApiModelProperty(value = "指定支付方式")
        private java.lang.String limitPay;

        /**
         * 订单信息域
         */
        @ApiModelProperty(value = "订单信息域")
        private java.lang.String orderInfo;

        /**
         * 附加信息
         */
        @ApiModelProperty(value = "附加信息")
        private java.lang.String attachInfo;

}
