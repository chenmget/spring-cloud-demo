package com.iwhalecloud.retail.warehouse.dto.request.markresswap;

import com.iwhalecloud.retail.warehouse.dto.request.markres.base.AbstractMarkResPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/6 19:00
 */
@Data
@ApiModel("查询零售商仓库终端的实例列表按多种条件请求参数")
public class QryMktInstInfoByConditionSwapReq extends AbstractMarkResPageRequest implements Serializable {


    @ApiModelProperty(value = "串码")
    private String barCode;
    @NotEmpty(message = "仓库ID不能为空")
    @ApiModelProperty(value = "仓库ID")
    private String storeId;

    @ApiModelProperty(value = "机型")
    private String mktResId;

    @ApiModelProperty(value = "入库时间开始时间")
    private String instoreBeginTime;

    @ApiModelProperty(value = "入库结束时间")
    private String instoreEndTime;

    @ApiModelProperty(value = "资源编码")
    private String mktResNbr;

    @ApiModelProperty(value = "资源名称")
    private String mktResName;

    @ApiModelProperty(value = "CRM状态")
    private String statusCd;
}
