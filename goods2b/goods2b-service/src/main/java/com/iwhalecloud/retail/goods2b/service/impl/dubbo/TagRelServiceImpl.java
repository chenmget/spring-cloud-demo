package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.dto.resp.TagRelListResp;
import com.iwhalecloud.retail.goods2b.entity.TagRel;
import com.iwhalecloud.retail.goods2b.manager.ProductManager;
import com.iwhalecloud.retail.goods2b.manager.TagRelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@Service
public class TagRelServiceImpl implements TagRelService {

    @Autowired
    private TagRelManager tagRelManager;

    @Autowired
    private ProductManager productManager;


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
        log.info("TagRelServiceImpl.batchAddTagRel()  req:{}", JSON.toJSONString(relBatchAddReq));

        if (CollectionUtils.isEmpty(relBatchAddReq.getTagList())) {
            return ResultVO.error("标签不能为空");
        }

        List<TagRel> tagRelList = handleTagRelList(relBatchAddReq);
        if (CollectionUtils.isEmpty(tagRelList)) {
            return ResultVO.error("数据有误");
        }

        boolean saveFlag = tagRelManager.saveBatch(tagRelList);

        log.info("TagRelServiceImpl.batchAddTagRel()  处理结果saveFlag:{}", JSON.toJSONString(saveFlag));
        return ResultVO.success(saveFlag);
    }

    /**
     * 根据参数处理 批量插入的 标签关联关系 列表
     * @param relBatchAddReq
     * @return
     */
    private List<TagRel> handleTagRelList(TagRelBatchAddReq relBatchAddReq) {

        List<String> tagList = relBatchAddReq.getTagList();
        String productId = relBatchAddReq.getProductId();
        String productBaseId = relBatchAddReq.getProductBaseId();

        List<TagRel> tagRelList = Lists.newArrayList();

        if (!StringUtils.isEmpty(productBaseId) && StringUtils.isEmpty(productId)) {
            // product_base_id不空 product_id为空 ： 根据product_base_id 获取 product_id集合

            ProductListReq productListReq = new ProductListReq();
            productListReq.setProductBaseId(productBaseId);
            List<String> productIdList = productManager.listProductId(productListReq);
            if (CollectionUtils.isEmpty(productIdList)) {
                return Lists.newArrayList();
            }
            for (String tag : tagList) {
                for (String prodId : productIdList) {
                    TagRel tagRel = new TagRel();
                    tagRel.setProductBaseId(productBaseId);
                    tagRel.setProductId(prodId);
                    tagRel.setTagId(tag);
                    tagRelList.add(tagRel);
                }
            }

        } else if (StringUtils.isEmpty(productBaseId) && !StringUtils.isEmpty(productId)) {
            // product_base_id为空 product_id不为空 ： 根据product_id 获取 product_base_id

            ProductResp productResp = productManager.getProduct(productId);
            if (Objects.isNull(productResp)) {
                return Lists.newArrayList();
            }
            for (String tag : tagList) {
                TagRel tagRel = new TagRel();
                tagRel.setProductBaseId(productResp.getProductBaseId());
                tagRel.setProductId(productId);
                tagRel.setTagId(tag);
                tagRelList.add(tagRel);
            }

        } else if (StringUtils.isEmpty(productBaseId) && StringUtils.isEmpty(productId)) {
            // product_base_id不为空 product_id不为空

            for (String tag : relBatchAddReq.getTagList()) {
                TagRel tagRel = new TagRel();
                tagRel.setProductBaseId(productBaseId);
                tagRel.setProductId(productId);
                tagRel.setTagId(tag);
                tagRelList.add(tagRel);
            }
        }

        return tagRelList;
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

    @Override
    public ResultVO<List<TagRelListResp>> listTagRel(TagRelListReq req) {
        return ResultVO.success(tagRelManager.listTagRel(req));
    }

    @Override
    public ResultVO<Boolean> batchAddTagRelProductId(TagRelBatchAddReq req) {
        log.info("TagRelServiceImpl.batchAddTagRelProductId req={}", JSON.toJSONString(req));

        if (CollectionUtils.isEmpty(req.getTagList())) {
            return ResultVO.error("标签不能为空");
        }

        List<TagRel> tagRelList = handleTagRelList(req);
        if (CollectionUtils.isEmpty(tagRelList)) {
            return ResultVO.error("数据有误");
        }

        boolean saveFlag = tagRelManager.saveBatch(tagRelList);

//        List<TagRel> tagRelList = Lists.newArrayList();
//        for (String tag : req.getTagList()) {
//            TagRel tagRel = new TagRel();
//            tagRel.setProductId(req.getProductId());
//            tagRel.setTagId(tag);
//            tagRelList.add(tagRel);
//        }
//        boolean saveFlag = tagRelManager.saveBatch(tagRelList);

        log.info("TagRelServiceImpl.batchAddTagRelProductId saveFlag={}", saveFlag);
        return ResultVO.success(saveFlag);
    }


    @Override
    public ResultVO<Boolean> deleteTagRelByProductId(TagRelDeleteByGoodsIdReq req){
        log.info("TagRelServiceImpl.deleteTagRelByProductId req={}", JSON.toJSONString(req));
        tagRelManager.deleteTagRelByProductId(req.getProductId());
        return ResultVO.success(true);
    }
}