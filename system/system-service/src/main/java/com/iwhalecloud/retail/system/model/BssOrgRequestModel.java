package com.iwhalecloud.retail.system.model;

import lombok.Data;

import java.util.Date;

/**
 * bss组织信息同步请求体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月07日
 */
@Data
public class BssOrgRequestModel {

    private Date statusDate;

    private String lanId;

    private String actType;

    private String orgId;

    private String orgCode;

    private Date createDate;

    private String regionId;

    private String updateStaff;

    private String parentOrgId;

    private String orgName;

    private String createStaff;

    private Date updateDate;

    private String saleBoxCode;

    private String statusCd;

    private Long orgLevel;
}
