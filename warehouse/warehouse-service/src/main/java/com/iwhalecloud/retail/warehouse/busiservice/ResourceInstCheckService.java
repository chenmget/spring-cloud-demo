package com.iwhalecloud.retail.warehouse.busiservice;


import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstAddReq;

import java.util.List;

public interface ResourceInstCheckService {


    /**
     * 校验自己仓库串码
     * @param req
     * @return
     */
    List<String> qryEnableInsertNbr(ResourceInstAddReq req);

}