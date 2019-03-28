package com.iwhalecloud.retail.goods.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.dto.req.AddCommentsReqDTO;
import com.iwhalecloud.retail.goods.dto.req.CommentsGoodsRateReqDTO;
import com.iwhalecloud.retail.goods.dto.req.ListCommentsReqDTO;
import com.iwhalecloud.retail.goods.entity.Comments;
import com.iwhalecloud.retail.goods.mapper.CommentsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author My
 * @Date 2018/11/12
 **/
@Component
public class CommentsManager {
    @Resource
    private CommentsMapper commentsMapper;

    private final static String GRADE_FIVE ="5";

    private final static String ZERO = "0";

    public BigDecimal getGoodsCommentRate(String objectId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("object_id",objectId);
        List<Comments> list = commentsMapper.selectList(queryWrapper);
        int count = 0;
        for(Comments comments:list){
            if(GRADE_FIVE.equals(String.valueOf(comments.getGrade()))){
                count++;
            }
        }
        return getGoodsRate(count,list.size());
    }

    public List<CommentsGoodsRateReqDTO> listGoodsRate(List<String> goodsList){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("object_id",goodsList);
        List<Comments> list = commentsMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        Map<String,List<Comments>> map = new HashedMap();
        //根据对象属性分组统计
        map = list.stream().collect(Collectors.groupingBy(Comments::getObjectId));
        List<CommentsGoodsRateReqDTO> goodsRates = Lists.newArrayList();
        for(Map.Entry<String,List<Comments>> entry:map.entrySet()){
            CommentsGoodsRateReqDTO comGoodsRate = new CommentsGoodsRateReqDTO();
            List<Comments> comList = entry.getValue();
            int count = 0;
            for (Comments comments:comList){
                if(GRADE_FIVE.equals(String.valueOf(comments.getGrade()))){
                    count++;
                }
            }
            BigDecimal goodsRate = getGoodsRate(count,comList.size());
            comGoodsRate.setCommentsNum(comList.size());
            comGoodsRate.setGoodsCommentsRate(goodsRate);
            comGoodsRate.setGoodsId(entry.getKey());
            count = 0;
            goodsRates.add(comGoodsRate);
        }
        return goodsRates;
    }

    /**
     * 统计好评率
     * @param goodsRateNum
     * @param commentsNum
     * @return
     */
    public BigDecimal getGoodsRate(int goodsRateNum,int commentsNum){
        //获取星级为5的评论作为分子
        BigDecimal firstResult =  new BigDecimal(goodsRateNum);
        //获取该商品的所有评论的评分等级作为分母
        BigDecimal secondResult =  new BigDecimal(commentsNum);
        BigDecimal lastResult = null;
        if(ZERO.equals(secondResult.toString())){
            return new BigDecimal(0);
        }
        try {
            lastResult = firstResult.divide(secondResult,3,BigDecimal.ROUND_HALF_DOWN);
            lastResult = lastResult.multiply(new BigDecimal(100)).setScale(1,BigDecimal.ROUND_HALF_DOWN);
        }catch (Exception e){
            e.printStackTrace();
        }
        return lastResult;
    }
    public IPage listAll(ListCommentsReqDTO req){
        Page page  =  new Page(req.getPageNo(),req.getPageSize());
        IPage iPage= commentsMapper.commentPage(page,req);
        List<Comments> list = commentsMapper.listComments(req);
        /*List<Map> commentsList = iPage.getRecords();
        for (Map comments : commentsList) {
            Timestamp ts = (Timestamp)comments.get("time");
            Date date = new Date();
            date = ts;
            comments.put("date", date);
            List<Comments> child = new ArrayList<Comments>();
            for(Comments reply:list){
                if(reply.getForCommentId().equals(comments.get("comment_id").toString())){
                    child.add(reply);
                }
            }
            comments.put("list", child);
            //comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
        }*/
        return iPage;
    }

    /**
     *新增评论、咨询或其回复
     * @param req
     */
    public int addComments(AddCommentsReqDTO req){
        Comments comments = new Comments();
        BeanUtils.copyProperties(req, comments);
        return commentsMapper.insert(comments);
    }
}
