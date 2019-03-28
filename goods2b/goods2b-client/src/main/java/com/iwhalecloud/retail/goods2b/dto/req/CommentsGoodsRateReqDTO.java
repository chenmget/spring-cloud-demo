package com.iwhalecloud.retail.goods2b.dto.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author My
 * @Date 2018/12/6
 **/
@Data
public class CommentsGoodsRateReqDTO implements Serializable {
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 好评率
     */
    private BigDecimal goodsCommentsRate;
    /**
     * 评论数
     */
    private Integer commentsNum;
}
