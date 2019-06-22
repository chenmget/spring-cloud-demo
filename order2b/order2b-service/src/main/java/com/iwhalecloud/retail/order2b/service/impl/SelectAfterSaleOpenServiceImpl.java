package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.busiservice.SelectOrderService;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderApplyDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleInfoDetailResp;
import com.iwhalecloud.retail.order2b.dto.response.AfterSaleResp;
import com.iwhalecloud.retail.order2b.dto.response.OrderApplyExportResp;
import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import com.iwhalecloud.retail.order2b.entity.Order;
import com.iwhalecloud.retail.order2b.entity.OrderApplyDetail;
import com.iwhalecloud.retail.order2b.entity.OrderItemDetail;
import com.iwhalecloud.retail.order2b.entity.ZFlow;
import com.iwhalecloud.retail.order2b.manager.AfterSaleManager;
import com.iwhalecloud.retail.order2b.manager.OrderManager;
import com.iwhalecloud.retail.order2b.manager.OrderZFlowManager;
import com.iwhalecloud.retail.order2b.model.OrderItemDetailModel;
import com.iwhalecloud.retail.order2b.model.SelectAfterModel;
import com.iwhalecloud.retail.order2b.service.SelectAfterSaleOpenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SelectAfterSaleOpenServiceImpl implements SelectAfterSaleOpenService {
    @Autowired
    private SelectOrderService selectOrderService;

    @Autowired
    private OrderZFlowManager orderZFlowManager;

    @Autowired
    private AfterSaleManager afterSaleManager;

    @Autowired
    private OrderManager orderManager;

    @Override
    public ResultVO<IPage<AfterSaleResp>> selectApply(SelectAfterSalesReq req) {
        ResultVO<IPage<AfterSaleResp>> resultVO = new ResultVO<>();
        SelectAfterModel selectAfterModel = new SelectAfterModel();
        BeanUtils.copyProperties(req, selectAfterModel);
        selectAfterModel.setApplyUserCode(req.getUserCode());
        IPage list = selectOrderService.selectAfterSale(selectAfterModel);
        List<AfterSaleResp> afterSaleRespList = JSON.parseArray(JSON.toJSONString(list.getRecords()), AfterSaleResp.class);
        list.setRecords(afterSaleRespList);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(list);
        return resultVO;
    }

    @Override
    public ResultVO<IPage<AfterSaleResp>> selectHandler(SelectAfterSalesReq req) {
        ResultVO<IPage<AfterSaleResp>> resultVO = new ResultVO<>();
        SelectAfterModel selectAfterModel = new SelectAfterModel();
        BeanUtils.copyProperties(req, selectAfterModel);
        selectAfterModel.setHandlerCode(req.getUserCode());

        /**
         * 多个lanId查询
         */
        OrderRequest bContext= Order2bContext.getDubboRequest();
        if(!StringUtils.isEmpty(bContext.getLanId()) && bContext.getLanId().contains(",")){
            selectAfterModel.setLanIdList(Arrays.asList(bContext.getLanId().split(",")));
            bContext.setLanId(null);
        }

        IPage list = selectOrderService.selectAfterSale(selectAfterModel);
        List<AfterSaleResp> afterSaleRespList = JSON.parseArray(JSON.toJSONString(list.getRecords()), AfterSaleResp.class);
        list.setRecords(afterSaleRespList);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(list);
        return resultVO;
    }

    @Override
    public ResultVO<IPage<AfterSaleResp>> selectManagerList(SelectAfterSalesReq req) {
        ResultVO<IPage<AfterSaleResp>> resultVO = new ResultVO<>();
        SelectAfterModel selectAfterModel = new SelectAfterModel();
        BeanUtils.copyProperties(req, selectAfterModel);
        IPage list = selectOrderService.selectAfterSale(selectAfterModel);
        List<AfterSaleResp> afterSaleRespList = JSON.parseArray(JSON.toJSONString(list.getRecords()), AfterSaleResp.class);
        list.setRecords(afterSaleRespList);
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(list);
        return resultVO;
    }

    @Override
    public ResultVO<AfterSaleInfoDetailResp> detail(SelectAfterSalesReq req) {
        ResultVO<AfterSaleInfoDetailResp> resultVO = new ResultVO<>();
        SelectAfterModel selectAfterModel = new SelectAfterModel();
        BeanUtils.copyProperties(req, selectAfterModel);
        IPage list = selectOrderService.selectAfterSale(selectAfterModel);
        if (CollectionUtils.isEmpty(list.getRecords())) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg("未查询到申请单");
            return resultVO;
        }
        AfterSaleResp saleOrder = JSON.parseObject(JSON.toJSONString(list.getRecords().get(0)), AfterSaleResp.class);
        AfterSaleInfoDetailResp detailDTO = new AfterSaleInfoDetailResp();
        detailDTO.setAfterSale(saleOrder);

        detailDTO.setCurrentFlowType(orderZFlowManager.selectCurrentFlowType(saleOrder.getOrderApplyId()));

        Order order = orderManager.getOrderById(saleOrder.getOrderId());

        //订单信息
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        detailDTO.setOrder(orderDTO);

        //串码信息
        OrderItemDetailModel model = new OrderItemDetailModel();
        model.setOrderApplyId(saleOrder.getOrderApplyId());
        List<OrderApplyDetail> resNbrs = afterSaleManager.selectOrderItem(model);
        detailDTO.setApplyResNbrs(new ArrayList<>(resNbrs.size()));
        for (OrderApplyDetail d : resNbrs) {
            detailDTO.getApplyResNbrs().add(d.getResNbr());
        }

        //更换的串码
        OrderItemDetailModel orderItemDReq = new OrderItemDetailModel();
        orderItemDReq.setOrderApplyId(saleOrder.getOrderApplyId());
        orderItemDReq.setStates(new ArrayList<>(2));
        orderItemDReq.getStates().add(5);
        orderItemDReq.getStates().add(6);
        List<OrderItemDetail> hhresNbrs = orderManager.selectOrderItemDetail(orderItemDReq);
        detailDTO.setHhResNbrs(new ArrayList<>(hhresNbrs.size()));
        for (OrderItemDetail d : hhresNbrs) {
            detailDTO.getHhResNbrs().add(d.getResNbr());
        }


        //工作流信息
        ZFlow orderZFlowDTO = new ZFlow();
        orderZFlowDTO.setOrderId(saleOrder.getOrderApplyId());
        orderZFlowDTO.setSourceFrom(req.getSourceFrom());
        detailDTO.setZFlowList(orderZFlowManager.selectFlowList(orderZFlowDTO));
        resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        resultVO.setResultData(detailDTO);
        return resultVO;
    }

    @Override
    public ResultVO<OrderApplyExportResp> orderApplyExport(SelectAfterSalesReq req) {
        ResultVO<OrderApplyExportResp> result = new ResultVO<>();
        ResultVO<IPage<AfterSaleResp>> resultList;
        switch (req.getUserExportType()) {
            case OrderManagerConsts.USER_EXPORT_TYPE_1: //我的售后
                resultList = selectApply(req);
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_2: //我要处理的
                resultList = selectHandler(req);
                break;
            case OrderManagerConsts.USER_EXPORT_TYPE_3: //管理员
                resultList = selectManagerList(req);
                break;
            default:
                result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
                result.setResultMsg("userExportType 类型不匹配");
                return result;
        }

        if (resultList.getResultData() == null || CollectionUtils.isEmpty(resultList.getResultData().getRecords())) {
            result.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            result.setResultMsg("导出失败，未查询到申请单数据");
            return result;
        }

        List<OrderApplyDTO> list = JSON.parseArray(JSON.toJSONString(resultList.getResultData().getRecords()),
                OrderApplyDTO.class);

        List<String> applyIds = new ArrayList<>();
        for (OrderApplyDTO dto : list) {
            Order order = orderManager.getOrderById(dto.getOrderId());
            applyIds.add(dto.getOrderApplyId());
            dto.setOrderCreateTime(order.getCreateTime());
            dto.setOrderStatus(order.getStatus());
            dto.setUserName(order.getUserName());
        }
        OrderApplyExportResp exportResp = new OrderApplyExportResp();

        OrderItemDetailModel detailReq = new OrderItemDetailModel();
        detailReq.setApplyIdList(applyIds);
        List<OrderApplyDetail> nbrs = afterSaleManager.selectOrderItem(detailReq);
        List<OrderItemDetailDTO> applyNbrs = JSON.parseArray(JSON.toJSONString(nbrs), OrderItemDetailDTO.class);

        for (OrderItemDetailDTO nbr : applyNbrs) {
            for (OrderApplyDTO dot : list) {
                if (nbr.getItemId().equals(dot.getOrderItemId())) {
                    nbr.setProductName(dot.getOrderItems().getProductName());
                    nbr.setGoodsName(dot.getOrderItems().getGoodsName());
                    break;
                }
            }
        }

        exportResp.setApplyNbrslist(applyNbrs);
        exportResp.setOrderApplyList(list);

        result.setResultCode(OmsCommonConsts.RESULE_CODE_SUCCESS);
        result.setResultData(exportResp);
        return result;
    }
}
