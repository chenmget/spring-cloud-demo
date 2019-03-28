package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.GoodsActRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsActRelListReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsActRel;
import com.iwhalecloud.retail.goods2b.manager.GoodsActRelManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsActRelService;
import com.iwhalecloud.retail.goods2b.utils.CloneUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("goodsActRelService")
@Service
public class GoodsActRelServiceImpl implements GoodsActRelService {

    @Autowired
    private GoodsActRelManager goodsActRelManager;

    @Override
    public ResultVO<List<GoodsActRelDTO>> queryGoodsActRel(GoodsActRelListReq req) {

        log.info("GoodsActRelServiceImpl.querygoodsActRel-->req={}", JSON.toJSONString(req));
        List<GoodsActRel> goodsActRels = goodsActRelManager.queryGoodsActRel(req.getGoodsId(), req.getActType());
        List<GoodsActRelDTO> goodsActRelDtos = CloneUtils.batchClone(goodsActRels,GoodsActRelDTO.class);
        log.info("GoodsActRelServiceImpl.querygoodsActRel-->goodsActRelDtos={}", JSON.toJSONString(goodsActRelDtos));
        return ResultVO.success(goodsActRelDtos);
    }
}