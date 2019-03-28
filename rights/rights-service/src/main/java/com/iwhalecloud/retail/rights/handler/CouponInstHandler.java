package com.iwhalecloud.retail.rights.handler;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.rights.entity.CouponDiscountRule;
import com.iwhalecloud.retail.rights.entity.CouponInst;
import com.iwhalecloud.retail.rights.entity.MktResCoupon;
import com.iwhalecloud.retail.rights.manager.CouponDiscountRuleManager;
import com.iwhalecloud.retail.rights.manager.CouponInstManager;
import com.iwhalecloud.retail.rights.manager.MktResCouponManager;
import com.iwhalecloud.retail.rights.model.MktResCouponModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CouponInstHandler {

    @Autowired
    private CouponInstManager couponInstManager;

    @Autowired
    private MktResCouponManager mktResCouponManager;

    @Autowired
    private CouponDiscountRuleManager couponDiscountRuleManager;

    /**
     * 获取优惠券的信息
     *
     * @param couponInstIdsList 优惠券实例Id列表
     * @return 优惠券信息列表
     */
    public ResultVO<List<MktResCouponModel>> getMktResMessage(List<String> couponInstIdsList) {
        List<MktResCouponModel> mktResCouponModels = new ArrayList<>();
        // 优惠券实例信息
        List<CouponInst> couponInsts = couponInstManager.queryResIdByInstIds(couponInstIdsList);
        List<String> mktResIds = new ArrayList<>();
        for (CouponInst inst : couponInsts) {
            mktResIds.add(inst.getMktResId());
        }
        // 优惠券信息
        List<MktResCoupon> mktResCouponList = mktResCouponManager.queryMktResCouponByIds(mktResIds);
        // 优惠券规则信息
        List<CouponDiscountRule> discountRules = couponDiscountRuleManager.queryDiscountRule(mktResIds);
        for (CouponInst couponInst : couponInsts) {
            MktResCouponModel model = new MktResCouponModel();
            String mktResId = couponInst.getMktResId();
            model.setCouponInstId(couponInst.getCouponInstId());
            model.setMktResId(mktResId);
            model.setMktResNbr(couponInst.getCouponInstNbr());
            for (MktResCoupon mktResCoupon : mktResCouponList) {
                if (mktResId.equals(mktResCoupon.getMktResId())) {
                    model.setMktResName(mktResCoupon.getMktResName());
                    mktResCouponModels.add(model);
                    break;
                }
            }
            for (CouponDiscountRule discountRule: discountRules) {
                if (mktResId.equals(discountRule.getMktResId())) {
                    model.setDiscountRule(discountRule);
                    break;
                }
            }
        }
        return ResultVO.success(mktResCouponModels);
    }
}
