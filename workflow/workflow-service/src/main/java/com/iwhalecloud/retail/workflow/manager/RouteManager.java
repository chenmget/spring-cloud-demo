package com.iwhalecloud.retail.workflow.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.RouteDTO;
import com.iwhalecloud.retail.workflow.entity.Route;
import com.iwhalecloud.retail.workflow.mapper.RouteMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class RouteManager{
    @Resource
    private RouteMapper routeMapper;

    /**
     * 根据流程id和当前节点id查询路由信息
     *
     * @param processId
     * @param curNodeId
     * @return
     */
    public List<Route> listRoute(String processId, String curNodeId) {
        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_id",processId);
        queryWrapper.eq("cur_node_id", curNodeId);
        List<Route> routeList = routeMapper.selectList(queryWrapper);
        return routeList;
    }

    /**
    * 根据路由id查询路由信息
    *
    * @param id
    * @return
    */
    @Cacheable(value = WorkFlowConst.CACHE_NAME_WF_ROUTE, key = "#id")
    public Route queryRouteById(String id) {
        return routeMapper.selectById(id);
    }

    /**
     * 添加路由
     *
     * @param routeDTO
     * @return
     */
    public Boolean addRoute(RouteDTO routeDTO) {
        Route route = new Route();
        BeanUtils.copyProperties(routeDTO, route);
        route.setCreateTime(new Date());
        route.setUpdateTime(new Date());
        return routeMapper.insert(route) > 0;
    }

    /**
     * 编辑路由
     *
     * @param routeDTO
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_ROUTE, key = "#routeDTO.routeId")
    })
    public Boolean editRoute(RouteDTO routeDTO){
        Route route = new Route();
        BeanUtils.copyProperties(routeDTO, route);
        route.setUpdateTime(new Date());
        return routeMapper.updateById(route) > 0;
    }

    /**
     * 根据路由ID删除路由
     *
     * @param routeId
     * @return
     */
    @Caching(evict = {
            @CacheEvict(value = WorkFlowConst.CACHE_NAME_WF_ROUTE, key = "#routeId")
    })
    public Boolean delRoute(String routeId){
        return routeMapper.deleteById(routeId) > 0;
    }

    /**
     * 查询路由列表
     *
     * @param
     * @return
     */
    public List<Route> listRouteByCondition(Route condition){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (condition.getRouteName() != null) {
            queryWrapper.eq("route_name",condition.getRouteName());
        }
        if (condition.getProcessId() != null) {
            queryWrapper.eq("process_id",condition.getProcessId());
        }
        if (condition.getCurNodeId() != null) {
            queryWrapper.eq("cur_node_id",condition.getCurNodeId());
        }
        return routeMapper.selectList(queryWrapper);
    }
}
