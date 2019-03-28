package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.warehouse.entity.ResourceInstStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ResourceInstStoreMapper
 * @author autoCreate
 */
@Mapper
public interface ResourceInstStoreMapper extends BaseMapper<ResourceInstStore>{

    List<String> getQuantityByMerchantId(@Param("mktResStoreId")String mktResStoreId, @Param("merchantId")String merchantId, @Param("statusList")List<String> statusList);

    Integer getProductQuantityByMerchant(@Param("mktResStoreId")String mktResStoreId, @Param("merchantId")String merchantId, @Param("productId")String productId, @Param("statusCd")String statusCd);

    Integer updateStock(@Param("mktResStoreId")String mktResStoreId, @Param("merchantId")String merchantId, @Param("productId")String productId, @Param("num")Long num);
}