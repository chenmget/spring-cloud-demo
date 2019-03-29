package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductGetByIdReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantListReq;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.PromoConst;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.AccountBalanceDetailDTO;
import com.iwhalecloud.retail.promo.dto.req.MarketingActivityListReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailForPageReq;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountIncomeDetailReq;
import com.iwhalecloud.retail.promo.dto.resp.MarketingActivityListResp;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.entity.AccountBalanceDetail;
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
                if(StringUtils.isNotEmpty(supplierId)){
                    ResultVO<MerchantDTO>  supplier = merchantService.getMerchantById(supplierId);
                    if(supplier!=null&&supplier.isSuccess()&&supplier.getResultData()!=null){
                        queryAccountBalanceDetailAllResp.setLanId(supplier.getResultData().getLanId());
                        queryAccountBalanceDetailAllResp.setLanName(supplier.getResultData().getLanName());
                        queryAccountBalanceDetailAllResp.setSupplierName(supplier.getResultData().getMerchantName());
                    }
                }
                //产品
                String productId = queryAccountBalanceDetailAllResp.getProductId();
                if(StringUtils.isNotEmpty(productId)){
                    ProductGetByIdReq productGetByIdReq = new ProductGetByIdReq();
                    productGetByIdReq.setProductId(productId);
                    ResultVO<ProductResp>  respResultVO = productService.getProduct(productGetByIdReq);
                    if(respResultVO!=null&&respResultVO.isSuccess()&&respResultVO.getResultData()!=null){
                        queryAccountBalanceDetailAllResp.setProductName(respResultVO.getResultData().getProductName());
                        queryAccountBalanceDetailAllResp.setSpecName(respResultVO.getResultData().getSpecName());
                        queryAccountBalanceDetailAllResp.setProductSn(respResultVO.getResultData().getSn());
                        queryAccountBalanceDetailAllResp.setUnitType(respResultVO.getResultData().getUnitType());

                    }
                }

            }
        }

        return ResultVO.success(page);

    }
    private void initQueryAccountBalanceDetailAll(QueryAccountBalanceDetailAllReq req){
        List<String> supplierIdList = new ArrayList<String>();
        List<String> actIdList = new ArrayList<String>();
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

    }
}