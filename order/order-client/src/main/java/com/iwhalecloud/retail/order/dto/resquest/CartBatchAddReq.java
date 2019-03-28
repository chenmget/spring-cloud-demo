package com.iwhalecloud.retail.order.dto.resquest;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/30
 **/
@Data
public class CartBatchAddReq implements Serializable {

    List<AddCartReqDTO> cartAddReqList;
}
