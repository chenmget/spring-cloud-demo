package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.InventoryWaringReq;
import com.iwhalecloud.retail.warehouse.dto.request.ProductQuantityItem;
import com.iwhalecloud.retail.warehouse.dto.request.GetProductQuantityByMerchantReq;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.InventoryWaringQueryDTO;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.request.InventoryWaringQueryReq;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/warehouse")
public class ResourceInstStoreB2BController {

    @Reference
    private ResourceInstStoreService resourceInstStoreService;

    @ApiOperation(value = "查询产品是否有库存", notes = "查询产品是否有库存")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping(value = "getProductQuantityByMerchant")
    public ResultVO getProductQuantityByMerchant(@RequestParam(value = "merchantId", required = false) String merchantId,
                                                 @RequestParam(value = "productIds", required = false) String productIds,
                                                 @RequestParam(value = "num", required = false) String num) {
        GetProductQuantityByMerchantReq req = new GetProductQuantityByMerchantReq();
        req.setMerchantId(merchantId);
        List<ProductQuantityItem> itemList = new ArrayList<>();
        String[] productIdArray = productIds.split(",");
        for (String productId : productIdArray) {
            ProductQuantityItem item = new ProductQuantityItem();
            item.setProductId(productId);
            item.setNum(Long.parseLong(num));
            itemList.add(item);
            req.setItemList(itemList);
        }
        return resourceInstStoreService.getProductQuantityByMerchant(req);
    }

    @ApiOperation(value = "查询产品库存是否预警", notes = "查询产品库存是否预警")
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping(value = "queryInventoryWarning")
    public ResultVO queryInventoryWarning(@RequestBody InventoryWaringQueryReq queryReq) {
        List<InventoryWaringQueryDTO> prodMerchantList = queryReq.getProdMerchantList();
        if (prodMerchantList.size() > 0) {
            List<InventoryWaringReq> realReq = Lists.newArrayList();
            prodMerchantList.forEach(item -> {
                InventoryWaringReq inventoryWaringReq = new InventoryWaringReq();
                inventoryWaringReq.setMerchantId(item.getMerchantId());
                inventoryWaringReq.setProductId(item.getProductId());
                realReq.add(inventoryWaringReq);
            });
            return resourceInstStoreService.queryInventoryWarning(realReq);
        }
        return ResultVO.success();
    }
}
