package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.order2b.service.AdvanceOrderOpenService;

import javax.annotation.Resource;


@Service
public class AdvanceOrderDServiceImpl implements AdvanceOrderOpenService {

    @Resource
    AdvanceOrderOpenService advanceOrderOpenService;

    @Override
    public void cancelOverTimePayOrder() {
        advanceOrderOpenService.cancelOverTimePayOrder();
    }
}
