package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.PartnerAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerAccountPageReq;

public interface PartnerAccountService{

    /**
     * 根据在页面上选择的代理商，状态查询代理商账户
     * @param page
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    Page<PartnerAccountDTO> qryPartnerAccountPageList(PartnerAccountPageReq page);

    /**
     * 新增代理商账户
     * @param partnerAccountDTO
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    PartnerAccountDTO addPartnerAccount(PartnerAccountDTO partnerAccountDTO);

    /**
     * 修改代理商账户
     * @param partnerAccountDTO
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    PartnerAccountDTO modifyPartnerAccount(PartnerAccountDTO partnerAccountDTO);

    /**
     * 删除代理商账户
     * @param accountId
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    int removePartnerAccount(String accountId);
}