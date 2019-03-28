package com.iwhalecloud.retail.oms.dto.resquest.gift;

import java.io.Serializable;

import lombok.Data;

/**
 * @author he.sw
 * @date 2018/10/16
 * * @Description: 用户奖品兑换传送对象
 */
@Data
public class UserGiftExchangeReqDTO implements Serializable {

    private static final long serialVersionUID = -3903559699538556671L;
    private Long userId;
    private Long giftId;
    private Integer gainAmount;

}
