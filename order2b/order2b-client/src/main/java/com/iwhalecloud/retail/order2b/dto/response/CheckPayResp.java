package com.iwhalecloud.retail.order2b.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CheckPayResp implements Serializable {

    private String payToken;
    private String orderId;

    private List<String> payTypeList;
}
