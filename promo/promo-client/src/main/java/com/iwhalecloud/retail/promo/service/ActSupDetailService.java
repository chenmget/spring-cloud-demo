package com.iwhalecloud.retail.promo.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.promo.dto.req.ActSupDetailReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupDetailResp;


public interface ActSupDetailService{

    ResultVO<Page<ActSupDetailResp>> listActSupDetail(ActSupDetailReq actSupDetailReq);

    /**
     * 校验订单和串码是否已经补录过了
     * @param actSupDetailReq
     * @return
     */
    ResultVO orderResSupCheck(ActSupDetailReq actSupDetailReq);
}