package com.iwhalecloud.retail.order2b.dto.resquest.cart;

import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/30
 **/
@Data
public class CartBatchAddReq extends OrderRequest implements Serializable {

    List<AddCartReq> cartAddReqList;
}
