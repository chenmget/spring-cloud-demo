package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.entity.MktResCouponTask;
import com.iwhalecloud.retail.rights.mapper.MktResCouponTaskMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class MktResCouponTaskManager extends ServiceImpl<MktResCouponTaskMapper,MktResCouponTask> {

    @Resource
    private MktResCouponTaskMapper mktResCouponTaskMapper;

    /**
     * 新增优惠券发放任务
     * @param mktResCouponTask
     * @return
     */
    public Integer insetMktResCouponTask(MktResCouponTask mktResCouponTask){
        return mktResCouponTaskMapper.insert(mktResCouponTask);
    }

    /**
     * 查询待处理的前100条发放任务
     * @return
     */
    public Page<MktResCouponTask> queryCouponTaskPage(){
        Page<MktResCouponTask> page = new Page<>(1, 100);
        return mktResCouponTaskMapper.queryCouponTaskPage(page);
    }

    /**
     * 跟新优惠券发放记录
     * @param mktResCouponTask
     * @return
     */
    public Integer updateMktResCouponTask(MktResCouponTask mktResCouponTask){
        mktResCouponTask.setUpdateDate(new Date());
        return mktResCouponTaskMapper.updateById(mktResCouponTask);
    }
}
