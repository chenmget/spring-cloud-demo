package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.dto.ContentChkhisDTO;

public interface ContentChkhisService{

    /**
     * 插入内容审核信息
     * @param contentChkhis
     * @return
     */
    public  int insertContentChkhis(ContentChkhisDTO contentChkhis);

    public ResultVO contentCheck(ContentChkhisDTO contentChkhis);

}