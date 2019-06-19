package com.iwhalecloud.retail.system.dto;

import lombok.Data;

/**
 * sys_common_org表
 * @author Administrator
 *
 */
@Data
public class SysCommonOrgResp implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orgId; 
    private String parentOrgId;
    private String parentOrgName;
    private String orgCode; 
    private String orgName ;
    private String lanId ;
    private String lan ;
    private String regionId; 
    private String region ;
    private String orgLevel; 
    private String statusDate; 
    private String statusCd; 
    private String remark; 
    private String createStaff; 
    private String createDate ;
    private String updateStaff ;
    private String updateDate;
    private String pathCode;

}
