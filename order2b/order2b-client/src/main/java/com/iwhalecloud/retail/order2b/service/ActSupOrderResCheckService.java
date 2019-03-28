package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderResCheckResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.AddActSupRecordReq;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhou.zc
 * @date 2019年03月07日
 * @Description:前置活动补录订单串码校验
 */
public interface ActSupOrderResCheckService {

    /**
     * 校验前置活动补录串码
     * @param addActSupRecordReq
     * @return
     */
    ResultVO orderResCheck(AddActSupRecordReq addActSupRecordReq);

    /**
     * 校验前置补贴活动的订单
     * @param addActSupRecordReq
     * @return
     */
    ResultVO orderCheck(AddActSupRecordReq addActSupRecordReq);
}
