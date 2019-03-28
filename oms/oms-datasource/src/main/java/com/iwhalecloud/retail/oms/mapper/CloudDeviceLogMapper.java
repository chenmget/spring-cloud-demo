package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.iwhalecloud.retail.oms.dto.CloudDeviceLogDTO;
import com.iwhalecloud.retail.oms.entity.CloudDeviceLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: CloudDeviceLogMapper
 * @author autoCreate
 */
@Mapper
public interface CloudDeviceLogMapper extends BaseMapper<CloudDeviceLog>{

    /**
     * 查询设备日志
     * @param cloudDeviceDTO
     * @return
     */
    List<CloudDeviceLogDTO> queryCloudDeviceLog(CloudDeviceLogDTO cloudDeviceDTO);

    /**
     *
     * @param deviceNum
     * @return
     */
    CloudDeviceLogDTO getLastUpdateDeviceLogRecord(String deviceNum);
}