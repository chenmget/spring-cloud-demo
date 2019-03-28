package com.iwhalecloud.retail.order.dto.resquest;

import com.iwhalecloud.retail.order.consts.order.OrderPayType;
import com.iwhalecloud.retail.order.consts.order.OrderShipType;
import com.iwhalecloud.retail.order.dto.base.EnumCheckValidate;
import com.iwhalecloud.retail.order.dto.base.GroupCheckValidate;
import com.iwhalecloud.retail.order.dto.base.ModifyBaseRequest;
import com.iwhalecloud.retail.order.dto.base.NullCheckValidate;
import com.iwhalecloud.retail.order.dto.model.ContractPInfoModel;
import com.iwhalecloud.retail.order.dto.model.OrderGoodsItemModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BuilderOrderRequest extends ModifyBaseRequest implements Serializable {

    /**
     * 订单信息
     */
    @ApiModelProperty(value = "买家留言")
    private String remark;

    @ApiModelProperty(value = "【必填】1微信支付  3线下付款")
    @EnumCheckValidate(enumClass= OrderPayType.class, message = "payType 类型不匹配,请参考文档")
    private String payType;

    @ApiModelProperty(value = "【必填】1:APP；2：微商城-普通单；3：微商城-商机单；4：其它")
    @NullCheckValidate(message = "typeCode 字段不能为空")
    private Integer typeCode;

    @ApiModelProperty(value = "【必填】 1订购 5 预约单")
    @NullCheckValidate(message = "orderType 字段不能为空")
    private String orderType;

    @ApiModelProperty(value = "优惠券实例id")
    private String couponCode;
    @ApiModelProperty(value = "优惠券名称")
    private String couponDesc;

    @ApiModelProperty(value = "【必填】绑定类型：0;普通，1合约机")
    @NullCheckValidate(message = "bindType 字段不能为空")
    private String bindType;

    @ApiModelProperty(value = "【合约机必填】合约信息")
    @GroupCheckValidate(key = "bindType",value = "1" ,isList = false,isObject = true,message = "contractInfo 合约机必填")
    private ContractPInfoModel contractInfo;
    @ApiModelProperty(value = "【必填】分销商id")
    @NullCheckValidate(message = "userId 字段不能为空")
    private String userId;
    @ApiModelProperty(value = "【必填】分销商名称")
    private String sourceShopName;
    @ApiModelProperty(value = "【必填】下单类型：购物车：GWCGM,立即购买:LJGM")
    private String sourceType;
    @ApiModelProperty(value = "金额")
    private String shippingAmount;

    @ApiModelProperty(value = "【必填】es_member_address")
    @NullCheckValidate(message =  "addressId 字段不能为空")
    private String addressId;
    @ApiModelProperty(value = "【立即购买必填】商品列表")
    @GroupCheckValidate(key = "sourceType",value = "LJGM" ,isList = true,isObject = false,message = "goodsItem 立即购买必填")
    private List<OrderGoodsItemModel> goodsItem;
    @ApiModelProperty(value = "【必填】 配发方式")
    @EnumCheckValidate(enumClass= OrderShipType.class, message = "shipType 类型不匹配,请参考文档")
    private String shipType;

    private String lvId;

    /**
     * 发票信息
     */
    @ApiModelProperty(value = "发票类型：1普通发票、2增值发票")
    private Integer invoiceType;
    @ApiModelProperty(value = "发票内容：1 明细，2 办公用品，3 电脑配件，4 耗材")
    private String invoiceContent;
    @ApiModelProperty(value = "发票内容")
    private String invoiceDetail;
    @ApiModelProperty(value = "发票抬头：1 个人 2单位")
    private Integer invoiceTitle;
    @ApiModelProperty(value = "发票抬头描述")
    private String invoiceTitleDesc;

    //是否使用优惠券(1使用)
    private Integer isUseCoupon;
    private String couponOrderId;

    @ApiModelProperty(value = "厅店id")
    private String shopId;
    @ApiModelProperty(value = "厅店名称")
    private String shopName;


}
