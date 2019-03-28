package com.iwhalecloud.retail.oms.dto.response.gift;

import java.util.Date;

import lombok.Data;

import com.iwhalecloud.retail.oms.dto.PageVO;

@Data
public class UserGiftExchangeRespDTO extends PageVO{

  private static final long serialVersionUID = -3773999699538556671L;

  private Integer userId;
  private Integer giftId;
  private Date createDate;
  private String giftName;
  private Integer needPointAmount;
  private Integer giftType;
  private Integer gainAmount;
  private Integer totalPoint;

}
