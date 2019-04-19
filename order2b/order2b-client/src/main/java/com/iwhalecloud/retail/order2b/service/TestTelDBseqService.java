package com.iwhalecloud.retail.order2b.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;

public interface TestTelDBseqService {

    ResultVO getseq(OrderRequest request);
}
