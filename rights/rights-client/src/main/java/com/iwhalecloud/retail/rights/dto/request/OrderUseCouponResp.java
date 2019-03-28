package com.iwhalecloud.retail.rights.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderUseCouponResp implements Serializable {

    private List<OrderUseCouponDTO> couponDTOList;
}
