package com.iwhalecloud.retail.order2b.manager;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.order2b.entity.PurApplyItemDetail;
import com.iwhalecloud.retail.order2b.mapper.PurApplyItemDetailMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/24 20:01
 * @description 采购管理-采购申请单项明细
 */

@Component
public class PurApplyItemDetailManager extends ServiceImpl<PurApplyItemDetailMapper, PurApplyItemDetail> {

    @Resource
    private PurApplyItemDetailMapper purApplyItemDetailMapper;
}

