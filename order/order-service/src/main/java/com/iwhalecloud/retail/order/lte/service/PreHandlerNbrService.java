package com.iwhalecloud.retail.order.lte.service;

import com.iwhalecloud.retail.order.lte.dto.LetNumReqDTO;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;

public interface PreHandlerNbrService {

    /**
     * 获取token
     */
    CommonResultResp getAccessToken(LetNumReqDTO letNumReqDTO);

    /**
     * 号码预占、号码释放
     */
    CommonResultResp OrdReleaseNum(LetNumReqDTO letNumReqDTO);


}
