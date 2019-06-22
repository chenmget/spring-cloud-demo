package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentPublishDTO;

import java.util.List;

public interface ContentPublishService {

    List<ContentPublishDTO> queryContentPublishList(String contentId);

    int deleteContentPublish(ContentPublishDTO dto);

    int createContentPublish(ContentPublishDTO dto);

    int updateContentPublishStatusToPutOn(ContentPublishDTO dto);

    int updateContentPublishStatusToPullOff(ContentPublishDTO dto);

    ContentPublishDTO switchListToString(ContentPublishDTO dto);
}