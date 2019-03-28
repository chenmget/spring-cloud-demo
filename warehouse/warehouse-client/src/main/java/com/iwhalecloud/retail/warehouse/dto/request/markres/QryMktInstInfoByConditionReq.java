package com.iwhalecloud.retail.warehouse.dto.request.markres;

import com.iwhalecloud.retail.warehouse.dto.request.markres.base.AbstractMarkResPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/2 14:45
 */
@Data
@ApiModel("查询零售商仓库终端的实例列表按多种条件请求参数")
public class QryMktInstInfoByConditionReq extends AbstractMarkResPageRequest implements Serializable {
    @NotEmpty(message = "串码不能为空")
    @ApiModelProperty(value = "串码")
    private String mkt_res_inst_nbr;
    @NotEmpty(message = "仓库ID不能为空")
    @ApiModelProperty(value = "仓库ID")
    private String mkt_res_store_id;

    @ApiModelProperty(value = "机型")
    private String mkt_res_id;
    @NotEmpty(message = "入库时间开始时间不能为空")
    @ApiModelProperty(value = "入库时间开始时间")
    private String instoreBeginTime;
    @NotEmpty(message = "入库结束时间不能为空")
    @ApiModelProperty(value = "入库结束时间")
    private String instoreEndTime;

    @ApiModelProperty(value = "资源编码")
    private String mkt_res_nbr;

    @ApiModelProperty(value = "资源名称")
    private String mkt_res_name;

    @ApiModelProperty(value = "CRM状态")
    private String status_cd;


}
