package com.iwhalecloud.retail.goods.service.dubbo;

import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.AddCommentsReqDTO;
import com.iwhalecloud.retail.goods.dto.req.CommentsGoodsRateReqDTO;
import com.iwhalecloud.retail.goods.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods.dto.resp.CommentsPageListResp;

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
    CommentsPageListResp listGoodsComments(ListCommentsReqDTO reqList);

    /**
     * 批量查询商品的好评率
     * @param goodsList
     * @return
     */
    ResultVO<List<CommentsGoodsRateReqDTO>> queryCommentsGoodsRate(List<String> goodsList);
}
