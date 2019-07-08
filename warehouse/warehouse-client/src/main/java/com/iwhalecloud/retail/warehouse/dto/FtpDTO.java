package com.iwhalecloud.retail.warehouse.dto;

import lombok.Data;

/**
 * ftp连接实体类
 * @author xu.qinyuan@ztesoft.com
 *  * @date 2019年03月07日
 */
@Data
public class FtpDTO {

    private String ipAddr;
    private Integer port;
    private String userName;
    private String password;
    private String filePath;
}
