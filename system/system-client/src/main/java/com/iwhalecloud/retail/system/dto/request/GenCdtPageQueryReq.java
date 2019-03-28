package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * lin.qi
 */
@Data
@ApiModel(value = "系统模块通用分页查询请求类")
public class GenCdtPageQueryReq extends PageVO {

    @ApiModelProperty(value = "自定义查询组件,可选lan,menu,organization,regions,role,user")
    private List<String> queryComponents;

    @ApiModelProperty(value = "对象编码")
    private String objCode;

    @ApiModelProperty(value = "模糊查询名称")
    private String blurName;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
