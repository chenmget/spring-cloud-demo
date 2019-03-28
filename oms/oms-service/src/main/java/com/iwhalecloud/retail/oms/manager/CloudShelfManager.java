package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.*;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudShelfRequestDTO;
import com.iwhalecloud.retail.oms.dto.resquest.CloudShelfPageReq;
import com.iwhalecloud.retail.oms.entity.CloudShelf;
import com.iwhalecloud.retail.oms.exception.BaseException;
import com.iwhalecloud.retail.oms.mapper.CloudShelfMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/21 20:15
 * @Description:
 */

@Component
public class CloudShelfManager {
    @Resource
    private CloudShelfMapper cloudShelfMapper;

    @Value("${fdfs.showUrl}")
    private String showUrl;

    public Page<CloudShelfDTO> queryCloudShelfList(CloudShelfPageReq pageReq) {
        Page<CloudShelfDTO> page = new Page<CloudShelfDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        return cloudShelfMapper.queryCloudShelfList(page, pageReq);
    }

    public CloudShelfDTO queryCloudShelfListDetails(String num, Boolean isQryDevice) throws ParseException, BaseException {
        CloudShelfDTO cloudShelfDTO = cloudShelfMapper.queryCloudShelfListDetails(num);
        if (cloudShelfDTO != null) {
            List<CloudDeviceDTO> list1;
            if (isQryDevice) {
                list1 = cloudShelfMapper.queryCloudShelfListDetails3(num);
                cloudShelfDTO.setCloudDeviceDetailDTOS(list1);
            }
            List<CloudShelfDetailDTO> list = cloudShelfMapper.queryCloudShelfListDetails1(num);
            CloudShelfDetailDTO cloudShelfDetailDTO = new CloudShelfDetailDTO();
            for (int i = 0; i < list.size(); i++) {
                cloudShelfDetailDTO = list.get(i);
                String operatingPositionId = cloudShelfDetailDTO.getOperatingPositionId();
                OperatingPositionBindDTO operatingPositionBindDTO = cloudShelfMapper.queryCloudShelfListDetails2(operatingPositionId);
                if (operatingPositionBindDTO != null) {
                    cloudShelfDetailDTO.setOperatingPositionBindDTO(operatingPositionBindDTO);
                    String contentNumber = operatingPositionBindDTO.getContentNumber();
                    if (!StringUtils.isEmpty(contentNumber)) {
                        long contentId = Long.parseLong(contentNumber);
                        // 查询素材内容
                        List<ContentMaterialDTO> contentMaterialDTOList = cloudShelfMapper.queryCloudShelfListDetails4(contentId);
                        for (ContentMaterialDTO contentMaterialDTO: contentMaterialDTOList){
                            // 对于素材中存储的图片URL，统一增加fdfs.showUrl的配置 add by lin,qi
                            String path = contentMaterialDTO.getPath();
                            if(!StringUtils.isEmpty(path) && !StringUtils.isEmpty(showUrl) && !path.toLowerCase().contains("http")){
                                contentMaterialDTO.setPath(showUrl + path);
                            }
                            String thumbpath = contentMaterialDTO.getThumbpath();
                            if(!StringUtils.isEmpty(thumbpath) && !StringUtils.isEmpty(showUrl) && !thumbpath.toLowerCase().contains("http")){
                                contentMaterialDTO.setThumbpath(showUrl + thumbpath);
                            }
                        }
                        if (contentMaterialDTOList.size() > 0) {
                            operatingPositionBindDTO.setContentMaterials(contentMaterialDTOList);
                        } else {
                            operatingPositionBindDTO.setContentMaterials(null);
                        }
                    } else {
                        operatingPositionBindDTO.setContentMaterials(null);
                    }
                } else {
                    cloudShelfDetailDTO.setOperatingPositionBindDTO(null);
                }
            }
            cloudShelfDTO.setCloudShelfDetailDTOS(list);
        }
        return cloudShelfDTO;
    }

    public CloudShelfDTO queryCloudShelfByNum(String num) {
        return cloudShelfMapper.queryCloudShelfListDetails(num);
    }


    public int createCloudShelf(CloudShelfDTO dto) {
        dto.setGmtCreate(new Date());
        dto.setGmtModified(new Date());
        CloudShelf cloudShelf = new CloudShelf();
        BeanUtils.copyProperties(dto, cloudShelf);
        int t = cloudShelfMapper.insert(cloudShelf);
        dto.setId(cloudShelf.getId());
        return t;
    }

    public int editCloudShelf(CloudShelfDTO dto) {
        dto.setGmtModified(new Date());
        int t = cloudShelfMapper.editCloudShelf(dto);
        return t;
    }

    public int deleteCloudShelf(CloudShelfDTO dto) {
        int t = cloudShelfMapper.deleteCloudShelf(dto);
        return t;
    }

    public int modifyCloudShelfByParam(CloudShelfRequestDTO cloudShelfRequestDTO) {
        return cloudShelfMapper.modifyCloudShelfByParam(cloudShelfRequestDTO);
    }

    public List<CloudShelf> getCloudShelfByName(String name) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("name", name);
        return cloudShelfMapper.selectByMap(map);
    }

}

