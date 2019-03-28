package com.iwhalecloud.retail.workflow.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.workflow.dto.ServiceDTO;
import com.iwhalecloud.retail.workflow.manager.ServiceManager;
import com.iwhalecloud.retail.workflow.service.ServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author mzl
 * @date 2019/1/14
 */
@Service
@Slf4j
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceManager serviceManager;


    @Override
    public ResultVO<Boolean> addService(ServiceDTO serviceDTO) {
        log.info("ServiceServiceImpl.addService serviceDTO={}", JSON.toJSONString(serviceDTO));
        com.iwhalecloud.retail.workflow.entity.Service service = new com.iwhalecloud.retail.workflow.entity.Service();
        BeanUtils.copyProperties(serviceDTO,service);
        return ResultVO.success(serviceManager.addService(service));
    }

    @Override
    public ResultVO<Boolean> editService(ServiceDTO serviceDTO) {
        log.info("ServiceServiceImpl.editService serviceDTO={}", JSON.toJSONString(serviceDTO));
        if (serviceDTO.getServiceId() == null) {
            ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        com.iwhalecloud.retail.workflow.entity.Service service = new com.iwhalecloud.retail.workflow.entity.Service();
        BeanUtils.copyProperties(serviceDTO,service);
        return ResultVO.success(serviceManager.editService(service));
    }

    @Override
    public ResultVO<Boolean> delService(String serviceId) {
        log.info("ServiceServiceImpl.delService serviceId={}", serviceId);
        if (serviceId == null) {
            ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        return ResultVO.success(serviceManager.delService(serviceId));
    }

    @Override
    public ResultVO<IPage<ServiceDTO>> listServiceByCondition(int pageNo, int pageSize, String serviceName) {
        IPage<com.iwhalecloud.retail.workflow.entity.Service> serviceIPage = serviceManager.listServiceByCondition(pageNo, pageSize, serviceName);
        if (serviceIPage == null || CollectionUtils.isEmpty(serviceIPage.getRecords())) {
            IPage<ServiceDTO> serviceDTOIPage = new Page<>();
            return ResultVO.success(serviceDTOIPage);
        }
        List<com.iwhalecloud.retail.workflow.entity.Service> serviceList = serviceIPage.getRecords();
        List<ServiceDTO> serviceDTOList = Lists.newArrayList();
        for (com.iwhalecloud.retail.workflow.entity.Service service : serviceList) {
            ServiceDTO serviceDTO = new ServiceDTO();
            BeanUtils.copyProperties(service, serviceDTO);
            serviceDTOList.add(serviceDTO);
        }
        IPage<ServiceDTO> serviceDTOIPage = new Page<>(pageNo, pageSize);
        serviceDTOIPage.setRecords(serviceDTOList);
        return ResultVO.success(serviceDTOIPage);
    }

}
