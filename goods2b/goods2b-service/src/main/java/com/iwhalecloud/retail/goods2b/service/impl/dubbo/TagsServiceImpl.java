package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsDetailDTO;
import com.iwhalecloud.retail.goods2b.dto.TagTelDTO;
import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.entity.Tags;
import com.iwhalecloud.retail.goods2b.manager.GoodsProductRelManager;
import com.iwhalecloud.retail.goods2b.manager.TagTelManager;
import com.iwhalecloud.retail.goods2b.manager.TagsManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsManager tagsManager;

    @Autowired
    private TagTelManager tagTelManager;

    @Autowired
    private GoodsProductRelManager goodsProductRelManager;


    /**
     * 查询标签列表通过goodsId
     * @param tagGetByGoodsIdReq
     * @return
     */
    @Override
    public ResultVO<List<TagsDTO>> getTagsByGoodsId(TagGetByGoodsIdReq tagGetByGoodsIdReq){
        log.info("TagsServiceImpl.getTagsByGoodsId goodsId={}", tagGetByGoodsIdReq.getGoodsId());
        return ResultVO.success(tagsManager.getTagsByGoodsId(tagGetByGoodsIdReq.getGoodsId()));
    }

    @Override
    public ResultVO<List<TagsDTO>> listProdTags(ProdTagsListReq req) {
        return ResultVO.success(tagsManager.listProdTags(req));
    }

    @Override
    public ResultVO<List<TagsDTO>> listProdTagsChannel() {
        return ResultVO.success(tagsManager.listProdTagsChannel());
    }
    
    @Override
    public ResultVO<String> addProdTags(TagsDTO tagsDTO) {
        log.info("TagsServiceImpl.addProdTags tagsDTO={}", JSON.toJSON(tagsDTO));
        if (tagsDTO == null) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        Tags tags = new Tags();
        BeanUtils.copyProperties(tagsDTO, tags);
        tagsManager.addProdTags(tags);
        return ResultVO.success(tags.getTagId());
    }

    @Override
    public ResultVO<Boolean> editProdTags(TagsDTO tagsDTO) {
        log.info("TagsServiceImpl.editProdTags tagsDTO={}", JSON.toJSON(tagsDTO));
        if (tagsDTO == null || StringUtils.isEmpty(tagsDTO.getTagId())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        Tags tags = new Tags();
        BeanUtils.copyProperties(tagsDTO, tags);
        int result = tagsManager.updateById(tags);
        return ResultVO.success(result > 0);
    }
    @Override
    public ResultVO<Boolean> deleteProdTags(ProdTagDeleteReq prodTagDeleteReq) {
        log.info("TagsServiceImpl.deleteProdTags tagId={}", prodTagDeleteReq.getTagId());
        if (StringUtils.isEmpty(prodTagDeleteReq.getTagId())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        int result = tagsManager.deleteById(prodTagDeleteReq.getTagId());
        return ResultVO.success(result > 0);
    }

    @Override
    public ResultVO<TagsDTO> getTagById(TagGetByIdReq req) {
        log.info("TagsServiceImpl.getTagById tagId={}", req.getTagId());
        if (StringUtils.isEmpty(req.getTagId())) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        Tags tags = tagsManager.getTagById(req.getTagId());
        if (tags != null) {
            TagsDTO tagsDTO = new TagsDTO();
            BeanUtils.copyProperties(tags, tagsDTO);
            return ResultVO.success(tagsDTO);
        }
        return ResultVO.successMessage("查询为空");
    }

    @Override
    public ResultVO<List<TagsDTO>> getTags(TagGetByTagReq req) {
        log.info("TagsServiceImpl.getTagById tagType={}, tagName={}", req.getTagType(), req.getTagName());
        List<Tags> tagsList = tagsManager.getTags(req.getTagType(), req.getTagName());
        List<TagsDTO> tagsDTOList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(tagsList)) {
            return ResultVO.success(tagsDTOList);
        }
        for (Tags tags : tagsList) {
            TagsDTO tagsDTO = new TagsDTO();
            BeanUtils.copyProperties(tags, tagsDTO);
            tagsDTOList.add(tagsDTO);
        }
        return ResultVO.success(tagsDTOList);
    }

    @Override
    public ResultVO<List<GoodsDetailDTO>> queryGoodsListByTag(GoodsQueryByTagsReq req) {
        log.info("TagsServiceImpl.queryGoodsForPageByTag goodsQueryByTagsReq={}", req);
        String tagId = req.getTagId();
        TagTelDTO tagTel = tagTelManager.getTagTelByTagId(tagId);
        String productId = tagTel.getProductId();
        List<GoodsDetailDTO> goodsDetailDTOS = goodsProductRelManager.qryGoodsByProductId(productId);
        return ResultVO.success(goodsDetailDTOS);
    }

}