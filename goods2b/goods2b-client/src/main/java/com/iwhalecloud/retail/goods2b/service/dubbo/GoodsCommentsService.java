package com.iwhalecloud.retail.goods2b.service.dubbo;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.AddCommentsReqDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CommentsGoodsRateReqDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods2b.dto.req.QueryCommentReq;
import com.iwhalecloud.retail.goods2b.dto.resp.CommentsPageListResp;

import java.util.List;

public interface GoodsCommentsService {
    /**
     * 新增评论、咨询或其回复
     * @param req
     * @return
     */
    int addComments(AddCommentsReqDTO req);

    /**
     *商品评论列表
     * @param reqList
     * @return
     */
    ResultVO<CommentsPageListResp> listGoodsComments(ListCommentsReqDTO reqList);

    /**
     * 批量查询商品的好评率
     * @param queryCommentReq
     * @return
     */
    ResultVO<List<CommentsGoodsRateReqDTO>> queryCommentsGoodsRate(QueryCommentReq queryCommentReq);
}
