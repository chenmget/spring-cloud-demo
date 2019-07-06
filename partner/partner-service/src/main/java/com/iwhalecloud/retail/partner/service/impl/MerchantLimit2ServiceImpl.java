package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantLimit2DTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimit2SaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimit2UpdateReq;
import com.iwhalecloud.retail.partner.entity.MerchantLimit2;
import com.iwhalecloud.retail.partner.manager.MerchantLimit2Manager;
import com.iwhalecloud.retail.partner.service.MerchantLimit2Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
@Service
public class MerchantLimit2ServiceImpl implements MerchantLimit2Service {

    @Autowired
    private MerchantLimit2Manager merchantLimit2Manager;

    /**
     * 添加一个 商家限额
     * 先判断 是否存在  存在就更新  不存在 就插入
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> saveMerchantLimit(MerchantLimit2SaveReq req) {
        log.info("MerchantLimit2ServiceImpl.saveMerchantLimit req={} ", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getLanId())) {
            return ResultVO.error("lanId不能为空");
        }
        Integer result = 0;
        MerchantLimit2 merchantLimit = merchantLimit2Manager.getMerchantLimit(req.getLanId());
        if (Objects.isNull(merchantLimit)) {
            // 不存在  插入
            result = merchantLimit2Manager.saveMerchantLimit(req);
        } else {
            // 更新
            BeanUtils.copyProperties(req, merchantLimit);
            result = merchantLimit2Manager.updateMerchantLimit(merchantLimit);
        }
        log.info("MerchantLimit2ServiceImpl.saveMerchantLimit effect rows = {} ", result);
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
    public ResultVO<MerchantLimit2DTO> getMerchantLimit(String lanId) {
        log.info("MerchantLimitServiceImpl.getMerchantLimit lanId={} ", lanId);
        if (StringUtils.isEmpty(lanId)) {
            return ResultVO.error("lanId不能为空");
        }
        MerchantLimit2 merchantLimit = merchantLimit2Manager.getMerchantLimit(lanId);
        log.info("MerchantLimitServiceImpl.getMerchantLimit merchantLimit= {} ", JSON.toJSONString(merchantLimit));

        MerchantLimit2DTO merchantLimitDTO = new MerchantLimit2DTO();
        if (Objects.nonNull(merchantLimit)) {
            BeanUtils.copyProperties(merchantLimit, merchantLimitDTO);
        }else {
            merchantLimitDTO.setLanId(lanId);
            merchantLimitDTO.setMaxSerialNum(0L);
            merchantLimitDTO.setSerialNumUsed(0L);
        }
        // 没有获取到数据  也返回成功 塞个 null 值
        return ResultVO.success(merchantLimitDTO);
    }

    /**
     * 更新一个 商家限额
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateMerchantLimit(MerchantLimit2UpdateReq req) {
        log.info("MerchantLimitServiceImpl.updateMerchantLimit req={}", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getLanId())
                || Objects.isNull(req.getSerialNumUsed())) {
            return ResultVO.error("lanId或已使用额度不能为空不能为空");
        }
        MerchantLimit2 merchantLimit = new MerchantLimit2();
        BeanUtils.copyProperties(req, merchantLimit);
        Integer result = merchantLimit2Manager.updateMerchantLimit(merchantLimit);
        log.info("MerchantLimitServiceImpl.updateMerchantLimit effect rows = {} ", result);
        if (result > 0) {
            return ResultVO.success(result);
        }
        return ResultVO.error("更新限额数据记录失败");
    }
}