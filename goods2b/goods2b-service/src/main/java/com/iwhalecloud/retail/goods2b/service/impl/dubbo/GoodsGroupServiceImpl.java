package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.GoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupEditReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupUpdateReq;
import com.iwhalecloud.retail.goods2b.manager.GoodsGroupManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsGroupService;
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
    public int deleteGoodsGroup(GoodsGroupEditReq goodsGroupEditReq) {
        String groupId = goodsGroupEditReq.getGroupId();
        return goodsGroupManager.deleteGoodsGroup(groupId);
    }

    @Override
    public GoodsGroupDTO listGoodsGroupByGroupId(GoodsGroupEditReq goodsGroupEditReq) {
        String groupId = goodsGroupEditReq.getGroupId();
        return goodsGroupManager.listGoodsGroupByGroupId(groupId);
    }

    @Override
    public Page<GoodsGroupDTO> listGoodsGroup(GoodGroupQueryReq req) {
        return goodsGroupManager.listGoodGroup(req);
    }

    @Override
    public List<GoodsDTO> listGoodsByGoodsId(GoodsGroupEditReq goodsGroupEditReq) {
        String goodsId = goodsGroupEditReq.getGoodsId();
        return goodsGroupManager.listGoodsByGoodsId(goodsId);
    }

    @Override
    public Boolean queryGoodsGroupNameIsContains(GoodsGroupEditReq goodsGroupEditReq) {
        String groupName = goodsGroupEditReq.getName();
        int count = goodsGroupManager.queryGoodsGroupNameIsContains(groupName);
        if(count>0){
            return true;
        }
        return false;
    }


}
