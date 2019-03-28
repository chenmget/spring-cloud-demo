package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.TagTel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: TagTelMapper
 * @author autoCreate
 */
@Mapper
public interface TagTelMapper extends BaseMapper<TagTel>{

    /**
     * 获取所有有效品牌列表
     * @return
     */
    List<TagTel> listAll();

    void batchAddTagTel(List<TagTel> tagRelList);
}