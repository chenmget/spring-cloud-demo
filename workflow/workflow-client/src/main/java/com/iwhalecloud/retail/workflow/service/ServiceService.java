package com.iwhalecloud.retail.workflow.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.ServiceDTO;

public interface ServiceService{

    /**
     * 新增服务
     *
     * @param serviceDTO
     * @return
     */
    ResultVO<Boolean> addService(ServiceDTO serviceDTO);

    /**
     * 编辑服务
     *
     * @param serviceDTO
     * @return
     */
    ResultVO<Boolean> editService(ServiceDTO serviceDTO);

    /**
     * 根据服务id删除服务
     *
     * @param serviceId
     * @return
     */
    ResultVO<Boolean> delService(String serviceId);

    /**
     * 查询服务列表
     *
     * @param serviceName
     * @return
     */
    ResultVO<IPage<ServiceDTO>> listServiceByCondition(int pageNo, int pageSize, String serviceName);
}