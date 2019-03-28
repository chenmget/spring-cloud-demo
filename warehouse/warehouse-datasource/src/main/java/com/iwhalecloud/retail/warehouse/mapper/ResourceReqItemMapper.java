package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.warehouse.entity.ResourceReqItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ResourceReqItemMapper
 * @author autoCreate
 */
@Mapper
public interface ResourceReqItemMapper extends BaseMapper<ResourceReqItem>{
    /**
     * 获取申请单项的数量的总和
     * @param requestId
     * @return
     */
    Integer getItemQuantity(@Param("requestId") String requestId);

    /**
     * 通过申请单的Id获取产品分类的名称
     * @param requestId
     * @return
     */
    List<String> getProductCatName(@Param("requestId") String requestId);
}