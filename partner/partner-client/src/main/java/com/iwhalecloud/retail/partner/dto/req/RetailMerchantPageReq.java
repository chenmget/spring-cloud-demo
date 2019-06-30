package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("根据条件查找 零售商类型的商家信息 分页列表")
public class RetailMerchantPageReq extends PageVO {

    /*
    （par_merchant表字段）商家名称、店中商编码、店中商经营主体名称、渠道视图状态、
      销售点名称、销售点编码、区域（分公司、市县、分局/县部门、营销中心/支局）、渠道大类、渠道小类、渠道子类。
    （sys_user表字段）系统账号、系统状态
    （par_invoice表字段）营业执照失效期
     (prod_merchant_tag_rel表字段）分组标签
     */

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "渠道状态:" +
            " 有效1000  主动暂停1001  异常暂停1002 无效1100 终止1101 退出1102 未生效1200" +
            " 已归档1300 预退出8922 冻结8923 主动暂停1001  异常暂停1002   无效1100  终止1101  " +
            " 退出1102  未生效1200  已归档1300 预退出8922  冻结8923 ")
    private String status;

    @ApiModelProperty(value = "商家所属经营主体	")
    private String businessEntityName;

    @ApiModelProperty(value = "地市")
    private java.lang.String lanId;

    @ApiModelProperty(value = "市县")
    private java.lang.String city;

    @ApiModelProperty(value = "地市ID集合")
    private List<String> lanIdList;

    @ApiModelProperty(value = "市县ID集合")
    private List<String> cityList;

    @ApiModelProperty(value = "分局/县部门")
    private java.lang.String subBureau;

    @ApiModelProperty(value = "营销中心/支局")
    private java.lang.String marketCenter;

    /* 市县、分局/县部门 合并为一个字段 经营单元（对应 组织表的 第3级组织部门）*/
    @ApiModelProperty(value = "经营单元(3级组织部门)")
    private java.lang.String OrgIdWithLevel3;

    /* 营销中心/支局 字段 改为 查  组织表的 第4级组织部门 */
    @ApiModelProperty(value = "营销中心/支局（4级组织部）")
    private java.lang.String OrgIdWithLevel4;


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


    /**  下面的是 非 par_merchant 表字段  **/

    // sys_user表字段
    @ApiModelProperty(value = "商家对应系统账号")
    private String loginName;

    @ApiModelProperty(value = "商家对应系统状态:   1有效、 0 禁用   2：失效(删除)  3:锁住（密码错误次数超限 等等）")
    private Integer userStatus;


    // par_invoice表字段
//    @ApiModelProperty(value = "营业执照到期日期")
//    private Date busiLicenceExpDate;

    @ApiModelProperty(value = "营业执照到期日期 开始时间 （跟结束时间一起 查 营业执照到期日期 在这个区间的")
    private Date startExpireDate;

    @ApiModelProperty(value = "营业执照到期日期 结束时间")
    private Date endExpireDate;


    // prod_merchant_tag_rel 表字段
    @ApiModelProperty(value = "标签ID")
    private String tagId;

    @ApiModelProperty(value = "专票状态 1 未录入/2 审核通过/3 审核中/4 审核不通过/5 已过期")
    private String vatInvoiceStatus;

}
