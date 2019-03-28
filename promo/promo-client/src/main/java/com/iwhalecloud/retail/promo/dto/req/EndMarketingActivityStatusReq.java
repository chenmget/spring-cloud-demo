package com.iwhalecloud.retail.promo.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class EndMarketingActivityStatusReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String activityId;

}
