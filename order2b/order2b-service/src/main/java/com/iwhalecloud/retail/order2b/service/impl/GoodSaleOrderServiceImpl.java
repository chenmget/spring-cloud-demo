package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsIdListReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsService;
import com.iwhalecloud.retail.order2b.dto.model.order.GoodsSaleOrderDTO;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.service.GoodSaleOrderService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/4/13.
 */

@Slf4j
@Component("goodSaleOrderService")
@Service
public class GoodSaleOrderServiceImpl implements GoodSaleOrderService {
    @Autowired
    private OrderManager orderManager;

    @Reference
    private CommonRegionService commonRegionService;

//    @Reference
//    private GoodsService goodsService;

    @Override
    public List<GoodsSaleOrderDTO> getGoodSaleNumByTime(String cacheKey) {
        Date beginTime = new Date();
        if(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_7.equals(cacheKey)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, - 8);
            c.set(Calendar.HOUR_OF_DAY, 24);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            beginTime = c.getTime();
        }else if(GoodsConst.CACHE_KEY_GOODS_SALE_ORDER_30.equals(cacheKey)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, - 31);
            c.set(Calendar.HOUR_OF_DAY, 24);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            beginTime = c.getTime();
        }
        List<GoodsSaleOrderDTO> list = new ArrayList<>();
        CommonRegionListReq req = new CommonRegionListReq();
        List<CommonRegionDTO> commonRegionDTOs = new ArrayList<>();
        ResultVO<List<CommonRegionDTO>> resultVO = commonRegionService.listCommonRegion(req);
        if(resultVO.isSuccess() && null!=resultVO.getResultData()){
            commonRegionDTOs = resultVO.getResultData();
        }
        if(!CollectionUtils.isEmpty(commonRegionDTOs)) {
            for (CommonRegionDTO commonRegionDTO : commonRegionDTOs) {
                String lanId = commonRegionDTO.getRegionId();
                log.info("GoodSaleOrderServiceImpl orderManager getGoodsSaleNumByTime  beginTime={},lanId = {},key = {}",beginTime,lanId,cacheKey);
                List<GoodsSaleOrderDTO> goodsSaleOrderDTOs = orderManager.getGoodsSaleNumByTime(beginTime, lanId);
                //循环相同的商品增加订购数。如果不存在list的商品 临时添加到buGoodsSaleOrderDTOs ，再汇总到list
                List<GoodsSaleOrderDTO> buGoodsSaleOrderDTOs = new ArrayList<>();
                if(!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(goodsSaleOrderDTOs)){

                    for(GoodsSaleOrderDTO goodsSaleOrderDTO:goodsSaleOrderDTOs){
                        boolean flag = true;
                        for(GoodsSaleOrderDTO allgoodsSaleOrderDTO : list){
                            if(goodsSaleOrderDTO.getProductId().equals(allgoodsSaleOrderDTO.getProductId())){
                                allgoodsSaleOrderDTO.setSaleNum(goodsSaleOrderDTO.getSaleNum()+allgoodsSaleOrderDTO.getSaleNum());
                                flag = false;
                                break;
                            }
                        }
                        if(flag){
                            buGoodsSaleOrderDTOs.add(goodsSaleOrderDTO);
                        }
                    }
                    if(!CollectionUtils.isEmpty(buGoodsSaleOrderDTOs)){
                        list.addAll(buGoodsSaleOrderDTOs);
                    }
                }else if(CollectionUtils.isEmpty(list)){
                    list.addAll(goodsSaleOrderDTOs);
                }
            }
        }

        return list;
    }
}
