package com.iwhalecloud.retail.oms.dto.resquest.gift;

import lombok.Data;

import com.iwhalecloud.retail.oms.dto.PageVO;

/**
 * @author he.sw
 * @date 2018/10/16
 * * @Description: 用户积分流水集传送对象
 */
@Data
public class ListUserGiftExchangeReqDTO extends PageVO {

    private static final long serialVersionUID = -3903559699538556671L;

    private Long userId;

}
