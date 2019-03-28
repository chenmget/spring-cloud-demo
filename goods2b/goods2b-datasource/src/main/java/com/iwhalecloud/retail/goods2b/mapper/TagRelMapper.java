package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.TagRel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ProdTagRelMapper
 * @author autoCreate
 */
@Mapper
public interface TagRelMapper extends BaseMapper<TagRel>{

    /**
     * 获取所有有效品牌列表
     * @return
     */
    public List<TagRel> listAll();

    public void batchAddTagRel(List<TagRel> tagRelList);
}