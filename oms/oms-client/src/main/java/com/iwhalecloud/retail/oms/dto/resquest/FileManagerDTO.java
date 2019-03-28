package com.iwhalecloud.retail.oms.dto.resquest;

import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

@Data
public class FileManagerDTO implements Serializable {

    private InputStream image;
    private String fileName;
    private Long fileSize;

    private String deleteImgName;

}
