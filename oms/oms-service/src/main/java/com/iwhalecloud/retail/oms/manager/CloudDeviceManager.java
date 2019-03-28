package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.CloudDeviceDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudDeviceReqDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudDevicePageReq;
import com.iwhalecloud.retail.oms.entity.CloudDevice;
import com.iwhalecloud.retail.oms.mapper.CloudDeviceMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/21 08:58
 * @Description:
 */

@Component
public class CloudDeviceManager {
    @Resource
    private CloudDeviceMapper cloudDeviceMapper;

    public Page<CloudDeviceDTO> queryCloudDeviceList(CloudDevicePageReq request) {
        Page<CloudDeviceDTO> page = new Page<CloudDeviceDTO>(request.getPageNo(), request.getPageSize());
        return cloudDeviceMapper.queryCloudDeviceList(page, request);
    }

    public List<CloudDeviceDTO> queryCloudDeviceListDetails(String num) {
        return cloudDeviceMapper.queryCloudDeviceListDetails(num);
    }

    public int createCloudDevice(CloudDeviceDTO dto) {
        dto.setGmtCreate(new Date());
        dto.setGmtModified(new Date());
        dto.setId(null);
        CloudDevice total = new CloudDevice();
        BeanUtils.copyProperties(dto, total);
        return cloudDeviceMapper.insert(total);
    }

    public int editCloudDevice(CloudDeviceDTO dto) {
        dto.setGmtModified(new Date());
        int t = cloudDeviceMapper.editCloudDevice(dto);
        return t;
    }

    public int deleteCloudDevice(CloudDeviceDTO dto) {
        int t = cloudDeviceMapper.deleteCloudDevice(dto);
        return t;
    }

    public int updateBatchCloudDevice(String number){
        return cloudDeviceMapper.updateBatchCloudDevice(number);
    }

    public int updateBatchNumCloudDevice(List<CloudDeviceReqDTO> list){
        return cloudDeviceMapper.updateBatchNumCloudDevice(list);
    }

    /**
     * 根据设备编码列表修改关联云货架编码
     * @param devices
     * @param cloudShelfNumber
     * @return
     * @author Ji.kai
     * @date 2018/10/29 15:27
     */
    public int updateCloudDeviceByNum(List<String> devices, String cloudShelfNumber) {
        return cloudDeviceMapper.updateCloudDeviceByNum(devices, cloudShelfNumber);
    }

    public List<CloudDeviceDTO> queryCloudDeviceBycloudShelfNumber(String cloudShelfNumber){
        return cloudDeviceMapper.queryCloudDeviceBycloudShelfNumber(cloudShelfNumber);
    }

}

