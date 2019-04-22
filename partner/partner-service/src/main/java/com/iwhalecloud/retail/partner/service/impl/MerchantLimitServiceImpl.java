package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.MerchantLimitDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitSaveReq;
import com.iwhalecloud.retail.partner.dto.req.MerchantLimitUpdateReq;
import com.iwhalecloud.retail.partner.entity.MerchantLimit;
import com.iwhalecloud.retail.partner.manager.MerchantLimitManager;
import com.iwhalecloud.retail.partner.service.MerchantLimitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Slf4j
@Service
public class MerchantLimitServiceImpl implements MerchantLimitService {

    @Autowired
    private MerchantLimitManager merchantLimitManager;

    /**
     * 添加一个 商家限额
     * 先判断 是否存在  存在就更新  不存在 就插入
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> saveMerchantLimit(MerchantLimitSaveReq req) {
        log.info("MerchantLimitServiceImpl.saveMerchantLimit(), input: req={} ", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getMerchantId())) {
            return ResultVO.error("商家ID不能为空");
        }
        Integer result = 0;
        MerchantLimit merchantLimit = merchantLimitManager.getMerchantLimit(req.getMerchantId());
        if (Objects.isNull(merchantLimit)) {
            // 不存在  插入
            result = merchantLimitManager.saveMerchantLimit(req);
        } else {
            // 更新
            BeanUtils.copyProperties(req, merchantLimit);
            result = merchantLimitManager.updateMerchantLimit(merchantLimit);
        }
        log.info("MerchantLimitServiceImpl.saveMerchantLimit(), output: effect rows = {} ", result);
        if (result > 0) {
            return ResultVO.success(result);
        }
        return ResultVO.error("保存商家限额数据记录失败");
    }

    /**
     * 根据商家ID 获取一个 商家限额
     * @param merchantId
     * @return
     */
    @Override
    public ResultVO<MerchantLimitDTO> getMerchantLimit(String merchantId) {
        log.info("MerchantLimitServiceImpl.getMerchantLimit(), input: merchantId={} ", JSON.toJSONString(merchantId));
        if (StringUtils.isEmpty(merchantId)) {
            return ResultVO.error("商家ID不能为空");
        }
        MerchantLimit merchantLimit = merchantLimitManager.getMerchantLimit(merchantId);
        log.info("MerchantLimitServiceImpl.getMerchantLimit(), output: merchantLimit= {} ", JSON.toJSONString(merchantLimit));

        MerchantLimitDTO merchantLimitDTO = new MerchantLimitDTO();
        if (Objects.nonNull(merchantLimit)) {
            BeanUtils.copyProperties(merchantLimit, merchantLimitDTO);
        }else {
            merchantLimitDTO.setMerchantId(merchantId);
            merchantLimitDTO.setMaxSerialNum(0L);
            merchantLimitDTO.setSerialNumUsed(0L);
        }
        // 没有获取到数据  也返回成功 塞个 null 值
        return ResultVO.success(merchantLimitDTO);
//        return ResultVO.error("获取商家ID为" + merchantId + "的限额数据记录失败");
    }

    /**
     * 更新一个 商家限额
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateMerchantLimit(MerchantLimitUpdateReq req) {
        log.info("MerchantLimitServiceImpl.updateMerchantLimit(), input: req={} ", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getMerchantId())
                || Objects.isNull(req.getSerialNumUsed())) {
            return ResultVO.error("商家ID或已使用额度不能为空不能为空");
        }
        MerchantLimit merchantLimit = new MerchantLimit();
        BeanUtils.copyProperties(req, merchantLimit);
        Integer result = merchantLimitManager.updateMerchantLimit(merchantLimit);
        log.info("MerchantLimitServiceImpl.updateMerchantLimit(), output: update effect rows = {} ", result);
        if (result > 0) {
            return ResultVO.success(result);
        }
        return ResultVO.error("更新商家限额数据记录失败");
    }
}