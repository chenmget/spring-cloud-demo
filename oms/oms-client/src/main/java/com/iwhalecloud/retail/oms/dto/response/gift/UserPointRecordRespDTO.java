package com.iwhalecloud.retail.oms.dto.response.gift;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserPointRecordRespDTO implements Serializable{
	
private static final long serialVersionUID = -3666659699538996671L;
	
    private Integer userId;
    private Integer srcType;
    private Integer totalPoint;
    private Integer changePoint;
    private String remark;
    private Date createDate;
}
