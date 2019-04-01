package com.iwhalecloud.retail.warehouse.manager;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsProductRelEditReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsProductRelService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.ProductQuantityItem;
import com.iwhalecloud.retail.warehouse.dto.request.UpdateStockReq;
import com.iwhalecloud.retail.warehouse.entity.ResourceInstStore;
import com.iwhalecloud.retail.warehouse.mapper.ResouceStoreMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstStoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class ResourceInstStoreManager{

    @Resource
    private ResourceInstStoreMapper resourceInstStoreMapper;

    @Resource
    private ResouceStoreMapper resouceStoreMapper;

    @Reference
    private GoodsProductRelService goodsProductRelService;

    public ResourceInstStoreDTO getStore(String merchantId, String productId, String statusCd, String storeId){
        QueryWrapper<ResourceInstStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ResourceInstStore.FieldNames.statusCd.getTableFieldName(), statusCd);
        queryWrapper.eq(ResourceInstStore.FieldNames.mktResId.getTableFieldName(), productId);
        queryWrapper.eq(ResourceInstStore.FieldNames.merchantId.getTableFieldName(), merchantId);
        queryWrapper.eq(ResourceInstStore.FieldNames.mktResStoreId.getTableFieldName(), storeId);
        List<ResourceInstStore> resourceInstStoreList = this.resourceInstStoreMapper.selectList(queryWrapper);
        ResourceInstStoreDTO dto = new ResourceInstStoreDTO();
        if(CollectionUtils.isNotEmpty(resourceInstStoreList)){
            ResourceInstStore resourceInstStore = resourceInstStoreList.get(0);
            BeanUtils.copyProperties(resourceInstStore,dto);
            return dto;
        }else{
            return null;
        }
    }

    public List<ResourceInstStoreDTO> listStore(String merchantId, List<String> productIds, String statusCd){
        QueryWrapper<ResourceInstStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ResourceInstStore.FieldNames.statusCd.getTableFieldName(), statusCd);
        queryWrapper.in(ResourceInstStore.FieldNames.mktResId.getTableFieldName(), productIds);
        queryWrapper.eq(ResourceInstStore.FieldNames.merchantId.getTableFieldName(), merchantId);
        List<ResourceInstStore> resourceInstStoreList = this.resourceInstStoreMapper.selectList(queryWrapper);
        List<ResourceInstStoreDTO> resourceInstStoreDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resourceInstStoreList)) {
            for (ResourceInstStore resourceInstStore : resourceInstStoreList) {
                ResourceInstStoreDTO dto = new ResourceInstStoreDTO();
                BeanUtils.copyProperties(resourceInstStore,dto );
                resourceInstStoreDTOList.add(dto);
            }
        }
        return resourceInstStoreDTOList;
    }

    public Integer getQuantityByMerchantId(String merchantId, List<String> statusList){
        ResouceStoreDTO resouceStoreDTO = resouceStoreMapper.getStore(merchantId, ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        List<String> list = resourceInstStoreMapper.getQuantityByMerchantId(resouceStoreDTO.getMktResStoreId(), merchantId, statusList);
        Integer number = CollectionUtils.isEmpty(list) ? 0 : list.size();
        return number;
    }

    public Integer getProductQuantityByMerchant(String merchantId, String productId, String statusCd){
        ResouceStoreDTO resouceStoreDTO = resouceStoreMapper.getStore(merchantId, ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
        Integer d = resourceInstStoreMapper.getProductQuantityByMerchant(resouceStoreDTO.getMktResStoreId(), merchantId, productId, statusCd);
        return d;
    }

    /**
     * 更新库存
     * @param resourceInstStoreDTO
     * @return
     */
    public Integer updateResourceInstStore(ResourceInstStoreDTO resourceInstStoreDTO){
        //Step1:当前商户、当前商品、当前仓库的串码实例是否存在
        //step2:存在，修改库存数量
        //step3:不存在，插入数据
        QueryWrapper<ResourceInstStore> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ResourceInstStore.FieldNames.merchantId.getTableFieldName(), resourceInstStoreDTO.getMerchantId());
        queryWrapper.eq(ResourceInstStore.FieldNames.mktResStoreId.getTableFieldName(), resourceInstStoreDTO.getMktResStoreId());
        queryWrapper.eq(ResourceInstStore.FieldNames.mktResId.getTableFieldName(), resourceInstStoreDTO.getMktResId());
        ResourceInstStore resourceInstStore = resourceInstStoreMapper.selectOne(queryWrapper);
        log.info("ResourceInstStoreManager.updateResourceInstStore selectOne req={}, resp={}", JSON.toJSONString(resourceInstStoreDTO),JSON.toJSONString(resourceInstStore));
        if(null != resourceInstStore) {
            Long quantity = 0L;
            Long onwayQuantity = 0L;

            // 变动库存、在途库存
            Boolean quantityAddFlag = resourceInstStoreDTO.getQuantityAddFlag();
            Long changeQuantity = resourceInstStoreDTO.getQuantity() == null ? 0L : resourceInstStoreDTO.getQuantity();
            Long changeOnwayQuantity = resourceInstStoreDTO.getOnwayQuantity() == null ? 0L : resourceInstStoreDTO.getOnwayQuantity();

            // 数据库中已有库存、在途库存
            Long exixtsQuantity = resourceInstStore.getQuantity() == null ? 0L : resourceInstStore.getQuantity();
            Long exixtsOnwayQuantity = resourceInstStore.getOnwayQuantity() == null ? 0L : resourceInstStore.getOnwayQuantity();
            if (null == quantityAddFlag) {
                // 不处理
                quantity = resourceInstStore.getQuantity();
            }else if (resourceInstStoreDTO.getQuantityAddFlag()) {
                quantity = exixtsQuantity + changeQuantity;
            }else if(!resourceInstStoreDTO.getQuantityAddFlag()){
                quantity = exixtsQuantity - changeQuantity;
            }

            Boolean onwayQuantityAddFlag = resourceInstStoreDTO.getOnwayQuantityAddFlag();
            if (null == onwayQuantityAddFlag) {
                // 不处理
                onwayQuantity = resourceInstStore.getOnwayQuantity();
            }else if (resourceInstStoreDTO.getOnwayQuantityAddFlag()) {
                onwayQuantity = exixtsOnwayQuantity + changeOnwayQuantity;
            }else if(!resourceInstStoreDTO.getOnwayQuantityAddFlag()){
                onwayQuantity = exixtsOnwayQuantity - changeOnwayQuantity;
            }

            // 无库存时通知商品中心
            if (Long.compare(quantity, 0) < 1) {
                try {
                    GoodsProductRelEditReq goodsProductRelEditReq = new GoodsProductRelEditReq();
                    goodsProductRelEditReq.setGoodsId(resourceInstStoreDTO.getMerchantId());
                    goodsProductRelEditReq.setProductId(resourceInstStoreDTO.getMktResId());
                    goodsProductRelEditReq.setIsHaveStock(false);
                    goodsProductRelService.updateIsHaveStock(goodsProductRelEditReq);
                } catch (Exception ex) {
                    log.error("通知商品中心异常", ex);
                }

                return 0;
            }

            ResourceInstStore updateResourceInstStore = new ResourceInstStore();
            updateResourceInstStore.setQuantity(quantity);
            updateResourceInstStore.setOnwayQuantity(onwayQuantity);
            updateResourceInstStore.setStatusDate(new Date());
            UpdateWrapper<ResourceInstStore> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq(ResourceInstStore.FieldNames.mktResInstStoreId.getTableFieldName(), resourceInstStore.getMktResInstStoreId());
            updateWrapper.eq(ResourceInstStore.FieldNames.mktResStoreId.getTableFieldName(), resourceInstStore.getMktResStoreId());
            return resourceInstStoreMapper.update(updateResourceInstStore, updateWrapper);
        }else{
            Date now = new Date();
            ResourceInstStore addResourceInstStore = new ResourceInstStore();
            BeanUtils.copyProperties(resourceInstStoreDTO, addResourceInstStore);
            addResourceInstStore.setUpdateDate(now);
            addResourceInstStore.setStatusDate(now);
            addResourceInstStore.setCreateDate(now);
            addResourceInstStore.setStatusCd(ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode());
            return resourceInstStoreMapper.insert(addResourceInstStore);
        }
    }

    /**
     * 修改串码实例库在途数量
     * @param req
     * @return
     */
    public int updateStock(UpdateStockReq req){
        int i = 0;
        for(ProductQuantityItem item : req.getItemList()){
            ResouceStoreDTO resouceStoreDTO = resouceStoreMapper.getStore(req.getMerchantId(), ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
            i = i + this.resourceInstStoreMapper.updateStock(resouceStoreDTO.getMktResStoreId(), req.getMerchantId(), item.getProductId(), item.getNum());
        }
        return i;
    }
}
