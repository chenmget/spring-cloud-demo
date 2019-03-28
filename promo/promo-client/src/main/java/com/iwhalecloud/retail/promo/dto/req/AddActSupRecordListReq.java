package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhou.zc
 * @date 2019年03月07日
 * @Description:新增前置补录记录
 */
@Data
public class AddActSupRecordListReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 959106633432580935L;

    /**
     * 补录记录ID
     */
    @ApiModelProperty(value = "补录记录ID")
    private String recordId;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private String orderId;


    /**
     * 订单中包含的串码
     */
    @ApiModelProperty(value = "订单中包含的串码")
    private String resNbr;

    @ApiModelProperty(value = "提交补录记录")
    private String checkRecord;

    @ApiModelProperty(value = "审核标识")
    private String checkFlag;

    @ApiModelProperty(value = "审核结果")
    private String result;

}
