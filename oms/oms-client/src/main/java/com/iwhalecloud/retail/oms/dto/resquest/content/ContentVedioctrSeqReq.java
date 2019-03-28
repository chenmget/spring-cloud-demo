package com.iwhalecloud.retail.oms.dto.resquest.content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: ji.kai
 * @Date: 2018/11/7 19:35
 * @Description:
 */
@Data
@ApiModel(value = "内容播放管理序列请求对象")
public class ContentVedioctrSeqReq implements java.io.Serializable  {

    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容id")
    private Long contentId;

    /**
     * 播放顺序
     */
    @ApiModelProperty(value = "播放顺序")
    private java.lang.Long playbackSequence;
}
