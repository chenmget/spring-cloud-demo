package com.iwhalecloud.retail.order2b.verificate;

import java.util.HashMap;
import java.util.Map;

public class OrderCheck {

    /**
     * 订单下单必填参数
     */
    public static Map builderOrderParams() {
        Map map = new HashMap();
        map.put("payType", "【必填】1微信支付  3线下付款");
        map.put("typeCode", "【必填】1:APP；2：微商城-普通单；3：微商城-商机单；4：其它");
        map.put("orderType", "【必填】 1订购");
        map.put("bindType", "【必填】商品种类，合约机，非合约机");
        map.put("userId", "【必填】分销商id");
        map.put("sourceType", "【必填】下单类型：购物车：GWCGM,立即购买:LJGM");
        map.put("addressId", "【必填】es_member_address");
        map.put("shipType", "【必填】 配发方式");
        return map;
    }
}
