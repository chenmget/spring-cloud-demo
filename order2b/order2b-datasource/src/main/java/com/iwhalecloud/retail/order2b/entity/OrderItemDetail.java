package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("ord_order_item_detail")
@WhaleCloudDBKeySequence(keySeqName = "detailId",
        keySeqValue = DBTableSequence.ORD_ORDER_ITEM_DETAIL)
public class OrderItemDetail implements Serializable {

    private String lanId;

    private String detailId;
    private String itemId;
    private String orderId;
    private String goodsId;
    private String productId;
    private String resNbr;
    private Integer batchId;
    private String createUserId;
    @TableTimeValidate
    private String createTime;
    private String  state;
    private String orderApplyId;

}
