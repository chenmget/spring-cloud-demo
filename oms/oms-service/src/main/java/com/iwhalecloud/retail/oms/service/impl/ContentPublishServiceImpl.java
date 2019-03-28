package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ContentPublishDTO;
import com.iwhalecloud.retail.oms.entity.ContentPublish;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.oms.manager.ContentPublishManager;
import com.iwhalecloud.retail.oms.service.ContentPublishService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
public class ContentPublishServiceImpl implements ContentPublishService {

    @Autowired
    private ContentPublishManager contentPublishManager;

    @Override
    public List<ContentPublishDTO> queryContentPublishList(String contentId) {
        List<ContentPublishDTO> contentPublishDTOList = contentPublishManager.queryContentPublishList(Long.parseLong(contentId));
        for(ContentPublishDTO contentPublishDTO:contentPublishDTOList){
            contentPublishDTO = this.switchStringToList(contentPublishDTO);
        }
        return contentPublishDTOList;
    }

    @Override
    public int deleteContentPublish(ContentPublishDTO dto) {
        return contentPublishManager.deleteContentPublish(dto);
    }

    @Override
    public int createContentPublish(ContentPublishDTO dto) {
        dto = this.switchListToString(dto);
        return contentPublishManager.createContentPublish(dto);
    }

    @Override
    public int updateContentPublishStatusToPutOn(ContentPublishDTO dto) {
        return 0;
    }

    @Override
    public int updateContentPublishStatusToPullOff(ContentPublishDTO dto) {
        return 0;
    }

    @Override
    public ContentPublishDTO switchListToString(ContentPublishDTO dto){
        List<String> areaList = dto.getAreaList();
        List<String> waytypeList = dto.getWaytypeList();
        StringBuffer area = new StringBuffer();
        StringBuffer waytype = new StringBuffer();
        for (int i = 0; i < areaList.size(); i++){
            area.append(areaList.get(i));
            if(i < areaList.size() - 1){
                area.append(",");
            }
        }
        for (int i = 0; i < waytypeList.size(); i++){
            waytype.append(waytypeList.get(i));
            if(i < waytypeList.size() - 1){
                waytype.append(",");
            }
        }
        dto.setArea(area.toString());
        dto.setWaytype(waytype.toString());
        return dto;
    }

    public ContentPublishDTO switchStringToList(ContentPublishDTO dto){
        String area = dto.getArea();
        String waytype = dto.getWaytype();
        String[] areaArray = area.split(",");
        String[] waytypeArray = waytype.split(",");

        List<String> areaList = new ArrayList<String>(Arrays.asList(areaArray));
        List<String> waytypeList = new ArrayList<String>(Arrays.asList(waytypeArray));

        dto.setAreaList(areaList);
        dto.setWaytypeList(waytypeList);
        return dto;
    }
}
