package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.SRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelectNbrReq extends SRequest implements Serializable {

    @ApiModelProperty("类型：1：采购，2销售，3管理")
    private String userExportType;
    @ApiModelProperty("订单项id")
    private String itemId;
}
