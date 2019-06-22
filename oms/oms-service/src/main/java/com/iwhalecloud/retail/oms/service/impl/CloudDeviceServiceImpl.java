package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.CloudDeviceDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudDevicePageReq;
import com.iwhalecloud.retail.oms.manager.CloudDeviceManager;
import com.iwhalecloud.retail.oms.service.CloudDeviceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @Auther: lin.wh
 * @Date: 2018/10/17 16:57
 * @Description:
 */

@Service
public class CloudDeviceServiceImpl implements CloudDeviceService {

    @Autowired
    private CloudDeviceManager cloudDeviceManager;

    @Override
    public Page<CloudDeviceDTO> queryCloudDeviceList(CloudDevicePageReq request) {
        return cloudDeviceManager.queryCloudDeviceList(request);
    }

    @Override
    public List<CloudDeviceDTO> queryCloudDeviceListDetails(String num) {
        return cloudDeviceManager.queryCloudDeviceListDetails(num);
    }

    @Override
    public int createCloudDevice(CloudDeviceDTO dto) {
        int t = cloudDeviceManager.createCloudDevice(dto);
        return t;
    }

    @Override
    public int editCloudDevice(CloudDeviceDTO dto) {
        int t = cloudDeviceManager.editCloudDevice(dto);
        return t;
    }

    @Override
    public int deleteCloudDevice(CloudDeviceDTO dto) {
        int t = cloudDeviceManager.deleteCloudDevice(dto);
        return t;
    }
}

