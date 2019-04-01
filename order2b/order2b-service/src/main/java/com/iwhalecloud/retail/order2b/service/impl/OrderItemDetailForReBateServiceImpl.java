package com.iwhalecloud.retail.order2b.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.QueryProductInfoReqDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.QueryProductInfoResqDTO;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderItemDetailDTO;
import com.iwhalecloud.retail.order2b.dto.response.OrderItemDetailReBateResp;
import com.iwhalecloud.retail.order2b.dto.response.ReBateOrderInDetailResp;
import com.iwhalecloud.retail.order2b.dto.resquest.promo.ReBateOrderInDetailReq;
import com.iwhalecloud.retail.order2b.manager.ReBateOrderInDetailManager;
import com.iwhalecloud.retail.order2b.service.OrderItemDetailForReBateService;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.ActivityRuleDTO;
import com.iwhalecloud.retail.promo.dto.req.QueryAccountBalanceDetailAllReq;
import com.iwhalecloud.retail.promo.dto.resp.QueryAccountBalanceDetailAllResp;
import com.iwhalecloud.retail.promo.service.AccountBalanceDetailService;
import com.iwhalecloud.retail.promo.service.ActivityRuleService;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserListReq;
import com.iwhalecloud.retail.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author lhr 2019-03-30 15:37:30
 */
@Service
@Slf4j
public class OrderItemDetailForReBateServiceImpl implements OrderItemDetailForReBateService {
    @Autowired
    private ReBateOrderInDetailManager orderInDetailManager;

    @Reference
    private ActivityRuleService activityRuleService;

    @Reference
    private ProductService productService;

    @Reference
    private AccountBalanceDetailService accountBalanceDetailService;

    @Reference
    private MerchantService merchantService;

    @Reference
    private UserService userService;
    @Override
    public ResultVO<Page<ReBateOrderInDetailResp>> queryOrderItemDetailDtoByOrderId(ReBateOrderInDetailReq reBateOrderInDetailReq) {
        log.info("OrderItemDetailForReBateServiceImpl.queryAccountBalanceOrderDetailPage req{}",JSON.toJSON(reBateOrderInDetailReq));
        Page<ReBateOrderInDetailResp> page = new Page<ReBateOrderInDetailResp>();
        ResultVO<Page<OrderItemDetailReBateResp>> resultOrderItemDetail = orderInDetailManager.queryOrderItemDetailByOrderId(reBateOrderInDetailReq);
        log.info("OrderItemDetailForReBateServiceImpl.queryAccountBalanceOrderDetailPage resultOrderItemDetail{}",JSON.toJSON(resultOrderItemDetail));
        if (!CollectionUtils.isEmpty(resultOrderItemDetail.getResultData().getRecords())){
            for (OrderItemDetailReBateResp orderItemDetailReBateResp : resultOrderItemDetail.getResultData().getRecords()){
                ReBateOrderInDetailResp reBateOrderInDetailResp = new ReBateOrderInDetailResp();
                //订单时间
                reBateOrderInDetailResp.setUpdateTime(orderItemDetailReBateResp.getUpdateTime());
                //串码
                reBateOrderInDetailResp.setResNbr(orderItemDetailReBateResp.getResNbr());
                QueryAccountBalanceDetailAllReq req = new QueryAccountBalanceDetailAllReq();
                req.setCustId(orderItemDetailReBateResp.getMerchantId());
                req.setOrderItemId(orderItemDetailReBateResp.getItemId());
                req.setAcctType(RebateConst.Const.ACCOUNT_BALANCE_DETAIL_ACCT_TYPE_REBATE.getValue());
                req.setPageNo(1);
                req.setPageSize(1);
                ResultVO<Page<QueryAccountBalanceDetailAllResp>> accountBalanceDetailAllResp = accountBalanceDetailService.queryAccountBalanceDetailAllForPage(req);
                log.info("OrderItemDetailForReBateServiceImpl.queryAccountBalanceOrderDetailPage accountBalanceDetailAllResp{}",JSON.toJSON(accountBalanceDetailAllResp));
                // 产品
                reBateOrderInDetailResp.setProductName(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getProductName());
                reBateOrderInDetailResp.setSpecName(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getSpecName());
                reBateOrderInDetailResp.setUnitType(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getUnitType());
                reBateOrderInDetailResp.setOrderId(orderItemDetailReBateResp.getOrderId());
                reBateOrderInDetailResp.setLanName(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getLanName());
                reBateOrderInDetailResp.setSupplierName(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getSupplierName());
                //供货商账号
                reBateOrderInDetailResp.setSupplyAccount(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getSupplierLoginName());
                //返利活动名称
                reBateOrderInDetailResp.setReBateActivityName(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getActName());
                //返利活动规则
                List<ActivityRuleDTO> activityRuleDTOS = activityRuleService.queryActivityRuleByCondition(accountBalanceDetailAllResp.getResultData().getRecords().get(0).getActId());
                if  (!CollectionUtils.isEmpty(activityRuleDTOS)){
                    reBateOrderInDetailResp.setReBateRule(activityRuleDTOS.get(0).getCalculationRule());
                }
                //串码入库时间
                reBateOrderInDetailResp.setMktResNbrStorageDate(orderItemDetailReBateResp.getReceiveTime());
                MerchantDTO merchantDTO = merchantService.getMerchantInfoById(orderItemDetailReBateResp.getMerchantId());
                log.info("OrderItemDetailForReBateServiceImpl.queryAccountBalanceOrderDetailPage merchantDTO{}",JSON.toJSON(merchantDTO));
                reBateOrderInDetailResp.setMerchantName(merchantDTO.getMerchantName());
                reBateOrderInDetailResp.setBusinessEntityName(merchantDTO.getBusinessEntityName());
                UserListReq userListReq = new UserListReq();
                userListReq.setRelCode(orderItemDetailReBateResp.getMerchantId());
                List<UserDTO> userList = userService.getUserList(userListReq);
                if (userList != null && !userList.isEmpty()) {
                    reBateOrderInDetailResp.setMerchantAccount(userList.get(0).getLoginName());
                }
                page.getRecords().add(reBateOrderInDetailResp);
            }
            log.info("OrderItemDetailForReBateServiceImpl.queryAccountBalanceOrderDetailPage page{}", JSON.toJSON(page));
            return ResultVO.success(page);
        }
        return ResultVO.error();
    }
}
