package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@Data
@ApiModel(value = "厂商注册请求参数")
public class ManufacturerResistReq implements Serializable {

    //商户信息
    @NotEmpty(message = "公司名称不能为空")
    @ApiModelProperty(value = "公司名称")
    private String merchantName;


    @NotEmpty(message = "地址不能为空")
    @ApiModelProperty(value = "公司地址")
    private String address;

    @NotEmpty(message = "法人姓名不能为空")
    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;

    //新增字段，品牌逗号分隔
    @NotEmpty(message = "经营品牌不能为空")
    @ApiModelProperty(value = "经营品牌")
    private String brand;


    //用户基本信息 sys-user
    /**
     * 登陆用户名
     */

    @NotEmpty(message = "账号名不能为空")
    @ApiModelProperty(value = "账号名")
    private String loginName;

    /**
     * 登陆密码
     */
    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(value = "密码")
    private String loginPwd;

    /**
     * 状态   1有效、 0 失效  2：其他状态
     */
    @ApiModelProperty(value = "状态   1有效、 0 失效  2：其他状态")
    private Integer statusCd;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String userName;


    /**
     * 1超级管理员 2普通管理员  3零售商(门店、店中商)  4省包供应商  5地包供应商
     * 6 代理商店员  7经营主体  8厂商
     */
    @ApiModelProperty(value = "1超级管理员 2普通管理员  3零售商(门店、店中商)  4省包供应商  5地包供应商  " +
            " 6 代理商店员  7经营主体  8厂商 ")
    private Integer userFounder;

    /**
     * 关联代理商ID  或 供应商ID
     */
    @ApiModelProperty(value = "关联代理商ID  或 供应商ID")
    private String relCode;

    /**
     * 用户电话号码
     */
    @ApiModelProperty(value = "用户电话号码")
    private String phoneNo;

}
