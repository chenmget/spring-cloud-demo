package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.dto.TagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelListReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.TagRelListResp;
import com.iwhalecloud.retail.goods2b.entity.TagRel;
import com.iwhalecloud.retail.goods2b.mapper.TagRelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class TagRelManager extends ServiceImpl<TagRelMapper,TagRel> {
    @Resource
    private TagRelMapper tagRelMapper;

    public void addTagRel(String[] tagIds, String productBaseId) {
        if (tagIds != null && tagIds.length > 0) {
            for (String tagId : tagIds) {
                TagRel tagRel = new TagRel();
                tagRel.setTagId(tagId);
                tagRel.setProductBaseId(productBaseId);
                tagRelMapper.insert(tagRel);
            }
        }
    }

    /**
     * 根据ID标签商品关系
     * @param relId
     * @return
     */
    public TagRelDTO getTagRel(String relId){
        TagRel t = tagRelMapper.selectById(relId);
        if(null == t){
          return null;
        }
        TagRelDTO dto = new TagRelDTO();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }

    /**
     * 获取所有有效品牌列表
     * @return
     */
    public List<TagRelDTO> listAll(){
        List<TagRel> list = tagRelMapper.listAll();
        if(null == list || list.isEmpty()){
            return null;
        }
        List<TagRelDTO> dtoList = new ArrayList<>();
        for(TagRel t : list){
            TagRelDTO dto = new TagRelDTO();
            BeanUtils.copyProperties(t, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 添加标签商品关系
     * @param req
     * @return
     */
    public Integer addTagRel(TagRelAddReq req){
        TagRel t = new TagRel();
        BeanUtils.copyProperties(req, t);
        return tagRelMapper.insert(t);
    }

//    /**
//     * 批量添加标签商品关系
//     * @param tagRelList
//     * @return
//     */
//    public void batchAddTagRel(List<TagRel> tagRelList){
//        tagRelMapper.batchAddTagRel(tagRelList);
//    }

    /**
     * 修改标签商品关系
     * @param req
     * @return
     */
    public Integer updateTagRel(TagRelUpdateReq req){
        TagRel t = new TagRel();
        BeanUtils.copyProperties(req, t);
        return tagRelMapper.updateById(t);
    }

    /**
     * 删除标签商品关系
     * @param relId
     * @return
     */
    public Integer deleteTagRel(String relId){
        return tagRelMapper.deleteById(relId);
    }

    /**
     * 删除标签商品关系
     * @param productBaseId
     * @return
     */
    public int deleteTagRelByProductBaseId(String productBaseId){
        UpdateWrapper<TagRel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PRODUCT_BASE_ID",productBaseId);
        return tagRelMapper.delete(updateWrapper);
    }


    /**
     * 根据商品ID查询标签
     * @return
     */
    public List<TagRelDTO> listTagByProductBaseId(String productBaseId) {
        QueryWrapper<TagRel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PRODUCT_BASE_ID",productBaseId);
        List<TagRel> tagRelList = tagRelMapper.selectList(queryWrapper);
        List<TagRelDTO> tagRelDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(tagRelList)) {
            for (TagRel tagRel : tagRelList) {
                TagRelDTO tagRelDTO = new TagRelDTO();
                BeanUtils.copyProperties(tagRel, tagRelDTO);
                tagRelDTOList.add(tagRelDTO);
            }
        }
        return tagRelDTOList;
    }

    /**
     * 查询产商品标签关联集合
     * @param req
     * @return
     */
    public List<TagRelListResp> listTagRel(TagRelListReq req){
        return tagRelMapper.listTagRel(req);
    }
}
