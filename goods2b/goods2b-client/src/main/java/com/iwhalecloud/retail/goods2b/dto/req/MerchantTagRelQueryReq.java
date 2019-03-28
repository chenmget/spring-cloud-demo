package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "店中商(分销商)和标签 关联关系，对应模型prod_partner_tag_rel, 对应实体PartnerTagRel类")
public class MerchantTagRelQueryReq extends AbstractRequest implements java.io.Serializable {
    private static final long serialVersionUID = -4407617677695267511L;

    //属性 begin
    /**
     * 关联ID
     */
    @ApiModelProperty(value = "关联ID")
    private String relId;

}