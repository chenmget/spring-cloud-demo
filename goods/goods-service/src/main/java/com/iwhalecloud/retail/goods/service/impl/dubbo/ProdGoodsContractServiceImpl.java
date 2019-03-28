package com.iwhalecloud.retail.goods.service.impl.dubbo;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.goods.common.ResultCodeEnum;
import com.iwhalecloud.retail.goods.dto.ProdGoodsContractDTO;
import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.entity.ProdGoodsContract;
import com.iwhalecloud.retail.goods.manager.ProdGoodsContractManager;
import com.iwhalecloud.retail.goods.service.dubbo.ProdGoodsContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("prodGoodsContractService")
@Service
public class ProdGoodsContractServiceImpl implements ProdGoodsContractService {

    @Autowired
    private ProdGoodsContractManager prodGoodsContractManager;


    @Override
    public ResultVO listGoodsContractByGoodsIds(List<String> goodsIds) {
        if (CollectionUtils.isEmpty(goodsIds)) {
            return ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        List<ProdGoodsContract> goodsContractList = prodGoodsContractManager.getGoodsContractByGoodsIds(goodsIds);
        if (CollectionUtils.isEmpty(goodsContractList)) {
            return ResultVO.success(Lists.newArrayList());
        }
        List<ProdGoodsContractDTO> goodsContractDTOList = Lists.newArrayList();
        for (ProdGoodsContract goodsContract : goodsContractList) {
            ProdGoodsContractDTO dto = new ProdGoodsContractDTO();
            BeanUtils.copyProperties(goodsContract,dto);
            goodsContractDTOList.add(dto);
        }
        return ResultVO.success(goodsContractDTOList);
    }
}