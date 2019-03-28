package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.response.content.ContentVedioctrQryResp;
import com.iwhalecloud.retail.oms.dto.resquest.content.ContentVedioctrAddReq;
import com.iwhalecloud.retail.oms.exception.BaseException;

public interface ContentVedioctrService{

    /**
     * 新增内容播放管理
     * @param contentVedioctrAddReq
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    Boolean addContentVedioctr(ContentVedioctrAddReq contentVedioctrAddReq) throws BaseException;

    /**
     * 查询内容播放管理
     * @param storageNum
     * @return
     * @author Ji.kai
     * @date 2018/11/7 15:27
     */
    ContentVedioctrQryResp qryContentVedioctr(String storageNum) throws BaseException;

}