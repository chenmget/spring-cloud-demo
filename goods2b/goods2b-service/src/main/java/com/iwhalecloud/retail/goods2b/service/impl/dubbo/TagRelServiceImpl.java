package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.entity.TagRel;
import com.iwhalecloud.retail.goods2b.manager.TagRelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Service
public class TagRelServiceImpl implements TagRelService {

    @Autowired
    private TagRelManager tagRelManager;


    /**
     * 根据ID标签商品关系
     * @param relGetByIdReq
     * @return
     */
    @Override
    public ResultVO<TagRelDTO> getTagRel(TagRelGetByIdReq relGetByIdReq){
        return ResultVO.success(tagRelManager.getTagRel(relGetByIdReq.getRelId()));
    }

    /**
     * 获取所有有效品牌列表
     * @return
     */
    @Override
    public ResultVO<List<TagRelDTO>> listAll(){
        return ResultVO.success(tagRelManager.listAll());
    }

    /**
     * 添加标签商品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> addTagRel(TagRelAddReq req){
        return ResultVO.success(tagRelManager.addTagRel(req));
    }
    /**
     * 批量添加标签商品关系
     * @param relBatchAddReq
     * @return
     */
    @Override
    public ResultVO<Boolean> batchAddTagRel(TagRelBatchAddReq relBatchAddReq) {
        List<TagRel> tagRelList = Lists.newArrayList();
        for (String tag : relBatchAddReq.getTagList()) {
            TagRel tagRel = new TagRel();
            tagRel.setProductBaseId(relBatchAddReq.getProductBaseId());
            tagRel.setTagId(tag);
            tagRelList.add(tagRel);
        }
        boolean saveFlag = tagRelManager.saveBatch(tagRelList);
        return ResultVO.success(saveFlag);
    }

    /**
     * 修改标签商品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateTagRel(TagRelUpdateReq req){
        return ResultVO.success(tagRelManager.updateTagRel(req));
    }

    /**
     * 删除标签商品关系
     * @param relDeleteByIdReq
     * @return
     */
    @Override
    public ResultVO<Integer> deleteTagRel(TagRelDeleteByIdReq relDeleteByIdReq){
        return ResultVO.success(tagRelManager.deleteTagRel(relDeleteByIdReq.getRelId()));
    }

    /**
     * 根据商品ID删除标签商品关系
     * @param relDeleteByGoodsIdReq
     * @return
     */
    @Override
    public ResultVO<Boolean> deleteTagRelByGoodsId(TagRelDeleteByGoodsIdReq relDeleteByGoodsIdReq){
        tagRelManager.deleteTagRelByProductBaseId(relDeleteByGoodsIdReq.getProductBaseId());
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<List<TagRelDTO>> listTagByGoodsId(TagRelListByGoodsIdReq relListByGoodsIdReq) {
        return ResultVO.success(tagRelManager.listTagByProductBaseId(relListByGoodsIdReq.getProductBaseId()));
    }

}