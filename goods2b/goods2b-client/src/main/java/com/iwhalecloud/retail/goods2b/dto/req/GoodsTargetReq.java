package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/11.
 */
@Data
@ApiModel(value = "商品发布对象请求参数")
public class GoodsTargetReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对象ID
     */
    @ApiModelProperty(value = "对象ID")
    private String targetId;

    @ApiModelProperty(value = "对象编码")
    private String targetCode;

    @ApiModelProperty(value = "对象名称")
    private String targetName;
}
