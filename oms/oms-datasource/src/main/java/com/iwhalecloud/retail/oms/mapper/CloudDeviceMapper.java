package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.CloudDeviceDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudDeviceReqDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudDevicePageReq;
import com.iwhalecloud.retail.oms.entity.CloudDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CloudDeviceMapper extends BaseMapper<CloudDevice> {

    Page<CloudDeviceDTO> queryCloudDeviceList(Page<CloudDeviceDTO> page, @Param("pageReq") CloudDevicePageReq request);

    List<CloudDeviceDTO> queryCloudDeviceListDetails(String num);

    List<CloudDeviceDTO> queryCloudDeviceBycloudShelfNumber(@Param("cloudShelfNumber") String cloudShelfNumber);

    int createCloudDevice(CloudDeviceDTO dto);

    int editCloudDevice(CloudDeviceDTO dto);

    int deleteCloudDevice(CloudDeviceDTO dto);

    int updateBatchCloudDevice(@Param("number") String number);

    int updateBatchNumCloudDevice(@Param("list") List<CloudDeviceReqDTO> list );


    int updateCloudDeviceByNum(@Param("devices") List<String> devices, @Param("cloudShelfNumber") String cloudShelfNumber);
}
