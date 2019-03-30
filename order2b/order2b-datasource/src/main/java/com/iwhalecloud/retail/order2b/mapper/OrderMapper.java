package com.iwhalecloud.retail.order2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.FtpOrderDataResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.AdvanceOrderReq;
import com.iwhalecloud.retail.order2b.dto.resquest.order.FtpOrderDataReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsRawReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.model.AdvanceOrderInfoModel;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;
import com.iwhalecloud.retail.order2b.model.SelectOrderDetailModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: OrderMapper
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Order getOrderById(Order orderId);

    IPage<OrderInfoModel> selectOrderListByOrder(Page page, @Param("req") SelectOrderDetailModel req);

     int updateOrderAttr(OrderUpdateAttrModel orderUpdateAttrModel);

    /**
     * 根据条件获取订单数量
     * @param req
     * @return
     */
     int getOrderCountByCondition(@Param("req")OrderStatisticsRawReq req);

    /**
     * 根据条件获取订单额度
     * @param req
     * @return
     */
     Double getOrderAmountByCondition(@Param("req")OrderStatisticsRawReq req);

    /**
     * 查询预售订单列表
     * @param page
     * @param req
     * @return
     */
     IPage<AdvanceOrderInfoModel> queryAdvanceOrderList(Page page, @Param("req")AdvanceOrderReq req);

    /**
     * 根据条件查询需要传送的订单数据
     * @param req
     * @return
     */
     List<FtpOrderDataResp> queryFtpOrderDataRespList(@Param("req") FtpOrderDataReq req);

    /**
     * 获取首个订单时间
     * @return
     */
     String getFstTransDate();

    /**
     * 获取需要导出的总条数
     * @param req
     * @return
     */
    int queryFtpOrderDataRespListCount(FtpOrderDataReq req);

    /**
     * 根据orderId查询未全部发货订单
     * @param orderIds
     * @return
     */
    List<OrderInfoModel> selectNotDeliveryOrderByIds(List<String> orderIds);

}