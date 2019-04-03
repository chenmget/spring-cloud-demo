package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "商家权限规则详情 获取列表请求对象，对应模型par_merchant_rules, 对应实体MerchantRules类")
public class MerchantRulesDetailPageReq extends PageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "规则类型:  1 经营权限   2 绿色通道权限   3 调拨权限")
    private String ruleType;

    /**
     * 对象类型:
     * RULE_TYPE是1 经营权限时: TARGET_TYPE是： 1 品牌  2 机型  3 区域 4 商家；
     * RULE_TYPE是2 绿色通道权限时: TARGET_TYPE是： 1 产品，TARGET_ID填写PRODUCT_BASE_ID   ,  2 机型，TARGET_ID填写PRODUCT_ID ;
     * RULE_TYPE是3 调拨权限时:  TARGET_TYPE是： 2 机型 3 区域 4 商家；
     */
    @ApiModelProperty(value = "对象类型:  " +
            " RULE_TYPE是1 经营权限时: TARGET_TYPE对应： 1 品牌，TARGET_ID存BRAND_ID  2 机型，TARGET_ID存PRODUCT_ID  3 区域,TARGET_ID存REGION_ID 4 商家TARGET_ID存MERCHANT_ID； " +
            " RULE_TYPE是2 绿色通道权限时: TARGET_TYPE对应： 1 产品，TARGET_ID存PRODUCT_BASE_ID,  2 机型，TARGET_ID存PRODUCT_ID ;" +
            " RULE_TYPE是3 调拨权限时:  TARGET_TYPE对应： 2 机型，TARGET_ID存PRODUCT_ID， 3 区域,TARGET_ID存REGION_ID 4 商家,TARGET_ID存MERCHANT_ID；")
    private String targetType;

    @ApiModelProperty(value = "渠道状态")
    private String status;

    @ApiModelProperty(value = "商家本地网")
    private List<String> lanId;

    @ApiModelProperty(value = "商家区县")
    private List<String> city;

    @ApiModelProperty(value = "商家所属经营主体名称")
    private String businessEntityName;

    @ApiModelProperty(value = "商家所属经营主体编码")
    private String businessEntityCode;

    @ApiModelProperty(value = "标签")
    private String tagId;

    @ApiModelProperty(value = "系统账号")
    private String loginName;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "用户关联商家Id")
    private String relCode;

    @ApiModelProperty(value = "标签关联商家Id集合",hidden = true)
    private List<String> tagMerchantList;


}
