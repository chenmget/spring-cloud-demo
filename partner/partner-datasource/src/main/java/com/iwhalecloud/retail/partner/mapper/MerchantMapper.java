package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.req.MerchantPageReq;
import com.iwhalecloud.retail.partner.dto.resp.MerchantPageResp;
import com.iwhalecloud.retail.partner.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: MerchantMapper
 * @author autoCreate
 */
@Mapper
public interface MerchantMapper extends BaseMapper<Merchant>{

    Page<MerchantPageResp> pageMerchant(Page<MerchantPageResp> page, @Param("req") MerchantPageReq req);

}