package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.AddCommentsReqDTO;
import com.iwhalecloud.retail.goods2b.dto.req.CommentsRequestDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods2b.dto.resp.CommentsPageListResp;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsCommentsService;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.member.service.MemberService;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.consts.DateUtils;
import com.iwhalecloud.retail.oms.dto.resquest.TGoodsEvaluateTotalDTO;
import com.iwhalecloud.retail.oms.service.CommentsService;
import com.iwhalecloud.retail.oms.service.GoodsEvaluateTotalService;
import com.iwhalecloud.retail.order.consts.order.ActionFlowType;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.UpdateOrderStatusRequest;
import com.iwhalecloud.retail.order.service.OrderManagerOpenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author pjq
 * @Date 2018/10/10
 **/
@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {


    @Autowired
    GoodsEvaluateTotalService goodsEvaluateTotalService;
    @Reference
    private OrderManagerOpenService orderManagerOpenService;
    @Reference
    private GoodsCommentsService goodsCommentsService;
    @Reference
    private MemberService memberService;

    @Override
    public ResultVO<Boolean> addComments(CommentsRequestDTO req) {
        log.info("CommentsServiceImpl addComments reqList={} ",req);
        try {
            for (AddCommentsReqDTO comments : req.getComments()) {
                MemberResp resp = memberService.getMember(req.getMemberId());
                if (null == resp) {
                    log.info("CommentsController addComments member={} ", resp);
                    return ResultVO.error("member is null");
                }
                comments.setMemberId(req.getMemberId());
                comments.setAuthorId(req.getMemberId());
                comments.setAuthor(resp.getName());
                comments.setContact(resp.getMobile());
                String orderId = comments.getOrderId();
                String objectId = comments.getObjectId();
                comments.setTime(DateUtils.currentSysTimeForDate());
                int reulst = goodsCommentsService.addComments(comments);
                if (reulst < 1) {
                    return ResultVO.error("addComments is fail");
                }
                //添加标签，给大数据处理
                TGoodsEvaluateTotalDTO totalDTO = new TGoodsEvaluateTotalDTO();
                totalDTO.setGoodsId(objectId);
                totalDTO.setEvaluateText(comments.getQuotas());
                goodsEvaluateTotalService.modifyGoodsEvaluate(totalDTO);

                //添加评论以后修改订单的状态
                UpdateOrderStatusRequest request = new UpdateOrderStatusRequest();
                request.setOrderId(orderId);
                request.setFlowType(ActionFlowType.ORDER_HANDLER_PJ.getCode());
                CommonResultResp comResp = orderManagerOpenService.updateOrderStatus(request);
                log.info("CommentsServiceImpl addComments result={} ", comResp.getResultCode());
                if(ResultCodeEnum.ERROR.getCode().equals(comResp.getResultCode())){
                    log.error("CommentsServiceImpl addComments result={}",resp);
                    return ResultVO.error(comResp.getResultMsg());
                }
            }

        } catch (Exception e) {
            log.error("CommentsServiceImpl addComments Exception={} ", e);
            return ResultVO.error();

        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<CommentsPageListResp> queryCommoentForPage(ListCommentsReqDTO req) {
        log.info("CommentsServiceImpl queryCommoentForPage req={} ",req);
        CommentsPageListResp resp = null;
        try {
            //resp = goodsCommentsService.listGoodsComments(req);
            if(null == resp){
                log.error("CommentsServiceImpl queryCommoentForPage comments is null");
                return ResultVO.error("comments is null");
            }
        }catch (Exception e){
            log.error("CommentsServiceImpl queryCommoentForPage Exception={} ",e);
            return ResultVO.error();
        }
        return ResultVO.success(resp);
    }
}
