package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.PartnerAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.PartnerAccountPageReq;
import com.iwhalecloud.retail.partner.entity.PartnerAccount;
import com.iwhalecloud.retail.partner.mapper.PartnerAccountMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class PartnerAccountManager{
    @Resource
    private PartnerAccountMapper partnerAccountMapper;

    /**
     * 根据在页面上选择的代理商，状态查询代理商账户列表
     * @param pageReq
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    public Page<PartnerAccountDTO> qryPartnerAccountPageList(PartnerAccountPageReq pageReq) {
        Page<PartnerAccountDTO> page = new Page<PartnerAccountDTO>(pageReq.getPageNo(), pageReq.getPageSize());
        if (null != pageReq.getStates() && pageReq.getStates().size() == 0)
        {
            pageReq.setStates(null);
        }
        page = partnerAccountMapper.qryPartnerAccountPageList(page, pageReq);
        return page;
    }


    /**
     * 新增代理商账户
     * @param partnerAccountDTO
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    public  PartnerAccountDTO insert(PartnerAccountDTO partnerAccountDTO){
        partnerAccountDTO.setIsDefault(PartnerConst.IsDeleted.NOT_DELETED.getCode());
        partnerAccountDTO.setState(PartnerConst.State.EFFECTIVE.getCode());
        PartnerAccount partnerAccount = new PartnerAccount();
        BeanUtils.copyProperties(partnerAccountDTO, partnerAccount);
        partnerAccountMapper.insert(partnerAccount);
        partnerAccountDTO.setAccountId(partnerAccount.getAccountId());
        return partnerAccountDTO;
    }

    /**
     * 修改代理商账户
     * @param partnerAccountDTO
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    public PartnerAccountDTO update(PartnerAccountDTO partnerAccountDTO){
        PartnerAccount partnerAccount = new PartnerAccount();
        BeanUtils.copyProperties(partnerAccountDTO, partnerAccount);
        partnerAccountMapper.updateById(partnerAccount);
        return partnerAccountDTO;
    }

    /**
     * 删除代理商账户
     * @param accountId
     * @return
     * @author Ji.kai
     * @date 2018/11/15 15:27
     */
    public int delete(String accountId){
        PartnerAccount partnerAccount = new PartnerAccount();
        partnerAccount.setAccountId(accountId );
        return partnerAccountMapper.deleteById(partnerAccount);
    }
    
}
