package com.iwhalecloud.retail.warehouse.dto.request.markres;

import com.iwhalecloud.retail.warehouse.dto.request.markres.base.AbstractMarkResRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/2 14:57
 */
@Data
@ApiModel("查询零售商仓库终端的库存数量按条件请求参数")
public class StoreInventoryQuantityReq extends AbstractMarkResRequest implements Serializable {
    @ApiModelProperty(value = "分页条数")
    private String pagesize;
    @ApiModelProperty(value = "当前页")
    private String pageindex;


    @ApiModelProperty(value = "仓库ID")
    private String storeid;

    @ApiModelProperty(value = "机型")
    private String mkt_res_id;

    @ApiModelProperty(value = "状态")
    private String state;

}
