package com.iwhalecloud.retail.goods.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods.entity.ProdSpecValues;

/**
 * @Class: ProdSpecValuesMapper
 * @author autoCreate
 */
@Mapper
public interface ProdSpecValuesMapper extends BaseMapper<ProdSpecValues>{

	/**
     * 获取规格值
     * @param specValueId
     * @return
     */
	ProdSpecValues getSpecValues(String specValueId);


    /**
     * 删除规格值
     * @param idArray
     * @return
     */
    Integer deleteSpecValues(String[] idArray);
    
    /**
     * 批量添加规格值
     * @param valuesList
     * @return
     */
    Integer insertBatchSpecValues(List<ProdSpecValues> valuesList);
}