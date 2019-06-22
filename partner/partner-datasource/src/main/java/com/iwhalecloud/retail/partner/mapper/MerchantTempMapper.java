package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.partner.entity.MerchantTemp;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author autoCreate
 * @Class: MerchantTempMapper
 */
@Mapper
public interface MerchantTempMapper extends BaseMapper<MerchantTemp> {

    /**
     * 批量新增
     *
     * @param merchantTempList
     * @return
     */
//    Integer batchInsert(List<MerchantTemp> merchantTempList);

    /**
     * 删除临时表的数据
     *
     * @param days
     * @return
     */
    Integer deleteMerchantTempData(String days);


}