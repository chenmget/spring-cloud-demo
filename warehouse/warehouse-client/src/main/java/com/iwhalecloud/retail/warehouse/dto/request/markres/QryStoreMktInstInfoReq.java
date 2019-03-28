package com.iwhalecloud.retail.warehouse.dto.request.markres;

import com.iwhalecloud.retail.warehouse.dto.request.markres.base.AbstractMarkResPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("按串码查询零售商仓库终端的实例信息请求参数")
public class QryStoreMktInstInfoReq extends AbstractMarkResPageRequest implements Serializable {

    @ApiModelProperty(value = "串码")
    private String mkt_res_inst_nbr;

    @ApiModelProperty(value = "仓库ID")
    private String mkt_res_store_id;

}
