package com.iwhalecloud.retail.warehouse.manager;

import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstItmsLimitSaveReq;
import com.iwhalecloud.retail.warehouse.entity.ResourceInstItmsLimit;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstItmsLimitMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ResourceInstItmsLimitManager {
    @Resource
    private ResourceInstItmsLimitMapper resourceInstItmsLimitMapper;

    /**
     * 添加一个 限额
     * @param req
     * @return
     */
    public Integer saveResourceInstItmsLimit(ResourceInstItmsLimitSaveReq req) {
        ResourceInstItmsLimit resourceInstItmsLimit = new ResourceInstItmsLimit();
        BeanUtils.copyProperties(req, resourceInstItmsLimit);
        return resourceInstItmsLimitMapper.insert(resourceInstItmsLimit);
    }

    /**
     * 根据商家ID 获取一个 限额
     * @param lanId
     * @return
     */
    public ResourceInstItmsLimit getResourceInstItmsLimit(String lanId) {
        return resourceInstItmsLimitMapper.selectById(lanId);
    }

    /**
     * 更新一个 限额
     * @param resourceInstItmsLimit
     * @return
     */
    public Integer updateResourceInstItmsLimit(ResourceInstItmsLimit resourceInstItmsLimit) {
        return resourceInstItmsLimitMapper.updateById(resourceInstItmsLimit);
    }
    
}
