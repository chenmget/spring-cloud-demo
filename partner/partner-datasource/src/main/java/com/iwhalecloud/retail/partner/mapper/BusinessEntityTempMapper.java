package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.partner.entity.BusinessEntityTemp;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author autoCreate
 * @Class: BusinessEntityTempMapper
 */
@Mapper
public interface BusinessEntityTempMapper extends BaseMapper<BusinessEntityTemp> {

    /**
     * 批量新增
     *
     * @param businessEntityTempList
     * @return
     */
//    Integer batchInsert(List<BusinessEntityTemp> businessEntityTempList);

    /**
     * 删除临时表的数据
     *
     * @param days 天数
     * @return
     */
    Integer deleteBusinessEntityTempData(String days);

}