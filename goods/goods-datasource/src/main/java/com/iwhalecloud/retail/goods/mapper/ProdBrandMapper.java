package com.iwhalecloud.retail.goods.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods.entity.ProdBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ProdBrandMapper
 * @author autoCreate
 */
@Mapper
public interface ProdBrandMapper extends BaseMapper<ProdBrand>{

	/**
     * 根据主键删除
     * @param brandId
     * @return
     */
    public int deleteByPrimaryKey(String brandId);
    
    /**
     * 根据主键查询
     * @param brandId
     * @return
     */
    public ProdBrand selectByPrimaryKey(String brandId);
    
    /**
     * 选择插入
     * @param record
     * @return
     */
    public int insertSelective(ProdBrand record);
    
    /**
     * 全量插入
     * @param record
     * @return
     */
    public int insert(ProdBrand record);
    
    
    /**
     * 部分更新
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(ProdBrand record);
    
    /**
     * 全量更新
     * @param record
     * @return
     */
    public int updateByPrimaryKey(ProdBrand record);
    
    /**
     * 查询全部
     * @return
     */
    public List<ProdBrand> listAll();
}