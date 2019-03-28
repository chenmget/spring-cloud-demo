package com.iwhalecloud.retail.warehouse.dto.request.markresswap;

import com.iwhalecloud.retail.warehouse.dto.request.markres.base.AbstractMarkResPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/6 19:25
 */
@Data
@ApiModel("按串码查询零售商仓库终端的实例信息请求参数")
public class QryStoreMktInstInfoSwapReq extends AbstractMarkResPageRequest implements Serializable {

    @ApiModelProperty(value = "串码")
    private String barCode;

    @ApiModelProperty(value = "仓库ID")
    private String storeId;

}
