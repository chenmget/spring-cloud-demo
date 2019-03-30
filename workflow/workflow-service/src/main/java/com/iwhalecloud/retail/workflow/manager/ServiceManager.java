package com.iwhalecloud.retail.workflow.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.entity.Service;
import com.iwhalecloud.retail.workflow.mapper.ServiceMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class ServiceManager{
    @Resource
    private ServiceMapper serviceMapper;

    /**
     * 根据服务id列表服务信息
     *
     * @param ids
     * @return
     */
    public List<Service> listServiceByIds(List<String> ids) {
        return serviceMapper.selectBatchIds(ids);
    }

    /**
     * 通过ID查询服务规格
     * @param serviceId
     * @return
     */
    @Cacheable(value = WorkFlowConst.CACHE_NAME_WF_SERVICE, key = "#serviceId")
    public Service getService(String serviceId) {
        if (StringUtils.isEmpty(serviceId)) {
            return null;
        }
        return serviceMapper.selectById(serviceId);
    }

    /**
     * 新增服务
     *
     * @param service
     * @return
     */
    public Boolean addService(Service service) {
        service.setCreateTime(new Date());
        service.setUpdateTime(new Date());
        return serviceMapper.insert(service) > 0;
    }

    /**
     * 根据服务id删除服务
     *
     * @param serviceId
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_SERVICE, key = "#serviceId")
    })
    public Boolean delService(String serviceId) {
        return serviceMapper.deleteById(serviceId) > 0;
    }

    /**
     * 编辑服务
     *
     * @param service
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_SERVICE, key = "#service.serviceId")
    })
    public Boolean editService(Service service) {
        service.setUpdateTime(new Date());
        return serviceMapper.updateById(service) > 0;
    }

    /**
     * 查询服务列表
     *
     * @param servicName
     * @return
     */
    public IPage<Service> listServiceByCondition(int pageNo, int pageSize, String servicName) {
        IPage<Service> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Service> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(servicName)) {
            queryWrapper.eq("service_name",servicName);
        }
        return serviceMapper.selectPage(page,queryWrapper);
    }
}
