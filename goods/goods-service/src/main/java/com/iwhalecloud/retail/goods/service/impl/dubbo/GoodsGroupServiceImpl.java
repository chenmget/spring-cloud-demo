package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.GoodsDTO;
import com.iwhalecloud.retail.goods.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupAddReq;
import com.iwhalecloud.retail.goods.dto.req.GoodsGroupUpdateReq;
import com.iwhalecloud.retail.goods.manager.GoodsGroupManager;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/5
 **/
@Slf4j
@Component("goodsGroupService")
@Service
public class GoodsGroupServiceImpl implements GoodsGroupService {

    @Autowired
    private GoodsGroupManager goodsGroupManager;
    @Override
    public int insertGoodsGroup(GoodsGroupAddReq req) {
        return goodsGroupManager.addGoodsGroup(req);
    }

    @Override
    public int updateGoodsGroup(GoodsGroupUpdateReq req) {
        return goodsGroupManager.updateGoodsGroup(req);
    }

    @Override
    public int deleteGoodsGroup(String groupId) {
        return goodsGroupManager.deleteGoodsGroup(groupId);
    }

    @Override
    public GoodsGroupDTO listGoodsGroupByGroupId(String groupId) {
        return goodsGroupManager.listGoodsGroupByGroupId(groupId);
    }

    @Override
    public Page<GoodsGroupDTO> listGoodsGroup(GoodGroupQueryReq req) {
        return goodsGroupManager.listGoodGroup(req);
    }

    @Override
    public List<GoodsDTO> listGoodsByGoodsId(String goodsId) {
        return goodsGroupManager.listGoodsByGoodsId(goodsId);
    }

    @Override
    public Boolean queryGoodsGroupNameIsContains(String groupName) {
        int count = goodsGroupManager.queryGoodsGroupNameIsContains(groupName);
        if(count>0){
            return true;
        }
        return false;
    }


}
