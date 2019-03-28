package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2019/1/22
 */
@Data
@ApiModel(value = "产品审批-修改审核状态")
public class UpdateProductAuditStateReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 2526351266914436981L;

    @NotBlank
    @ApiModelProperty(value = "产品BaseID")
    private String productBaseId;

    @ApiModelProperty(value = "修改人")
    private String updateStaff;

}
