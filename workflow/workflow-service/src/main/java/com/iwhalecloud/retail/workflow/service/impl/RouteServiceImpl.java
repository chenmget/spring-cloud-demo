package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.RouteDTO;
import com.iwhalecloud.retail.workflow.entity.Route;
import com.iwhalecloud.retail.workflow.manager.RouteManager;
import com.iwhalecloud.retail.workflow.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/16
 */
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteManager routeManager;

    @Override
    public ResultVO<Boolean> addRoute(RouteDTO routeDTO) {
        log.info("RouteServiceImpl.addRoute routeDTO={}", JSON.toJSONString(routeDTO));
        return ResultVO.success(routeManager.addRoute(routeDTO));
    }

    @Override
    public ResultVO<Boolean> editRoute(RouteDTO routeDTO) {
        log.info("RouteServiceImpl.editRoute routeDTO={}", JSON.toJSONString(routeDTO));
        return ResultVO.success(routeManager.editRoute(routeDTO));
    }

    @Override
    public ResultVO<Boolean> delRoute(String routeId) {
        log.info("RouteServiceImpl.delRoute routeId={}", routeId);
        return ResultVO.success(routeManager.delRoute(routeId));
    }

    @Override
    public ResultVO<List<RouteDTO>> listRouteByCondition(String routeName) {
        log.info("RouteServiceImpl.listRouteByCondition routeName={}", JSON.toJSONString(routeName));
        Route condition = new Route();
        condition.setRouteName(routeName);
        List<Route> routeList = routeManager.listRouteByCondition(condition);
        List<RouteDTO> routeDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(routeList)) {
            for (Route route : routeList) {
                RouteDTO routeDTO = new RouteDTO();
                BeanUtils.copyProperties(route, routeDTO);
                routeDTOList.add(routeDTO);
            }
        }
        return ResultVO.success(routeDTOList);
    }
}
