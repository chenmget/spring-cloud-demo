package com.iwhalecloud.retail.goods.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods.entity.ProdSpecification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ProdSpecificationMapper
 * @author autoCreate
 */
@Mapper
public interface ProdSpecificationMapper extends BaseMapper<ProdSpecification>{

	/**
     * 获取所有规格
     * @param req
     * @return
     */
    public List<ProdSpecification> listSpec();


    /**
     * 修改规格
     * @param req
     * @return
     */
    public Integer updateSpec(ProdSpecification req);

    /**
     * 删除规格(删除prod_sepcification、prod_spec_value、prod_goods_spec)
     * @param idArray
     * @return
     */
    public Integer deleteSpec(String[] idArray);
}