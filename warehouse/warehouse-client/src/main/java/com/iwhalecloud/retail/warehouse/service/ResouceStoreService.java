package com.iwhalecloud.retail.warehouse.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AllocateStorePageReq;
import com.iwhalecloud.retail.warehouse.dto.request.StoreGetStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;

public interface ResouceStoreService{

    /**
     * 仓库查询
     *
     * @param req
     * @return
     */
    Page<ResouceStoreDTO> pageStore(StorePageReq req);

    /**
     * 根据仓库id获取商家
     *
     * @param storeId
     * @return
     */
    ResultVO getMerchantByStore(String storeId);

    /**
     * 商家调拨时，指定仓库查询
     *
     * @param req
     * @return
     */
    ResultVO<Page<ResouceStoreDTO>> pageMerchantAllocateStore(AllocateStorePageReq req);

    /**
     * 根据id获取仓库
     *
     * @param storeId
     * @return
     */
    ResultVO<ResouceStoreDTO> getResouceStore(String storeId);

    /**
     * 仓库ID查询
     *
     * @param req
     * @return
     */
    String getStoreId(StoreGetStoreIdReq req);

    /**
     * 初始化仓库
     *
     * @return
     */
    void initStoredata();

}