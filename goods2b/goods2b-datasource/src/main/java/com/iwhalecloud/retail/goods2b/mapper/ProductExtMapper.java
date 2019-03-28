package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.ProductExt;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ProductExtMapper
 * @author autoCreate
 */
@Mapper
public interface ProductExtMapper extends BaseMapper<ProductExt>{

    /**
     * 获取所有产品扩展参数
     * @return
     */
    public List<ProductExt> listAll();
}