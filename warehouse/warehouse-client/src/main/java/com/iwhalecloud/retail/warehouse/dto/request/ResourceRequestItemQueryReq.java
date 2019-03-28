package com.iwhalecloud.retail.warehouse.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Data
public class ResourceRequestItemQueryReq extends PageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请单ID
     */
    @ApiModelProperty(value = "申请单ID")
    private String mktResReqId;



}
