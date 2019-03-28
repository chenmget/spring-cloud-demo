package com.iwhalecloud.retail.oms.dto.resquest.content;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 内容列表分页查询请求参数对象.
 *
 * @author Ji.kai
 * @date 2018/10/27 15:27
 */
@Data
@ApiModel(value = "内容列表分页查询请求参数对象")
public class ContentBasePageReq extends PageVO {
    @ApiModelProperty(value = "目录Id")
    private String catalogId;

    @ApiModelProperty(value = "标签Id")
    private List<String> tagIds;

    @ApiModelProperty(value = "操作人Id")
    private String oprid;

    @ApiModelProperty(value = "内容类型")
    private List<String> types;

    @ApiModelProperty(value = "内容状态")
    private List<String> status;

    @ApiModelProperty(value = "内容名称")
    private String title;

    @ApiModelProperty(value = "时间范围起始")
    private Date beginTime;

    @ApiModelProperty(value = "时间范围截止")
    private Date endTime;

    @ApiModelProperty(value = "内容名称或目录名称")
    private String fieldVal;

    @ApiModelProperty(value = "当前时间")
    private Date currentTime;

}
