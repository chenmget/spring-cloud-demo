package com.iwhalecloud.retail.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/3/22 14:55
 * @description
 */

@Data
@ApiModel(value = "附件对象")
public class FileDTO implements Serializable {

    private static final long serialVersionUID = 8646438097710859189L;

    /**
     * 附件id
     */
    @ApiModelProperty(value = "附件id")
    private String uid;

    /**
     * 附件name
     */
    @ApiModelProperty(value = "附件name")
    private String name;

    /**
     * 附件url
     */
    @ApiModelProperty(value = "附件url")
    private String url;
}

