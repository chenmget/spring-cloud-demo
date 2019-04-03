package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.MerchantAccountDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantAccountListReq;
import com.iwhalecloud.retail.partner.entity.MerchantAccount;
import com.iwhalecloud.retail.partner.mapper.MerchantAccountMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Component
public class MerchantAccountManager{
    @Resource
    private MerchantAccountMapper merchantAccountMapper;


    /**
     * 添加一个 商家账号
     * @param merchantAccount
     * @return
     */
    public MerchantAccountDTO insert(MerchantAccount merchantAccount){
        merchantAccount.setState(PartnerConst.CommonState.VALID.getCode());
        int resultInt = merchantAccountMapper.insert(merchantAccount);
        if(resultInt > 0){
            MerchantAccountDTO merchantAccountDTO = new MerchantAccountDTO();
            BeanUtils.copyProperties(merchantAccount, merchantAccountDTO);
            return merchantAccountDTO;
        }
        return null;
    }

    /**
     * 根据条件 获取一个 商家账号
     * @param accountId
     * @return
     */
    public MerchantAccountDTO getMerchantAccountById(String accountId) {
        MerchantAccount merchantAccount = merchantAccountMapper.selectById(accountId);
        if (merchantAccount == null) {
            return null;
        }
        MerchantAccountDTO merchantAccountDTO = new MerchantAccountDTO();
        BeanUtils.copyProperties(merchantAccount, merchantAccountDTO);
        return merchantAccountDTO;
    }

    /**
     * 编辑 商家账号 信息
     * @param merchantAccount
     * @return
     */
    public int updateMerchantAccount(MerchantAccount merchantAccount) {
        return merchantAccountMapper.updateById(merchantAccount);
    }

    /**
     * 删除 商家账号 信息
     * @param accountId
     * @return
     */
    public int deleteMerchantAccountById(String accountId){
        return merchantAccountMapper.deleteById(accountId);
    }

    /**
     * 商家账号信息列表
     * @param req
     * @return
     */
    public List<MerchantAccount> listMerchantAccount(MerchantAccountListReq req) {
        QueryWrapper<MerchantAccount> queryWrapper = new QueryWrapper<MerchantAccount>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getAccountId())){
            hasParam = true;
            queryWrapper.eq(MerchantAccount.FieldNames.accountId.getTableFieldName(), req.getAccountId());
        }
        if(!StringUtils.isEmpty(req.getAccountType())){
            hasParam = true;
            queryWrapper.eq(MerchantAccount.FieldNames.accountType.getTableFieldName(), req.getAccountType());
        }
        if(!StringUtils.isEmpty(req.getMerchantId())){
            hasParam = true;
            queryWrapper.eq(MerchantAccount.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if(!StringUtils.isEmpty(req.getAccount())){
            hasParam = true;
            queryWrapper.eq(MerchantAccount.FieldNames.account.getTableFieldName(), req.getAccount());
        }
        if(!StringUtils.isEmpty(req.getBankAccount())){
            hasParam = true;
            queryWrapper.like(MerchantAccount.FieldNames.bankAccount.getTableFieldName(), req.getBankAccount());
        }
        if(!CollectionUtils.isEmpty(req.getMerchantIdList())){
            hasParam = true;
            queryWrapper.in(MerchantAccount.FieldNames.merchantId.getTableFieldName(), req.getMerchantIdList());
        }

        if (!hasParam) {
            // 没参数 返回空  不能查整个表
            return Lists.newArrayList();
        }
        return merchantAccountMapper.selectList(queryWrapper);
    }
}
