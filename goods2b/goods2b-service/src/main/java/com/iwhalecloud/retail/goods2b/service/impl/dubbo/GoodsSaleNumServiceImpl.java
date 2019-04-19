package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsSaleNumDTO;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsForPageQueryResp;
import com.iwhalecloud.retail.goods2b.entity.Goods;
import com.iwhalecloud.retail.goods2b.manager.GoodsManager;
import com.iwhalecloud.retail.goods2b.manager.ProdFileManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsSaleNumService;
import com.iwhalecloud.retail.order2b.dto.model.order.GoodsSaleOrderDTO;
import com.iwhalecloud.retail.order2b.service.GoodSaleOrderService;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/13.
 */
@Slf4j
@Component
@Service
public class GoodsSaleNumServiceImpl implements GoodsSaleNumService {
    @Reference
    private CommonRegionService commonRegionService;

    @Reference(timeout = 60000)
    private GoodSaleOrderService goodSaleOrderService;

    @Autowired
    private GoodsManager goodsManager;

    @Autowired
    private ProdFileManager prodFileManager;

    @Override
    @Cacheable(value = GoodsConst.CACHE_NAME_GOODS_SALE_ORDER, key = "#cacheKey")
    public ResultVO<List<GoodsSaleNumDTO>> getGoodsSaleOrder(String cacheKey) {
        List<GoodsSaleNumDTO> list = new ArrayList<>();

        List<GoodsSaleOrderDTO> goodsSaleOrderDTOs = goodSaleOrderService.getGoodSaleNumByTime(cacheKey);

        if(!CollectionUtils.isEmpty(goodsSaleOrderDTOs)){
            List<String> goodsIds = new ArrayList<>();
            for(GoodsSaleOrderDTO goodsSaleOrderDTO:goodsSaleOrderDTOs){
                goodsIds.add(goodsSaleOrderDTO.getGoodsId());
            }

            log.info("GoodSaleOrderServiceImpl goodsManager.listGoods  goodsIds={}:",goodsIds);
            List<Goods> goodsList =  goodsManager.listGoods(goodsIds);
            if(!CollectionUtils.isEmpty(goodsList)){
                for(GoodsSaleOrderDTO goodsSaleOrderDTO:goodsSaleOrderDTOs){
                    for(Goods goods: goodsList){
                        if(goodsSaleOrderDTO.getGoodsId().equals(goods.getGoodsId())){
                            goodsSaleOrderDTO.setGoodsName(goods.getGoodsName());
                        }
                    }
                    GoodsSaleNumDTO goodsSaleNumDTO = new GoodsSaleNumDTO();
                    BeanUtils.copyProperties(goodsSaleOrderDTO,goodsSaleNumDTO);
                    list.add(goodsSaleNumDTO);
                }
            }

            setGoodsImageUrl(list,goodsIds);
        }

        return ResultVO.success(list);
    }

    @Override
    @CacheEvict(value = GoodsConst.CACHE_NAME_GOODS_SALE_ORDER,allEntries = true,beforeInvocation = true, key = "#cacheKey")
    public ResultVO<Boolean> cleanCacheGoodSaleNum(String cacheKey) {
        log.info("GoodSaleOrderServiceImpl.cleanCacheGoodSaleNum clean cacheKey = {}!!!",cacheKey);
        return ResultVO.success(true);
    }

    private void setGoodsImageUrl(List<GoodsSaleNumDTO> goodsDTOList, List<String> goodsIdList) {
        // 查询商品默认图片
        List<ProdFileDTO> prodFileDTOList = prodFileManager.queryGoodsImage(goodsIdList, FileConst.SubType.THUMBNAILS_SUB);

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(prodFileDTOList)) {
            for (GoodsSaleNumDTO goodsSaleNumDTO : goodsDTOList) {
                // 设置缩略图
                for (ProdFileDTO prodFileDTO : prodFileDTOList) {
                    if (goodsSaleNumDTO.getGoodsId().equals(prodFileDTO.getTargetId())) {
                        goodsSaleNumDTO.setImageUrl(prodFileDTO.getFileUrl());
                        break;
                    }
                }
            }
        }
    }
}
