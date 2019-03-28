package com.iwhalecloud.retail.order2b.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
public class SRequest extends OrderRequest implements Serializable {

    @ApiModelProperty(value = "页数，默认=1")
    private Integer pageNo;

    @ApiModelProperty(value = "分页数量，默认=10")
    private Integer pageSize;

    private String userId;

    private String userCode;

    public Integer getPageNo() {
        if (StringUtils.isEmpty(pageNo)) {
            pageNo = 1;
        }
        return pageNo;
    }

    public Integer getPageSize() {
        if (StringUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        return pageSize;
    }
}
