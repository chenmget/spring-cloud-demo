package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentVideosDTO;

import java.util.List;

public interface ContentVideosService {

    /**
     * 查询视频内容信息
     * @param contentIds
     * @return
     */
    public List<ContentVideosDTO> queryContentVideoDefalutList(List<Long> contentIds);

}