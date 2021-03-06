package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductRebateReq;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductInfoReqDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.dto.resp.QueryProductInfoResqDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.ArithUtil;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.req.GetMerchantIdListReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalancePayoutReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalancePayoutResp;
import com.iwhalecloud.retail.promo.manager.AccountBalancePayoutManager;
import com.iwhalecloud.retail.promo.manager.AccountManager;
import com.iwhalecloud.retail.promo.service.AccountBalancePayoutService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service(timeout = 50000)
@Component("accountBalancePayoutService")
public class AccountBalancePayoutServiceImpl implements AccountBalancePayoutService {
    @Reference
    private MerchantService merchantService;
    @Reference
    private UserService userService;

    @Reference
    private ProductService productService;


    @Autowired
    private AccountManager accountManager;

    @Autowired
    private AccountBalancePayoutManager accountBalancePayoutManager;

    @Override
    public ResultVO<Page<QueryAccountBalancePayoutResp>> queryAccountBalancePayoutForPage(QueryAccountBalancePayoutReq req) {
        this.initQueryAccountBalancePayoutReq(req);
        log.info(AccountBalancePayoutServiceImpl.class.getName()+" queryAccountBalancePayoutForPage, req={}", req == null ? "" : JSON.toJSON(req));
        Page<QueryAccountBalancePayoutResp> page = accountBalancePayoutManager.queryAccountBalancePayoutForPage(req);

        if (page != null && page.getRecords() != null) {
            List<QueryAccountBalancePayoutResp> list = page.getRecords();

            for (QueryAccountBalancePayoutResp data : list) {
                //供应商信息
                String supplierId = data.getSupplierId();
                if (StringUtils.isNotEmpty(supplierId)) {
                    ResultVO<MerchantDTO> supplier = merchantService.getMerchantById(supplierId);
                    if (supplier != null && supplier.isSuccess() && supplier.getResultData() != null) {
                        data.setRegionId(supplier.getResultData().getCity());
                        data.setRegionName(supplier.getResultData().getCityName());
                        data.setLanId(supplier.getResultData().getLanId());
                        data.setLanName(supplier.getResultData().getLanName());
                        data.setSupplierName(supplier.getResultData().getMerchantName());
                        String merchantId = supplier.getResultData().getMerchantId();

                        UserListReq userListReq = new UserListReq();
                        userListReq.setRelCode(merchantId);
                        List<UserDTO> userList = userService.getUserList(userListReq);
                        if (userList != null && !userList.isEmpty()) {
                            data.setSupplierLoginName(userList.get(0).getLoginName());

                        }


                    }
                }
                //单位转换
                if (StringUtils.isNotEmpty(data.getAmount())) {
                    data.setAmountYuan(ArithUtil.fenToYuan(data.getAmount()));
                }
                if (StringUtils.isNotEmpty(data.getBalance())) {
                    data.setBalanceYuan(ArithUtil.fenToYuan(data.getBalance()));
                }
                //产品信息

                String productId = data.getProductId();
                if (StringUtils.isNotEmpty(productId)) {
                    QueryProductInfoReqDTO queryProductInfoReqDTO = new QueryProductInfoReqDTO();
                    queryProductInfoReqDTO.setProductId(productId);
                    ResultVO<QueryProductInfoResqDTO> respResultVO = productService.getProductInfo(queryProductInfoReqDTO);
                    if (respResultVO != null && respResultVO.isSuccess() && respResultVO.getResultData() != null) {
                        data.setProductName(respResultVO.getResultData().getProductName());
                        data.setSpecName(respResultVO.getResultData().getSpecName());

                    }
                }
            }
        }
        ResultVO resultVO = ResultVO.success(page);
        log.info(AccountBalancePayoutServiceImpl.class.getName()+" queryAccountBalancePayoutForPage, rsp={}", resultVO == null ? "" : JSON.toJSON(resultVO));

        return resultVO;
    }

    private void initQueryAccountBalancePayoutReq(QueryAccountBalancePayoutReq req) {
        String acctId = this.accountManager.getAccountId(req.getCustId(),req.getAcctType());
        req.setAcctId(acctId);
        boolean istMerchant = false;
        if(StringUtils.isNotEmpty(req.getSupplierName())||StringUtils.isNotEmpty(req.getSupplierLoginName())){
            istMerchant = true;
        }
        List<String> supplierIdList = new ArrayList<String>();

        GetMerchantIdListReq getMerchantIdListReq = new GetMerchantIdListReq();
        getMerchantIdListReq.setMerchantName(req.getSupplierName());
        getMerchantIdListReq.setMerchantLoginName(req.getSupplierLoginName());
        supplierIdList = this.getMerchantIdList(getMerchantIdListReq);


        List<String> productIdList = new ArrayList<String>();
        String productName = req.getProductName();
        String brandName = req.getBrandName();
        String unitType = req.getUnitType();
        ProductRebateReq productRebateReq = new ProductRebateReq();
        boolean isProduct = false;
        if (StringUtils.isNotEmpty(productName)) {
            isProduct = true;
            productRebateReq.setProductName(productName);
        }
        if (StringUtils.isNotEmpty(brandName)) {
            isProduct = true;
            productRebateReq.setBrandName(brandName);
        }
        if (StringUtils.isNotEmpty(unitType)) {
            isProduct = true;
            productRebateReq.setUnitType(unitType);
        }
        if (isProduct) {
            ResultVO<List<ProductResp>> listResultVO = productService.getProductForRebate(productRebateReq);
            if (listResultVO != null && listResultVO.getResultData() != null) {
                List<ProductResp> productList = listResultVO.getResultData();
                for (ProductResp productResp : productList) {
                    productIdList.add(productResp.getProductId());
                }
            }
        }
        if(istMerchant&&supplierIdList.isEmpty()){
            supplierIdList.add(RebateConst.QUERY_NULL);
        }
        if(isProduct&&productIdList.isEmpty()){
            productIdList.add(RebateConst.QUERY_NULL);
        }
        req.setSupplierIdList(supplierIdList);
        req.setProductIdList(productIdList);
    }

    @Override
    public List<String> getMerchantIdList(GetMerchantIdListReq req) {
        List<String> supplierIdList = new ArrayList<String>();

        //供应商名称
        String merchantName = req.getMerchantName();
        //供应商账号:登录账户
        String merchantLoginName = req.getMerchantLoginName();
        if (StringUtils.isNotEmpty(merchantName)) {
            MerchantListReq merchantListReq = new MerchantListReq();
            merchantListReq.setMerchantName(merchantName);
            ResultVO<List<MerchantDTO>> listResultVO = merchantService.listMerchant(merchantListReq);
            if (listResultVO != null && listResultVO.isSuccess() && listResultVO.getResultData() != null) {
                List<MerchantDTO> merchantDTOList = listResultVO.getResultData();
                for (MerchantDTO merchantDTO : merchantDTOList) {
                    supplierIdList.add(merchantDTO.getMerchantId());
                }
            }

        }
        if (StringUtils.isNotEmpty(merchantLoginName)) {
            List<String> supplierIdListTemp = new ArrayList<String>();
            UserListReq userListReq = new UserListReq();
            userListReq.setLoginName(merchantLoginName);
            List<UserDTO> userList = userService.getUserList(userListReq);
            if (userList != null && !userList.isEmpty()) {
                for (UserDTO userDTO : userList) {
                    supplierIdListTemp.add(userDTO.getRelCode());
                }
            }
            //取并集
            supplierIdList.removeAll(supplierIdListTemp);
            supplierIdList.addAll(supplierIdListTemp);
        }
        return supplierIdList;
    }
}