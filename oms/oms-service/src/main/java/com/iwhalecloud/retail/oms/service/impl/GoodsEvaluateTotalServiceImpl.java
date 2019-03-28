package com.iwhalecloud.retail.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iwhalecloud.retail.oms.consts.GoodsEvaluateConsts;
import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;
import com.iwhalecloud.retail.oms.manager.GoodsEvaluateTotalManager;
import com.iwhalecloud.retail.oms.service.GoodsEvaluateTotalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class GoodsEvaluateTotalServiceImpl implements GoodsEvaluateTotalService {

    @Autowired
    private GoodsEvaluateTotalManager goodsEvaluateTotalManager;

    @Override
    public int addGoodsEvaluate(TGoodsEvaluateTotalDTO t) {
        if (StringUtils.isEmpty(t.getEvaluateText())) {
            return 0;
        }
        for (GoodsEvaluateConsts ev : GoodsEvaluateConsts.values()) {
            if (t.getEvaluateText().contains(ev.getCodeName())) {
                t.setTagsName(ev.getCodeName());
                t.setCounts(1);
                t.setTagsId(ev.getCode());
                break;
            }
        }

        if (t.getTagsId() == null) {
            return 0;
        }
        return goodsEvaluateTotalManager.insert(t);
    }

    @Override
    public int modifyGoodsEvaluate(TGoodsEvaluateTotalDTO t) {
        LambdaQueryWrapper lambdaQueryWrapper=new LambdaQueryWrapper();
        int total= goodsEvaluateTotalManager.modify(t);
        return total;
    }

    @Override
    public List<TGoodsEvaluateTotalDTO> selectGoodsEvaluate(TGoodsEvaluateTotalDTO t) {
        return goodsEvaluateTotalManager.select(t);
    }
}
