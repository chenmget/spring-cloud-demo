package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TagTelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.entity.TagTel;
import com.iwhalecloud.retail.goods2b.manager.TagTelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagTelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Service
public class TagTelServiceImpl implements TagTelService {

    @Autowired
    private TagTelManager tagTelManager;


    /**
     * 根据ID标签产品关系
     * @param tagTelGetByIdReq
     * @return
     */
    @Override
    public ResultVO<TagTelDTO> getTagTel(TagTelGetByIdReq tagTelGetByIdReq){
        return ResultVO.success(tagTelManager.getTagTel(tagTelGetByIdReq.getRelId()));
    }

    /**
     * 获取所有有效品牌列表
     * @return
     */
    @Override
    public ResultVO<List<TagTelDTO>> listAll(){
        return ResultVO.success(tagTelManager.listAll());
    }

    /**
     * 添加标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> addTagTel(TagTelAddReq req){
        return ResultVO.success(tagTelManager.addTagTel(req));
    }
    /**
     * 批量添加标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Boolean> batchAddTagTel(TagTelBatchAddReq req) {
        List<TagTel> tagTelList = Lists.newArrayList();
        for (String tag : req.getTagList()) {
            TagTel tagTel = new TagTel();
            tagTel.setProductId(req.getProductId());
            tagTel.setTagId(tag);
            tagTelList.add(tagTel);
        }
        tagTelManager.batchAddTagTel(tagTelList);
        return ResultVO.success(true);
    }

    /**
     * 修改标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateTagTel(TagTelUpdateReq req){
        return ResultVO.success(tagTelManager.updateTagTel(req));
    }

    /**
     * 删除标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> deleteTagTel(TagTelDeleteByIdReq req){
        return ResultVO.success(tagTelManager.deleteTagTel(req.getRelId()));
    }

    /**
     * 根据产品ID删除标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Boolean> deleteTagTelByProductId(TagTelDeleteByProductIdReq req){
        tagTelManager.deleteTagTelByProductId(req.getProductId());
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<List<TagTelDTO>> listTagByProductId(TagTelListByProductIdReq req) {
        return ResultVO.success(tagTelManager.listTagByProductId(req.getProductId()));
    }

}