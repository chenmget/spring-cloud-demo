package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDetailDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailForPageReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountIncomeDetailReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceDetail;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.promo.manager.AccountBalanceDetailManager;
import com.iwhalecloud.retail.promo.service.AccountBalanceDetailService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@Component("accountBalanceDetailService")
public class AccountBalanceDetailServiceImpl implements AccountBalanceDetailService {

    @Autowired
    private AccountBalanceDetailManager accountBalanceDetailManager;

    @Reference
    private UserService userService;

    @Reference
    private MerchantService merchantService;

    @Override
    public String addAccountBalanceDetail(AccountBalanceDetailDTO accountBalanceDetailDTO) {
        AccountBalanceDetail detail = new AccountBalanceDetail();
        Date date = new Date();
        detail.setCurStatusDate(date);
        detail.setCreateDate(date);
        detail.setStatusCd(Long.valueOf(RebateConst.STATUS_USE));
        detail.setStatusDate(date);

        boolean isSuc = accountBalanceDetailManager.save(detail);
        if(isSuc){
            return detail.getOperIncomeId();
        }
        return null;

    }

    @Override
    public ResultVO<Page<AccountBalanceDetailDTO>> queryAccountBalanceDetailForPage(QueryAccountBalanceDetailForPageReq req) {
        return ResultVO.success(accountBalanceDetailManager.queryAccountBalanceDetailForPage(req));
    }
    @Override
    public ResultVO<Page<QueryAccountBalanceDetailAllResp>> queryAccountBalanceDetailAllForPage(QueryAccountBalanceDetailAllReq req){
        this.initQueryAccountBalanceDetailAll(req);
        Page<QueryAccountBalanceDetailAllResp> page = accountBalanceDetailManager.queryAccountBalanceDetailAllForPage(req);
        if(page!=null&&page.getRecords()!=null){
            List<QueryAccountBalanceDetailAllResp> dataList = page.getRecords();
            for (QueryAccountBalanceDetailAllResp queryAccountBalanceDetailAllResp : dataList) {
                //卖家ID
                String supplierId = queryAccountBalanceDetailAllResp.getSupplierId();
                if(StringUtils.isNotEmpty(supplierId)){
                    ResultVO<MerchantDTO>  supplier = merchantService.getMerchantById(supplierId);
                    if(supplier!=null&&supplier.isSuccess()&&supplier.getResultData()!=null){
                        queryAccountBalanceDetailAllResp.setSupplierName(supplier.getResultData().getMerchantName());
                    }
                }

            }
        }

        return ResultVO.success(page);

    }
    private void initQueryAccountBalanceDetailAll(QueryAccountBalanceDetailAllReq req){
        List<String> supplierIdList = new ArrayList<String>();
        //供应商名称
        String supplierName = req.getSupplierName();
        //供应商账号:登录账户
        String supplierLoginName = req.getSupplierLoginName();
        if(StringUtils.isNotEmpty(supplierName)){
            MerchantListReq merchantListReq = new MerchantListReq();
            merchantListReq.setMerchantName(supplierName);
            ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchant(merchantListReq);
            if(listResultVO!=null&&listResultVO.isSuccess()&&listResultVO.getResultData()!=null){
                List<MerchantDTO> merchantDTOList = listResultVO.getResultData();
                for (MerchantDTO merchantDTO : merchantDTOList) {
                    supplierIdList.add(merchantDTO.getMerchantId());
                }
            }
        }

        if(StringUtils.isNotEmpty(supplierLoginName)){
            UserListReq userListReq = new UserListReq();
            userListReq.setLoginName(supplierLoginName);
            List<UserDTO> userList = userService.getUserList(userListReq);
            //根据用户获取商家
            if(userList!=null&&!userList.isEmpty()){
                for (UserDTO userDTO : userList) {
                    supplierIdList.add(userDTO.getRelCode());
                }

            }

        }
        req.setSupplierIdList(supplierIdList);

        //活动的处理
        String actName = req.getActName();
        if(StringUtils.isNotEmpty(actName)){

        }

    }
}