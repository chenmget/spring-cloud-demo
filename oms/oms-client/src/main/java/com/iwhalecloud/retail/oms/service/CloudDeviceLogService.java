package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.CloudDeviceLogDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudDeviceLogReq;

public interface CloudDeviceLogService{

    /**
     * 新增/更新云设备日志
     * @param cloudDeviceLogDTO
     * @return
     * @throws Exception
     */
    public ResultVO addCloudDeviceLog(CloudDeviceLogDTO cloudDeviceLogDTO)throws  Exception;


    public ResultVO queryDeviceNumberWorkTime(CloudDeviceLogReq cloudDeviceLogReq)throws  Exception;

    /**
     * 根据设备编号获取最新工作时长记录
     * @param deviceNum
     * @return
     */
    CloudDeviceLogDTO getLastUpdateDeviceLogRecord(String deviceNum);

    /**
     * 更新或增加设备日志记录
     * @param deviceLog
     * @return
     */
    int updateOrAddDeviceLog(CloudDeviceLogDTO deviceLog);

}