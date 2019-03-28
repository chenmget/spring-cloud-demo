package com.iwhalecloud.retail.partner.service;


import com.iwhalecloud.retail.partner.dto.SupplyRelDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelAddReq;
import com.iwhalecloud.retail.partner.dto.req.SupplyRelDeleteReq;

public interface SupplyRelService{

    /**
     * 可供代理商删除绑定
     * @param supplyRelDeleteReq
     * @return
     */
    int deleteSupplyRel(SupplyRelDeleteReq supplyRelDeleteReq);

    /**
     * 可供代理商绑定
     * @param req
     * @return
     */
    SupplyRelDTO addSupplyRel(SupplyRelAddReq req);
}