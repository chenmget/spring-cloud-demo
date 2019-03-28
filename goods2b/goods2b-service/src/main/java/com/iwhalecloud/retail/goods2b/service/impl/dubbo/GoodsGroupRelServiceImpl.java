package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.goods2b.dto.GoodsGroupRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupRelAddReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsGroupRel;
import com.iwhalecloud.retail.goods2b.manager.GoodsGroupRelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsGroupRelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public int deleteGoodsGroupRel(GoodsGroupRelDTO goodsGroupRelDTO) {
        String goodsId = goodsGroupRelDTO.getGoodsId();
        return goodsGroupRelManager.deleteGoodsGroupRel(goodsId);
    }

    @Override
    public int deleteGoodsGroupRelByGroupId(GoodsGroupRelDTO goodsGroupRelDTO) {
        String groupId = goodsGroupRelDTO.getGroupId();
        return goodsGroupRelManager.deleteGoodsGroupRelByGroupId(groupId);
    }

    @Override
    public int deleteOneGoodsGroupRel(GoodsGroupRelDTO goodsGroupRelDTO) {
        String groupRelId = goodsGroupRelDTO.getGroupRelId();
        return goodsGroupRelManager.deleteOneGoodsGroupRel(groupRelId);
    }

    @Override
    public int updateGoodsGroupRel(GoodsGroupRelDTO req) {
        return goodsGroupRelManager.updateGoodsGroupRel(req);
    }

    @Override
    public List<GoodsGroupRelDTO> queryGoodsGroupRelByGoodsId(GoodsGroupRelDTO goodsGroupRelDTO) {
        String goodsId = goodsGroupRelDTO.getGoodsId();
        List<GoodsGroupRelDTO> goodsGroupRelDTOs = new ArrayList<>();
        List<GoodsGroupRel> goodsGroupRels = goodsGroupRelManager.queryGoodsGroupRelByGoodsId(goodsId);
        if(CollectionUtils.isNotEmpty(goodsGroupRels)){
            for(GoodsGroupRel goodsGroupRel: goodsGroupRels){
                if (goodsGroupRel != null){
                    GoodsGroupRelDTO goodsGroupRelDTO1 = new GoodsGroupRelDTO();
                    BeanUtils.copyProperties(goodsGroupRel, goodsGroupRelDTO1);
                    goodsGroupRelDTOs.add(goodsGroupRelDTO1);
                }
            }
        }
        return goodsGroupRelDTOs;
    }

    @Override
    public List<GoodsGroupRelDTO> queryGoodsGroupRelByGroupId(GoodsGroupRelDTO goodsGroupRelDTO) {
        String groupId = goodsGroupRelDTO.getGroupId();
        List<GoodsGroupRelDTO> goodsGroupRelDTOs = new ArrayList<>();
        List<GoodsGroupRel> goodsGroupRels = goodsGroupRelManager.queryGoodsGroupRelByGroupId(groupId);
        if(CollectionUtils.isNotEmpty(goodsGroupRels)){
            for(GoodsGroupRel goodsGroupRel: goodsGroupRels){
                if (goodsGroupRel != null){
                    GoodsGroupRelDTO goodsGroupRelDTO1 = new GoodsGroupRelDTO();
                    BeanUtils.copyProperties(goodsGroupRel, goodsGroupRelDTO1);
                    goodsGroupRelDTOs.add(goodsGroupRelDTO1);
                }
            }
        }
        return goodsGroupRelDTOs;
    }
}
