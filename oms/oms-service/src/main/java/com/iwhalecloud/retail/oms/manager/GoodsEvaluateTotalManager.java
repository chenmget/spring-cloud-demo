package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;
import com.iwhalecloud.retail.oms.entity.TGoodsEvaluateTotal;
import com.iwhalecloud.retail.oms.mapper.GoodsEvaluateTotalMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class GoodsEvaluateTotalManager {

    @Resource
    private GoodsEvaluateTotalMapper goodsEvaluateTotalMapper;

    public int insert(TGoodsEvaluateTotalDTO dto) {
        TGoodsEvaluateTotal total = new TGoodsEvaluateTotal();
        BeanUtils.copyProperties(dto, total);
        int t = goodsEvaluateTotalMapper.insert(total);
        return t;
    }

    public int modify(TGoodsEvaluateTotalDTO dto) {
        int t = goodsEvaluateTotalMapper.updateCountsByGoods(dto);
        return t;
    }

    public List<TGoodsEvaluateTotalDTO> select(TGoodsEvaluateTotalDTO dto) {

        List<TGoodsEvaluateTotalDTO> list = goodsEvaluateTotalMapper.selectCountsByGoods(dto);
        return list;
    }
}
