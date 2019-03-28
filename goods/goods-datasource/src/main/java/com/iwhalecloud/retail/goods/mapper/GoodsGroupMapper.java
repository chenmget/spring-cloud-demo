package com.iwhalecloud.retail.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.GoodsDTO;
import com.iwhalecloud.retail.goods.dto.GoodsGroupDTO;
import com.iwhalecloud.retail.goods.dto.req.GoodGroupQueryReq;
import com.iwhalecloud.retail.goods.entity.GoodsGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/5
 **/
@Mapper
public interface GoodsGroupMapper extends BaseMapper<GoodsGroup> {
    /**
     * 通过商品组ID查询商品列表
     * @param groupId
     * @return
     */
    List<GoodsDTO>   listGoodsByGroupId(String groupId);

    /**
     *查询商品组列表
     * @param req
     * @return
     */
    Page<GoodsGroupDTO> listGoodGroup(Page<GoodsGroupDTO> page,@Param("req") GoodGroupQueryReq req);
}
