package com.iwhalecloud.retail.web.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.AddCommentsReqDTO;
import com.iwhalecloud.retail.goods.dto.req.CommentsRequestDTO;
import com.iwhalecloud.retail.goods.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods.dto.resp.CommentsPageListResp;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.service.CommentsService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.interceptor.MemberContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pjq
 * @Date 2018/10/10
 **/
@RestController
@RequestMapping("/api/comment")
@Slf4j
public class GoodsCommentsController extends BaseController {

    @Reference
    private CommentsService commentsService;

    /**
     * 添加评论
     * @param commentsRequestDTO
     * @return
     */
    @RequestMapping(value="/addComments",method = RequestMethod.POST)
    @UserLoginToken
    public ResultVO<Boolean> addComments(@RequestBody CommentsRequestDTO commentsRequestDTO){
        log.info("CommentsController addComments commentsRequestDTO={} ",commentsRequestDTO);
        //获取memberId
        String memberId = MemberContext.getMemberId();
        for(AddCommentsReqDTO comments:commentsRequestDTO.getComments()){
            if(StringUtils.isEmpty(memberId)){
                return failResultVO();
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

        return null;
    }

    @RequestMapping(value="/queryCommentForPage",method = RequestMethod.POST)
    public ResultVO<CommentsPageListResp> queryCommentForPage(@RequestBody ListCommentsReqDTO request){
        log.info("CommentsController queryCommentForPage req={} ",request);
        if(null == request){
            return failResultVO();
        }
        if (StringUtils.isEmpty(request.getGoodsId())) {
            return ResultVO.error("objectId is must not be null");
        }
        if(StringUtils.isEmpty(request.getCommentType())){
            return ResultVO.error("commenttype is must not be null");
        }
       /* ResultVO<CommentsPageListResp> page=commentsService.queryCommoentForPage(request);
        if(null == page){
            return ResultVO.error("comments  is null");
        }*/
        return ResultVO.success();
    }



}

