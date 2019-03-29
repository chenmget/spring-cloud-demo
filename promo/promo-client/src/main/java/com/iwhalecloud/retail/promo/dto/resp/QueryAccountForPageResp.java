package com.iwhalecloud.retail.promo.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴良勇
 * @date 2019/3/26 15:15
 */
@Data
public class QueryAccountForPageResp implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。
     */
    @ApiModelProperty(value = "为每个账户生成的唯一编号，只具有逻辑上的含义，没有物理意义。每个账户标识生成之后，账户标识在整个服务提供有效期内保持不变。")
    private String acctId;

    /**
     * 账户注册的名称
     */
    @ApiModelProperty(value = "账户注册的名称")
    private String acctName;

    /**
     * 10余额账户、20返利账户、30价保账户
     */
    @ApiModelProperty(value = "10余额账户、20返利账户、30价保账户")
    private String acctType;

    /**
     * 记录商家唯一标识，作为外键
     */
    @ApiModelProperty(value = "记录商家唯一标识，作为外键")
    private String custId;

    /**
     * 生效时间
     */
    @ApiModelProperty(value = "生效时间")
    private java.util.Date effDate;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    private java.util.Date expDate;

    /**
     * 当前帐户的状态; 1000 有效，1100 失效
     */
    @ApiModelProperty(value = "当前帐户的状态; 1000 有效，1100 失效")
    private Long statusCd;

    /**
     * 账户状态变更的时间。
     */
    @ApiModelProperty(value = "账户状态变更的时间。")
    private java.util.Date statusDate;

    /**
     * 记录创建的员工。
     */
    @ApiModelProperty(value = "记录创建的员工。")
    private String createStaff;

    /**
     * 记录创建的时间。
     */
    @ApiModelProperty(value = "记录创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录修改的员工。
     */
    @ApiModelProperty(value = "记录修改的员工。")
    private String updateStaff;

    /**
     * 记录修改的时间。
     */
    @ApiModelProperty(value = "记录修改的时间。")
    private java.util.Date updateDate;

    /**
     * 记录备注信息。
     */
    @ApiModelProperty(value = "记录备注信息。")
    private String remark;

}
