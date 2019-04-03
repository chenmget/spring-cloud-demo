package com.iwhalecloud.retail.promo.dto.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/30 14:55
 */
@Data
public class GetMerchantIdListReq implements Serializable {
    private String merchantName;
    private String merchantLoginName;
}
