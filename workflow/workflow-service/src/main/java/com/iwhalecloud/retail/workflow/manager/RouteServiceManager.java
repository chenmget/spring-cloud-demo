package com.iwhalecloud.retail.workflow.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.RouteServiceDTO;
import com.iwhalecloud.retail.workflow.entity.RouteService;
import com.iwhalecloud.retail.workflow.mapper.RouteServiceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class RouteServiceManager{
    @Resource
    private RouteServiceMapper routeServiceMapper;

    /**
     * 根据路由id查询路由服务信息
     *
     * @param routeId
     * @return
     */
    public List<RouteService> listRouteService(String routeId) {
        QueryWrapper<RouteService> routeServiceQueryWrapper = new QueryWrapper<>();
        if (routeId != null) {
            routeServiceQueryWrapper.eq("route_id", routeId);
        }
        return routeServiceMapper.selectList(routeServiceQueryWrapper);
    }

    /**
     * 添加路由服务
     *
     * @param routeServiceDTO
     * @return
     */
    public Boolean addRouteService(RouteServiceDTO routeServiceDTO) {
        RouteService routeService = new RouteService();
        BeanUtils.copyProperties(routeServiceDTO, routeService);
        routeService.setCreateTime(new Date());
        routeService.setUpdateTime(new Date());
        return routeServiceMapper.insert(routeService) > 0;
    }

    /**
     * 编辑路由服务
     *
     * @param routeServiceDTO
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_ROUTE_SERVICE, key = "#routeServiceDTO.id")
    })
    public Boolean editRouteService(RouteServiceDTO routeServiceDTO){
        RouteService routeService = new RouteService();
        BeanUtils.copyProperties(routeServiceDTO, routeService);
        routeService.setUpdateTime(new Date());
        return routeServiceMapper.updateById(routeService) > 0;
    }

    /**
     * 根据路由服务ID删除路由服务
     *
     * @param routeServiceId
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_ROUTE_SERVICE, key = "#routeServiceId")
    })
    public Boolean delRouteService(String routeServiceId){
        return routeServiceMapper.deleteById(routeServiceId) > 0;
    }
    
}
