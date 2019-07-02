package com.iwhalecloud.retail.web.controller.b2b.fdfs.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileManagerRespDTO implements Serializable {

    private String path;
    private String group;
    private String fileUrl;
    private String fullPath;
}
