package com.iwhalecloud.retail.web.controller.b2b.fdfs.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * base64文件上传请求类
 * @author Z
 * @date 2018/11/28
 */
@Data
public class FileBase64UploadReq {

    @ApiModelProperty(value = "附件信息，base64字符串")
    private String image;
    @ApiModelProperty(value = "附件名称")
    private String fileName;
    @ApiModelProperty(value = "附件大小")
    private Long fileSize;

    private String deleteImgName;
}
