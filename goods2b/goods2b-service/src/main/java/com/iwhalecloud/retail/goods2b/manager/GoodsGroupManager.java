package com.iwhalecloud.retail.goods2b.manager;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.dto.GoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsGroupUpdateReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsGroup;
import com.iwhalecloud.retail.goods2b.entity.GoodsGroupRel;
import com.iwhalecloud.retail.goods2b.mapper.GoodsGroupMapper;
import com.iwhalecloud.retail.goods2b.mapper.GoodsGroupRelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/5
 **/
@Component
public class GoodsGroupManager {
    @Resource
    private GoodsGroupMapper goodsGroupMapper;
    @Resource
    private GoodsGroupRelMapper goodsGroupRelMapper;

    /**
     * 添加商品组
     * @param req
     * @return
     */
    public int addGoodsGroup(GoodsGroupAddReq req){
        GoodsGroup goodsGroup = new GoodsGroup();
        BeanUtils.copyProperties(req, goodsGroup);
        Timestamp date=new Timestamp(System.currentTimeMillis());
        goodsGroup.setCreateTime(date);
        int num = goodsGroupMapper.insert(goodsGroup);
        if(CollectionUtils.isNotEmpty(req.getGoodsIds())){
            batchInsert(req.getGoodsIds(),goodsGroup.getGroupId(),req.getSourceFrom());
        }
        return num;
    }

    /**
     * 修改商品组
     * @param req
     * @return
     */
    public int updateGoodsGroup(GoodsGroupUpdateReq req){
        GoodsGroup goodsGroup = new GoodsGroup();
        BeanUtils.copyProperties(req, goodsGroup);
        int num = goodsGroupMapper.updateById(goodsGroup);
        QueryWrapper<GoodsGroupRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",req.getGroupId());
        goodsGroupRelMapper.delete(queryWrapper);
        if(CollectionUtils.isNotEmpty(req.getGoodsIds())){
            batchInsert(req.getGoodsIds(),goodsGroup.getGroupId(),req.getSourceFrom());
        }
        return num;
    }

    /**
     * 删除商品组
     * @param groupId
     * @return
     */
    public int deleteGoodsGroup(String groupId){
        QueryWrapper<GoodsGroupRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        goodsGroupRelMapper.delete(queryWrapper);
        return goodsGroupMapper.deleteById(groupId);
    }
    /**
     * 通过商品ID获取商品组列表
     * @param groupId
     * @return
     */
    public GoodsGroupDTO listGoodsGroupByGroupId(String groupId){
        GoodsGroupDTO goodsGroupDTO = new GoodsGroupDTO();
        GoodsGroup goodsGroup = goodsGroupMapper.selectById(groupId);
        BeanUtils.copyProperties(goodsGroup, goodsGroupDTO);
        List<GoodsDTO> goods = goodsGroupMapper.listGoodsByGroupId(groupId);
        goodsGroupDTO.setGoods(goods);
        return goodsGroupDTO;
    }

    /**
     * 查询商品组列表
     * @param req
     * @return
     */
    public Page<GoodsGroupDTO> listGoodGroup(GoodGroupQueryReq req){
        Page<GoodsGroupDTO> page = new Page<>(req.getPageNo(),req.getPageSize());
        page = goodsGroupMapper.listGoodGroup(page,req);
        List<GoodsGroupDTO> list = page.getRecords();
        if(CollectionUtils.isEmpty(list)){
            return page;
        }
        for(GoodsGroupDTO groupDTO:list){
            int goodsNum = goodsGroupRelMapper.getGoodsNum(groupDTO.getGroupId());
            groupDTO.setGoodsNum(goodsNum);
        }
        return page;
    }

    public void batchInsert(List<String> goodsIdList,String groupId,String sourceFrom){
        for (String str:goodsIdList){
            GoodsGroupRel rel = new GoodsGroupRel();
            rel.setGoodsId(str);
            rel.setGroupId(groupId);
            goodsGroupRelMapper.insert(rel);
        }
    }

    public List<GoodsDTO> listGoodsByGoodsId(String goodsId){
        QueryWrapper<GoodsGroupRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsId);
        List<GoodsGroupRel> relList = goodsGroupRelMapper.selectList(queryWrapper);
        List<GoodsDTO> list = Lists.newArrayList();
        if(null == relList){
            return list;
        }
        for(GoodsGroupRel groupRel:relList){
            List<GoodsDTO> tempList = goodsGroupMapper.listGoodsByGroupId(groupRel.getGroupId());
            list.addAll(tempList);
        }
        list = removeDuplicate(list);
        return list;
    }

    /**
     * 删除重复元素
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    public int queryGoodsGroupNameIsContains(String groupName){
        QueryWrapper<GoodsGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_name", groupName);
        return goodsGroupMapper.selectCount(queryWrapper);
    }
}
