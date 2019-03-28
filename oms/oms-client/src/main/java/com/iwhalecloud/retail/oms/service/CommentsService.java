package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.CommentsRequestDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.CommentsPageListResp;

/**
 * @Author pjq
 * @Date 2018/10/10
 **/
public interface CommentsService {
    /**
     * 添加评论
     * @param req
     */
    ResultVO<Boolean> addComments(CommentsRequestDTO req);

    /**
     *分页查询用户评论
     * @param req
     * @return
     */
    ResultVO<CommentsPageListResp> queryCommoentForPage(ListCommentsReqDTO req);
}
