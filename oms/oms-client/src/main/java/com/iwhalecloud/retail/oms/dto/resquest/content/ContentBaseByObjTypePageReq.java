package com.iwhalecloud.retail.oms.dto.resquest.content;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 查询条件根据对象类型 内容列表分页查询请求参数对象.
 *
 * @author Ji.kai
 * @date 2018/10/27 15:27
 */
@Data
@ApiModel(value = "查询条件根据对象类型 内容列表分页查询请求参数对象")
public class ContentBaseByObjTypePageReq extends PageVO {

    @ApiModelProperty(value = "内容名称")
    private String title;

    @ApiModelProperty(value = "内容名称或目录名称")
    private String objType;

    @ApiModelProperty(value = "内容状态")
    private List<String> status;

    @ApiModelProperty(value = "目录Id")
    private String catalogId;

    @ApiModelProperty(value = "内容类型")
    private String type;

    @ApiModelProperty(value = "内容发布区域")
    private String area;

}
