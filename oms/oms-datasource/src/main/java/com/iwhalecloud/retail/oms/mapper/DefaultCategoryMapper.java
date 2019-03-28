package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.entity.DefaultCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @Class: DefaultCategoryMapper
 * @author autoCreate
 */
@Mapper
public interface DefaultCategoryMapper extends BaseMapper<DefaultCategory>{

    int updateBatchDefaultCatagory(@Param("list") List<DefaultCategory> list);

    int insertDefaultCatagory(DefaultCategory defaultCategory);

    List<DefaultCategory> queryDefaultCategorys(@Param("list")List<Long> lists);

}