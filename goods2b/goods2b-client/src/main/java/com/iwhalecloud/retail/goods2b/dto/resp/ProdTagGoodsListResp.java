package com.iwhalecloud.retail.goods2b.dto.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@Data
public class ProdTagGoodsListResp implements Serializable {
    /**
     * 商品列表
     */
    private List goodsList;
}
