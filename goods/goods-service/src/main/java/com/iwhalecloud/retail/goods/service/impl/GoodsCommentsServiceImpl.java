package com.iwhalecloud.retail.goods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.req.AddCommentsReqDTO;
import com.iwhalecloud.retail.goods.dto.req.CommentsGoodsRateReqDTO;
import com.iwhalecloud.retail.goods.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods.dto.resp.CommentsPageListResp;
import com.iwhalecloud.retail.goods.manager.CommentsManager;
import com.iwhalecloud.retail.goods.service.dubbo.GoodsCommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/27
 **/
@Service
@Component("goodsCommentsService")
@Slf4j
public class GoodsCommentsServiceImpl implements GoodsCommentsService {
    @Autowired
    private CommentsManager commentsManager;

    @Override
    public int addComments(AddCommentsReqDTO req) {
        try {
            return commentsManager.addComments(req);
        }catch (Exception e){
            log.error("GoodsCommentsServiceImpl addComments Exception={} ",e);
            return Integer.valueOf(ResultCodeEnum.ERROR.getCode());
        }
    }

    @Override
    public CommentsPageListResp listGoodsComments(ListCommentsReqDTO req) {
        CommentsPageListResp resp = new CommentsPageListResp();
        try {
            IPage page = commentsManager.listAll(req);
            if(null == page){
                log.error("GoodsCommentsServiceImpl listGoodsComments commentsPage is null");
                return null;
            }
            BigDecimal goodRate = commentsManager.getGoodsCommentRate(req.getGoodsId());
            resp.setGoodCommentRate(goodRate.toString());
            resp.setRecords(page.getRecords());
            resp.setSize(page.getSize());
            resp.setTotal(page.getTotal());
            resp.setPages(page.getPages());
        }catch (Exception e){
            log.error("GoodsCommentsServiceImpl listGoodsComments Exception={} ",e);
            return null;
        }
        return resp;
    }

    @Override
    public ResultVO<List<CommentsGoodsRateReqDTO>> queryCommentsGoodsRate(List<String> goodsList) {
        List<CommentsGoodsRateReqDTO> list = null;
        try {
            list = commentsManager.listGoodsRate(goodsList);
            if(null == list){
                log.info("GoodsCommentsServiceImpl queryCommentsGoodsRate list  is null");
               return ResultVO.success();
            }
        }catch (Exception e){
            log.error("GoodsCommentsServiceImpl queryCommentsGoodsRate Exception = {} ",e);
            return ResultVO.error("there is exception");
        }
        return ResultVO.success(list);
    }
}
