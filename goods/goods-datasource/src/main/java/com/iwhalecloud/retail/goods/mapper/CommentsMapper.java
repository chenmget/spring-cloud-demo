package com.iwhalecloud.retail.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods.dto.CommentsDTO;
import com.iwhalecloud.retail.goods.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods.entity.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/12
 **/
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {
    /**
     * 查询商品评论
     * @param page
     * @param req
     * @return
     */
    IPage<Comments> commentPage(Page<Comments> page, @Param("req") ListCommentsReqDTO req);

    /**
     * 查询商品评论列表
     * @param req
     * @return
     */
    List<Comments> listComments(@Param("req") ListCommentsReqDTO req);
}
