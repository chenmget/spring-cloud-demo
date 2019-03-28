package com.iwhalecloud.retail.partner.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商家信息同步实体(改类特殊，不能修改属性顺序)
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月12日
 */
@Data
public class MerchantSyncDTO implements Serializable {
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
     * (商家)联系人
     */
    @ApiModelProperty(value = "(商家)联系人	")
    private java.lang.String linkman;

    /**
     * (商家)联系电话
     */
    @ApiModelProperty(value = "(商家)联系电话")
    private java.lang.String phoneNo;

    /**
     * 商家所属经营主体	编码
     */
    @ApiModelProperty(value = "商家所属经营主体	编码")
    private java.lang.String businessEntityCode;

    /**
     * 商家所属经营主体
     */
    @ApiModelProperty(value = "商家所属经营主体	")
    private java.lang.String businessEntityName;

    /**
     * 城乡属性 县 乡（农村）
     */
    @ApiModelProperty(value = "城乡属性 县 乡（农村）")
    private java.lang.String cityAttr;

    /**
     * 所属版块
     */
    @ApiModelProperty(value = "所属版块	")
    private java.lang.String block;

    /**
     * 地市
     */
    @ApiModelProperty(value = "地市")
    private java.lang.String lanId;

    /**
     * 市县
     */
    @ApiModelProperty(value = "市县")
    private java.lang.String city;

    /**
     * 分局/县部门
     */
    @ApiModelProperty(value = "分局/县部门")
    private java.lang.String subBureau;

    /**
     * 营销中心/支局
     */
    @ApiModelProperty(value = "营销中心/支局")
    private java.lang.String marketCenter;

    /**
     * 翼支付账号
     */
    @ApiModelProperty(value = "翼支付账号")
    private java.lang.String account;

    /**
     * (商家)CRM组织ID
     */
    @ApiModelProperty(value = "(商家)CRM组织ID	")
    private java.lang.String parCrmOrgId;

    /**
     * (商家)CRM组织编码
     */
    @ApiModelProperty(value = "(商家)CRM组织编码")
    private java.lang.String parCrmOrgCode;

    /**
     * CRM是否接入
     */
    @ApiModelProperty(value = "CRM是否接入	")
    private java.lang.String isCrm;

    @ApiModelProperty(value = "渠道大类:" +
        " 自有厅10  政企直销经理11  政企直销代理 12  公众直销经理 13 公众直销代理 14 连锁店 20 独立店 " +
        " 24 便利点 30 互联网自营 31 互联网代理 32 移动物联网自营 33 移动互联网代理 34 客服型电子渠道 35 专营店 " +
        " 40  政企直销经理 11  政企直销代理 12 公众直销经理 13  公众直销代理 14  连锁店 20 独立店 24  便利点 " +
        " 30 互联网自营 31 互联网代理 32  移动物联网自营 33  移动互联网代理 34 客服型电子渠道 35  专营店 40 ")
    private java.lang.String channelType;

    /**
     * 渠道小类
     */
    @ApiModelProperty(value = "渠道小类	")
    private java.lang.String channelMediType;

    /**
     * 渠道子类
     */
    @ApiModelProperty(value = "渠道子类	")
    private java.lang.String channelSubType;

    @ApiModelProperty(value = "渠道状态:" +
        " 有效1000  主动暂停1001  异常暂停1002 无效1100 终止1101 退出1102 未生效1200" +
        " 已归档1300 预退出8922 冻结8923 主动暂停1001  异常暂停1002   无效1100  终止1101  " +
        " 退出1102  未生效1200  已归档1300 预退出8922  冻结8923 ")
    private java.lang.String status;

    /**
     * TOP商简称
     */
    @ApiModelProperty(value = "TOP商简称	")
    private java.lang.String topShortName;

    /**
     * TOP商级别
     */
    @ApiModelProperty(value = "TOP商级别	")
    private java.lang.String topLevel;

    /**
     * TOP10标识
     */
    @ApiModelProperty(value = "TOP10标识	")
    private java.lang.String top10Id;

    /**
     * 核心商圈名称
     */
    @ApiModelProperty(value = "核心商圈名称	")
    private java.lang.String busiCenterName;

    /**
     * 商圈级别
     */
    @ApiModelProperty(value = "商圈级别	")
    private java.lang.String busiCenterLevel;

    /**
     * 商圈类型
     */
    @ApiModelProperty(value = "商圈类型	")
    private java.lang.String busiCenterType;

    /**
     * 是否自有物业
     */
    @ApiModelProperty(value = "是否自有物业	")
    private java.lang.String isOwnProperty;

    /**
     * 自营厅级别
     */
    @ApiModelProperty(value = "自营厅级别	")
    private java.lang.String selfShopLevel;

    /**
     * 是否校园网点
     */
    @ApiModelProperty(value = "是否校园网点	")
    private java.lang.String isCampusShop;

    /**
     * 是否为厂商专卖店
     */
    @ApiModelProperty(value = "是否为厂商专卖店	")
    private java.lang.String isManufacturerShop;

    /**
     * 厂商渠道标识
     */
    @ApiModelProperty(value = "厂商渠道标识	")
    private java.lang.String manufacturerChannelId;

    /**
     * 是否IPHONE授权店
     */
    @ApiModelProperty(value = "是否IPHONE授权店	")
    private java.lang.String isIphoneAuthShop;

    /**
     * 销售点编码
     */
    @ApiModelProperty(value = "销售点编码")
    private java.lang.String shopCode;

    /**
     * 销售点名称
     */
    @ApiModelProperty(value = "销售点名称")
    private java.lang.String shopName;

    @ApiModelProperty(value = "销售点一级分类:" +
        "  实体渠道 110000 直销渠道 100000 电子渠道 120000 直销渠道 100000 电子渠道 120000 ")
    private java.lang.String shopPriType;

    /**
     * 销售点二级分类
     */
    @ApiModelProperty(value = "销售点二级分类	")
    private java.lang.String shopSecondType;

    /**
     * 销售点三级分类
     */
    @ApiModelProperty(value = "销售点三级分类	")
    private java.lang.String shopThirdType;

    /**
     * 是否结佣
     */
    @ApiModelProperty(value = "是否结佣	")
    private java.lang.String isCommission;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称	")
    private java.lang.String supplierName;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码	")
    private java.lang.String supplierCode;

    /**
     * 供应商状态
     */
    @ApiModelProperty(value = "供应商状态	")
    private java.lang.String supplierState;

    /**
     * 供应商税号
     */
    @ApiModelProperty(value = "供应商税号	")
    private java.lang.String supplierTaxCode;

    /**
     * 开户银行
     */
    @ApiModelProperty(value = "开户银行")
    private java.lang.String bank;

    /**
     * 公司账户
     */
    @ApiModelProperty(value = "公司账户")
    private java.lang.String companyAccount;

    /**
     * 账户名称
     */
    @ApiModelProperty(value = "账户名称")
    private java.lang.String accountName;

    /**
     * 商家归属县级经营主体编码
     */
    @ApiModelProperty(value = "商家归属县级经营主体编码")
    private java.lang.String parCountyBusiEntityCode;

    /**
     * 商家归属县级经营主体名称
     */
    @ApiModelProperty(value = "商家归属县级经营主体名称	")
    private java.lang.String parCountyBusiEntityName;

    /**
     * parCityBusiEntityName
     */
    @ApiModelProperty(value = "parCityBusiEntityName")
    private java.lang.String parCityBusiEntityName;

    /**
     * 商家归属本地网级经营主体编码
     */
    @ApiModelProperty(value = "商家归属本地网级经营主体编码")
    private java.lang.String parCityBusiEntityCode;

    /**
     * 商家归属省级经营主体名称
     */
    @ApiModelProperty(value = "商家归属省级经营主体名称")
    private java.lang.String parProvBusiEntityName;

    /**
     * 商家归属省级经营主体编码
     */
    @ApiModelProperty(value = "商家归属省级经营主体编码	")
    private java.lang.String parProvBusiEntityCode;

    /**
     * 商家归属全国级经营主体名称
     */
    @ApiModelProperty(value = "商家归属全国级经营主体名称	")
    private java.lang.String parCountryBusiEntityName;

    /**
     * 商家归属全国级经营主体编码
     */
    @ApiModelProperty(value = "商家归属全国级经营主体编码")
    private java.lang.String parCountryBusiEntityCode;

    /**
     * 销售点地址
     */
    @ApiModelProperty(value = "销售点地址	")
    private java.lang.String shopAddress;

    /**
     * 销售点归属县级经营主体编码
     */
    @ApiModelProperty(value = "销售点归属县级经营主体编码	")
    private java.lang.String shopCountyBusiEntityCode;

    /**
     * 销售点归属县级经营主体名称
     */
    @ApiModelProperty(value = "销售点归属县级经营主体名称")
    private java.lang.String shopCountyBusiEntityName;

    /**
     * 销售点归属本地网经营主体编码
     */
    @ApiModelProperty(value = "销售点归属本地网经营主体编码	")
    private java.lang.String shopCityBusiEntityCode;

    /**
     * 销售点归属本地网经营主体名称
     */
    @ApiModelProperty(value = "销售点归属本地网经营主体名称")
    private java.lang.String shopCityBusiEntityName;

    /**
     * 销售点归属省级经营主体编码
     */
    @ApiModelProperty(value = "销售点归属省级经营主体编码	")
    private java.lang.String shopProvBusiEntityCode;

    /**
     * 销售点归属省级经营主体名称
     */
    @ApiModelProperty(value = "销售点归属省级经营主体名称")
    private java.lang.String shopProvBusiEntityName;

    /**
     * 销售点归属省全国级经营主体编码
     */
    @ApiModelProperty(value = "销售点归属省全国级经营主体编码	")
    private java.lang.String shopCountryBusiEntityCode;

    /**
     * 销售点归属省全国级经营主体名称
     */
    @ApiModelProperty(value = "销售点归属省全国级经营主体名称")
    private java.lang.String shopCountryBusiEntityName;
}
