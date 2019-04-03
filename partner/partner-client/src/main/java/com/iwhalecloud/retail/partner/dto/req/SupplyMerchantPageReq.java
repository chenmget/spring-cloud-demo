package com.iwhalecloud.retail.partner.dto.req;


import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("根据条件查找 供应商类型的商家信息 分页列表")
public class SupplyMerchantPageReq extends PageVO {

    //（par_merchant表字段）商家名称、商家渠道视图编码、商家渠道视图经营主体、渠道视图状态、
    //（sys_user表字段）系统账号、系统状态
    //（par_merchant_account表字段）翼支付收款账号、支付宝收款账号、
    //（par_invoice表字段）营业执照号、税号、公司账号、营业执照失效期

    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    @ApiModelProperty(value = "商家类型（这里只能选 2或3:  1 厂商    2 地包商    3 省包商   4 零售商")
    private java.lang.String merchantType;

    @ApiModelProperty(value = "渠道状态:" +
            " 有效1000  主动暂停1001  异常暂停1002 无效1100 终止1101 退出1102 未生效1200" +
            " 已归档1300 预退出8922 冻结8923 主动暂停1001  异常暂停1002   无效1100  终止1101  " +
            " 退出1102  未生效1200  已归档1300 预退出8922  冻结8923 ")
    private String status;

    @ApiModelProperty(value = "商家所属经营主体	")
    private String businessEntityName;


    /**  下面的是 非 par_merchant 表字段  **/

    // sys_user表字段
    @ApiModelProperty(value = "商家对应系统账号")
    private String loginName;

    @ApiModelProperty(value = "商家对应系统状态:   1有效、 0 禁用   2：失效(删除)  3:锁住（密码错误次数超限 等等）")
    private Integer userStatus;


    // par_invoice表字段
    @ApiModelProperty(value = "纳税人识别号（税号）")
    private String taxCode;

    @ApiModelProperty(value = "营业执照号")
    private String busiLicenceCode;

    @ApiModelProperty(value = "营业执照到期日期（只传这个会查出 到期日期  大于此日期的 数据）")
    private Date busiLicenceExpDate;

    @ApiModelProperty(value = "营业执照到期日期 开始时间 （跟结束时间一起 查 营业执照到期日期 在这个区间的")
    private Date startExpireDate;

    @ApiModelProperty(value = "营业执照到期日期 结束时间")
    private Date endExpireDate;

    @ApiModelProperty(value = "银行账号（公司账号）")
    private String registerBankAcct;

    // par_merchant_account表字段
    @ApiModelProperty(value = "收款账号（翼支付、支付宝、微信、银行账号")
    private String account;


}
