package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("根据条件查找 商家详细（整表）信息 分页列表， 要包含 RetailMerchantPageReq、SupplyMerchantPageReq 、FactoryMerchantPageReq")
public class MerchantDetailPageReq extends PageVO {

// "零售商列表字段与查询条件为：
//    系统账号、店中商编码、店中商名称、店中商经营主体名称、销售点名称、销售点编码、区域（分公司、市县、分局/县部门、营销中心/支局）、
//    渠道视图状态、系统状态、分组标签、商家类型、营业执照失效期、渠道大类、渠道小类、渠道子类。"
// "地包商、国/省包商列表字段与查询条件
//    系统账号、商家名称、商家渠道视图编码、商家渠道视图经营主体、系统状态、渠道视图状态、
//    翼支付收款账号、支付宝收款账号、营业执照号、税号、公司账号、营业执照失效期"
// "厂商列表字段与查询条件
//    系统账号、商家名称、商家渠道视图编码，可查看经营的产品信息、串码录入权限信息、代理国、省包商信息。"

    // par_merchant 表字段
    @ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
    private String merchantType;

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "渠道状态:" +
            " 有效1000  主动暂停1001  异常暂停1002 无效1100 终止1101 退出1102 未生效1200" +
            " 已归档1300 预退出8922 冻结8923 主动暂停1001  异常暂停1002   无效1100  终止1101  " +
            " 退出1102  未生效1200  已归档1300 预退出8922  冻结8923 ")
    private java.lang.String status;

    @ApiModelProperty(value = "地市")
    private java.lang.String lanId;

    @ApiModelProperty(value = "市县")
    private java.lang.String city;

    @ApiModelProperty(value = "分局/县部门")
    private java.lang.String subBureau;

    @ApiModelProperty(value = "营销中心/支局")
    private java.lang.String marketCenter;

    @ApiModelProperty(value = "商家所属经营主体	")
    private java.lang.String businessEntityName;

    @ApiModelProperty(value = "商家所属经营主体	编码")
    private java.lang.String businessEntityCode;

    @ApiModelProperty(value = "销售点编码")
    private java.lang.String shopCode;

    @ApiModelProperty(value = "销售点名称")
    private java.lang.String shopName;

    @ApiModelProperty(value = "渠道大类:" +
            " 自有厅10  政企直销经理11  政企直销代理 12  公众直销经理 13 公众直销代理 14 连锁店 20 独立店 " +
            " 24 便利点 30 互联网自营 31 互联网代理 32 移动物联网自营 33 移动互联网代理 34 客服型电子渠道 35 专营店 " +
            " 40  政企直销经理 11  政企直销代理 12 公众直销经理 13  公众直销代理 14  连锁店 20 独立店 24  便利点 " +
            " 30 互联网自营 31 互联网代理 32  移动物联网自营 33  移动互联网代理 34 客服型电子渠道 35  专营店 40 ")
    private java.lang.String channelType;

    @ApiModelProperty(value = "渠道小类	")
    private java.lang.String channelMediType;

    @ApiModelProperty(value = "渠道子类	")
    private java.lang.Long channelSubType;


// 其他表字段
    @ApiModelProperty(value = "系统账号")
    private String loginName;

    @ApiModelProperty(value = "商家id集合")
    private List<String> merchantIdList;

    @ApiModelProperty(value = "标签ID")
    private String tagId;

}
