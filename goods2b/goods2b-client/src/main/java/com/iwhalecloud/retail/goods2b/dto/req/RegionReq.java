package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/12/24
 */
@Data
@ApiModel(value = "地市请求参数,  保持和 GoodsRegionRel 类的字段 一样")
public class RegionReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 2549130864173144470L;

    /**
     * 区域编码
     */
    @NotBlank
    @ApiModelProperty(value = "区域编码")
    private String regionId;

    /**
     * 地市名称
     */
    @NotBlank
    @ApiModelProperty(value = "地市名称")
    private String regionName;

    /**
     * lanid
     */
    @ApiModelProperty(value = "地市编码")
    private String lanId;

    /**
     * 组织ID, sys_commom_org表主键
     */
    @ApiModelProperty(value = "组织ID, sys_commom_org表主键")
    private java.lang.String orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    private java.lang.String orgName;

}
