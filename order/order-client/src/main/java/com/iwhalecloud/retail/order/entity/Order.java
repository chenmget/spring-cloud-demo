package com.iwhalecloud.retail.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Order
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型ord_order, 对应实体Order类")
@TableName("ord_order")
public class Order implements Serializable {
    /**表名常量*/
    public static final String TNAME = "ord_order";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * orderId
  	 */
  	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "orderId")
  	private java.lang.String orderId;
  	/**
  	 * sn
  	 */
	@ApiModelProperty(value = "sn")
  	private java.lang.String sn;
  	
  	/**
  	 * 记录ord_order_buyer表的购买者信息
  	 */
	@ApiModelProperty(value = "记录ord_order_buyer表的购买者信息")
  	private java.lang.String memberId;
  	
  	/**
  	 * 已换货:-7；换货被拒绝:-6；退货被拒绝-5；申请换货:-4；申请退货：-3；退货：-2；退款：-1；正常：0；暂停：1；待审核:0；
待确认:-50；待审核审核不通过：1 ；未付款、审核通过：2 ；已支付、待受理：3 ；已受理待发货：4 ；已发货待确认：5 ；
确认收货：6 ；已完成：7 ；作废：8 ；撤单：-9 ；取消订单：-10 ；受理失败：-11 ；订单异常：99 ；退货申请：20 ；
退货申请审核通过：21 ；换货申请：22 ；换货申请通过：23 ；订单异常：99 ；
  	 */
	@ApiModelProperty(value = "已换货:-7；换货被拒绝:-6；退货被拒绝-5；申请换货:-4；申请退货：-3；退货：-2；退款：-1；正常：0；暂停：1；待审核:0；" +
	"待确认:-50；待审核审核不通过：1 ；未付款、审核通过：2 ；已支付、待受理：3 ；已受理待发货：4 ；已发货待确认：5 ；" +
	"确认收货：6 ；已完成：7 ；作废：8 ；撤单：-9 ；取消订单：-10 ；受理失败：-11 ；订单异常：99 ；退货申请：20 ；" +
	"退货申请审核通过：21 ；换货申请：22 ；换货申请通过：23 ；订单异常：99 ；")
  	private java.lang.Long status;
  	
  	/**
  	 * -2已退款，-1未付款，1已付款
  	 */
	@ApiModelProperty(value = "-2已退款，-1未付款，1已付款")
  	private java.lang.Long payStatus;
  	
  	/**
  	 * -2已退货，-1未发货，1已发货
  	 */
	@ApiModelProperty(value = "-2已退货，-1未发货，1已发货")
  	private java.lang.Long shipStatus;
  	
  	/**
  	 * shippingId
  	 */
	@ApiModelProperty(value = "shippingId")
  	private java.lang.String shippingId;
  	
  	/**
  	 * shippingType
  	 */
	@ApiModelProperty(value = "shippingType")
  	private java.lang.String shippingType;
  	
  	/**
  	 * shippingArea
  	 */
	@ApiModelProperty(value = "shippingArea")
  	private java.lang.String shippingArea;
  	
  	/**
  	 * paymentId
  	 */
	@ApiModelProperty(value = "paymentId")
  	private java.lang.String paymentId;
  	
  	/**
  	 * paymentName
  	 */
	@ApiModelProperty(value = "paymentName")
  	private java.lang.String paymentName;
  	
  	/**
  	 * 1、在线支付，2、货到付款
  	 */
	@ApiModelProperty(value = "1、在线支付，2、货到付款")
  	private java.lang.String paymentType;
  	
  	/**
  	 * paymoney
  	 */
	@ApiModelProperty(value = "paymoney")
  	private java.lang.Double paymoney;
  	
  	/**
  	 * createTime
  	 */
	@ApiModelProperty(value = "createTime")
  	private java.util.Date createTime;
  	
  	/**
  	 * shipName
  	 */
	@ApiModelProperty(value = "shipName")
  	private java.lang.String shipName;
  	
  	/**
  	 * 物流地址
  	 */
	@ApiModelProperty(value = "物流地址")
  	private java.lang.String shipAddr;
  	
  	/**
  	 * 物流邮编
  	 */
	@ApiModelProperty(value = "物流邮编")
  	private java.lang.String shipZip;
  	
  	/**
  	 * shipEmail
  	 */
	@ApiModelProperty(value = "shipEmail")
  	private java.lang.String shipEmail;
  	
  	/**
  	 * shipMobile
  	 */
	@ApiModelProperty(value = "shipMobile")
  	private java.lang.String shipMobile;
  	
  	/**
  	 * shipTel
  	 */
	@ApiModelProperty(value = "shipTel")
  	private java.lang.String shipTel;
  	
  	/**
  	 * shipDay
  	 */
	@ApiModelProperty(value = "shipDay")
  	private java.lang.String shipDay;
  	
  	/**
  	 * shipTime
  	 */
	@ApiModelProperty(value = "shipTime")
  	private java.util.Date shipTime;
  	
  	/**
  	 * isProtect
  	 */
	@ApiModelProperty(value = "isProtect")
  	private java.lang.Long isProtect;
  	
  	/**
  	 * protectPrice
  	 */
	@ApiModelProperty(value = "protectPrice")
  	private java.lang.Double protectPrice;
  	
  	/**
  	 * goodsAmount
  	 */
	@ApiModelProperty(value = "goodsAmount")
  	private java.lang.Double goodsAmount;
  	
  	/**
  	 * shippingAmount
  	 */
	@ApiModelProperty(value = "shippingAmount")
  	private java.lang.Double shippingAmount;
  	
  	/**
  	 * orderAmount
  	 */
	@ApiModelProperty(value = "orderAmount")
  	private java.lang.Double orderAmount;
  	
  	/**
  	 * weight
  	 */
	@ApiModelProperty(value = "weight")
  	private java.lang.Double weight;
  	
  	/**
  	 * goodsNum
  	 */
	@ApiModelProperty(value = "goodsNum")
  	private java.lang.Long goodsNum;
  	
  	/**
  	 * gainedpoint
  	 */
	@ApiModelProperty(value = "gainedpoint")
  	private java.lang.Long gainedpoint;
  	
  	/**
  	 * consumepoint
  	 */
	@ApiModelProperty(value = "consumepoint")
  	private java.lang.Long consumepoint;
  	
  	/**
  	 * disabled
  	 */
	@ApiModelProperty(value = "disabled")
  	private java.lang.Long disabled;
  	
  	/**
  	 * discount
  	 */
	@ApiModelProperty(value = "discount")
  	private java.lang.Double discount;
  	
  	/**
  	 * 0未转账\n            1已转账
  	 */
	@ApiModelProperty(value = "0未转账\n            1已转账")
  	private java.lang.Long imported;
  	
  	/**
  	 * pimported
  	 */
	@ApiModelProperty(value = "pimported")
  	private java.lang.Long pimported;
  	
  	/**
  	 * completeTime
  	 */
	@ApiModelProperty(value = "completeTime")
  	private java.util.Date completeTime;
  	
  	/**
  	 * 银行ID
  	 */
	@ApiModelProperty(value = "银行ID")
  	private java.lang.String bankId;
  	
  	/**
  	 * goods
  	 */
	@ApiModelProperty(value = "goods")
  	private java.lang.String goods;
  	
  	/**
  	 * remark
  	 */
	@ApiModelProperty(value = "remark")
  	private java.lang.String remark;
  	
  	/**
  	 * 用户购买商品,用户id
  	 */
	@ApiModelProperty(value = "用户购买商品,用户id")
  	private java.lang.String userid;
  	
  	/**
  	 * 订单来源 京东、淘宝、网厅、电子渠道
  	 */
	@ApiModelProperty(value = "订单来源 京东、淘宝、网厅、电子渠道")
  	private java.lang.String sourceFrom;
  	
  	/**
  	 * 受理时间
  	 */
	@ApiModelProperty(value = "受理时间")
  	private java.util.Date acceptTime;
  	
  	/**
  	 * transactionId
  	 */
	@ApiModelProperty(value = "transactionId")
  	private java.lang.String transactionId;
  	
  	/**
  	 * 订单类型 1订购、2退费、3换货、4退货、5预约单（商机单）
  	 */
	@ApiModelProperty(value = "订单类型 1订购、2退费、3换货、4退货、5预约单（商机单）")
  	private java.lang.String orderType;
  	
  	/**
  	 * 当col3=yijiandaif时，商品所属店铺保存ord_order->col1;当col3=fenxian时，商品所属店铺保存ord_order->col1;当col3=fenxxiao时，原商品所属店铺保存ord_order->col1
  	 */
	@ApiModelProperty(value = "当col3=yijiandaif时，商品所属店铺保存ord_order->col1;当col3=fenxian时，商品所属店铺保存ord_order->col1;当col3=fenxxiao时，原商品所属店铺保存ord_order->col1")
  	private java.lang.String col1;
  	
  	/**
  	 * 当col3=yijiandaif时，代发分销商(商品所属店铺)ord_order->col2;当col3=fenxian时分享会员ord_order->col2;当col3=fenxxiao分销商(商品所属店铺)ord_order->col2
  	 */
	@ApiModelProperty(value = "当col3=yijiandaif时，代发分销商(商品所属店铺)ord_order->col2;当col3=fenxian时分享会员ord_order->col2;当col3=fenxxiao分销商(商品所属店铺)ord_order->col2")
  	private java.lang.String col2;
  	
  	/**
  	 * fenxian、yijiandaifa、fenxiao 保存到ord_order col3
  	 */
	@ApiModelProperty(value = "fenxian、yijiandaifa、fenxiao 保存到ord_order col3")
  	private java.lang.String col3;
  	
  	/**
  	 * col4
  	 */
	@ApiModelProperty(value = "col4")
  	private java.lang.String col4;
  	
  	/**
  	 * col5
  	 */
	@ApiModelProperty(value = "col5")
  	private java.util.Date col5;
  	
  	/**
  	 * 福建宽带商品：40000； 装维延伸：50000；智能家居：60000；
  	 */
	@ApiModelProperty(value = "福建宽带商品：40000； 装维延伸：50000；智能家居：60000；")
  	private java.lang.String typeCode;
  	
  	/**
  	 * 受理状态，0未受理，1审核通过，2审核 不通过，3受理成功，4受理失败
  	 */
	@ApiModelProperty(value = "受理状态，0未受理，1审核通过，2审核 不通过，3受理成功，4受理失败")
  	private java.lang.Long acceptStatus;
  	
  	/**
  	 * 商品id
  	 */
	@ApiModelProperty(value = "商品id")
  	private java.lang.String goodsId;
  	
  	/**
  	 * 支付时间
  	 */
	@ApiModelProperty(value = "支付时间")
  	private java.util.Date payTime;
  	
  	/**
  	 * 订单归属本地网
  	 */
	@ApiModelProperty(value = "订单归属本地网")
  	private java.lang.String lanId;
  	
  	/**
  	 * 处理消息
  	 */
	@ApiModelProperty(value = "处理消息")
  	private java.lang.String dealMessage;
  	
  	/**
  	 * T 需要对账、 F 不需要对账
  	 */
	@ApiModelProperty(value = "T 需要对账、 F 不需要对账")
  	private java.lang.String billFlag;
  	
  	/**
  	 * 业务号码
  	 */
	@ApiModelProperty(value = "业务号码")
  	private java.lang.String accNbr;
  	
  	/**
  	 * 批次ID
  	 */
	@ApiModelProperty(value = "批次ID")
  	private java.lang.String batchId;
  	
  	/**
  	 * 发票类型：1普通发票、2增值发票
  	 */
	@ApiModelProperty(value = "发票类型：1普通发票、2增值发票")
  	private java.lang.Long invoiceType;
  	
  	/**
  	 * 发票抬头描述
  	 */
	@ApiModelProperty(value = "发票抬头描述")
  	private java.lang.String invoiceTitleDesc;
  	
  	/**
  	 * 发票内容:1明细，2办公用品，3电脑配件，4耗材
  	 */
	@ApiModelProperty(value = "发票内容:1明细，2办公用品，3电脑配件，4耗材")
  	private java.lang.Long invoiceContent;
  	
  	/**
  	 * 最后修改时间
  	 */
	@ApiModelProperty(value = "最后修改时间")
  	private java.util.Date lastUpdate;
  	
  	/**
  	 * 订单生成类型：1平台获取、2手工新建、3批量导入、4售货自建
  	 */
	@ApiModelProperty(value = "订单生成类型：1平台获取、2手工新建、3批量导入、4售货自建")
  	private java.lang.Long createType;
  	
  	/**
  	 * 来源店铺：京东、淘宝店铺名称
  	 */
	@ApiModelProperty(value = "来源店铺：京东、淘宝店铺名称")
  	private java.lang.String sourceShopName;
  	
  	/**
  	 * 订单归属对象（A）：供货商订单、二级分销商订单、一级分销商订单、分销商代理订单、电信订单
  	 */
	@ApiModelProperty(value = "订单归属对象（A）：供货商订单、二级分销商订单、一级分销商订单、分销商代理订单、电信订单")
  	private java.lang.String orderAdscriptionId;
  	
  	/**
  	 * 货到付款：款到发货、货到付款
  	 */
	@ApiModelProperty(value = "货到付款：款到发货、货到付款")
  	private java.lang.Long payType;
  	
  	/**
  	 * 确认状态：0未确认、1已拆分完、2部分拆分、3取消、4余单撤销、5失败订单、6异常订单(异常类型设置)
  	 */
	@ApiModelProperty(value = "确认状态：0未确认、1已拆分完、2部分拆分、3取消、4余单撤销、5失败订单、6异常订单(异常类型设置)")
  	private java.lang.Long confirmStatus;
  	
  	/**
  	 * 确认分派时间
  	 */
	@ApiModelProperty(value = "确认分派时间")
  	private java.util.Date confirmTime;
  	
  	/**
  	 * 确认组、、当确认人员为空，则归属确认组的人员可查询订单、当确认人员不为空，则订单归属确认人员
  	 */
	@ApiModelProperty(value = "确认组、、当确认人员为空，则归属确认组的人员可查询订单、当确认人员不为空，则订单归属确认人员")
  	private java.lang.String confirmGroupId;
  	
  	/**
  	 * 确认工号
  	 */
	@ApiModelProperty(value = "确认工号")
  	private java.lang.String confirmUserId;
  	
  	/**
  	 * 发货分派时间
  	 */
	@ApiModelProperty(value = "发货分派时间")
  	private java.util.Date shipAssignTime;
  	
  	/**
  	 * 发货组
  	 */
	@ApiModelProperty(value = "发货组")
  	private java.lang.String shipGroupId;
  	
  	/**
  	 * 发货工号
  	 */
	@ApiModelProperty(value = "发货工号")
  	private java.lang.String shipUserId;
  	
  	/**
  	 * 受理分派时间
  	 */
	@ApiModelProperty(value = "受理分派时间")
  	private java.util.Date acceptAssignTime;
  	
  	/**
  	 * 受理组
  	 */
	@ApiModelProperty(value = "受理组")
  	private java.lang.String acceptGroupId;
  	
  	/**
  	 * 受理工号
  	 */
	@ApiModelProperty(value = "受理工号")
  	private java.lang.String acceptUserId;
  	
  	/**
  	 * 支付分派时间
  	 */
	@ApiModelProperty(value = "支付分派时间")
  	private java.util.Date payAssignTime;
  	
  	/**
  	 * 支付组
  	 */
	@ApiModelProperty(value = "支付组")
  	private java.lang.String payGroupId;
  	
  	/**
  	 * 支付工号
  	 */
	@ApiModelProperty(value = "支付工号")
  	private java.lang.String payUserId;
  	
  	/**
  	 * 税金
  	 */
	@ApiModelProperty(value = "税金")
  	private java.lang.Long taxes;
  	
  	/**
  	 * 折扣
  	 */
	@ApiModelProperty(value = "折扣")
  	private java.lang.Long orderDiscount;
  	
  	/**
  	 * 订单促销优惠
  	 */
	@ApiModelProperty(value = "订单促销优惠")
  	private java.lang.Long orderCoupon;
  	
  	/**
  	 * 订单记录状态:1暂停、0恢复
  	 */
	@ApiModelProperty(value = "订单记录状态:1暂停、0恢复")
  	private java.lang.Long orderRecordStatus;
  	
  	/**
  	 * 查询组
  	 */
	@ApiModelProperty(value = "查询组")
  	private java.lang.String queryGroupId;
  	
  	/**
  	 * 查询组id
  	 */
	@ApiModelProperty(value = "查询组id")
  	private java.lang.String queryUserId;
  	
  	/**
  	 * dlyAddressId
  	 */
	@ApiModelProperty(value = "dlyAddressId")
  	private java.lang.String dlyAddressId;
  	
  	/**
  	 * 推广员id
  	 */
	@ApiModelProperty(value = "推广员id")
  	private java.lang.String spreadId;
  	
  	/**
  	 * 业务类型
  	 */
	@ApiModelProperty(value = "业务类型")
  	private java.lang.String serviceType;
  	
  	/**
  	 * 业务ID
  	 */
	@ApiModelProperty(value = "业务ID")
  	private java.lang.String serviceId;
  	
  	/**
  	 * serviceCode
  	 */
	@ApiModelProperty(value = "serviceCode")
  	private java.lang.String serviceCode;
  	
  	/**
  	 * appKey
  	 */
	@ApiModelProperty(value = "appKey")
  	private java.lang.String appKey;
  	
  	/**
  	 * 入网资料
  	 */
	@ApiModelProperty(value = "入网资料")
  	private java.lang.String col6;
  	
  	/**
  	 * 身份证上传
  	 */
	@ApiModelProperty(value = "身份证上传")
  	private java.lang.String col7;
  	
  	/**
  	 * col8
  	 */
	@ApiModelProperty(value = "col8")
  	private java.lang.Long col8;
  	
  	/**
  	 * col9
  	 */
	@ApiModelProperty(value = "col9")
  	private java.lang.Long col9;
  	
  	/**
  	 * col10
  	 */
	@ApiModelProperty(value = "col10")
  	private java.util.Date col10;
  	
  	/**
  	 * seviceType
  	 */
	@ApiModelProperty(value = "seviceType")
  	private java.lang.String seviceType;
  	
  	/**
  	 * 配送方式（老），新商城转物流模式
  	 */
	@ApiModelProperty(value = "配送方式（老），新商城转物流模式")
  	private java.lang.String oldShippingType;
  	
  	/**
  	 * 是否需要发货，1：是，0：否
  	 */
	@ApiModelProperty(value = "是否需要发货，1：是，0：否")
  	private java.lang.String needShipping;
  	
  	/**
  	 * 发票抬头：1个人、2单位--
  	 */
	@ApiModelProperty(value = "发票抬头：1个人、2单位--")
  	private java.lang.Long invoiceTitle;
  	
  	/**
  	 * 发票内容
  	 */
	@ApiModelProperty(value = "发票内容")
  	private java.lang.String invoiceDetail;
  	
  	/**
  	 * 会员等级ID
  	 */
	@ApiModelProperty(value = "会员等级ID")
  	private java.lang.String lvId;
  	
  	/**
  	 * 优惠编码
  	 */
	@ApiModelProperty(value = "优惠编码")
  	private java.lang.String couponCode;
  	
  	/**
  	 * 工作流Id
  	 */
	@ApiModelProperty(value = "工作流Id")
  	private java.lang.String workId;
  	
  	
  	//属性 end
  	
  	public static enum FieldNames{
        /** orderId */
        orderId,
        /** sn */
        sn,
        /** 记录ord_order_buyer表的购买者信息 */
        memberId,
        /** 已换货:-7；换货被拒绝:-6；退货被拒绝-5；申请换货:-4；申请退货：-3；退货：-2；退款：-1；正常：0；暂停：1；待审核:0；
待确认:-50；待审核审核不通过：1 ；未付款、审核通过：2 ；已支付、待受理：3 ；已受理待发货：4 ；已发货待确认：5 ；
确认收货：6 ；已完成：7 ；作废：8 ；撤单：-9 ；取消订单：-10 ；受理失败：-11 ；订单异常：99 ；退货申请：20 ；
退货申请审核通过：21 ；换货申请：22 ；换货申请通过：23 ；订单异常：99 ； */
        status,
        /** -2已退款，-1未付款，1已付款 */
        payStatus,
        /** -2已退货，-1未发货，1已发货 */
        shipStatus,
        /** shippingId */
        shippingId,
        /** shippingType */
        shippingType,
        /** shippingArea */
        shippingArea,
        /** paymentId */
        paymentId,
        /** paymentName */
        paymentName,
        /** 1、在线支付，2、货到付款 */
        paymentType,
        /** paymoney */
        paymoney,
        /** createTime */
        createTime,
        /** shipName */
        shipName,
        /** 物流地址 */
        shipAddr,
        /** 物流邮编 */
        shipZip,
        /** shipEmail */
        shipEmail,
        /** shipMobile */
        shipMobile,
        /** shipTel */
        shipTel,
        /** shipDay */
        shipDay,
        /** shipTime */
        shipTime,
        /** isProtect */
        isProtect,
        /** protectPrice */
        protectPrice,
        /** goodsAmount */
        goodsAmount,
        /** shippingAmount */
        shippingAmount,
        /** orderAmount */
        orderAmount,
        /** weight */
        weight,
        /** goodsNum */
        goodsNum,
        /** gainedpoint */
        gainedpoint,
        /** consumepoint */
        consumepoint,
        /** disabled */
        disabled,
        /** discount */
        discount,
        /** 0未转账\n            1已转账 */
        imported,
        /** pimported */
        pimported,
        /** completeTime */
        completeTime,
        /** 银行ID */
        bankId,
        /** goods */
        goods,
        /** remark */
        remark,
        /** 用户购买商品,用户id */
        userid,
        /** 订单来源 京东、淘宝、网厅、电子渠道 */
        sourceFrom,
        /** 受理时间 */
        acceptTime,
        /** transactionId */
        transactionId,
        /** 订单类型 1订购、2退费、3换货、4退货、5预约单（商机单） */
        orderType,
        /** 当col3=yijiandaif时，商品所属店铺保存ord_order->col1;当col3=fenxian时，商品所属店铺保存ord_order->col1;当col3=fenxxiao时，原商品所属店铺保存ord_order->col1 */
        col1,
        /** 当col3=yijiandaif时，代发分销商(商品所属店铺)ord_order->col2;当col3=fenxian时分享会员ord_order->col2;当col3=fenxxiao分销商(商品所属店铺)ord_order->col2 */
        col2,
        /** fenxian、yijiandaifa、fenxiao 保存到ord_order col3 */
        col3,
        /** col4 */
        col4,
        /** col5 */
        col5,
        /** 福建宽带商品：40000； 装维延伸：50000；智能家居：60000； */
        typeCode,
        /** 受理状态，0未受理，1审核通过，2审核 不通过，3受理成功，4受理失败 */
        acceptStatus,
        /** 商品id */
        goodsId,
        /** 支付时间 */
        payTime,
        /** 订单归属本地网 */
        lanId,
        /** 处理消息 */
        dealMessage,
        /** T 需要对账、 F 不需要对账 */
        billFlag,
        /** 业务号码 */
        accNbr,
        /** 批次ID */
        batchId,
        /** 发票类型：1普通发票、2增值发票 */
        invoiceType,
        /** 发票抬头描述 */
        invoiceTitleDesc,
        /** 发票内容:1明细，2办公用品，3电脑配件，4耗材 */
        invoiceContent,
        /** 最后修改时间 */
        lastUpdate,
        /** 订单生成类型：1平台获取、2手工新建、3批量导入、4售货自建 */
        createType,
        /** 来源店铺：京东、淘宝店铺名称 */
        sourceShopName,
        /** 订单归属对象（A）：供货商订单、二级分销商订单、一级分销商订单、分销商代理订单、电信订单 */
        orderAdscriptionId,
        /** 货到付款：款到发货、货到付款 */
        payType,
        /** 确认状态：0未确认、1已拆分完、2部分拆分、3取消、4余单撤销、5失败订单、6异常订单(异常类型设置) */
        confirmStatus,
        /** 确认分派时间 */
        confirmTime,
        /** 确认组、、当确认人员为空，则归属确认组的人员可查询订单、当确认人员不为空，则订单归属确认人员 */
        confirmGroupId,
        /** 确认工号 */
        confirmUserId,
        /** 发货分派时间 */
        shipAssignTime,
        /** 发货组 */
        shipGroupId,
        /** 发货工号 */
        shipUserId,
        /** 受理分派时间 */
        acceptAssignTime,
        /** 受理组 */
        acceptGroupId,
        /** 受理工号 */
        acceptUserId,
        /** 支付分派时间 */
        payAssignTime,
        /** 支付组 */
        payGroupId,
        /** 支付工号 */
        payUserId,
        /** 税金 */
        taxes,
        /** 折扣 */
        orderDiscount,
        /** 订单促销优惠 */
        orderCoupon,
        /** 订单记录状态:1暂停、0恢复 */
        orderRecordStatus,
        /** 查询组 */
        queryGroupId,
        /** 查询组id */
        queryUserId,
        /** dlyAddressId */
        dlyAddressId,
        /** 推广员id */
        spreadId,
        /** 业务类型 */
        serviceType,
        /** 业务ID */
        serviceId,
        /** serviceCode */
        serviceCode,
        /** appKey */
        appKey,
        /** 入网资料 */
        col6,
        /** 身份证上传 */
        col7,
        /** col8 */
        col8,
        /** col9 */
        col9,
        /** col10 */
        col10,
        /** seviceType */
        seviceType,
        /** 配送方式（老），新商城转物流模式 */
        oldShippingType,
        /** 是否需要发货，1：是，0：否 */
        needShipping,
        /** 发票抬头：1个人、2单位-- */
        invoiceTitle,
        /** 发票内容 */
        invoiceDetail,
        /** 会员等级ID */
        lvId,
        /** 优惠编码 */
        couponCode,
        /** 工作流Id */
        workId
    }

	

}
