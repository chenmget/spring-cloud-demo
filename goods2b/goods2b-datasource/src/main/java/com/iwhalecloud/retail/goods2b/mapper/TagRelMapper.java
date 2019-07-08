package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelDetailListReq;
import com.iwhalecloud.retail.goods2b.dto.req.TagRelListReq;
import com.iwhalecloud.retail.goods2b.dto.resp.TagRelDetailListResp;
import com.iwhalecloud.retail.goods2b.dto.resp.TagRelListResp;
import com.iwhalecloud.retail.goods2b.entity.TagRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询产商品标签关联集合
     * @param req
     * @return
     */
    List<TagRelListResp> listTagRel(TagRelListReq req);

    /**
     * 查询产商品标签关联详情 （带出标签信息）
     * @param req
     * @return
     */
    List<TagRelDetailListResp> listTagRelDetail(@Param("req")TagRelDetailListReq req);

}