package com.iwhalecloud.retail.order2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.resquest.report.OrderStatisticsRawReq;
import com.iwhalecloud.retail.order2b.entity.OrderApply;
import com.iwhalecloud.retail.order2b.model.AfterSalesModel;
import com.iwhalecloud.retail.order2b.model.SelectAfterModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderApplyMapper extends BaseMapper<OrderApply> {

    OrderApply getLastOrderApplyByOrderId(OrderApply orderId);
    List<OrderApply> selectOrderApply(OrderApply orderId);

    OrderApply selectOrderApplyById(OrderApply applyId);

    int updateApplyState(OrderApply apply);

    int selectAllSubmit(OrderApply orderId);

    IPage<AfterSalesModel> selectAfterSales(Page page, @Param("req")SelectAfterModel apply);

    int getApplyOrderCountByCondition(@Param("req") OrderStatisticsRawReq req);

}
