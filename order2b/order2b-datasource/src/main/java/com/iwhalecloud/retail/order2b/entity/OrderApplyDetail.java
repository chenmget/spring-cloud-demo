package com.iwhalecloud.retail.order2b.entity;

import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import lombok.Data;

import java.io.Serializable;

@Data
@WhaleCloudDBKeySequence(keySeqName = "detailId",
        keySeqValue = DBTableSequence.ORD_ORDER_APPLY_DETAIL)
public class OrderApplyDetail implements Serializable {

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
