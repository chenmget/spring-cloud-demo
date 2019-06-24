package com.iwhalecloud.retail.rights.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.entity.MktResCouponTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: MktResCouponTaskMapper
 * @author autoCreate
 */
@Mapper
public interface MktResCouponTaskMapper extends BaseMapper<MktResCouponTask>{

    /**
     * 查询前100条优惠券发放任务
     * @param page
     * @return
     */
    Page<MktResCouponTask> queryCouponTaskPage(Page<MktResCouponTask> page);

}