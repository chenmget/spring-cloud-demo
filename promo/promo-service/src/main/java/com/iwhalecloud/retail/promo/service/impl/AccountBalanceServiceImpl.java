package com.iwhalecloud.retail.promo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantService;
import com.iwhalecloud.retail.promo.common.RebateConst;
import com.iwhalecloud.retail.promo.dto.*;
import com.iwhalecloud.retail.promo.dto.req.*;
import com.iwhalecloud.retail.promo.dto.resp.*;
import com.iwhalecloud.retail.promo.entity.AccountBalance;
import com.iwhalecloud.retail.promo.entity.AccountBalanceDetail;
import com.iwhalecloud.retail.promo.entity.AccountBalanceLog;
import com.iwhalecloud.retail.promo.entity.AccountBalanceType;
import com.iwhalecloud.retail.promo.exception.BusinessException;
import com.iwhalecloud.retail.promo.manager.AccountBalanceDetailManager;
import com.iwhalecloud.retail.promo.manager.AccountBalanceLogManager;
import com.iwhalecloud.retail.promo.manager.AccountBalanceManager;
import com.iwhalecloud.retail.promo.manager.AccountBalanceTypeManager;
import com.iwhalecloud.retail.promo.rebate.RebateRuleBase;
import com.iwhalecloud.retail.promo.rebate.RebateRuleFactory;
import com.iwhalecloud.retail.promo.service.*;
import com.iwhalecloud.retail.system.common.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
@Component("accountBalanceService")
public class AccountBalanceServiceImpl implements AccountBalanceService {

    @Autowired
    private AccountBalanceManager accountBalanceManager;

    @Autowired
    private AccountService accountService;

    @Reference
    private MerchantService merchantService;
    @Reference
    private AccountBalanceTypeService accountBalanceTypeService;

    @Reference
    private AccountBalanceRuleService accountBalanceRuleService;

    @Reference
    private ActivityRuleService activityRuleService;


    @Autowired
    private AccountBalanceLogManager accountBalanceLogManager;
    @Autowired
    private AccountBalanceDetailManager accountBalanceDetailManager;

    @Reference
    private ActActivityProductRuleService actActivityProductRuleService;
    @Reference
    private AccountBalanceDetailService accountBalanceDetailService;




    @Override
    public ResultVO calculation(AccountBalanceCalculationReq req)  {
        log.info("AccountBalanceServiceImpl calculation, req={}", req == null ? "" : JSON.toJSON(req));
        try{
            this.calculationTransactional(req);
            log.info("AccountBalanceServiceImpl calculation suc");
        }catch (BusinessException e){
            log.error("AccountBalanceServiceImpl calculation error",e);
            return ResultVO.error(e.getMessage());
        }catch (Exception e){
            log.error("AccountBalanceServiceImpl calculation error",e);
            return ResultVO.error(e.getMessage());
        }
        return ResultVO.success();
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO calculationTransactional(AccountBalanceCalculationReq req)  throws Exception{

        AccountDTO accout = this.calculationCheck(req);
        List<AccountBalanceCalculationOrderItemReq> orderItemList = req.getOrderItemList();
        String custId = req.getCustId();

        Map<String, List<AccountBalanceCalculationOrderItemReq>> orderItemMap = new HashMap<String, List<AccountBalanceCalculationOrderItemReq>>();
        //组织数据:买家，卖家，活动，确定唯一性，将请求数据进行组织到orderItemMap
        for (AccountBalanceCalculationOrderItemReq orderItemReq : orderItemList) {
            String actId = orderItemReq.getActId();
            //卖家ID
            String supplierId = orderItemReq.getSupplierId();
            //买家，活动作为唯一性
            String key = custId  + RebateConst.SPLIT_V + actId;
            List<AccountBalanceCalculationOrderItemReq> list = orderItemMap.get(key);
            if (!orderItemMap.containsKey(key)) {
                list = new ArrayList<AccountBalanceCalculationOrderItemReq>();
                orderItemMap.put(key, list);
            }
            list.add(orderItemReq);
        }
        //对组织后的数据进行计算
        for (String key : orderItemMap.keySet()) {
            //计算返利需要的数据
            List<AccountBalanceCalculationOrderItemReq> itemList = orderItemMap.get(key);
            InitCreateAccountBalanceReq initReq = new InitCreateAccountBalanceReq();
            initReq.setAcctId(accout.getAcctId());
            initReq.setCreateStaff(req.getUserId());
            initReq.setCustId(req.getCustId());
            initReq.setOrderItemReqList(itemList);
            this.calculationForAct(initReq);
        }


        return ResultVO.success();
    }

    /**
     * 校验请求参数
     *
     * @param req
     * @return
     */
    private AccountDTO calculationCheck(AccountBalanceCalculationReq req) throws Exception{
        String custId = req.getCustId();
        //获取账户
        ResultVO<AccountDTO> accountDTOResultVO = accountService.getAccountByCustId(custId,RebateConst.Const.ACCOUNT_BALANCE_DETAIL_ACCT_TYPE_REBATE.getValue());
        if (accountDTOResultVO == null || accountDTOResultVO.getResultData() == null) {
            throw new Exception("该商家账户不存在");
        }
        AccountDTO accout = accountDTOResultVO.getResultData();
        List<AccountBalanceCalculationOrderItemReq> orderItemList = req.getOrderItemList();
        if (orderItemList == null || orderItemList.isEmpty()) {
            throw new Exception("订单项不能为空");
        }
        //校验订单项不可重复返利
        Set<String> orderItemSet = new HashSet<String>();
        for (AccountBalanceCalculationOrderItemReq orderItemReq : orderItemList) {
            QueryAccountBalanceDetailForPageReq orderItemCheckReq = new QueryAccountBalanceDetailForPageReq();
            orderItemCheckReq.setOrderItemId(orderItemReq.getOrderItemId());
            orderItemCheckReq.setPageNo(1);
            orderItemCheckReq.setPageSize(1);
            orderItemCheckReq.setAcctId(accout.getAcctId());
            ResultVO<Page<AccountBalanceDetailDTO>> orderItemCheckPage =  accountBalanceDetailService.queryAccountBalanceDetailForPage(orderItemCheckReq);
            if(orderItemCheckPage!=null&&orderItemCheckPage.getResultData()!=null&&orderItemCheckPage.getResultData().getRecords()!=null
                    &&!orderItemCheckPage.getResultData().getRecords().isEmpty()){
                throw new Exception("订单项:"+orderItemReq.getOrderItemId()+"已计算返利，不可重复计算返利");
            }
            if(orderItemSet.contains(orderItemReq.getOrderItemId())){
                throw new Exception("参数错误,订单项:"+orderItemReq.getOrderItemId()+"数据重复");
            }
            orderItemSet.add(orderItemReq.getOrderItemId());


        }

        return accout;
    }


    @Override
    public List<AccountBalanceDTO> queryAccountBalanceList(QueryAccountBalanceReq req) {

        return accountBalanceManager.queryAccountBalanceList(req);
    }

    /**
     * 单个返利活动返利计算：操作账本，收入明细，日志表
     * @param req
     */
    private void calculationForAct(InitCreateAccountBalanceReq req) throws Exception{
        List<AccountBalanceCalculationOrderItemReq> orderItemReqList = req.getOrderItemReqList();
        String actName = orderItemReqList.get(0).getActName();
        //1.判断账本有没有存在
        //a.根据活动获取余额账户类型
        AccountBalanceRuleReq ruleReq = new AccountBalanceRuleReq();
        ruleReq.setActId(orderItemReqList.get(0).getActId());
        ruleReq.setRuleType(RebateConst.Const.RULE_TYPE_REBATE.getValue());
        ResultVO<List<AccountBalanceRuleResp>> ruleList = accountBalanceRuleService.queryAccountBalanceRuleList(ruleReq);
        if(ruleList==null||ruleList.getResultData()==null||ruleList.getResultData().isEmpty()){
            throw new Exception("活动规则配置错误,未找到账户余额类型");
        }
        //b.根据商家ID和余额账户类型获取账本
        QueryAccountBalanceReq queryAccountBalanceReq = new QueryAccountBalanceReq();
        queryAccountBalanceReq.setCustId(req.getCustId());
        queryAccountBalanceReq.setBalanceTypeId(ruleList.getResultData().get(0).getBalanceTypeId());
        queryAccountBalanceReq.setAcctId(req.getAcctId());
        List<AccountBalanceDTO> accountBalanceDTOList = this.queryAccountBalanceList(queryAccountBalanceReq);
        boolean isAddBalance = false;
        AccountBalanceDTO currentAccountBalance = null;
//        AccountBalanceTypeDTO accountBalanceType = null;
//        AccountBalanceRuleDTO accountBalanceRule = null;
        List<AccountBalanceDetail>  detailList = new ArrayList<AccountBalanceDetail>();
        List<AccountBalanceLog>  logList = new ArrayList<AccountBalanceLog>();
        //账本不存在，则新增
        if (accountBalanceDTOList == null || accountBalanceDTOList.isEmpty()) {
            req.setBalanceTypeId(queryAccountBalanceReq.getBalanceTypeId());
            InitCreateAccountBalanceResp initResp = this.initCreateAccountBalance(req);
            currentAccountBalance = initResp.getAccountBalance();

            isAddBalance = true;
        } else {
            currentAccountBalance = accountBalanceDTOList.get(0);
        }
        Date date = new Date();
        for (AccountBalanceCalculationOrderItemReq orderItemReq : orderItemReqList) {
            //计算，会改变currentAccountBalance的值
            CalculationOrderItemResp calculationOrderItemResp = this.calculationOrderItemNoDb(currentAccountBalance, orderItemReq);
            //有返利才记录日志
            if(StringUtils.isNotEmpty(calculationOrderItemResp.getAmount())&&Long.valueOf(calculationOrderItemResp.getAmount())>0){
                AccountBalanceDetail detail = new AccountBalanceDetail();
                detail.setAccountBalanceId(currentAccountBalance.getAccountBalanceId());
                detail.setCreateStaff(req.getCreateStaff());
                detail.setAcctId(req.getAcctId());

                //操作类型，1 存（收入）；2 转（收入）；3 补（收入）；4 冲正；5 调帐
                detail.setOperType(RebateConst.Const.ACCOUNT_BALANCE_DETAIL_OPER_TYPE_ADD.getValue());
                //10余额账户、20返利账户、30价保账户，
                detail.setAcctType(RebateConst.Const.ACCOUNT_BALANCE_DETAIL_ACCT_TYPE_REBATE.getValue());

                //入账金额
                detail.setAmount(Long.valueOf(calculationOrderItemResp.getAmount()));
                //操作后账户余额
                detail.setBalance(Long.valueOf(calculationOrderItemResp.getBalance()));
                //剩余余额
                detail.setCurAmount(Long.valueOf(calculationOrderItemResp.getCurAmount()));
                //来源类型标识
                detail.setBalanceSourceTypeId(RebateConst.Const.ACCOUNT_BALANCE_DETAIL_BALANCE_SOURCE_TYPE_ID.getValue());
                detail.setOrderItemId(calculationOrderItemResp.getOrderItemId());
                //付款流水号:不填
                detail.setPaymentId("");
                detail.setOrderId(orderItemReq.getOrderId());
                detail.setProductId(orderItemReq.getProductId());
                detail.setRewardPrice(calculationOrderItemResp.getRewardPrice());
                String operIncomeId = this.accountService.getRebateNextId();
                detail.setOperIncomeId(operIncomeId);
                detail.setCurStatusDate(date);
                detail.setCreateDate(date);
                detail.setExpDate(DateUtils.strToUtilDate(RebateConst.EXP_DATE_DEF));
                detail.setEffDate(new Date());

                detail.setStatusCd(Long.valueOf(RebateConst.Const.STATUS_USE.getValue()));
                detail.setStatusDate(date);
                detailList.add(detail);

                AccountBalanceLog log = new AccountBalanceLog();
                log.setBalanceLogId(this.accountService.getRebateNextId());
                log.setAcctId(req.getAcctId());
                //余额账本标识
                log.setAccountBalanceId(currentAccountBalance.getAccountBalanceId());
                log.setOperIncomeId(operIncomeId);
                //余额来源原金额
//                log.setSrcAmount(Long.valueOf(calculationOrderItemResp.getCurAmount()));
                log.setStatusCd(String.valueOf(RebateConst.Const.STATUS_USE.getValue()));
                log.setSrcAmount(Long.valueOf(calculationOrderItemResp.getAmount()));

                log.setStatusDate(new Date());
                logList.add(log);

            }


        }
        AccountBalance saveAccountBalance = new AccountBalance();
        BeanUtils.copyProperties(currentAccountBalance, saveAccountBalance);

        //开始数据库提交

        if(!detailList.isEmpty()){
            accountBalanceDetailManager.saveBatch(detailList);
        }
        if(!logList.isEmpty()){
            accountBalanceLogManager.saveBatch(logList);
        }
        if(isAddBalance){
           accountBalanceManager.save(saveAccountBalance);
//           accountBalanceTypeService.addAccountBalanceType(accountBalanceType);
//           accountBalanceRuleService.addAccountBalanceRule(accountBalanceRule);
        }else{
            saveAccountBalance.setUpdateDate(new Date());
            saveAccountBalance.setUpdateStaff(req.getCreateStaff());
            UpdateWrapper updateWrapper = new UpdateWrapper();
            saveAccountBalance.setAcctId(null);
            updateWrapper.set(AccountBalance.FieldNames.accountBalanceId.getTableFieldName(),saveAccountBalance.getAccountBalanceId());
            accountBalanceManager.update(saveAccountBalance,updateWrapper);

        }

    }

    /**
     *
     * 获取ACCOUNT_BALANCE初始化的基本信息，同时新增账户余额类型ACCOUNT_BALANCE_TYPE,账户余额使用规则ACCOUNT_BALANCE_RULE
     * 所获取的ACCOUNT_BALANCE基本信息需要通过计算，且同时需要插入日志后才可以新增
     *
     * @param req
     * @return
     */
    private InitCreateAccountBalanceResp initCreateAccountBalance(InitCreateAccountBalanceReq req)throws Exception {

        List<AccountBalanceCalculationOrderItemReq> orderItemReqList = req.getOrderItemReqList();
        AccountBalanceDTO currentAccountBalance = null;
        //活动ID
        String actId = orderItemReqList.get(0).getActId();
        //活动名称
        String actName = orderItemReqList.get(0).getActName();


        currentAccountBalance = new AccountBalanceDTO();
        currentAccountBalance.setAccountBalanceId(accountService.getRebateNextId());
        currentAccountBalance.setAcctId(req.getAcctId());
        currentAccountBalance.setCustId(req.getCustId());
        currentAccountBalance.setBalanceTypeId(req.getBalanceTypeId());
        //账户可用余额:通过计算获取,新建的为0
        currentAccountBalance.setAmount(0L);
        currentAccountBalance.setCreateStaff(req.getCreateStaff());
        //限额类型：1 总额封顶，2  余额对象封顶 ，3   帐户封顶，4  服务封顶，5  消费比例封顶
        currentAccountBalance.setCycleType(RebateConst.Const.CYCLE_TYPE_PER.getValue());
        currentAccountBalance.setCreateStaff(req.getCreateStaff());
        currentAccountBalance.setCreateDate(new Date());
        currentAccountBalance.setStatusCd(RebateConst.Const.STATUS_USE.getValue());
        currentAccountBalance.setStatusDate(new Date());
        currentAccountBalance.setEffDate(new Date());
        currentAccountBalance.setExpDate(DateUtils.strToUtilDate(RebateConst.EXP_DATE_DEF));
        InitCreateAccountBalanceResp resp = new InitCreateAccountBalanceResp();
        resp.setAccountBalance(currentAccountBalance);

        return resp;
    }

    @Override
    public CalculationOrderItemResp calculationOrderItemNoDb(AccountBalanceDTO accountBalance, AccountBalanceCalculationOrderItemReq itemReq)
    throws Exception{

        String actId = itemReq.getActId();
        String productId = itemReq.getProductId();

        ResultVO<ActActivityProductRuleServiceResp> prouductRuleList = actActivityProductRuleService.
                queryActActivityProductRuleServiceResp(actId,productId);
        if (prouductRuleList==null||prouductRuleList.getResultData()==null) {
            throw new Exception("活动规则配置错误,未找到产品规则");
        }
        ActActivityProductRuleServiceResp actiResp =  prouductRuleList.getResultData();
        List<ActActivityProductRuleDTO> actActivityProductRuleDTOS = actiResp.getActActivityProductRuleDTOS();
        List<ActivityProductDTO>  activityRuleDTOS =  actiResp.getActivityProductDTOS();
        List<ActivityRuleDTO> activityRuleDTOList = actiResp.getActivityRuleDTOList();
        if(actActivityProductRuleDTOS==null||actActivityProductRuleDTOS.isEmpty()){
            throw new Exception("活动规则配置错误,未找到活动规则");
        }
        if(activityRuleDTOS==null||activityRuleDTOS.isEmpty()){
            throw new Exception("活动规则配置错误,未找到活动对应的产品");
        }
        if(activityRuleDTOList==null||activityRuleDTOList.isEmpty()){
            throw new Exception("活动规则配置错误,未找到活动产品规则");
        }

       //返利只有一条规则
        ActivityRuleDTO activityRuleDTO = activityRuleDTOList.get(0);
        String calculationRule = activityRuleDTO.getCalculationRule();
        RebateRuleBase rebateRuleBase = RebateRuleFactory.getRebateRuleBase(calculationRule);
        if (rebateRuleBase == null) {
            throw new Exception("返利计算规则配置错误");
        }
        rebateRuleBase.init(itemReq,activityRuleDTO,actActivityProductRuleDTOS);
        CalculationOrderItemResp resp = new CalculationOrderItemResp();
        resp.setOrderId(itemReq.getOrderId());
        resp.setOrderItemId(itemReq.getOrderItemId());
        //入账金额
        resp.setAmount(rebateRuleBase.calculation());
        //返利单价
        resp.setRewardPrice(rebateRuleBase.getRewardPrice());
        //账户可用余额
        Long amount = accountBalance.getAmount();
        String balance = String.valueOf(amount + Long.valueOf(resp.getAmount()));
        //操作后账户余额:余额加上入账金额
        resp.setBalance(balance);
        //剩余余额:上次的金额
        String curAmount = accountBalance.getAmount().toString();
        resp.setCurAmount(curAmount);
        //修改账本可用余额
        accountBalance.setAmount(Long.valueOf(balance));
        //使用上限：活动规则的返利款抵扣比例*可用余额
        //30.28%，写3028这种格式
        String deductionScale = activityRuleDTO.getDeductionScale();
        if (StringUtils.isEmpty(deductionScale)) {
            throw new Exception("活动返利款抵扣比例配置为空");
        }
        //使用上限
        accountBalance.setCycleUpper(Long.valueOf(balance) * Long.valueOf(deductionScale) / 10000);
        return resp;

    }

    @Override
    public String addAccountBalance(AccountBalanceDTO accountBalanceDTO) {
        AccountBalance accountBalance = new AccountBalance();
        BeanUtils.copyProperties(accountBalanceDTO, accountBalance);
        accountBalance.setCreateDate(new Date());
        accountBalance.setStatusCd(RebateConst.Const.STATUS_USE.getValue());
        accountBalance.setEffDate(new Date());
        //失效时间
        accountBalance.setExpDate(DateUtils.strToUtilDate(RebateConst.EXP_DATE_DEF));
        accountBalance.setStatusDate(new Date());
        boolean isSuc = accountBalanceManager.save(accountBalance);
        if (isSuc) {
            return accountBalance.getAccountBalanceId();
        }
        return null;
    }

    @Override
    public AccountBalanceStResp getBalanceSt(AccountBalanceStReq req) {
        return accountBalanceManager.getBalanceSt(req);
    }

    @Override
    public int getAccountBalanceSum(AccountBalanceStReq req) {

        return accountBalanceManager.getAccountBalanceSum(req);
    }

    @Override
    public ResultVO<Page<QueryAccountBalanceAllResp>> queryAccountBalanceAllForPage(QueryAccountBalanceAllReq req) {
        initQueryAccountBalanceAllReq(req);
        Page<QueryAccountBalanceAllResp> page = accountBalanceManager.queryAccountBalanceAllForPage(req);
        //获取供应商名称
        if (page != null && page.getRecords() != null) {
            List<QueryAccountBalanceAllResp> dataList = page.getRecords();
            for (QueryAccountBalanceAllResp queryAccountBalanceAllResp : dataList) {
                AccountBalanceRuleReq ruleReq = new AccountBalanceRuleReq();
                ruleReq.setBalanceTypeId(queryAccountBalanceAllResp.getBalanceTypeId());
                ResultVO<List<AccountBalanceRuleResp>> listResultVO = accountBalanceRuleService.queryAccountBalanceRuleList(ruleReq);

                if(listResultVO!=null&&listResultVO.getResultData()!=null&&!listResultVO.getResultData().isEmpty()){
                    AccountBalanceRuleResp rule = listResultVO.getResultData().get(0);
                    queryAccountBalanceAllResp.setSupplierId(rule.getObjId());
                    queryAccountBalanceAllResp.setBalanceTypeName(rule.getBalanceTypeName());
                }

                //卖家ID
                String supplierId = queryAccountBalanceAllResp.getSupplierId();
                if (org.apache.commons.lang.StringUtils.isNotEmpty(supplierId)) {
                    ResultVO<MerchantDTO> supplier = merchantService.getMerchantById(supplierId);
                    if (supplier != null && supplier.isSuccess() && supplier.getResultData() != null) {
                        queryAccountBalanceAllResp.setSupplierName(supplier.getResultData().getMerchantName());
                    }
                }

            }
        }


        return ResultVO.success(page);
    }
    private void initQueryAccountBalanceAllReq(QueryAccountBalanceAllReq req){
        String acctId = accountService.getAccountId(req.getCustId(),req.getAcctType());
        req.setAcctId(acctId);
         List<String> balanceTypeIdList = new ArrayList<String>();
        String supplierId = req.getSupplierId();
        boolean isQuery = false;
        if(StringUtils.isNotEmpty(supplierId)){
            isQuery = true;
            AccountBalanceRuleReq ruleReq = new AccountBalanceRuleReq();
            ruleReq.setObjId(supplierId);
            ruleReq.setRuleType(RebateConst.Const.RULE_TYPE_REBATE.getValue());
            ResultVO<List<AccountBalanceRuleResp>>  ruleListResult = accountBalanceRuleService.queryAccountBalanceRuleList(ruleReq);
            if(ruleListResult!=null&&ruleListResult.getResultData()!=null&&!ruleListResult.getResultData().isEmpty()){
                List<AccountBalanceRuleResp> ruleList = ruleListResult.getResultData();
                for (AccountBalanceRuleResp accountBalanceRuleResp : ruleList) {
                    balanceTypeIdList.add(accountBalanceRuleResp.getBalanceTypeId()) ;
                }

            }
        }
        if(isQuery&&balanceTypeIdList.isEmpty()){
            balanceTypeIdList.add("-1");
        }
        req.setBalanceTypeIdList(balanceTypeIdList);


    }
}