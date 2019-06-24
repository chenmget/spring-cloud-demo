package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsSaleNumDTO;

import java.util.List;

/**
 * Created by Administrator on 2019/4/13.
 */
public interface GoodsSaleNumService {

    /**
     * 统计指定时间内商品销售数量
     *
     * @param cacheKey
     * @return 更新结果
     */
    ResultVO<List<GoodsSaleNumDTO>> getGoodsSaleOrder(String cacheKey);

    public ResultVO<Boolean> cleanCacheGoodSaleNum(String cacheKey);

    ResultVO<List<GoodsSaleNumDTO>> getProductSaleOrder(String key);

    public ResultVO<Boolean> cleanCacheProductSaleNum();

    ResultVO<List<GoodsSaleNumDTO>> queryProductSaleOrderByProductId(String productId);
}
