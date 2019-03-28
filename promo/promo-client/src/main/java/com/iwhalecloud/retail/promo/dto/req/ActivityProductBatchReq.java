package com.iwhalecloud.retail.promo.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 添加参与活动产品请求体
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年02月20日
 */
@Data
@ApiModel(value = "批量添加活动产品请求入参")
public class ActivityProductBatchReq implements Serializable{

    private static final long serialVersionUID = -8566403044667613809L;

    List<ActivityProductReq> activityProductReqList;
}
