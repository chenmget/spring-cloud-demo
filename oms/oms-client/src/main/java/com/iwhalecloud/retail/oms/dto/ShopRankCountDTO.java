package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopRankCountDTO implements Serializable {

    private Integer rankNo;

    private String shopName;

    private Double shopNumber;
}
