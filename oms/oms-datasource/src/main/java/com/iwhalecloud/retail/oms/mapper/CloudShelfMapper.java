package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.*;

import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfRequestDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudShelfPageReq;
import com.iwhalecloud.retail.oms.entity.CloudShelf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CloudShelfMapper extends BaseMapper<CloudShelf> {
    Page<CloudShelfDTO> queryCloudShelfList(Page<CloudShelfDTO> page, @Param("pageReq") CloudShelfPageReq pageReq);

    CloudShelfDTO queryCloudShelfListDetails(String num);

    int createCloudShelf(CloudShelfDTO dto);

    int editCloudShelf(CloudShelfDTO dto);

    int deleteCloudShelf(CloudShelfDTO dto);

    int modifyCloudShelfByParam(CloudShelfRequestDTO cloudShelfRequestDTO);

    List<CloudShelfDetailDTO> queryCloudShelfListDetails1(String num);

    OperatingPositionBindDTO queryCloudShelfListDetails2(String operatingPositionId);

    List<CloudDeviceDTO> queryCloudShelfListDetails3(String num);

    List<ContentMaterialDTO> queryCloudShelfListDetails4(long contentId);
}
