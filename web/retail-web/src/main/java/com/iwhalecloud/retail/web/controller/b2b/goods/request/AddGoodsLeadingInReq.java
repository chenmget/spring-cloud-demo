package com.iwhalecloud.retail.web.controller.b2b.goods.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/****
 * @author gs_10010
 * @date 2019/7/9 11:17
 */
@Data
public class AddGoodsLeadingInReq extends AbstractRequest implements Serializable {

    @ApiModelProperty("1:按机型，2:按规格")
    private String addGodsType;

    @ApiModelProperty("对象类型{1:经营主体，2:店中商，3:地包}")
    private String objType;

}
