package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProdTagsListReq;
import com.iwhalecloud.retail.goods2b.entity.Tags;
import com.iwhalecloud.retail.goods2b.mapper.TagsMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class TagsManager {
   @Resource
    private TagsMapper tagsMapper;
    /**
     * 查询标签列表通过goodsId
     * @param goodsId
     * @return
     */
    public List<TagsDTO> getTagsByGoodsId(String goodsId){
        return tagsMapper.getTagsByGoodsId(goodsId);
    }

    public List<TagsDTO> listProdTags(ProdTagsListReq req){
        return tagsMapper.listProdTags(req);
    }

    public List<TagsDTO> listProdTagsChannel(){
        return tagsMapper.listProdTagsChannel();
    }
    
    public int addProdTags(Tags tags) {
        tags.setIsDeleted(GoodsConst.NO_DELETE);
        tags.setCreateDate(new Date());
        tags.setUpdateDate(new Date());
        return tagsMapper.insert(tags);
    }

    public int updateById(Tags tags) {
        tags.setUpdateDate(new Date());
        return tagsMapper.updateById(tags);
    }

    public int deleteById(String tagId) {
        UpdateWrapper<Tags> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("TAG_ID",tagId);
        Tags tags = new Tags();
        tags.setIsDeleted(GoodsConst.DELETE);
        return tagsMapper.update(tags, updateWrapper);
    }

    public Tags getTagById(String tagId) {
        Tags tags = tagsMapper.selectById(tagId);
        if (GoodsConst.DELETE.equals(tags.getIsDeleted())) {
            return null;
        }
        return tags;
    }

    public List<Tags> getTags(String tagType, String tagName) {
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("IS_DELETED", GoodsConst.NO_DELETE);
        if (!StringUtils.isEmpty(tagType)) {
            queryWrapper.eq("TAG_TYPE", tagType);
        } else {
            //tagsType不传默认查全部 01、03 不查渠道的
            queryWrapper.in("TAG_TYPE", "01", "03");
        }
        if (!StringUtils.isEmpty(tagName)) {
            queryWrapper.like("TAG_NAME", tagName);
        }
        List<Tags> tags = tagsMapper.selectList(queryWrapper);
        return tags;
    }
}
