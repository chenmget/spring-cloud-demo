package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/2.
 */
@Data
public class ProdGoodsRuleByExcelFileReq extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty
    private String goodsId;
    @ApiModelProperty
    private byte[] excelFileBytes;
}
