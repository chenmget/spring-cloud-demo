package com.iwhalecloud.retail.workflow.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.RouteDTO;
import com.iwhalecloud.retail.workflow.dto.req.RouteReq;

import java.util.List;

public interface RouteService{

    /**
     * 添加路由
     *
     * @param routeDTO
     * @return
     */
    ResultVO<Boolean> addRoute(RouteDTO routeDTO);

    /**
     * 编辑路由
     *
     * @param routeDTO
     * @return
     */
    ResultVO<Boolean> editRoute(RouteDTO routeDTO);

    /**
     * 根据路由ID删除路由
     *
     * @param routeId
     * @return
     */
    ResultVO<Boolean> delRoute(String routeId);

    /**
     * 查询路由列表
     *
     * @param
     * @return
     */
    ResultVO<List<RouteDTO>> listRouteByCondition(String routeName);

    /**
     * 查询路由列表
     *
     * @param
     * @return
     */
    ResultVO<List<RouteDTO>> listRoute(RouteReq req);

}