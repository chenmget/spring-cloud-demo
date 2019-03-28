package com.iwhalecloud.retail.rights.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.rights.consts.RightsStatusConsts;
import com.iwhalecloud.retail.rights.entity.CouponDiscountRule;
import com.iwhalecloud.retail.rights.entity.MktResType;
import com.iwhalecloud.retail.rights.mapper.MktResCouponRuleMapper;
import com.iwhalecloud.retail.rights.mapper.MktResTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月22日
 * @Description:
 **/
@Component
public class MktResCouponManagerRule {

    @Resource
    private MktResCouponRuleMapper mktResCouponRuleMapper;

    @Resource
    private MktResTypeMapper mktResTypeMapper;

    public CouponDiscountRule queryCouponlistById(String mktResId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(CouponDiscountRule.FieldNames.mktResId.getTableFieldName(),mktResId);
        queryWrapper.eq(CouponDiscountRule.FieldNames.statusCd.getTableFieldName(), RightsStatusConsts.COUPON_RULE_STATUS_USED);
        return mktResCouponRuleMapper.selectOne(queryWrapper);
    }

    public MktResType queryCouponTypeById(Long mktResTypeId){
        QueryWrapper queryWrapper = new QueryWrapper();
        System.out.println("mktResTypeId-----"+mktResTypeId);
        queryWrapper.eq(MktResType.FieldNames.mktResTypeId.getTableFieldName(),mktResTypeId);
        queryWrapper.eq(MktResType.FieldNames.statusCd.getTableFieldName(), RightsStatusConsts.COUPON_STATUS_USED);
//        queryWrapper.le(MktResType.FieldNames.effDate.getTableFieldName(), " <= now()");
//        queryWrapper.ge(MktResType.FieldNames.expDate.getTableFieldName(), " >= now()");
        return mktResTypeMapper.selectOne(queryWrapper);
    }
}
