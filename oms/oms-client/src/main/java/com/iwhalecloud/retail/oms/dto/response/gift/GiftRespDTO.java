package com.iwhalecloud.retail.oms.dto.response.gift;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class GiftRespDTO implements Serializable{

	private static final long serialVersionUID = -3883559699538996671L;
	
	private Long giftId;
    private String giftName;
    private Integer giftType;
    private Integer giftValue;
    private Integer gainType;
    private Integer needPointAmount;
    private Integer giftAmount;
    private Integer giftLeftAmount;
    private Integer giftExAmount;
    private Integer gainAmount;
    private Integer totalGainAmount;
    private Integer isDown;
    private Date downDate;
    private Date upDate;
    private Date effDate;
    private Date expDate;
    private Integer isDel;
    private Date createDate;
    private Integer createUser;
}
