package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年02月27日
 * @Description:
 **/
@Data
public class CatComplexDeleteReq extends AbstractRequest implements Serializable {
    /**
     * id集合
     */
    @ApiModelProperty(value = "ids")
    private List<String> ids;

}
