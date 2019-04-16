package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.GoodsSaleOrderDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/4/13.
 */
public interface GoodSaleOrderService {

    public List<GoodsSaleOrderDTO> getGoodSaleNumByTime(String cacheKey);

}