package com.iwhalecloud.retail.partner.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerAccountDTO;
import com.iwhalecloud.retail.partner.dto.PartnerDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerAccountPageReq;
import com.iwhalecloud.retail.partner.dto.req.PartnerPageReq;
import com.iwhalecloud.retail.partner.entity.PartnerAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: PartnerAccountMapper
 * @author autoCreate
 */
@Mapper
public interface PartnerAccountMapper extends BaseMapper<PartnerAccount>{
    /**
     * 根据在页面上选择的代理商名称，状态查询代理商账户列表
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    Page<PartnerAccountDTO> qryPartnerAccountPageList(Page<PartnerAccountDTO> page, @Param("pageReq") PartnerAccountPageReq pageReq);
}