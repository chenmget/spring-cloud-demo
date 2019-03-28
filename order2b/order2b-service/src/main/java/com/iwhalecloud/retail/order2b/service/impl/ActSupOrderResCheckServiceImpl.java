package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.AddActSupRecordReq;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.manager.ActSupOrderResCheckManager;
import com.iwhalecloud.retail.order2b.manager.DeliveryManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.PromotionManager;
import com.iwhalecloud.retail.order2b.service.ActSupOrderResCheckService;
import com.iwhalecloud.retail.promo.dto.req.ActSupDetailReq;
import com.iwhalecloud.retail.promo.service.ActSupDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author zhou.zc
 * @date 2019年03月07日
 * @Description:前置补贴活动补录串码校验
 */
@Service
@Slf4j
public class ActSupOrderResCheckServiceImpl implements ActSupOrderResCheckService {

    @Autowired
    private ActSupOrderResCheckManager actSupOrderResCheckManager;

    @Reference
    private ActSupDetailService actSupDetailService;

    @Override
    public ResultVO orderResCheck(AddActSupRecordReq addActSupRecordReq) {
        log.info("ActSupOrderResCheckServiceImpl.orderResCheck addActSupRecordReq={}", JSON.toJSON(addActSupRecordReq));
        ActSupDetailReq actSupDetailReq = new ActSupDetailReq();
        BeanUtils.copyProperties(addActSupRecordReq, actSupDetailReq);
        //校验订单串码是否已经补录了
        ResultVO resultVO = actSupDetailService.orderResSupCheck(actSupDetailReq);
        log.info("ActSupOrderResCheckServiceImpl.orderResCheck actSupDetailService.orderResSupCheck resultVO={}", JSON.toJSON(resultVO));
        if (!resultVO.isSuccess()) {
            return ResultVO.error("订单串码已经补录");
        }
        //校验订单和串码是否匹配
        OrderItemDetail orderItemDetail = actSupOrderResCheckManager.orderNesCheck(addActSupRecordReq);
        log.info("ActSupOrderResCheckServiceImpl.orderResCheck orderManager.orderNesCheck orderItemDetail={}", JSON.toJSON(orderItemDetail));
        if (orderItemDetail == null) {
            return ResultVO.error("串码和订单不匹配");
        }
        //校验发货时间是否在活动有效期之后
        if (actSupOrderResCheckManager.orderShippingTimeCheck(addActSupRecordReq, orderItemDetail.getBatchId()) == 0) {
            return ResultVO.error("发货时间不符合");
        }
        return ResultVO.success();
    }

    @Override
    public ResultVO orderCheck(AddActSupRecordReq addActSupRecordReq) {
        //校验订单是否在活动有效期生成
        if (actSupOrderResCheckManager.orderCreateDateCheck(addActSupRecordReq) == 0) {
            return ResultVO.error("订单不在活动期间");
        }
        //校验订单是否参与该活动
        if (actSupOrderResCheckManager.orderActivityCheck(addActSupRecordReq) == 0) {
            return ResultVO.error("订单与活动不匹配");
        }
        return ResultVO.success();
    }
}
