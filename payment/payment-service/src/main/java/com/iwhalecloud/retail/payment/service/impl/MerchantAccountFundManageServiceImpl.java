package com.iwhalecloud.retail.payment.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductIdListReq;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductBaseService;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.SelectOrderRequest;
import com.iwhalecloud.retail.order.ropservice.MemberOrderService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantIdListForPaymentReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.payment.dto.req.*;
import com.iwhalecloud.retail.payment.dto.resp.*;
import com.iwhalecloud.retail.payment.manager.AccountBalanceRuleManager;
import com.iwhalecloud.retail.payment.service.*;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.MarketingActivityDTO;
import com.iwhalecloud.retail.promo.dto.req.ActIdlistForPaymentReq;
import com.iwhalecloud.retail.promo.dto.req.QueryMarketingActivityReq;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import jdk.internal.dynalink.beans.BeansLinker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;


@Slf4j
@Service
@Component("merchantService")
public class MerchantAccountFundManageServiceImpl implements MerchantAccountFundManageService {

    @Reference
    AccountService accountService;

    @Reference
    AccountBalanceService accountBalanceService;

    @Reference
    AccountBalanceTypeService accountBalanceTypeService;

    @Reference
    AccountBalanceRuleService accountBalanceRuleService;

    @Reference
    AccountBalanceDetailService accountBalanceDetailService;

    @Reference
    MerchantService merchantService;

    @Reference
    MarketingActivityService marketingActivityService;

    @Reference
    MemberOrderService memberOrderService;

    @Reference
    ProductBaseService productBaseService;

    @Autowired
    AccountBalanceRuleManager accountBalanceRuleManager;

    @Override
    public ResultVO<Page<MerchantAccountBalancePageResp>> pageMerchantAccountBalance(MerchantAccountBalancePageReq pageReq){
        log.info("MerchantAccountFundManageServiceImpl.pageMerchantAccountBalance(), input: MerchantAccountBalancePageReq={} ", JSON.toJSONString(pageReq));
        List<MerchantAccountBalancePageResp> listResult = new ArrayList<MerchantAccountBalancePageResp>();
        Page<MerchantAccountBalancePageResp> pageResult = new Page<MerchantAccountBalancePageResp>();

        QueryAccountForPageReq queryAccountForPageReq = new QueryAccountForPageReq();
        BeanUtils.copyProperties(pageReq,queryAccountForPageReq);
//        queryAccountForPageReq.setPageNo(1);
//        queryAccountForPageReq.setPageSize(10000);

        List<String> custIdList = Lists.newArrayList();
        if(!StringUtils.isEmpty(pageReq.getMerchantName()) || !StringUtils.isEmpty(pageReq.getMerchantType())
                || !StringUtils.isEmpty(pageReq.getLoginName())){
            MerchantIdListForPaymentReq merchantIdListForPaymentReq = new MerchantIdListForPaymentReq();
            BeanUtils.copyProperties(pageReq,merchantIdListForPaymentReq);
            custIdList = merchantService.getMerchantIdListForPayment(merchantIdListForPaymentReq);
            //条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(custIdList)){
                return ResultVO.success(new Page<MerchantAccountBalancePageResp>());
            }
        }
        queryAccountForPageReq.setCustIdList(custIdList);

        ResultVO<Page<QueryAccountForPageResp>> accountResultVO = accountService.queryAccountForPage(queryAccountForPageReq);
        if(accountResultVO != null && accountResultVO.isSuccess() && !CollectionUtils.isEmpty(accountResultVO.getResultData().getRecords())){
            accountResultVO.getResultData().getRecords().forEach(QueryAccountForPageResp ->{
                MerchantAccountBalancePageResp respObj = new MerchantAccountBalancePageResp();
                BeanUtils.copyProperties(QueryAccountForPageResp,respObj);
                listResult.add(respObj);
            });
            pageResult.setRecords(listResult);
        } else{
            return ResultVO.success(new Page<MerchantAccountBalancePageResp>());
        }

        //拼接返回信息
        if(pageResult != null && !CollectionUtils.isEmpty(pageResult.getRecords())){
            pageResult.getRecords().forEach(MerchantAccountBalancePageResp -> {
                //获取账户余额累计信息
                AccountBalanceStatisticsReq accountBalanceStatisticsReq = new AccountBalanceStatisticsReq();
                accountBalanceStatisticsReq.setAcctId(MerchantAccountBalancePageResp.getAcctId());
                AccountBalanceStatisticsResp accountBalanceStatisticsResp = accountBalanceService.getBalanceStatistics(accountBalanceStatisticsReq);
                if(!Objects.isNull(accountBalanceStatisticsResp)){
                    MerchantAccountBalancePageResp.setTotalUnEffectiveAmount(accountBalanceStatisticsResp.getTotalUnEffectiveAmount());
                    MerchantAccountBalancePageResp.setTotalInvaildAmount(accountBalanceStatisticsResp.getTotalInvaildAmount());
                    MerchantAccountBalancePageResp.setTotalBlockAmount(accountBalanceStatisticsResp.getTotalBlockAmount());
                    MerchantAccountBalancePageResp.setTotalAmount(accountBalanceStatisticsResp.getTotalAmount());
                    MerchantAccountBalancePageResp.setTotalSumAmount(accountBalanceStatisticsResp.getTotalSumAmount());
                    MerchantAccountBalancePageResp.setBalanceTypeId(accountBalanceStatisticsResp.getBalanceTypeId());
                }

                //获取商家信息
                ResultVO<MerchantDTO> resultVO = merchantService.getMerchantById(MerchantAccountBalancePageResp.getCustId());
                if(resultVO != null && resultVO.isSuccess() && resultVO.getResultData() != null){
                    MerchantAccountBalancePageResp.setMerchantName(resultVO.getResultData().getMerchantName());
                    MerchantAccountBalancePageResp.setMerchantType(resultVO.getResultData().getMerchantType());
                    MerchantAccountBalancePageResp.setMerchantCode(resultVO.getResultData().getMerchantCode());
//                    MerchantAccountBalancePageResp.setSupplierCode(resultVO.getResultData().getSupplierCode());
//                    MerchantAccountBalancePageResp.setSupplierName(resultVO.getResultData().getSupplierName());
                    MerchantAccountBalancePageResp.setLanName(resultVO.getResultData().getLanId());
                    MerchantAccountBalancePageResp.setLanName(resultVO.getResultData().getLanName());
                }

                //获取供应商名称
                List<MerchantAccountBalancePageResp> dataList = pageResult.getRecords();
                for (MerchantAccountBalancePageResp merchantAccountBalancePageResp : dataList) {
                    AccountBalanceRuleReq ruleReq = new AccountBalanceRuleReq();
                    ruleReq.setBalanceTypeId(merchantAccountBalancePageResp.getBalanceTypeId());
                    List<AccountBalanceRuleResp> ruleList = accountBalanceRuleManager.queryAccountBalanceRuleList(ruleReq);
                    if (ruleList != null && !ruleList.isEmpty()) {
                        AccountBalanceRuleResp rule = ruleList.get(0);
                        merchantAccountBalancePageResp.setSupplierId(rule.getObjId());
                        merchantAccountBalancePageResp.setBalanceTypeName(rule.getBalanceTypeName());
                    }

                    //卖家ID
                    String supplierId = merchantAccountBalancePageResp.getSupplierId();
                    if (!StringUtils.isEmpty(supplierId)) {
                        ResultVO<MerchantDTO> supplier = merchantService.getMerchantById(supplierId);
                        if (supplier != null && supplier.isSuccess() && supplier.getResultData() != null) {
                            merchantAccountBalancePageResp.setSupplierName(supplier.getResultData().getMerchantName());
                        }
                    }
                }
            });
        }

        //加1条累计信息
        Double unEffectiveAmount = 0.0,invaildAmount = 0.0,blockAmount =0.0,amount = 0.0,totalAmount = 0.0;
        if(pageResult != null && !CollectionUtils.isEmpty(pageResult.getRecords())){
            for(int i = 0; i < pageResult.getRecords().size(); i++){
                MerchantAccountBalancePageResp b = new MerchantAccountBalancePageResp();
                b = pageResult.getRecords().get(i);
                unEffectiveAmount += b.getTotalUnEffectiveAmount();
                invaildAmount += b.getTotalInvaildAmount();
                blockAmount += b.getTotalBlockAmount();
                amount += b.getTotalAmount();
                totalAmount += b.getTotalSumAmount();
            }
            MerchantAccountBalancePageResp last = new MerchantAccountBalancePageResp();
            last.setMerchantName("合计");
            last.setBalanceTypeName("");
            last.setTotalUnEffectiveAmount(unEffectiveAmount);
            last.setTotalInvaildAmount(invaildAmount);
            last.setTotalBlockAmount(blockAmount);
            last.setTotalAmount(amount);
            last.setTotalSumAmount(totalAmount);
            pageResult.getRecords().add(last);
        }

        return ResultVO.success(pageResult);
    }

    @Override
    public ResultVO<Page<MerchantAccountBalancePageResp>> pageb2bMerchantAccountBalance(MerchantAccountBalancePageReq pageReq){
        log.info("MerchantAccountFundManageServiceImpl.pageB2BMerchantAccountBalance(), input: MerchantAccountBalancePageReq={} ", JSON.toJSONString(pageReq));
        List<MerchantAccountBalancePageResp> listResult = new ArrayList<MerchantAccountBalancePageResp>();
        Page<MerchantAccountBalancePageResp> pageResult = new Page<MerchantAccountBalancePageResp>();

        QueryAccountForPageReq queryAccountForPageReq = new QueryAccountForPageReq();
        BeanUtils.copyProperties(pageReq,queryAccountForPageReq);
        queryAccountForPageReq.setPageNo(1);
        queryAccountForPageReq.setPageSize(10000);

        List<String> acctIdList = Lists.newArrayList();
        if(!StringUtils.isEmpty(pageReq.getSupplierName())){
            List<String> supplierIdList = Lists.newArrayList();
            List<String> balanceTypeIdList = Lists.newArrayList();

            MerchantIdListForPaymentReq merchantIdListForPaymentReq = new MerchantIdListForPaymentReq();
            merchantIdListForPaymentReq.setMerchantName(pageReq.getSupplierName());
            supplierIdList = merchantService.getMerchantIdListForPayment(merchantIdListForPaymentReq);
            //供应商条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(supplierIdList)){
                return ResultVO.success(new Page<MerchantAccountBalancePageResp>());
            }

            BalanceTypeIdListReq balanceTypeIdListReq = new BalanceTypeIdListReq();
            balanceTypeIdListReq.setObjIdList(supplierIdList);
            balanceTypeIdListReq.setObjType(RebateConst.Const.ACCOUNT_BALANCE_TYPE_MERCHANT.getValue());
            balanceTypeIdList = accountBalanceTypeService.listBalanceTypeId(balanceTypeIdListReq);
            //条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(balanceTypeIdList)){
                return ResultVO.success(new Page<MerchantAccountBalancePageResp>());
            }

            AcctIdListReq AcctIdListReq = new AcctIdListReq();
            AcctIdListReq.setBalanceTypeIdList(balanceTypeIdList);
            acctIdList = accountService.listAcctId(AcctIdListReq);
            //条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(acctIdList)){
                return ResultVO.success(new Page<MerchantAccountBalancePageResp>());
            }
        }

        queryAccountForPageReq.setAcctIdList(acctIdList);
        ResultVO<Page<QueryAccountForPageResp>> accountResultVO = accountService.queryAccountForPage(queryAccountForPageReq);
        if(accountResultVO != null && accountResultVO.isSuccess() && !CollectionUtils.isEmpty(accountResultVO.getResultData().getRecords())){
            accountResultVO.getResultData().getRecords().forEach(QueryAccountForPageResp ->{
                MerchantAccountBalancePageResp respObj = new MerchantAccountBalancePageResp();
                BeanUtils.copyProperties(QueryAccountForPageResp,respObj);
                listResult.add(respObj);
            });
            pageResult.setRecords(listResult);
        } else{
            return ResultVO.success(new Page<MerchantAccountBalancePageResp>());
        }

        //拼接返回信息
        if(pageResult != null && !CollectionUtils.isEmpty(pageResult.getRecords())){
            pageResult.getRecords().forEach(MerchantAccountBalancePageResp -> {
                //获取账户余额累计信息
                AccountBalanceStatisticsReq accountBalanceStatisticsReq = new AccountBalanceStatisticsReq();
                accountBalanceStatisticsReq.setAcctId(MerchantAccountBalancePageResp.getAcctId());
                AccountBalanceStatisticsResp accountBalanceStatisticsResp = accountBalanceService.getBalanceStatistics(accountBalanceStatisticsReq);
                if(!Objects.isNull(accountBalanceStatisticsResp)){
                    MerchantAccountBalancePageResp.setTotalUnEffectiveAmount(accountBalanceStatisticsResp.getTotalUnEffectiveAmount());
                    MerchantAccountBalancePageResp.setTotalInvaildAmount(accountBalanceStatisticsResp.getTotalInvaildAmount());
                    MerchantAccountBalancePageResp.setTotalBlockAmount(accountBalanceStatisticsResp.getTotalBlockAmount());
                    MerchantAccountBalancePageResp.setTotalAmount(accountBalanceStatisticsResp.getTotalAmount());
                    MerchantAccountBalancePageResp.setTotalSumAmount(accountBalanceStatisticsResp.getTotalSumAmount());
                    MerchantAccountBalancePageResp.setBalanceTypeId(accountBalanceStatisticsResp.getBalanceTypeId());
                }

                //获取商家信息
                ResultVO<MerchantDTO> resultVO = merchantService.getMerchantById(MerchantAccountBalancePageResp.getCustId());
                if(resultVO != null && resultVO.isSuccess() && resultVO.getResultData() != null){
                    MerchantAccountBalancePageResp.setMerchantName(resultVO.getResultData().getMerchantName());
                    MerchantAccountBalancePageResp.setMerchantType(resultVO.getResultData().getMerchantType());
                    MerchantAccountBalancePageResp.setMerchantCode(resultVO.getResultData().getMerchantCode());
//                    MerchantAccountBalancePageResp.setSupplierCode(resultVO.getResultData().getSupplierCode());
//                    MerchantAccountBalancePageResp.setSupplierName(resultVO.getResultData().getSupplierName());
                    MerchantAccountBalancePageResp.setLanName(resultVO.getResultData().getLanId());
                    MerchantAccountBalancePageResp.setLanName(resultVO.getResultData().getLanName());
                }

                //获取供应商名称
                List<MerchantAccountBalancePageResp> dataList = pageResult.getRecords();
                for (MerchantAccountBalancePageResp merchantAccountBalancePageResp : dataList) {
                    AccountBalanceRuleReq ruleReq = new AccountBalanceRuleReq();
                    ruleReq.setBalanceTypeId(merchantAccountBalancePageResp.getBalanceTypeId());
                    List<AccountBalanceRuleResp> ruleList = accountBalanceRuleManager.queryAccountBalanceRuleList(ruleReq);
                    if (ruleList != null && !ruleList.isEmpty()) {
                        AccountBalanceRuleResp rule = ruleList.get(0);
                        merchantAccountBalancePageResp.setSupplierId(rule.getObjId());
                        merchantAccountBalancePageResp.setBalanceTypeName(rule.getBalanceTypeName());
                    }

                    //卖家ID
                    String supplierId = merchantAccountBalancePageResp.getSupplierId();
                    if (!StringUtils.isEmpty(supplierId)) {
                        ResultVO<MerchantDTO> supplier = merchantService.getMerchantById(supplierId);
                        if (supplier != null && supplier.isSuccess() && supplier.getResultData() != null) {
                            merchantAccountBalancePageResp.setSupplierName(supplier.getResultData().getMerchantName());
                        }
                    }
                }
            });
        }

        //加1条累计信息
        Double unEffectiveAmount = 0.0,invaildAmount = 0.0,blockAmount =0.0,amount = 0.0,totalAmount = 0.0;
        if(pageResult != null && !CollectionUtils.isEmpty(pageResult.getRecords())){
            for(int i = 0; i < pageResult.getRecords().size(); i++){
                MerchantAccountBalancePageResp b = new MerchantAccountBalancePageResp();
                b = pageResult.getRecords().get(i);
                unEffectiveAmount += b.getTotalUnEffectiveAmount();
                invaildAmount += b.getTotalInvaildAmount();
                blockAmount += b.getTotalBlockAmount();
                amount += b.getTotalAmount();
                totalAmount += b.getTotalSumAmount();
            }
            MerchantAccountBalancePageResp last = new MerchantAccountBalancePageResp();
            last.setSupplierName("合计");
            last.setBalanceTypeName("");
            last.setTotalUnEffectiveAmount(unEffectiveAmount);
            last.setTotalInvaildAmount(invaildAmount);
            last.setTotalBlockAmount(blockAmount);
            last.setTotalAmount(amount);
            last.setTotalSumAmount(totalAmount);
            pageResult.getRecords().add(last);
        }

        return ResultVO.success(pageResult);
    }

    private List<String> getAcctIdListByConditions(){
        return Lists.newArrayList();
    }


    /**
     * 商家账户收入明细（笔数）列表
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<MerchantAccountImcomeDetailResp>> pageb2bMerchantAccountImcomeDetail(MerchantAccountImcomeDetailPageReq pageReq){
        log.info("MerchantAccountFundManageServiceImpl.pageb2bMerchantAccountImcomeDetail(), input: MerchantAccountImcomeDetailsPageReq={} ", JSON.toJSONString(pageReq));

        AccountBalanceDetailPageReq accountBalanceDetailPageReq = new AccountBalanceDetailPageReq();

        //存在活动表筛选条件时，转为活动act_id
        List<String> actIdList = Lists.newArrayList();
        if(!StringUtils.isEmpty(pageReq.getProductName())){
            ActIdlistForPaymentReq actIdlistForPaymentReq = new ActIdlistForPaymentReq();
            actIdlistForPaymentReq.setActivityName(pageReq.getProductName());
            actIdList = marketingActivityService.getActIdListForPayment(actIdlistForPaymentReq);
            //活动表条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(actIdList)){
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }
        }

        List<String> balanceTypeIdList = Lists.newArrayList();
        if(!StringUtils.isEmpty(pageReq.getSupplierName()) || !StringUtils.isEmpty(pageReq.getSupplierLoginName())
                || !CollectionUtils.isEmpty(actIdList)) {
            List<String> supplierIdList = Lists.newArrayList();
            MerchantIdListForPaymentReq merchantIdListForPaymentReq = new MerchantIdListForPaymentReq();
            merchantIdListForPaymentReq.setMerchantName(pageReq.getSupplierName());
            merchantIdListForPaymentReq.setLoginName(pageReq.getSupplierLoginName());
            supplierIdList = merchantService.getMerchantIdListForPayment(merchantIdListForPaymentReq);
            //供应商条件过滤后为空，直接返回空
            if (CollectionUtils.isEmpty(supplierIdList)) {
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }

            BalanceTypeIdListReq balanceTypeIdListReq = new BalanceTypeIdListReq();
            balanceTypeIdListReq.setActIdList(actIdList);
            balanceTypeIdListReq.setObjIdList(supplierIdList);
            balanceTypeIdListReq.setObjType(RebateConst.Const.ACCOUNT_BALANCE_TYPE_MERCHANT.getValue());
            balanceTypeIdList = accountBalanceTypeService.listBalanceTypeId(balanceTypeIdListReq);
            //条件过滤后为空，直接返回空
            if (CollectionUtils.isEmpty(balanceTypeIdList)) {
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }
        }

        //存在产品表筛选条件时，转为product_id
        List<String> productIdList = Lists.newArrayList();
        if(!StringUtils.isEmpty(pageReq.getProductName())){
            ProductIdListReq productIdListReq = new ProductIdListReq();
            productIdListReq.setProductName(pageReq.getProductName());
            ResultVO<List<String>> productIdListVO = productBaseService.listProductId(productIdListReq);
            //商家条件过滤后为空，直接返回空
            if(productIdListVO != null && productIdListVO.isSuccess() && CollectionUtils.isEmpty(productIdListVO.getResultData())){
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }
            productIdList = productIdListVO.getResultData();
        }

        accountBalanceDetailPageReq.setProductIdList(productIdList);
        accountBalanceDetailPageReq.setBalanceTypeIdList(balanceTypeIdList);

        //分页获取收入明细（笔数）信息
        BeanUtils.copyProperties(pageReq,accountBalanceDetailPageReq);
        accountBalanceDetailPageReq.setProductIdList(productIdList);
        ResultVO<Page<MerchantAccountImcomeDetailResp>> pageResult = accountBalanceDetailService.pageAccountBalanceDetail(accountBalanceDetailPageReq);

        //拼接商家模块数据
        if(pageResult != null && pageResult.isSuccess() && !CollectionUtils.isEmpty(pageResult.getResultData().getRecords())){
            pageResult.getResultData().getRecords().forEach(MerchantAccountImcomeDetailResp -> {
                ResultVO<MerchantDTO> merchantDTO = merchantService.getMerchantById(MerchantAccountImcomeDetailResp.getCustId());
                MerchantAccountImcomeDetailResp.setMerchantName(merchantDTO.getResultData().getMerchantName());
                MerchantAccountImcomeDetailResp.setMerchantType(merchantDTO.getResultData().getMerchantType());
                MerchantAccountImcomeDetailResp.setSupplierCode(merchantDTO.getResultData().getSupplierCode());
                MerchantAccountImcomeDetailResp.setSupplierName(merchantDTO.getResultData().getSupplierName());
                MerchantAccountImcomeDetailResp.setLanName(merchantDTO.getResultData().getLanName());
            });
        }

        //拼接订单模块数据
        if(pageResult != null && pageResult.isSuccess() && !CollectionUtils.isEmpty(pageResult.getResultData().getRecords())){
            pageResult.getResultData().getRecords().forEach(MerchantAccountImcomeDetailResp -> {
                SelectOrderRequest selectOrderRequest = new SelectOrderRequest();
                selectOrderRequest.setOrderId(MerchantAccountImcomeDetailResp.getOrderId());
                CommonResultResp CommonResultResp = memberOrderService.selectOrderDetail(selectOrderRequest);
//                MerchantAccountImcomeDetailResp.setMerchantName(CommonResultResp.getResultData());
            });
        }

        //拼接活动模块数据
        if(pageResult != null && pageResult.isSuccess() && !CollectionUtils.isEmpty(pageResult.getResultData().getRecords())){
            pageResult.getResultData().getRecords().forEach(MerchantAccountImcomeDetailResp -> {
                QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
                queryMarketingActivityReq.setMarketingActivityId(MerchantAccountImcomeDetailResp.getActivityId());
                ResultVO<MarketingActivityDTO> MarketingActivityVO =  marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
                MerchantAccountImcomeDetailResp.setMerchantName(MarketingActivityVO.getResultData().getName());
                MerchantAccountImcomeDetailResp.setMerchantType(MarketingActivityVO.getResultData().getActivityTypeName());
            });
        }

        return pageResult;
    }


    /**
     * 商家账户收入明细（笔数）列表
     * @param pageReq
     * @return
     */
    @Override
    public ResultVO<Page<MerchantAccountImcomeDetailResp>> pageMerchantAccountImcomeDetail(MerchantAccountImcomeDetailPageReq pageReq){
        log.info("MerchantAccountFundManageServiceImpl.pageMerchantAccountImcomeDetails(), input: MerchantAccountImcomeDetailsPageReq={} ", JSON.toJSONString(pageReq));

        AccountBalanceDetailPageReq accountBalanceDetailPageReq = new AccountBalanceDetailPageReq();

        List<String> merchantIdList = Lists.newArrayList();
        //若是 商家登入 增加商家id限定
        if(!StringUtils.isEmpty(pageReq.getCustId())){
            merchantIdList.add(pageReq.getCustId());
        }
        //存在商家表筛选条件时，转为merchant_id
        if(!(StringUtils.isEmpty(pageReq.getLoginName()) && (StringUtils.isEmpty(pageReq.getSupplierLoginName())
                && StringUtils.isEmpty(pageReq.getMerchantName()) && StringUtils.isEmpty(pageReq.getMerchantType())
                && StringUtils.isEmpty(pageReq.getMerchantCode()) && StringUtils.isEmpty(pageReq.getSupplierName())))){
            MerchantIdListForPaymentReq merchantIdListForPaymentReq = new MerchantIdListForPaymentReq();
            BeanUtils.copyProperties(pageReq,merchantIdListForPaymentReq);
            merchantIdList = this.getMerchantIdListByMerchantConditions(merchantIdListForPaymentReq);
            //商家条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(merchantIdList)){
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }
            accountBalanceDetailPageReq.setCustIdList(merchantIdList);
        }

        //存在供应商筛选条件时，转为merchant_id
        List<String> supplierIdList = Lists.newArrayList();
        if(!(StringUtils.isEmpty(pageReq.getSupplierLoginName()) && StringUtils.isEmpty(pageReq.getSupplierName()))){
            MerchantIdListForPaymentReq merchantIdListForPaymentReq = new MerchantIdListForPaymentReq();
            BeanUtils.copyProperties(pageReq,merchantIdListForPaymentReq);
            supplierIdList = this.getMerchantIdListByMerchantConditions(merchantIdListForPaymentReq);
            //供应家条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(supplierIdList)){
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }
//            accountBalanceDetailPageReq.setSupplierIdList(supplierIdList);
        }

        //存在产品表筛选条件时，转为product_id
        if(!StringUtils.isEmpty(pageReq.getProductName())){
            MerchantIdListForPaymentReq merchantIdListForPaymentReq = new MerchantIdListForPaymentReq();
            BeanUtils.copyProperties(pageReq,merchantIdListForPaymentReq);
            merchantIdList = this.getMerchantIdListByMerchantConditions(merchantIdListForPaymentReq);
            //商家条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(merchantIdList)){
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }
            accountBalanceDetailPageReq.setProductIdList(merchantIdList);
        }

        //存在活动表筛选条件时，转为act_id
        List<String> actIdList = Lists.newArrayList();
        if(!StringUtils.isEmpty(pageReq.getProductName())){
            ActIdlistForPaymentReq actIdlistForPaymentReq = new ActIdlistForPaymentReq();
            BeanUtils.copyProperties(pageReq,actIdlistForPaymentReq);
            actIdList = marketingActivityService.getActIdListForPayment(actIdlistForPaymentReq);
            //活动表条件过滤后为空，直接返回空
            if(CollectionUtils.isEmpty(merchantIdList)){
                return ResultVO.success(new Page<MerchantAccountImcomeDetailResp>());
            }
//            accountBalanceDetailPageReq.setActIdList(actIdList);
        }

        //分页获取账户账本信息
        BeanUtils.copyProperties(pageReq,accountBalanceDetailPageReq);
        ResultVO<Page<MerchantAccountImcomeDetailResp>> pageResult = accountBalanceDetailService.pageAccountBalanceDetail(accountBalanceDetailPageReq);

        //拼接商家模块数据
        if(pageResult != null && pageResult.isSuccess() && !CollectionUtils.isEmpty(pageResult.getResultData().getRecords())){
            pageResult.getResultData().getRecords().forEach(MerchantAccountImcomeDetailResp -> {
                ResultVO<MerchantDTO> merchantDTO = merchantService.getMerchantById(MerchantAccountImcomeDetailResp.getCustId());
                MerchantAccountImcomeDetailResp.setMerchantName(merchantDTO.getResultData().getMerchantName());
                MerchantAccountImcomeDetailResp.setMerchantType(merchantDTO.getResultData().getMerchantType());
                MerchantAccountImcomeDetailResp.setSupplierCode(merchantDTO.getResultData().getSupplierCode());
                MerchantAccountImcomeDetailResp.setSupplierName(merchantDTO.getResultData().getSupplierName());
                MerchantAccountImcomeDetailResp.setLanName(merchantDTO.getResultData().getLanName());
            });
        }

        //拼接订单模块数据
        if(pageResult != null && pageResult.isSuccess() && !CollectionUtils.isEmpty(pageResult.getResultData().getRecords())){
            pageResult.getResultData().getRecords().forEach(MerchantAccountImcomeDetailResp -> {
                SelectOrderRequest selectOrderRequest = new SelectOrderRequest();
                selectOrderRequest.setOrderId(MerchantAccountImcomeDetailResp.getOrderId());
                CommonResultResp CommonResultResp = memberOrderService.selectOrderDetail(selectOrderRequest);
//                MerchantAccountImcomeDetailResp.setMerchantName(CommonResultResp.getResultData());
            });
        }

        //拼接活动模块数据
        if(pageResult != null && pageResult.isSuccess() && !CollectionUtils.isEmpty(pageResult.getResultData().getRecords())){
            pageResult.getResultData().getRecords().forEach(MerchantAccountImcomeDetailResp -> {
                QueryMarketingActivityReq queryMarketingActivityReq = new QueryMarketingActivityReq();
                queryMarketingActivityReq.setMarketingActivityId(MerchantAccountImcomeDetailResp.getActivityId());
                ResultVO<MarketingActivityDTO> MarketingActivityVO =  marketingActivityService.queryMarketingActivityById(queryMarketingActivityReq);
                MerchantAccountImcomeDetailResp.setMerchantName(MarketingActivityVO.getResultData().getName());
                MerchantAccountImcomeDetailResp.setMerchantType(MarketingActivityVO.getResultData().getActivityTypeName());
            });
        }

        return pageResult;
    }


    /**
     * 根据商家表条件筛选返回账户id列表
     * @param merchantIdListForPaymentReq
     * @return
     */
    private List<String> getMerchantIdListByMerchantConditions(MerchantIdListForPaymentReq merchantIdListForPaymentReq){
        return merchantService.getMerchantIdListForPayment(merchantIdListForPaymentReq);
    }

}
