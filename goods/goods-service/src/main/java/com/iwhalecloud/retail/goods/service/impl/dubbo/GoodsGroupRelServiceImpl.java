package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupRelAddReq;
import com.iwhalecloud.retail.goods.manager.GoodsGroupRelManager;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsGroupRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author My
 * @Date 2018/11/5
 **/
@Slf4j
@Component("goodsGroupRelService")
@Service
public class GoodsGroupRelServiceImpl implements GoodsGroupRelService {

    @Autowired
    private GoodsGroupRelManager goodsGroupRelManager;

    @Override
    public int insertGoodsGroupRel(GoodsGroupRelAddReq req) {
        return goodsGroupRelManager.addGoodsGroupRel(req);
    }
}
