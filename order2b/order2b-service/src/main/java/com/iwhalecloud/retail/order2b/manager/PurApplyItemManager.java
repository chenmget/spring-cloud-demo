package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.order2b.entity.PurApplyItem;
import com.iwhalecloud.retail.order2b.mapper.PurApplyItemMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/25 20:43
 * @description
 */

@Component
public class PurApplyItemManager {

    @Resource
    private PurApplyItemMapper purApplyItemMapper;

    /**
     * 通过采购申请单查询采购申请单项
     * @param applyId
     * @return
     */
    public List<PurApplyItem> getPurApplyItem(String applyId) {
        QueryWrapper<PurApplyItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id", applyId);
        return purApplyItemMapper.selectList(queryWrapper);
    }

    /**
     * 通过采购申请单查询采购申请单项
     * @param applyId
     * @return
     */
    public List<PurApplyItem> getDeliveryInfoByApplyID(String applyId) {
        QueryWrapper<PurApplyItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id", applyId);
        queryWrapper.eq("status_cd", "5");
        return purApplyItemMapper.selectList(queryWrapper);
    }
}

