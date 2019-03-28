package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/1.
 */
@Data
public class CatQueryReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = -1524264697344418417L;

    @ApiModelProperty(value = "updateReqList")
    private List<CatUpdateReq> updateReqList;

    @ApiModelProperty(value = "catId")
    private String catId;

    @ApiModelProperty(value = "pageNum")
    private Long pageNum;

    @ApiModelProperty(value = "pageSize")
    private Long pageSize;

    @ApiModelProperty(value = "catName")
    private String catName;

    @ApiModelProperty(value = "parentCatId")
    private String parentCatId;

    @ApiModelProperty(value = "typeId")
    private String typeId;
}
