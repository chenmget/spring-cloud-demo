package com.iwhalecloud.retail.order2b.util;

import com.iwhalecloud.retail.dto.ResultVO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * @author 吴良勇
 * @date 2019/3/29 22:55
 */
public class FtpUtils {

    /**
     * 获取ftp连接
     *
     * @param ftp
     * @return
     * @throws Exception
     */
    public static FTPClient connectFtp(Ftp ftp) throws Exception {
        FTPClient ftpClient = new FTPClient();
        int reply;
        if (ftp.getPort() == null) {
            ftpClient.connect(ftp.getIpAddr(), 21);
        } else {
            ftpClient.connect(ftp.getIpAddr(), ftp.getPort());
        }
        ftpClient.login(ftp.getUserName(), ftp.getPwd());
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            return null;
        }
        if (StringUtils.isNotEmpty(ftp.getPath())) {
            ftpClient.changeWorkingDirectory(ftp.getPath());
        }
        return ftpClient;
    }

    /**
     * 关闭ftp连接
     */
    public static void closeFtp(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上传文件到ftp
     *
     * @param ftp
     * @param file
     * @throws Exception
     */
    public static ResultVO upload(Ftp ftp, File file) throws Exception {
        FTPClient ftpClient = connectFtp(ftp);
        FileInputStream input = null;
        try {
            if (ftpClient == null) {
                return ResultVO.error("链接ftp失败");
            }
//            File file2 = new File(file.getPath());
            input = new FileInputStream(file);
            ftpClient.storeFile(file.getName(), input);

        } finally {
            closeFtp(ftpClient);
            if (input != null) {
                input.close();
            }

        }

        return ResultVO.success();
    }

    /**
     * 将文件内容写入到FTP上
     * @param ftp
     * @param fileContent
     * @param fileName
     * @return
     * @throws Exception
     */
    public static ResultVO upload(Ftp ftp, String fileContent, String fileName) throws Exception {
        FTPClient ftpClient = connectFtp(ftp);
        InputStream input = null;
        try {
            if (ftpClient == null) {
                return ResultVO.error("链接ftp失败");
            }
            input = new ByteArrayInputStream(fileContent.getBytes());
            ftpClient.storeFile(fileName, input);

        } finally {
            closeFtp(ftpClient);
            if (input != null) {
                input.close();
            }
        }
        return ResultVO.success();
    }

    public static void main(String[] args) throws Exception {
        Ftp ftp = new Ftp();
        ftp.setIpAddr("134.176.97.50");
        ftp.setUserName("fdfs");
        ftp.setPwd("cl3AO&UXBdip");
        ftp.setPort(21);
        ftp.setPath("/channel/");

        File file = new File("C:\\Users\\ly\\Desktop\\lytest.txt");
        FtpUtils.upload(ftp, file);


    }


    @Data
    public static class Ftp {
        //ip地址
        private String ipAddr;
        //端口号
        private Integer port;
        //用户名
        private String userName;
        //密码
        private String pwd;

        private String path;//aaa路径
    }
}
