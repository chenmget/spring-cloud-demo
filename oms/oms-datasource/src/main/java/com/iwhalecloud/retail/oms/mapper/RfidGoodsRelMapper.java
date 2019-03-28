package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.TRfidGoodsRelDTO;
import com.iwhalecloud.retail.oms.entity.TRfidGoodsRel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RfidGoodsRelMapper extends BaseMapper<TRfidGoodsRel> {

    int updateRfidGoodsRel(TRfidGoodsRelDTO tRfidGoodsRelDTO);

    List<TRfidGoodsRelDTO> selectRfidGoodsRel(TRfidGoodsRelDTO tRfidGoodsRelDTO);
}
