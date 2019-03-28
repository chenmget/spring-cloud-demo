package com.iwhalecloud.retail.workflow.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.RouteServiceDTO;

import java.util.List;

public interface RouteServiceService{

    /**
     * 添加路由服务
     *
     * @param routeServiceDTO
     * @return
     */
    ResultVO<Boolean> addRouteService(RouteServiceDTO routeServiceDTO);

    /**
     * 编辑路由服务
     *
     * @param routeServiceDTO
     * @return
     */
    ResultVO<Boolean> editRouteService(RouteServiceDTO routeServiceDTO);

    /**
     * 根据路由服务ID删除路由服务
     *
     * @param routeServiceId
     * @return
     */
    ResultVO<Boolean> delRouteService(String routeServiceId);

    /**
     * 根据路由id查询路由服务信息
     *
     * @param routeId
     * @return
     */
    ResultVO<List<RouteServiceDTO>> listRouteService(String routeId);
}