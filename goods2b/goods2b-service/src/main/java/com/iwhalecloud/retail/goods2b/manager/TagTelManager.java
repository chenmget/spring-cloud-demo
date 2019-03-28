package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods2b.dto.TagTelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.TagTelAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagTelUpdateReq;
import com.iwhalecloud.retail.goods2b.entity.TagTel;
import com.iwhalecloud.retail.goods2b.mapper.TagTelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class TagTelManager {
    @Resource
    private TagTelMapper tagTelMapper;

    /**
     * 根据ID标签产品关系
     * @param telId
     * @return
     */
    public TagTelDTO getTagTel(String telId){
        TagTel t = tagTelMapper.selectById(telId);
        if(null == t){
          return null;
        }
        TagTelDTO dto = new TagTelDTO();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }

    /**
     * 获取所有有效品牌列表
     * @return
     */
    public List<TagTelDTO> listAll(){
        List<TagTel> list = tagTelMapper.listAll();
        if(null == list || list.isEmpty()){
            return null;
        }
        List<TagTelDTO> dtoList = new ArrayList<>();
        for(TagTel t : list){
            TagTelDTO dto = new TagTelDTO();
            BeanUtils.copyProperties(t, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 添加标签产品关系
     * @param req
     * @return
     */
    public Integer addTagTel(TagTelAddReq req){
        TagTel t = new TagTel();
        BeanUtils.copyProperties(req, t);
        return tagTelMapper.insert(t);
    }

    /**
     * 批量添加标签产品关系
     * @param tagTelList
     * @return
     */
    public void batchAddTagTel(List<TagTel> tagTelList){
        tagTelMapper.batchAddTagTel(tagTelList);
    }

    /**
     * 修改标签产品关系
     * @param req
     * @return
     */
    public Integer updateTagTel(TagTelUpdateReq req){
        TagTel t = new TagTel();
        BeanUtils.copyProperties(req, t);
        return tagTelMapper.updateById(t);
    }

    /**
     * 删除标签产品关系
     * @param relId
     * @return
     */
    public Integer deleteTagTel(String relId){
        return tagTelMapper.deleteById(relId);
    }

    /**
     * 删除标签产品关系
     * @param productId
     * @return
     */
    public int deleteTagTelByProductId(String productId){
        UpdateWrapper<TagTel> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("PRODUCT_ID",productId);
        return tagTelMapper.delete(updateWrapper);
    }


    /**
     * 根据产品ID查询标签
     * @return
     */
    public List<TagTelDTO> listTagByProductId(String productId) {
        QueryWrapper<TagTel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PRODUCT_ID",productId);
        List<TagTel> tagTelList = tagTelMapper.selectList(queryWrapper);
        List<TagTelDTO> tagTelDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(tagTelList)) {
            for (TagTel tagTel : tagTelList) {
                TagTelDTO tagTelDTO = new TagTelDTO();
                BeanUtils.copyProperties(tagTel, tagTelDTO);
                tagTelDTOList.add(tagTelDTO);
            }
        }
        return tagTelDTOList;
    }

    /**
     * 根据标签ID查询标签关系
     * @param tegId
     * @return
     */
    public TagTelDTO getTagTelByTagId(String tegId){
        QueryWrapper<TagTel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("TAG_ID",tegId);
        TagTel t = tagTelMapper.selectOne(queryWrapper);
        if(t == null){
            return null;
        }
        TagTelDTO dto = new TagTelDTO();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }
}
