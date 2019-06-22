package com.iwhalecloud.retail.oms.dto.response.gift;

import com.iwhalecloud.retail.oms.dto.PageVO;
import lombok.Data;

import java.util.Date;

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
