package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AllocateStorePageReq;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author my
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/b2b/warehouse")
public class ResouceStoreB2BController {

    @Reference
    private ResouceStoreService resouceStoreService;

    @ApiOperation(value = "营销资源仓库", notes = "提供给管理员使用，根据相关条件查仓库")
    @GetMapping(value = "/listStore")
    public ResultVO<Page<ResouceStoreDTO>> listStore(@RequestParam(value = "merchantName", required = false) String merchantName,
                              @RequestParam(value = "merchantCode", required = false) String merchantCode,
                              @RequestParam(value = "merchantType", required = false) String merchantType,
                              @RequestParam(value = "storeName", required = false) String storeName,
                              @RequestParam(value = "storeType", required = false) String storeType,
                              @RequestParam(value = "storeSubType", required = false) String storeSubType,
                              @RequestParam(value = "lanName", required = false) String lanName,
                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize){
        StorePageReq storePageReq = new StorePageReq();
        storePageReq.setMerchantName(merchantName);
        storePageReq.setMerchantCode(merchantCode);
        storePageReq.setMerchantType(merchantType);
        storePageReq.setStoreName(storeName);
        storePageReq.setStoreType(storeType);
        storePageReq.setStoreSubType(storeSubType);
        storePageReq.setLanIdName(lanName);
        storePageReq.setPageNo(pageNo);
        storePageReq.setPageSize(pageSize);
        Page<ResouceStoreDTO> resouceStoreDTOPage = resouceStoreService.pageStore(storePageReq);
        return ResultVO.success(resouceStoreDTOPage);
    }

    @ApiOperation(value = "营销资源仓库", notes = "A:storeType=self查自己的仓库；storeType=allocate查自己权限下可以调拨的仓库")
    @GetMapping(value = "/listAllocateStore")
    public ResultVO listStore(@RequestParam(value = "merchantId", required = false) String merchantId,
                              @RequestParam(value = "qryType", required = false) String qryType,
                              @RequestParam(value = "storeName", required = false) String storeName,
                              @RequestParam(value = "merchantName", required = false) String merchantName,
                              @RequestParam(value = "merchantCode", required = false) String merchantCode,
                              @RequestParam(value = "merchantType", required = false) String merchantType,
                              @RequestParam(value = "storeSubType", required = false) String storeSubType,
                              @RequestParam(value = "lanName", required = false) String lanName,
                              @RequestParam(value = "pageNo", required = false) Integer pageNo,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              @RequestParam(value = "storeType", required = false) String storeType){
        if(StringUtils.isNotEmpty(qryType) && qryType.equals("self")){
            StorePageReq storePageReq = new StorePageReq();
            if (StringUtils.isNotEmpty(merchantId)) {
                storePageReq.setMerchantIds(Lists.newArrayList(merchantId));
            }
            storePageReq.setStoreName(storeName);
            storePageReq.setMerchantName(merchantName);
            storePageReq.setMerchantCode(merchantCode);
            storePageReq.setMerchantType(merchantType);
            storePageReq.setStoreSubType(storeSubType);
            storePageReq.setLanIdName(lanName);
            storePageReq.setStoreType(storeType);
            storePageReq.setPageNo(pageNo);
            storePageReq.setPageSize(pageSize);
            Page<ResouceStoreDTO> resouceStoreDTOPage = resouceStoreService.pageStore(storePageReq);
            return ResultVO.success(resouceStoreDTOPage);
        }else if(StringUtils.isNotEmpty(qryType) && qryType.equals("allocate")){
            AllocateStorePageReq req = new AllocateStorePageReq();
            req.setMerchantId(merchantId);
            req.setStoreName(storeName);
            req.setMerchantName(merchantName);
            req.setMerchantCode(merchantCode);
            req.setMerchantType(merchantType);
            req.setStoreSubType(storeSubType);
            req.setLanIdName(lanName);
            req.setStoryType(storeType);
            req.setPageNo(pageNo);
            req.setPageSize(pageSize);
            return resouceStoreService.pageMerchantAllocateStore(req);
        }
        return ResultVO.success();
    }

}
