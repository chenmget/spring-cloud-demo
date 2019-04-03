package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.dto.request.AutoPushCouponReq;
import com.iwhalecloud.retail.rights.dto.response.CouponSupplyRuleRespDTO;
import com.iwhalecloud.retail.rights.entity.MktResCoupon;
import com.iwhalecloud.retail.rights.entity.MktResCouponTask;
import com.iwhalecloud.retail.rights.manager.CouponSupplyRuleManager;
import com.iwhalecloud.retail.rights.manager.MktResCouponManager;
import com.iwhalecloud.retail.rights.manager.MktResCouponTaskManager;
import com.iwhalecloud.retail.rights.service.MktResCouponTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
@Component("mktResCouponTaskService")
public class MktResCouponTaskServiceImpl implements MktResCouponTaskService {

    @Autowired
    private MktResCouponTaskManager mktResCouponTaskManager;

    @Autowired
    private MktResCouponManager mktResCouponManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Async
    public void addMktResCouponTask(AutoPushCouponReq autoPushCouponReq) {
        log.info("MktResCouponTaskServiceImpl.addMktResCouponTask --> autoPushCouponReq={}", JSON.toJSON(autoPushCouponReq));
        List<String> merchantIds = autoPushCouponReq.getMerchantIds();
        List<CouponSupplyRuleRespDTO> couponSupplyRuleRespDTOS = mktResCouponManager.queryAutoPushCoupon(autoPushCouponReq.getMarketingActivityId());
        log.info("MktResCouponTaskServiceImpl.addMktResCouponTask mktResCouponManager.queryAutoPushCoupon --> couponSupplyRuleRespDTOS={}", JSON.toJSON(couponSupplyRuleRespDTOS));
        try {
            for (CouponSupplyRuleRespDTO couponSupplyRuleRespDTO : couponSupplyRuleRespDTOS) {
                Long maxNum = couponSupplyRuleRespDTO.getMaxNum();
                Long supplyNum = couponSupplyRuleRespDTO.getSupplyNum();
                String numLimitFlg = couponSupplyRuleRespDTO.getNumLimitFlg();
                if (RightsConst.LimitFlg.LIMIT_FLG_0.getType().equals(numLimitFlg) || maxNum >= supplyNum * merchantIds.size()) {
                    //批量加入数据
                    List<MktResCouponTask> mktResCouponTasks = Lists.newArrayList();
                    merchantIds.forEach(e->{
                        for (int i = 0; i < supplyNum; i++) {
                            MktResCouponTask mktResCouponTask = new MktResCouponTask();
                            mktResCouponTask.setMarketingActivityId(autoPushCouponReq.getMarketingActivityId());
                            mktResCouponTask.setMktResId(couponSupplyRuleRespDTO.getMktResId());
                            //主动推送的创建人暂定-1
                            mktResCouponTask.setCreateStaff("-1");
                            mktResCouponTask.setCreateDate(new Date());
                            mktResCouponTask.setStatusCd(RightsConst.TaskStatusCd.TASK_STATUS_CD_0.getType());
                            mktResCouponTask.setStatusDate(new Date());
                            mktResCouponTask.setCustAcctId(e);
                            mktResCouponTasks.add(mktResCouponTask);
                        }
                    });
                    mktResCouponTaskManager.saveBatch(mktResCouponTasks);
                } else {
                    //推送优惠券的总数小于商家数量和领取数量的乘积
                    MktResCouponTask mktResCouponTask = new MktResCouponTask();
                    mktResCouponTask.setMarketingActivityId(autoPushCouponReq.getMarketingActivityId());
                    mktResCouponTask.setMktResId(couponSupplyRuleRespDTO.getMktResId());
                    //主动推送的创建人暂定-1
                    mktResCouponTask.setCreateStaff("-1");
                    mktResCouponTask.setCreateDate(new Date());
                    mktResCouponTask.setCustAcctId("-1");
                    mktResCouponTask.setStatusCd(RightsConst.TaskStatusCd.TASK_STATUS_CD_1_.getType());
                    mktResCouponTask.setStatusDate(new Date());
                    mktResCouponTask.setRemark("优惠券总量不足以推送给各个商家");
                    mktResCouponTaskManager.insetMktResCouponTask(mktResCouponTask);
                }
            }
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info("MktResCouponTaskServiceImpl.addMktResCouponTask 推送优惠券任务入库失败",e);
            MktResCouponTask mktResCouponTask = new MktResCouponTask();
            mktResCouponTask.setMarketingActivityId(autoPushCouponReq.getMarketingActivityId());
            //推送入库失败记录
            mktResCouponTask.setMktResId("-1");
            //主动推送的创建人暂定-1
            mktResCouponTask.setCreateStaff("-1");
            mktResCouponTask.setCreateDate(new Date());
            mktResCouponTask.setRemark(e.getMessage().substring(0,500));
            mktResCouponTaskManager.insetMktResCouponTask(mktResCouponTask);
        }
    }
}