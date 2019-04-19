package com.iwhalecloud.retail.order2b.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import com.iwhalecloud.retail.order2b.mapper.SelectSeqMapper;
import com.iwhalecloud.retail.order2b.service.TestTelDBseqService;

import javax.annotation.Resource;

@Service
public class TestTelDBseqServiceImpl implements TestTelDBseqService {
    @Resource
    private SelectSeqMapper selectSeqMapper;

    @Override
    public ResultVO getseq(OrderRequest request) {
        String string = selectSeqMapper.getSeq(DBTableSequence.ORD_ORDER.getCode());
        return ResultVO.success(string);
    }
}
