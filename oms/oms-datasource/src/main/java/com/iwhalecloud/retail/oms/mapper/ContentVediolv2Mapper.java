package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.iwhalecloud.retail.oms.dto.ContentVediolv2DTO;
import com.iwhalecloud.retail.oms.entity.ContentVediolv2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Class: ContentVediolv2Mapper
 * @author autoCreate
 */
@Mapper
public interface ContentVediolv2Mapper extends BaseMapper<ContentVediolv2>{

    public  List<ContentVediolv2DTO> queryContentVediolTwoList(List<Long> matids);

    public  List<ContentVediolv2DTO> queryContentVediolByUpmatid(List<Long> list);

    /**
     * 根据商品ID集合查询内容ID集合
     * @param productIds
     * @return
     */
    public List<Long> queryVideolv2ContentIdsByProductIds(@Param("productIds") List<String> productIds);

}