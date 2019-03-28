package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.CloudDeviceLogDTO;
import com.iwhalecloud.retail.oms.dto.response.cloud.CloudDeviceLogResp;
import com.iwhalecloud.retail.oms.dto.response.cloud.CloudDeviceNumber;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudDeviceLogReq;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.manager.CloudDeviceLogManager;
import com.iwhalecloud.retail.oms.service.CloudDeviceLogService;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class CloudDeviceLogServiceImpl implements CloudDeviceLogService {

    @Autowired
    private CloudDeviceLogManager cloudDeviceLogManager;


    @Override
    @Transactional
    public ResultVO addCloudDeviceLog(CloudDeviceLogDTO cloudDeviceLogDTO) throws  Exception{
        ResultVO resultVO = new ResultVO();
        cloudDeviceLogDTO.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
        //根据时间查询，设备编码查询该设备是否在线
        List<CloudDeviceLogDTO> existCloudDeviceLogs = cloudDeviceLogManager.queryCloudDeviceLog(cloudDeviceLogDTO);
        CloudDeviceLogDTO existCloudDeviceLog =null;
        if(existCloudDeviceLogs.size()>0){
            existCloudDeviceLog = existCloudDeviceLogs.get(0);
        }
        java.util.Date gmtCreate = cloudDeviceLogDTO.getGmtCreate();
        java.util.Date gmtModified = cloudDeviceLogDTO.getGmtModified();
        Long diff = gmtModified.getTime() - gmtCreate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        long seconds = minutes*60;
        if(existCloudDeviceLog!=null){
            existCloudDeviceLog.setGmtModified(gmtModified);
            existCloudDeviceLog.setWorkTime(seconds);
            cloudDeviceLogManager.updateCloudDeviceLog(existCloudDeviceLog);
            resultVO.setResultData("更新成功");
        }else{
            cloudDeviceLogDTO.setWorkTime(seconds);
            cloudDeviceLogManager.addCloudDeviceLog(cloudDeviceLogDTO);
            resultVO.setResultData("插入成功");
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultMsg("success");
        return resultVO;
    }

    @Override
    public ResultVO queryDeviceNumberWorkTime(CloudDeviceLogReq cloudDeviceLogReq) throws Exception {
        ResultVO resultVO = new ResultVO();
        CloudDeviceLogDTO cloudDeviceLogDTO = new CloudDeviceLogDTO();
        cloudDeviceLogDTO.setAdscriptionShopId(cloudDeviceLogReq.getAdscriptionShopId());
        cloudDeviceLogDTO.setAdscriptionCity(cloudDeviceLogReq.getAdscriptionCity());
        cloudDeviceLogDTO.setAdscriptionCityArea(cloudDeviceLogReq.getAdscriptionCityArea());
        cloudDeviceLogDTO.setStartTime(cloudDeviceLogReq.getStartTime());
        cloudDeviceLogDTO.setEndTime(cloudDeviceLogReq.getEndTime());
        cloudDeviceLogDTO.setCountState(cloudDeviceLogReq.getCountState());
        List<CloudDeviceLogDTO> cloudDeviceLogDTOS = cloudDeviceLogManager.queryCloudDeviceLog(cloudDeviceLogDTO);
        if(cloudDeviceLogDTOS.size()>0){
            CloudDeviceLogResp cloudDeviceLogResp = new CloudDeviceLogResp();
            cloudDeviceLogResp.setAdscriptionCity(cloudDeviceLogDTOS.get(0).getAdscriptionCity());
            cloudDeviceLogResp.setAdscriptionCityArea(cloudDeviceLogDTOS.get(0).getAdscriptionCityArea());
            List<CloudDeviceNumber> cloudDeviceNumberList = new ArrayList<CloudDeviceNumber>();
            if(String.valueOf(OmsConst.QueryDeviceLogMethod.WEEK.getCode()).equals(String.valueOf(cloudDeviceLogReq.getCountState()))
               && cloudDeviceLogDTOS.size()==1){
                   //长时间开机，单独一条记录，单独设置工作时长
                    // TODO 如何判断长时间开机？？？
                    CloudDeviceNumber cloudDeviceNumber = new CloudDeviceNumber();
                    cloudDeviceNumber.setAdscriptionShopId(cloudDeviceLogDTOS.get(0).getAdscriptionShopId());
                    cloudDeviceNumber.setCloudDeviceNumber(cloudDeviceLogDTOS.get(0).getCloudDeviceNumber());
                    long dayWorkTime = 24 * 60 * 60;
                    cloudDeviceNumber.setWorkTime(dayWorkTime);
                    cloudDeviceNumberList.add(cloudDeviceNumber);
            }else{
                for (int i = 0; i < cloudDeviceLogDTOS.size(); i++) {
                    CloudDeviceNumber cloudDeviceNumber = new CloudDeviceNumber();
                    cloudDeviceNumber.setAdscriptionShopId(cloudDeviceLogDTOS.get(i).getAdscriptionShopId());
                    cloudDeviceNumber.setCloudDeviceNumber(cloudDeviceLogDTOS.get(i).getCloudDeviceNumber());
                    cloudDeviceNumber.setWorkTime(cloudDeviceLogDTOS.get(i).getWorkTime());
                    cloudDeviceNumberList.add(cloudDeviceNumber);
                }
            }
            cloudDeviceLogResp.setCloudDeviceNumberList(cloudDeviceNumberList);
            resultVO.setResultData(cloudDeviceLogResp);
        }
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultMsg("success");
        return resultVO;
    }

    @Override
    public CloudDeviceLogDTO getLastUpdateDeviceLogRecord(String deviceNum) {
        return cloudDeviceLogManager.getLastUpdateDeviceLogRecord(deviceNum);
    }

    @Override
    public int updateOrAddDeviceLog(CloudDeviceLogDTO deviceLog) {
        int effectRownum = 0;
        if (null != deviceLog.getId()){
            effectRownum = cloudDeviceLogManager.updateCloudDeviceLog(deviceLog);
        } else {
            effectRownum = cloudDeviceLogManager.addCloudDeviceLog(deviceLog);
        }
        return effectRownum;
    }

}