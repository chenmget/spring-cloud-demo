package com.iwhalecloud.retail.partner.manager;

import com.iwhalecloud.retail.partner.dto.req.MerchantLimitSaveReq;
import com.iwhalecloud.retail.partner.entity.MerchantLimit;
import com.iwhalecloud.retail.partner.mapper.MerchantLimitMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MerchantLimitManager{
    @Resource
    private MerchantLimitMapper merchantLimitMapper;

    /**
     * 添加一个 商家限额
     * @param req
     * @return
     */
    public Integer saveMerchantLimit(MerchantLimitSaveReq req) {
        MerchantLimit merchantLimit = new MerchantLimit();
        BeanUtils.copyProperties(req, merchantLimit);
        return merchantLimitMapper.insert(merchantLimit);
    }

    /**
     * 根据商家ID 获取一个 商家限额
     * @param merchantId
     * @return
     */
    public MerchantLimit getMerchantLimit(String merchantId) {
        return merchantLimitMapper.selectById(merchantId);
    }

    /**
     * 更新一个 商家限额
     * @param merchantLimit
     * @return
     */
    public Integer updateMerchantLimit(MerchantLimit merchantLimit) {
        return merchantLimitMapper.updateById(merchantLimit);
    }
    
}
