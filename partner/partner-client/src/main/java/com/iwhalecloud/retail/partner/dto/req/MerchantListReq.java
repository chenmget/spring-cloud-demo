package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@ApiModel("根据条件查找 商家信息 列表")
public class MerchantListReq implements Serializable {

    private static final long serialVersionUID = 4557127783464661625L;

    @ApiModelProperty(value = "是否需要查其他表的额外字段，默认false：不需要查其他额外字段   true：要查其他额外字段（比如翻译后的lanName 等等")
    private Boolean needOtherTableFields;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    /**
     * 商家类型:  1 厂商    2 地包商    3 省包商   4 零售商
     */
    @ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
    private String merchantType;

       /**
     * 商家id集合
     */
    @ApiModelProperty(value = "商家id集合")
    private List<String> merchantIdList;

    /**
     * 商家code集合
     */
    @ApiModelProperty(value = "商家code集合")
    private List<String> merchantCodeList;

    /**
     * 商家所属经营主体	编码集合
     */
    @ApiModelProperty(value = "商家所属经营主体	编码集合")
    private List<String> businessEntityCodeList;

    @ApiModelProperty(value = "商家本地网")
    private String lanId;

    @ApiModelProperty(value = "商家市县ID")
    private String city;

    @ApiModelProperty(value = "商家市县ID集合")
    private List<String> cityList;

    @ApiModelProperty(value = "标签ID")
    private String tagId;

    // sys_user表字段
    @ApiModelProperty(value = "系统账号")
    private String loginName;

    @ApiModelProperty(value = "(商家)CRM组织ID集合(精确查找某些orgId")
    private List<String> parCrmOrgIdList;

    /**
     * 是否已赋权
     */
    @ApiModelProperty(value = "是否已赋权")
    private java.lang.String assignedFlg;
}