package com.iwhalecloud.retail.goods.manager;

import com.iwhalecloud.retail.goods.entity.ProdGoodsContract;
import com.iwhalecloud.retail.goods.mapper.ProdGoodsContractMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ProdGoodsContractManager{
    @Resource
    private ProdGoodsContractMapper prodGoodsContractMapper;

    /**
     * 添加合约计划
     * @param goodsId
     * @param contractPeriod
     * @return
     */
    public int insert(String goodsId, Long contractPeriod) {
        ProdGoodsContract prodGoodsContract = new ProdGoodsContract();
        prodGoodsContract.setGoodsId(goodsId);
        prodGoodsContract.setContractPeriod(contractPeriod);
        return prodGoodsContractMapper.insert(prodGoodsContract);
    }

    public ProdGoodsContract getGoodsContractByGoodsId(String goodsId) {
        return prodGoodsContractMapper.selectById(goodsId);
    }

    public List<ProdGoodsContract> getGoodsContractByGoodsIds(List<String> goodsIds) {
        return prodGoodsContractMapper.selectBatchIds(goodsIds);
    }

    public int updateGoodsContractByGoodsId(ProdGoodsContract goodsContract) {
        return prodGoodsContractMapper.updateById(goodsContract);
    }

    public int delGoodsContractByGoodsId(String goodsId) {
        return prodGoodsContractMapper.deleteById(goodsId);
    }
}
