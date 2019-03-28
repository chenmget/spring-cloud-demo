package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.TagTelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.entity.TagTel;
import com.iwhalecloud.retail.goods2b.manager.TagTelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagTelService;
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
    private TagTelManager TagTelManager;


    /**
     * 根据ID标签产品关系
     * @param tagTelGetByIdReq
     * @return
     */
    @Override
    public ResultVO<TagTelDTO> getTagTel(TagTelGetByIdReq tagTelGetByIdReq){
        return ResultVO.success(TagTelManager.getTagTel(tagTelGetByIdReq.getRelId()));
    }

    /**
     * 获取所有有效品牌列表
     * @return
     */
    @Override
    public ResultVO<List<TagTelDTO>> listAll(){
        return ResultVO.success(TagTelManager.listAll());
    }

    /**
     * 添加标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> addTagTel(TagTelAddReq req){
        return ResultVO.success(TagTelManager.addTagTel(req));
    }
    /**
     * 批量添加标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Boolean> batchAddTagTel(TagTelBatchAddReq req) {
        List<TagTel> TagTelList = Lists.newArrayList();
        for (String tag : req.getTagList()) {
            TagTel TagTel = new TagTel();
            TagTel.setProductId(req.getProductId());
            TagTel.setTagId(tag);
            TagTelList.add(TagTel);
        }
        TagTelManager.batchAddTagTel(TagTelList);
        return ResultVO.success(true);
    }

    /**
     * 修改标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateTagTel(TagTelUpdateReq req){
        return ResultVO.success(TagTelManager.updateTagTel(req));
    }

    /**
     * 删除标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> deleteTagTel(TagTelDeleteByIdReq req){
        return ResultVO.success(TagTelManager.deleteTagTel(req.getRelId()));
    }

    /**
     * 根据产品ID删除标签产品关系
     * @param req
     * @return
     */
    @Override
    public ResultVO<Boolean> deleteTagTelByProductId(TagTelDeleteByProductIdReq req){
        TagTelManager.deleteTagTelByProductId(req.getProductId());
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<List<TagTelDTO>> listTagByProductId(TagTelListByProductIdReq req) {
        return ResultVO.success(TagTelManager.listTagByProductId(req.getProductId()));
    }

}