package com.iwhalecloud.retail.oms.dto.resquest.content;

import com.iwhalecloud.retail.oms.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Wu.LiangHang
 * @date 2018/11/13 17:16
 */
@Data
@ApiModel(value = "广告/软文推送分页查询请求参数对象")
public class ContentBasePTPageReq extends PageVO {
    @ApiModelProperty(value = "内容类型")
    private String type;

    @ApiModelProperty(value = "内容状态")
    private String status;

    @ApiModelProperty(value = "当前时间")
    private Date currentTime;

    @ApiModelProperty(value = "区域")
    private String area;
}
