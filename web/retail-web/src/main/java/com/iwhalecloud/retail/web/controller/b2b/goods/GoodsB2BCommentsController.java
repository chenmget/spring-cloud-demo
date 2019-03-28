package com.iwhalecloud.retail.web.controller.b2b.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.CommentsPageListResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsCommentsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author My
 * @Date 2018/12/29
 **/
@RestController
@RequestMapping("/api/b2b/comment")
@Slf4j
public class GoodsB2BCommentsController {
    @Reference
    private GoodsCommentsService goodsCommentsService;
    /**
     * 添加评论
     * @param
     * @return
     *//*
    @RequestMapping(value="/addComments",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> addComments(@RequestBody CommentsRequestDTO commentsRequestDTO){
        log.info("CommentsController addComments commentsRequestDTO={} ",commentsRequestDTO);
        //获取memberId
        String memberId = MemberContext.getMemberId();
        for(AddCommentsReqDTO comments:commentsRequestDTO.getComments()){
            if(StringUtils.isEmpty(memberId)){
                return ResultVO.error();
            }
            commentsRequestDTO.setMemberId(memberId);
            //将评论先设置为true
            if(null == comments){
                return ResultVO.error();
            }
            String objectId = comments.getObjectId();
            String orderId = comments.getOrderId();
            if(StringUtils.isEmpty(objectId)){
                ResultVO.error("objectId is  null");
            }
            if(StringUtils.isEmpty(orderId)){
                ResultVO.error("orderId is null");
            }
            comments.setDisplay("true");
        }
        ResultVO<Boolean>  resp = commentsService.addComments(commentsRequestDTO);
        if(ResultCodeEnum.ERROR.getCode().equals(resp.getResultCode())){
            return ResultVO.error(resp.getResultMsg());
        }else {
            return ResultVO.success();
        }
    }*/

    @RequestMapping(value="/queryCommentForPage",method = RequestMethod.GET)
    public ResultVO<CommentsPageListResp> queryCommentForPage(@RequestParam String goodsId){
        log.info("CommentsController queryCommentForPage req={} ",goodsId);
        if(StringUtils.isEmpty(goodsId)){
            return ResultVO.error("goodsId must is not be null");
        }
        ListCommentsReqDTO listCommentsReqDTO = new ListCommentsReqDTO();
        listCommentsReqDTO.setCommentType("goods");
        listCommentsReqDTO.setGoodsId(goodsId);
        ResultVO<CommentsPageListResp> page=goodsCommentsService.listGoodsComments(listCommentsReqDTO);
        if(null == page){
            return ResultVO.success();
        }
        return page;
    }
}
