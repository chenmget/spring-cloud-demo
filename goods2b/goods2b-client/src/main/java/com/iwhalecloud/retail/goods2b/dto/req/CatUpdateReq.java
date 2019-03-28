package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author My
 * @Date 2018/12/21
 **/
@Data
public class CatUpdateReq extends CatAddReq implements Serializable {
    @ApiModelProperty(value = "类别ID")
    @NotBlank
    private String catId;

    @ApiModelProperty(value = "修改人")
    private String updateStaff;

    @ApiModelProperty(value = "修改时间")
    private Date updateDate;
}
