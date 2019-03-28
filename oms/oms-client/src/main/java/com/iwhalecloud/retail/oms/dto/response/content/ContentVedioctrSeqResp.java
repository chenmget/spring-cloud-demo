package com.iwhalecloud.retail.oms.dto.response.content;

import com.iwhalecloud.retail.oms.dto.ContentBaseDTO;
import com.iwhalecloud.retail.oms.dto.ContentMaterialDTO;
import com.iwhalecloud.retail.oms.dto.ContentVediolv2DTO;
import com.iwhalecloud.retail.oms.dto.ContentVideosDTO;
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
@ApiModel(value = "内容播放管理序列响应对象")
public class ContentVedioctrSeqResp implements java.io.Serializable  {

    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容id")
    private Long contentId;

    /**
     * 播放顺序
     */
    @ApiModelProperty(value = "播放顺序")
    private Long playbackSequence;

    /**
     * 内容对象
     */
    @ApiModelProperty(value = "内容对象")
    private ContentBaseDTO contentBase;

    /**
     * 内容素材
     */
    @ApiModelProperty(value = "内容素材")
    private List<ContentMaterialDTO> contentMaterials;

    /**
     * 视频二级内容表
     */
    @ApiModelProperty(value = "视频二级内容表")
    private List<ContentVediolv2DTO> contentVediolv2s;

    /**
     * 视频内容
     */
    @ApiModelProperty(value = "视频内容")
    private List<ContentVideosDTO> contentVedios;
}
