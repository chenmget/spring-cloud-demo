package com.iwhalecloud.retail.oms.dto.resquest.gift;

import com.iwhalecloud.retail.oms.dto.PageVO;
import lombok.Data;

/**
 * @author hesw
 * @date 2018/10/26
 * @Description: 查询奖品传送对象
 */
@Data
public class ListGiftReqDTO extends PageVO {

    private static final long serialVersionUID = -3903559699538996671L;

    private Integer giftType;

}
