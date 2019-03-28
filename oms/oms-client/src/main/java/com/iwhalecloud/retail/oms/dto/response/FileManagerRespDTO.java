package com.iwhalecloud.retail.oms.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileManagerRespDTO implements Serializable {

    private String path;
    private String group;
    private String fileUrl;
    private String fullPath;
}
