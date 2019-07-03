package com.iwhalecloud.retail.web.controller.b2b.warehouse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AllocateStorePageReq;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                              @RequestParam(value = "storeGrade", required = false) String storeGrade,
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
        storePageReq.setStoreGrade(storeGrade);

        // zhongwenlong 判断是否是地市管理员 是：默认设置lanId值为当前用户的lanId
        if (UserContext.isCityAdminType()) {
            storePageReq.setLanId(UserContext.getUser().getLanId());
        }

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
                              @RequestParam(value = "storeType", required = false) String storeType,
                              @RequestParam(value = "storeGrade", required = false) String storeGrade){
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
            storePageReq.setStoreGrade(storeGrade);
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
            req.setStoreGrade(storeGrade);
            return resouceStoreService.pageMerchantAllocateStore(req);
        }
        return ResultVO.success();
    }

    @ApiOperation(value = "营销资源仓库查询", notes = "查询十四个地市级仓库")
    @GetMapping(value = "/listGivenStore")
    public ResultVO<List<ResouceStoreDTO>> listGivenStore(){
        return resouceStoreService.listGivenStore();
    }
}
