package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "店中商(分销商)和标签 关联关系 列表查询 请求对象，对应模型prod_partner_tag_rel, 对应实体PartnerTagRel类")
public class MerchantTagRelListReq extends AbstractRequest implements java.io.Serializable {
    private static final long serialVersionUID = -3664120367721004392L;


    //属性 begin
    /**
     * 关联ID
     */
//    @ApiModelProperty(value = "关联ID")
//    private String relId;

    /**
     * 标签ID
     */
    @ApiModelProperty(value = "标签ID")
    private String tagId;

    /**
     * 商家 ID
     */
    @ApiModelProperty(value = "商家 ID")
    private String merchantId;

    /**
     * 商家 ID
     */
    @ApiModelProperty(value = "商家 ID 集合")
    private List<String> merchantIdList;

}