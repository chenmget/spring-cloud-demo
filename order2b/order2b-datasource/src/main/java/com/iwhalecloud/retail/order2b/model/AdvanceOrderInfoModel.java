package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.entity.AdvanceOrder;
import lombok.Data;

import java.io.Serializable;

/**
 * 预售订单信息
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月04日
 */
@Data
public class AdvanceOrderInfoModel extends OrderInfoModel implements Serializable {
    private AdvanceOrder advanceOrder;
}
