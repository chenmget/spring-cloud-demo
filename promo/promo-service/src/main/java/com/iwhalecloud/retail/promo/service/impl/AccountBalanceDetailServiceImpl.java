package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductInfoReqDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.dto.resp.QueryProductInfoResqDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.ArithUtil;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDetailDTO;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityListResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceDetail;
import com.iwhalecloud.retail.promo.service.AccountBalancePayoutService;
import com.iwhalecloud.retail.promo.service.MarketingActivityService;
import com.iwhalecloud.retail.system.common.DateUtils;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

    @Reference
    private ProductService productService;

    @Reference
    private MarketingActivityService marketingActivityService;

    @Reference
    private AccountBalancePayoutService accountBalancePayoutService;



    @Override
    public String addAccountBalanceDetail(AccountBalanceDetailDTO accountBalanceDetailDTO) {
        AccountBalanceDetail detail = new AccountBalanceDetail();
        BeanUtils.copyProperties(accountBalanceDetailDTO,detail);
        Date date = new Date();
        detail.setCurStatusDate(date);
        detail.setCreateDate(date);
        detail.setExpDate(DateUtils.strToUtilDate(RebateConst.EXP_DATE_DEF));
        detail.setEffDate(new Date());

        detail.setStatusCd(Long.valueOf(RebateConst.Const.STATUS_USE.getValue()));
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
        log.info(AccountBalanceDetailServiceImpl.class.getName()+" queryAccountBalanceDetailAllForPage, req={}", req == null ? "" : JSON.toJSON(req));

        this.initQueryAccountBalanceDetailAll(req);
        Page<QueryAccountBalanceDetailAllResp> page = accountBalanceDetailManager.queryAccountBalanceDetailAllForPage(req);
        if(page!=null&&page.getRecords()!=null){
            List<QueryAccountBalanceDetailAllResp> dataList = page.getRecords();
            for (QueryAccountBalanceDetailAllResp queryAccountBalanceDetailAllResp : dataList) {
                //卖家ID
                String supplierId = queryAccountBalanceDetailAllResp.getSupplierId();
                String statusCdDesc = RebateConst.Const.STATUS_UN_USE.getName();
                if(RebateConst.Const.STATUS_USE.getValue().equals(queryAccountBalanceDetailAllResp.getStatusCd())){
                    statusCdDesc = RebateConst.Const.STATUS_USE.getName();
                }
                queryAccountBalanceDetailAllResp.setStatusCdDesc(statusCdDesc);
                if(StringUtils.isNotEmpty(queryAccountBalanceDetailAllResp.getAmount())){
                    queryAccountBalanceDetailAllResp.setAmountYuan(ArithUtil.fenToYuan(queryAccountBalanceDetailAllResp.getAmount()));
                }
                if(StringUtils.isNotEmpty(queryAccountBalanceDetailAllResp.getRewardPrice())){
                    queryAccountBalanceDetailAllResp.setRewardPrice(ArithUtil.fenToYuan(queryAccountBalanceDetailAllResp.getRewardPrice()));
                }


                if(StringUtils.isNotEmpty(supplierId)){
                    ResultVO<MerchantDTO>  supplier = merchantService.getMerchantById(supplierId);
                    if(supplier!=null&&supplier.isSuccess()&&supplier.getResultData()!=null){
                        queryAccountBalanceDetailAllResp.setLanId(supplier.getResultData().getLanId());
                        queryAccountBalanceDetailAllResp.setLanName(supplier.getResultData().getLanName());
                        queryAccountBalanceDetailAllResp.setSupplierName(supplier.getResultData().getMerchantName());
                        UserListReq userListReq = new UserListReq();
                        userListReq.setRelCode(supplierId);
                        List<UserDTO> userList = userService.getUserList(userListReq);
                        if (userList != null && !userList.isEmpty()) {
                            queryAccountBalanceDetailAllResp.setSupplierLoginName(userList.get(0).getLoginName());

                        }
                    }
                }
                //产品
                String productId = queryAccountBalanceDetailAllResp.getProductId();
                if(StringUtils.isNotEmpty(productId)){
                    QueryProductInfoReqDTO queryProductInfoReqDTO = new QueryProductInfoReqDTO();
                    queryProductInfoReqDTO.setProductId(productId);

                    ResultVO<QueryProductInfoResqDTO>  respResultVO = productService.getProductInfo(queryProductInfoReqDTO);
                    if(respResultVO!=null&&respResultVO.isSuccess()&&respResultVO.getResultData()!=null){
                        queryAccountBalanceDetailAllResp.setProductName(respResultVO.getResultData().getProductName());
                        queryAccountBalanceDetailAllResp.setSpecName(respResultVO.getResultData().getSpecName());
                        queryAccountBalanceDetailAllResp.setProductSn(respResultVO.getResultData().getSn());
                        queryAccountBalanceDetailAllResp.setUnitType(respResultVO.getResultData().getUnitType());

                    }
                }

            }
        }
        ResultVO resultVO = ResultVO.success(page);
        log.info(AccountBalanceDetailServiceImpl.class.getName()+" queryAccountBalanceDetailAllForPage, rsp={}", resultVO == null ? "" : JSON.toJSON(resultVO));

        return resultVO;

    }
    private void initQueryAccountBalanceDetailAll(QueryAccountBalanceDetailAllReq req){
        List<String> actIdList = new ArrayList<String>();

        GetMerchantIdListReq getMerchantIdListReq = new GetMerchantIdListReq();
        getMerchantIdListReq.setMerchantName(req.getSupplierName());
        getMerchantIdListReq.setMerchantLoginName(req.getSupplierLoginName());
        req.setSupplierIdList(accountBalancePayoutService.getMerchantIdList(getMerchantIdListReq));

        //活动的处理
        String actName = req.getActName();
        if(StringUtils.isNotEmpty(actName)){
            MarketingActivityListReq activityListReq = new MarketingActivityListReq();
            activityListReq.setActivityName(actName);
            //只查询返利活动
            activityListReq.setActivityType(PromoConst.ACTIVITYTYPE.REBATE.getCode());
            activityListReq.setPageNo(1);
            activityListReq.setPageSize(Integer.MAX_VALUE);
            ResultVO<Page<MarketingActivityListResp>> page = marketingActivityService.listMarketingActivity(activityListReq);
            if(page!=null&&page.getResultData()!=null&&page.getResultData().getRecords()!=null){
                List<MarketingActivityListResp> actList = page.getResultData().getRecords();
                for (MarketingActivityListResp marketingActivityListResp : actList) {
                    actIdList.add(marketingActivityListResp.getId());
                }
            }
        }
        req.setActIdList(actIdList);

    }
}