package com.iwhalecloud.retail.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.CloudDeviceDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudDevicePageReq;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/17
 * @Description: 云货架终端设备
 */

public interface CloudDeviceService {

    /**
     * 查询
     *
     * @param request
     * @return
     */
    Page<CloudDeviceDTO> queryCloudDeviceList(CloudDevicePageReq request);

    /**
     * 查询详情
     *
     * @param num
     * @return
     */
    List<CloudDeviceDTO> queryCloudDeviceListDetails(String num);

    /**
     * 添加
     *
     * @param dto
     * @return
     */
    int createCloudDevice(CloudDeviceDTO dto);

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    int editCloudDevice(CloudDeviceDTO dto);

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    int deleteCloudDevice(CloudDeviceDTO dto);
}
