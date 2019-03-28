package com.iwhalecloud.retail.promo.manager;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.promo.dto.req.ActSupDetailReq;
import com.iwhalecloud.retail.promo.dto.resp.ActSupDetailResp;
import com.iwhalecloud.retail.promo.entity.ActSupDetail;
import com.iwhalecloud.retail.promo.mapper.ActSupDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class ActSupDetailManager extends ServiceImpl<ActSupDetailMapper,ActSupDetail> {
    @Resource
    private ActSupDetailMapper actSupDetailMapper;

    /**
     * 根据补录记录ID获取补录活动记录的明细
     * @param actSupDetailReq
     */

    public Page<ActSupDetailResp> getActSupDetailByRecordId(ActSupDetailReq actSupDetailReq) {
        log.info("ActSupDetailManager.getActSupDetail req={}", JSON.toJSONString(actSupDetailReq));
        Page<ActSupDetailResp> page = new Page<>(actSupDetailReq.getPageNo(), actSupDetailReq.getPageSize());
        page = actSupDetailMapper.listActSupDetailByRecordId(page,actSupDetailReq);
        log.info("ActSupDetailManager.getActSupDetail resp={}", JSON.toJSONString(page));
        return page;
    }

    /**
     * 新增前置补录明细
     * @param actSupDetail
     */
    public Integer addActSupDetail(ActSupDetail actSupDetail){
        actSupDetail.setGmtCreate(new Date());
        actSupDetail.setGmtModified(new Date());
        return actSupDetailMapper.insert(actSupDetail);
    }

    /**
     * 校验订单和串码是否已经补录过了
     * @param actSupDetailReq
     * @return
     */
    public Integer orderResSupCheck(ActSupDetailReq actSupDetailReq){
        return actSupDetailMapper.orderResSupCheck(actSupDetailReq);
    }

}
