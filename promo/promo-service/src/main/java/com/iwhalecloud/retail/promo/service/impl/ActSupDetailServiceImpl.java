package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.dto.model.order.DeliveryDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDTO;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.service.OrderSelectOpenService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.dto.req.ActSupDetailReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupDetailResp;
import com.iwhalecloud.retail.promo.manager.ActSupDetailManager;
import com.iwhalecloud.retail.promo.service.ActSupDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Service
@Component("actSupDetailService")
@Slf4j
public class ActSupDetailServiceImpl implements ActSupDetailService {

    @Autowired
    private ActSupDetailManager actSupDetailManager;

    @Reference
    private ProductService productService;

    @Reference
    private OrderSelectOpenService orderSelectOpenService;

    @Reference
    private MerchantService merchantService;



    @Override
    public ResultVO<Page<ActSupDetailResp>> listActSupDetail(ActSupDetailReq actSupDetailReq) {
        log.info("ActSupDetailServiceImpl.getActSupDetail req={}", JSON.toJSONString(actSupDetailReq));
        Page<ActSupDetailResp> page = actSupDetailManager.getActSupDetailByRecordId(actSupDetailReq);
        List<ActSupDetailResp>  actSupDetailList = page.getRecords();
        log.info("ActSupDetailServiceImpl.getActSupDetail resp={}", JSON.toJSONString(actSupDetailList));
        if(CollectionUtils.isEmpty(actSupDetailList)) {
            return ResultVO.error("详情信息不存在");
        }

        for (ActSupDetailResp supDetail:actSupDetailList) {
            if (StringUtils.isEmpty(supDetail.getResNbr())) {
                continue;
            }

            //1.根据串码查询订单项明细获取①订单项Id、②发货批次
            ResultVO<OrderItemDetailDTO> rderItemResult = orderSelectOpenService.selectOrderItemDetailBySn(supDetail.getResNbr());
            if (!rderItemResult.isSuccess()) {
                continue;

            }
            OrderItemDetailDTO  rderItemDetailDTO = rderItemResult.getResultData();

            //2.由订单项ID查询订单项表获取①价格②优惠金额③产品Id④创建时间（下单时间）
            OrderItemDTO orderItemDTO = orderSelectOpenService.getOrderItemById(rderItemDetailDTO.getItemId());
            if (ObjectUtils.isEmpty(orderItemDTO)) {
                continue;

            }
            supDetail.setPrice(orderItemDTO.getPrice());
            supDetail.setDiscount(orderItemDTO.getCouponPrice());

            //3.根据第2步的产品ID查询产品表获取1.产品名称,2.产品型号,3.产品规格(color+memory)
            ProductGetByIdReq productGetReq = new ProductGetByIdReq();
            productGetReq.setProductId(orderItemDTO.getProductId());
            ResultVO<ProductResp>  productResp = productService.getProduct(productGetReq);
            if (productResp.isSuccess() && productResp.getResultData() != null) {
                supDetail.setProductName(productResp.getResultData().getProductName());
                supDetail.setUnitType(productResp.getResultData().getUnitType());
                String specName = StringUtils.isEmpty(productResp.getResultData().getColor()) ? "" :productResp.getResultData().getColor();
                specName = StringUtils.isEmpty(productResp.getResultData().getMemory()) ? specName : specName + productResp.getResultData().getMemory();
                supDetail.setColor(productResp.getResultData().getColor());
                supDetail.setMemory(productResp.getResultData().getMemory());
                supDetail.setSpecName(specName);
                supDetail.setProductCode(productResp.getResultData().getProductCode());
            }

            // 4.由订单Id和发货批次查询订单发货记录获取1.发送时间
            DeliveryDTO deliveryDTO = orderSelectOpenService.selectDeliveryListByOrderIdAndBatchId(supDetail.getOrderId(),String.valueOf(rderItemDetailDTO.getBatchId()));
            if (!ObjectUtils.isEmpty(deliveryDTO)) {
                supDetail.setShipTime(deliveryDTO.getCreateTime());
            }

            // 5.根据订单Id查询订单表获取1.供应商Id、2.零售商Id
            ResultVO<OrderDTO> orderResult = orderSelectOpenService.selectOrderById(orderItemDTO.getOrderId());
            if (orderResult.isSuccess()&& !ObjectUtils.isEmpty(orderResult.getResultData())) {
                OrderDTO orderDTO = orderResult.getResultData();

                // 6.根据商户Id获取商户名称、根据供应商id获取供应商名称
                ResultVO<MerchantDTO> merchantDTOResult  =  merchantService.getMerchantById(orderDTO.getMerchantId());
                if (!ObjectUtils.isEmpty(merchantDTOResult.getResultData())) {
                    supDetail.setMerchantName(merchantDTOResult.getResultData().getMerchantName());

                }

                ResultVO<MerchantDTO> applyDTOResult  =  merchantService.getMerchantById(orderDTO.getSupplierId());
                if (!ObjectUtils.isEmpty(applyDTOResult.getResultData())) {
                    supDetail.setSupplierName(applyDTOResult.getResultData().getMerchantName());

                }
            }

        }

        return  ResultVO.success(page);
    }

    @Override
    public ResultVO orderResSupCheck(ActSupDetailReq actSupDetailReq) {
        Integer sum = actSupDetailManager.orderResSupCheck(actSupDetailReq);
        if (sum >0) {
            return ResultVO.error("订单和串码已补录");
        }
        return ResultVO.success();
    }
}