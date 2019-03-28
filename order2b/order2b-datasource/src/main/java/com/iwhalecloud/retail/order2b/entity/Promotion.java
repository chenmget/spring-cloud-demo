package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import lombok.Data;

@Data
@WhaleCloudDBKeySequence(keySeqName = "ordPromotioId",keySeqValue = DBTableSequence.ORD_PROMOTION)
@TableName(value = "ord_promotion")
public class Promotion {

    private String lanId;

    private String ordPromotioId;
    private String orderId;
    private String orderItemId;
    private String goodsId;
    private String productId;
    private String mktActId;
    private String mktActName;
    private String mktActType;
    private String promotionId;
    private String promotionName;
    private String promotionType;
    private String promotionInstId;
    private String discount;
    private String statusCd;
    @TableTimeValidate
    private String statusDate;
    private String createDate;
    private String sourceFrom;


}
