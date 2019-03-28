package com.iwhalecloud.retail.partner.model;

import lombok.Data;

/**
 * ftp连接实体类
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月07日
 */
@Data
public class FtpDTO {
    private String ip;
    private String userName;
    private String password;
    private Integer port;
    private String filePath;
    private String fileName;
}
