package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
@ApiModel(value = "店中商(分销商)和标签 关联关系 新增请求对象，对应模型prod_partner_tag_rel, 对应实体PartnerTagRel类")
public class MerchantTagRelSaveReq extends AbstractRequest implements java.io.Serializable {

    private static final long serialVersionUID = 3570285789851568676L;

    //属性 begin
    /**
     * 关联ID
     */
//    @ApiModelProperty(value = "关联ID")
//    private String relId;

    /**
     * 商家 ID
     */
    @ApiModelProperty(value = "商家 ID")
    @NotEmpty(message = "商家ID不能为空")
    private String merchantId;


    /**
     * 标签ID
     */
//    @ApiModelProperty(value = "标签ID")
//    private String tagId;

    /**
     * 标签ID集合
     */
    @ApiModelProperty(value = "标签ID集合，用于批量插入 ")
    private List<String> tagIdList;


}