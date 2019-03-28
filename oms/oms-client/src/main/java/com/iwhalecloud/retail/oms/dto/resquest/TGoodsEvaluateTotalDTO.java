package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.Serializable;

@Data
public class TGoodsEvaluateTotalDTO implements Serializable {

    private Long id;
    private String goodsId;
    private Integer tagsId;
    private String tagsName;
    private Integer counts;

    private String evaluateText;

}
