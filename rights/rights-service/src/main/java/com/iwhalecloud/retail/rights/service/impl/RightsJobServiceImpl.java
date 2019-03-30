package com.iwhalecloud.retail.rights.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.rights.common.RightsConst;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.dto.response.CouponDiscountRuleRespDTO;
import com.iwhalecloud.retail.rights.dto.response.CouponEffExpRuleRespDTO;
import com.iwhalecloud.retail.rights.dto.response.QueryPreSubsidyCouponResqDTO;
import com.iwhalecloud.retail.rights.entity.CouponInst;
import com.iwhalecloud.retail.rights.entity.CouponInstProvRec;
import com.iwhalecloud.retail.rights.entity.MktResCouponTask;
import com.iwhalecloud.retail.rights.manager.*;
import com.iwhalecloud.retail.rights.service.RightsJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhou.zc
 * @date 2019年03月30日
 * @Description:权益定时任务服务接口
 */
@Service
@Slf4j
public class RightsJobServiceImpl implements RightsJobService{

    @Autowired
    private MktResCouponTaskManager mktResCouponTaskManager;

    @Autowired
    private CouponInstManager couponInstManager;

    @Autowired
    private CouponInstProvRecManager couponInstProvRecManager;

    @Autowired
    private CouponEffExpRuleManager couponEffExpRuleManager;

    @Autowired
    private CouponDiscountRuleManager couponDiscountRuleManager;

    @Override
    public void autoReceiveCoupon() {
        Page<MktResCouponTask> mktResCouponTaskPage = mktResCouponTaskManager.queryCouponTaskPage();
        log.info("RightsJobServiceImpl.autoReceiveCoupon  mktResCouponTaskManager.queryCouponTaskPage mktResCouponTaskPage={}", JSON.toJSON(mktResCouponTaskPage));
        List<MktResCouponTask> records = mktResCouponTaskPage.getRecords();
        //优惠券信息缓存
        Map<String, QueryPreSubsidyCouponResqDTO> couponMap = new HashMap<>();
        if (records.size() > 0) {
            records.forEach((MktResCouponTask mktResCouponTask) -> {
                String mktResId = mktResCouponTask.getMktResId();
                QueryPreSubsidyCouponResqDTO queryPreSubsidyCouponResqDTO = new QueryPreSubsidyCouponResqDTO();
                if (couponMap.containsKey(mktResId)) {
                    queryPreSubsidyCouponResqDTO = couponMap.get(mktResId);
                } else {
                    CouponEffExpRuleRespDTO expRuleOne = couponEffExpRuleManager.queryCouponEffExpRuleOne(mktResId);
                    log.info("RightsJobServiceImpl.autoReceiveCoupon couponEffExpRuleManager.queryCouponEffExpRuleOne expRuleOne={}", JSON.toJSON(expRuleOne));
                    queryPreSubsidyCouponResqDTO.setEffDate(expRuleOne.getEffDate());
                    queryPreSubsidyCouponResqDTO.setExpDate(expRuleOne.getExpDate());
                    CouponDiscountRuleRespDTO ruleRespDTO = couponDiscountRuleManager.queryCouponDiscountRuleOne(mktResId);
                    log.info("RightsJobServiceImpl.autoReceiveCoupon couponEffExpRuleManager.queryCouponEffExpRuleOne expRuleOne={}", JSON.toJSON(expRuleOne));
                    queryPreSubsidyCouponResqDTO.setDiscountValue(ruleRespDTO.getDiscountValue());
                    couponMap.put(mktResId, queryPreSubsidyCouponResqDTO);
                }
                receiveCoupon(mktResCouponTask, queryPreSubsidyCouponResqDTO);
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void receiveCoupon(MktResCouponTask mktResCouponTask,QueryPreSubsidyCouponResqDTO queryPreSubsidyCouponResqDTO){
        log.info("RightsJobServiceImpl.receiveCoupon   mktResCouponTask={},queryPreSubsidyCouponResqDTO={}",JSON.toJSON(mktResCouponTask),JSON.toJSON(queryPreSubsidyCouponResqDTO));
        try {
            /**推送优惠券*/
            CouponInst couponInst = new CouponInst();
            Long biggestInstNbr = couponInstManager.queryBiggestInstNbr();
            biggestInstNbr = biggestInstNbr == null ? 0 : biggestInstNbr;
            couponInst.setCouponInstNbr(String.valueOf(biggestInstNbr + 1));
            couponInst.setMktResId(mktResCouponTask.getMktResId());
            couponInst.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
            couponInst.setStatusDate(new Date());
            /**自动推送的创建人为-1*/
            couponInst.setCreateStaff("-1");
            couponInst.setUpdateStaff("-1");
            couponInst.setCreateDate(new Date());
            couponInst.setUpdateDate(new Date());
            couponInst.setEffDate(queryPreSubsidyCouponResqDTO.getEffDate());
            couponInst.setExpDate(queryPreSubsidyCouponResqDTO.getExpDate());
            couponInst.setCustAcctId(mktResCouponTask.getCustAcctId());
            couponInst.setCouponAmount(queryPreSubsidyCouponResqDTO.getDiscountValue().longValue());
            couponInstManager.insertCouponInst(couponInst);

            /**优惠券领取记录*/
            String couponInstId = couponInst.getCouponInstId();
            CouponInstProvRec couponInstProvRec = new CouponInstProvRec();
            couponInstProvRec.setCouponInstId(couponInstId);
            couponInstProvRec.setProvObjId(mktResCouponTask.getCustAcctId());
            couponInstProvRec.setProvObjId("-1");
            couponInstProvRec.setCreateDate(new Date());
            couponInstProvRec.setUpdateDate(new Date());
            couponInstProvRec.setProvDate(new Date());
            couponInstProvRec.setStatusDate(new Date());
            couponInstProvRec.setProvDesc("主动推送优惠券发放");
            couponInstProvRec.setCreateStaff("-1");
            couponInstProvRec.setUpdateStaff("-1");
            couponInstProvRec.setProvChannelType("1000");
            couponInstProvRec.setProvObjType(RightsStatusConsts.PROVOBJ_TYPE_CUS);
            couponInstProvRec.setStatusCd(RightsStatusConsts.RIGHTS_STATUS_UNUSED);
            couponInstProvRecManager.insertCouponInstProvRec(couponInstProvRec);

            /**更新优惠券发放记录*/
            mktResCouponTask.setStatusCd(RightsConst.TaskStatusCd.TASK_STATUS_CD_1.getType());
            mktResCouponTask.setProvRecId(couponInstProvRec.getProvRecId());
            mktResCouponTask.setStatusDate(new Date());
            mktResCouponTask.setUpdateStaff("-1");
            mktResCouponTaskManager.updateMktResCouponTask(mktResCouponTask);
        }catch (Exception e){
            log.info("RightsJobServiceImpl.receiveCoupon 主动推送优惠券生成实例失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            mktResCouponTask.setStatusCd(RightsConst.TaskStatusCd.TASK_STATUS_CD_1_.getType());
            mktResCouponTask.setStatusDate(new Date());
            mktResCouponTask.setUpdateStaff("-1");
            //保存推送异常原因的前500个字符
            mktResCouponTask.setRemark(e.getMessage().substring(0,500));
            mktResCouponTaskManager.updateMktResCouponTask(mktResCouponTask);
        }

    }
}
