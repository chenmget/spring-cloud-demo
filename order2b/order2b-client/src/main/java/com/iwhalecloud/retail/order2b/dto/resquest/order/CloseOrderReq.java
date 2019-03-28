package com.iwhalecloud.retail.order2b.dto.resquest.order;

import com.iwhalecloud.retail.order2b.dto.base.MRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 关闭订单请求参数
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月25日
 */
@Data
public class CloseOrderReq extends MRequest {
    @ApiModelProperty("订单Id")
    private String orderId;

    @ApiModelProperty("退款凭证图片url")
    private List<String> refundImgUrls;

    @ApiModelProperty("关闭说明")
    private String questionDesc;

}
