package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author wenlong.zhong
 * @date 2019/6/5
 */
@Data
public class BrandPageReq extends PageVO implements Serializable {

    private static final long serialVersionUID = -499950887898731471L;

    @ApiModelProperty(value = "品牌标识brandId")
    private String brandId;

    @ApiModelProperty(value = "分类标识catId")
    private String catId;

    @ApiModelProperty(value = "品牌标识集合brandIdList")
    private List brandIdList;
}
