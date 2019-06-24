package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import lombok.Data;

@Data
@TableName("ord_advance_order")
public class AdvanceOrder {
    private String lanId;

    private String id;
    private String orderId;
    private String activityId;
    private Double advanceAmount;
    private String advancePayBegin;
    private String advancePayEnd;
    private String advancePaymentType;
    private String advancePayStatus;
    private String advancePayMoney;

    @TableTimeValidate
    private String advancePayTime;
    private String advancePayType;
    private String advancePayCode;

    private Double restAmount;
    private String restPayBegin;
    private String restPayEnd;
    private String restPayStatus;
    private String restPaymentType;
    private String restPayMoney;
    @TableTimeValidate
    private String restPayTime;
    private String restPayType;
    private String restPayCode;

    private String remark;
    private String createUserId;
    private String createTime;
    private String sourceFrom;

    private String advanceTransId;    //  定金交易流水id
    private String restTransId;       //  尾款交易流水id

}
