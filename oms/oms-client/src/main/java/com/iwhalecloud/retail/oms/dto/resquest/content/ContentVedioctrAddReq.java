package com.iwhalecloud.retail.oms.dto.resquest.content;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Auther: ji.kai
 * @Date: 2018/11/7 19:35
 * @Description:
 */
@Data
@ApiModel(value = "新增内容播放管理对象")
public class ContentVedioctrAddReq implements java.io.Serializable {

    /**
     * 货架选择
     */
    @ApiModelProperty(value = "货架选择")
    private java.lang.String storageNum;

    /**
     * 启动播放时长
     */
    @ApiModelProperty(value = "启动播放时长")
    private java.lang.Long playbackTime;
    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容播放管理序列请求对象")
    private List<ContentVedioctrSeqReq> ContentVedioctrSeqs;

}
