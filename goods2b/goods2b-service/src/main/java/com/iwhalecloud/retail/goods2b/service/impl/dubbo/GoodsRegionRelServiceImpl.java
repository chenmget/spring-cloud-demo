package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods2b.manager.GoodsRegionRelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRegionRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("goodsRegionRelService")
@Service
public class GoodsRegionRelServiceImpl implements GoodsRegionRelService {

    @Autowired
    private GoodsRegionRelManager goodsRegionRelManager;





}