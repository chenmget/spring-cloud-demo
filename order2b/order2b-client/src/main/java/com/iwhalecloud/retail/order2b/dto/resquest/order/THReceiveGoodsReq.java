package com.iwhalecloud.retail.order2b.dto.resquest.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class THReceiveGoodsReq extends UpdateApplyStatusRequest implements Serializable {

    private List<String> resNbrList;

}
