package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstItmsLimitSaveReq;
import com.iwhalecloud.retail.warehouse.dto.request.ResourceInstItmsLimitUpdateReq;
import com.iwhalecloud.retail.warehouse.dto.response.ResourceInstItmsLimitResp;
import com.iwhalecloud.retail.warehouse.entity.ResourceInstItmsLimit;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstItmsLimitManager;
import com.iwhalecloud.retail.warehouse.service.ResourceInstItmsLimitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service("resourceInstItmsLimitService")
public class ResourceInstItmsLimitServiceImpl implements ResourceInstItmsLimitService {

    @Autowired
    private ResourceInstItmsLimitManager resourceInstItmsLimitManager;

    /**
     * 添加一个 商家限额
     * 先判断 是否存在  存在就更新  不存在 就插入
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> saveResourceInstItmsLimit(ResourceInstItmsLimitSaveReq req) {
        log.info("ResourceInstItmsLimitServiceImpl.saveResourceInstItmsLimit req={} ", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getLanId())) {
            return ResultVO.error("lanId不能为空");
        }
        Integer result = 0;
        ResourceInstItmsLimit limit = resourceInstItmsLimitManager.getResourceInstItmsLimit(req.getLanId());
        if (Objects.isNull(limit)) {
            // 不存在  插入
            result = resourceInstItmsLimitManager.saveResourceInstItmsLimit(req);
        } else {
            // 更新
            BeanUtils.copyProperties(req, limit);
            result = resourceInstItmsLimitManager.updateResourceInstItmsLimit(limit);
        }
        log.info("ResourceInstItmsLimitServiceImpl.saveResourceInstItmsLimit effect rows = {} ", result);
        if (result > 0) {
            return ResultVO.success(result);
        }
        return ResultVO.error("保存限额数据记录失败");
    }

    /**
     * 根据商家ID 获取一个 限额
     * @param lanId
     * @return
     */
    @Override
    public ResultVO<ResourceInstItmsLimitResp> getResourceInstItmsLimit(String lanId) {
        log.info("ResourceInstItmsLimitServiceImpl.getResourceInstItmsLimit lanId={} ", lanId);
        if (StringUtils.isEmpty(lanId)) {
            return ResultVO.error("lanId不能为空");
        }
        ResourceInstItmsLimit limit = resourceInstItmsLimitManager.getResourceInstItmsLimit(lanId);
        log.info("ResourceInstItmsLimitServiceImpl.getResourceInstItmsLimit limit= {} ", JSON.toJSONString(limit));

        ResourceInstItmsLimitResp resp = new ResourceInstItmsLimitResp();
        if (Objects.nonNull(limit)) {
            BeanUtils.copyProperties(limit, resp);
        }else {
            resp.setLanId(lanId);
            resp.setMaxSerialNum(0L);
            resp.setSerialNumUsed(0L);
        }
        // 没有获取到数据  也返回成功 塞个 null 值
        return ResultVO.success(resp);
    }

    /**
     * 更新一个 商家限额
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateResourceInstItmsLimit(ResourceInstItmsLimitUpdateReq req) {
        log.info("ResourceInstItmsLimitServiceImpl.updateResourceInstItmsLimit req={}", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getLanId())
                || Objects.isNull(req.getSerialNumUsed())) {
            return ResultVO.error("lanId或已使用额度不能为空不能为空");
        }
        ResourceInstItmsLimit limit = new ResourceInstItmsLimit();
        BeanUtils.copyProperties(req, limit);
        Integer result = resourceInstItmsLimitManager.updateResourceInstItmsLimit(limit);
        log.info("ResourceInstItmsLimitServiceImpl.updateResourceInstItmsLimit effect rows = {} ", result);
        if (result > 0) {
            return ResultVO.success(result);
        }
        return ResultVO.error("更新限额数据记录失败");
    }
}