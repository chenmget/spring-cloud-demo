package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.RouteServiceDTO;
import com.iwhalecloud.retail.workflow.entity.RouteService;
import com.iwhalecloud.retail.workflow.manager.RouteServiceManager;
import com.iwhalecloud.retail.workflow.service.RouteServiceService;
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
public class RouteServiceServiceImpl implements RouteServiceService {

    @Autowired
    private RouteServiceManager routeServiceManager;

    @Override
    public ResultVO<Boolean> addRouteService(RouteServiceDTO routeServiceDTO) {
        log.info("RouteServiceServiceImpl.addRouteService routeServiceDTO={}", JSON.toJSONString(routeServiceDTO));
        return ResultVO.success(routeServiceManager.addRouteService(routeServiceDTO));
    }

    @Override
    public ResultVO<Boolean> editRouteService(RouteServiceDTO routeServiceDTO) {
        log.info("RouteServiceServiceImpl.editRouteService routeServiceDTO={}", JSON.toJSONString(routeServiceDTO));
        return ResultVO.success(routeServiceManager.editRouteService(routeServiceDTO));
    }

    @Override
    public ResultVO<Boolean> delRouteService(String routeServiceId) {
        log.info("RouteServiceServiceImpl.delRouteService routeServiceId={}", routeServiceId);
        return ResultVO.success(routeServiceManager.delRouteService(routeServiceId));
    }

    @Override
    public ResultVO<List<RouteServiceDTO>> listRouteService(String routeId) {
        log.info("RouteServiceServiceImpl.listRouteService routeId={}", routeId);
        List<RouteService> routeList = routeServiceManager.listRouteService(routeId);
        List<RouteServiceDTO> routeServiceDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(routeList)) {
            for (RouteService routeService : routeList) {
                RouteServiceDTO routeServiceDTO = new RouteServiceDTO();
                BeanUtils.copyProperties(routeService, routeServiceDTO);
                routeServiceDTOList.add(routeServiceDTO);
            }
        }
        return ResultVO.success(routeServiceDTOList);
    }

}
