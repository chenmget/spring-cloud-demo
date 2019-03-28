package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;
import com.iwhalecloud.retail.oms.entity.TGoodsEvaluateTotal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsEvaluateTotalMapper extends BaseMapper<TGoodsEvaluateTotal> {


    int updateCountsByGoods(TGoodsEvaluateTotalDTO tGoodsEvaluateTotalDTO);

    List<TGoodsEvaluateTotalDTO> selectCountsByGoods(TGoodsEvaluateTotalDTO t);
}
