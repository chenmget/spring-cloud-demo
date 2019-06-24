package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.req.MerchantGetReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLigthReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantPageReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantLigthResp;
import com.iwhalecloud.retail.partner.dto.resp.MerchantPageResp;
import com.iwhalecloud.retail.partner.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: MerchantMapper
 */
@Mapper
public interface MerchantMapper extends BaseMapper<Merchant> {

    Page<MerchantPageResp> pageMerchant(Page<MerchantPageResp> page, @Param("req") MerchantPageReq req);

    /**
     * 商家信息列表（只取部分必要字段）
     * @param req
     * @return
     */
    List<MerchantLigthResp> listMerchantForOrder(MerchantLigthReq req);

    /**
     * 商家信息列表（只取部分必要字段）
     * @param req
     * @return
     */
    MerchantLigthResp getMerchantForOrder(MerchantGetReq req);

}