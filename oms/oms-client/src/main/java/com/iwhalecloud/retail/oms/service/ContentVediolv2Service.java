package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.ContentVediolv2DTO;

import java.util.List;

public interface ContentVediolv2Service{

    /**
     * 查询视频二级内容信息
     * @param matids
     * @return
     */
    public List<ContentVediolv2DTO> queryContentVediolTwoList(List<Long> matids);

    public  List<ContentVediolv2DTO> queryContentVediolByUpmatid(List<Long> list);

}