package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.CloudDeviceLogDTO;
import com.iwhalecloud.retail.oms.entity.CloudDeviceLog;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import com.iwhalecloud.retail.oms.mapper.CloudDeviceLogMapper;

import java.util.List;


@Component
public class CloudDeviceLogManager{
    @Resource
    private CloudDeviceLogMapper cloudDeviceLogMapper;


    public int addCloudDeviceLog(CloudDeviceLogDTO cloudDeviceDTO) {
        CloudDeviceLog cloudDeviceLog = new CloudDeviceLog();
        BeanUtils.copyProperties(cloudDeviceDTO,cloudDeviceLog);
        cloudDeviceLog.setId(null);
        return cloudDeviceLogMapper.insert(cloudDeviceLog);
    }

    public List<CloudDeviceLogDTO> queryCloudDeviceLog(CloudDeviceLogDTO cloudDeviceDTO){
        return cloudDeviceLogMapper.queryCloudDeviceLog(cloudDeviceDTO);
    }

    public int updateCloudDeviceLog(CloudDeviceLogDTO cloudDeviceDTO) {
        CloudDeviceLog cloudDeviceLog = new CloudDeviceLog();
        BeanUtils.copyProperties(cloudDeviceDTO,cloudDeviceLog);
        return cloudDeviceLogMapper.updateById(cloudDeviceLog);
    }

    public CloudDeviceLogDTO getLastUpdateDeviceLogRecord(String deviceNum){
        return cloudDeviceLogMapper.getLastUpdateDeviceLogRecord(deviceNum);
    }
}
