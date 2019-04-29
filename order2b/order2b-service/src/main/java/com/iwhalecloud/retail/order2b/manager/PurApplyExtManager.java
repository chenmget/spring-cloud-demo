package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyExtReq;
import com.iwhalecloud.retail.order2b.entity.PurApplyExt;
import com.iwhalecloud.retail.order2b.mapper.PurApplyExtMapper;
import com.iwhalecloud.retail.system.common.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/29 16:28
 * @description
 */

@Component
public class PurApplyExtManager {

    @Resource
    private PurApplyExtMapper purApplyExtMapper;

    public int addPurApplyExtInfo(PurApplyExtReq req) {
        PurApplyExt purApplyExt = new PurApplyExt();
        BeanUtils.copyProperties(req, purApplyExt);
        purApplyExt.setCreateDate(DateUtils.currentSysTimeForDate());
        return purApplyExtMapper.insert(purApplyExt);
    }

    public int updatePurApplyExtInfo(PurApplyExtReq req) {
        PurApplyExt purApplyExt = new PurApplyExt();
        BeanUtils.copyProperties(req, purApplyExt);
        purApplyExt.setUpdateDate(DateUtils.currentSysTimeForDate());
        //修改条件
        UpdateWrapper<PurApplyExt> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("APPLY_ID", req.getApplyId());
        return purApplyExtMapper.update(purApplyExt, updateWrapper);
    }
}

