package com.iwhalecloud.retail.order2b.dto.response.purapply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/24 10:29
 * @description 申请单发货返回对象
 */

@Data
public class PurApplyDeliveryResp implements Serializable {

    private static final long serialVersionUID = 1563989075663069549L;

    private String unitName;
    private String mktResInstNbr;
    private String batchId;
    private String createDate;
    private  String productId;





}

