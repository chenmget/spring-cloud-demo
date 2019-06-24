package com.iwhalecloud.retail.order2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.model.order.GoodsSaleOrderDTO;
import com.iwhalecloud.retail.order2b.dto.response.FtpOrderDataResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.FtpOrderDataReq;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsRawReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.model.OrderInfoModel;
import com.iwhalecloud.retail.order2b.model.OrderUpdateAttrModel;
import com.iwhalecloud.retail.order2b.model.SelectOrderDetailModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * 根据条件查询需要传送的订单数据
     * @param req
     * @return
     */
    Page<FtpOrderDataResp> queryFtpOrderDataRespList(Page<FtpOrderDataResp> page,@Param("req") FtpOrderDataReq req);

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
    int queryFtpOrderDataRespListCount(@Param("req") FtpOrderDataReq req);

    /**
     * 根据orderId查询未全部发货订单
     * @param orderIds
     * @return
     */
    List<OrderInfoModel> selectNotDeliveryOrderByIds(List<String> orderIds);

    /**
     * 根据指定lanid和 时间到当前时间内销售的商品数量
     * @param beginTime
     * @param lanId
     * @return
     */
    List<GoodsSaleOrderDTO> getGoodsSaleNumByTime(@Param("beginTime")Date beginTime,@Param("lanId")String lanId);

    List<GoodsSaleOrderDTO> getGoodsSaleNum(@Param("lanId")String lanId);

    List<GoodsSaleOrderDTO> getGoodsSaleNumByProductId(@Param("productId")String productId,@Param("beginTime")Date beginTime,@Param("lanId")String lanId);

    String findPayAccountByOrderId(@Param("orderId") String orderId);

    Map<String, Object> findReptAccountAndMoneyByOrderId(@Param("orderId") String orderId);

    public int updatePayTransId(@Param("orderId") String orderId, @Param("reqSeq") String reqSeq);

	int updateStatusByOrderId(@Param("orderId")String orderId, @Param("status")String status, @Param("payType")String payType);

}