package com.iwhalecloud.retail.partner.manager;

import com.iwhalecloud.retail.partner.dto.req.MerchantLimit2SaveReq;
import com.iwhalecloud.retail.partner.entity.MerchantLimit2;
import com.iwhalecloud.retail.partner.mapper.MerchantLimit2Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class MerchantLimit2Manager {
    @Resource
    private MerchantLimit2Mapper merchantLimit2Mapper;

    /**
     * 添加一个 限额
     * @param req
     * @return
     */
    public Integer saveMerchantLimit(MerchantLimit2SaveReq req) {
        MerchantLimit2 merchantLimit2 = new MerchantLimit2();
        BeanUtils.copyProperties(req, merchantLimit2);
        return merchantLimit2Mapper.insert(merchantLimit2);
    }

    /**
     * 根据商家ID 获取一个 限额
     * @param lanId
     * @return
     */
    public MerchantLimit2 getMerchantLimit(String lanId) {
        return merchantLimit2Mapper.selectById(lanId);
    }

    /**
     * 更新一个 限额
     * @param merchantLimit2
     * @return
     */
    public Integer updateMerchantLimit(MerchantLimit2 merchantLimit2) {
        return merchantLimit2Mapper.updateById(merchantLimit2);
    }
    
}
