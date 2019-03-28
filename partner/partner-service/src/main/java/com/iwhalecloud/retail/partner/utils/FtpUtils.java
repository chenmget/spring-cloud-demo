package com.iwhalecloud.retail.partner.utils;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.model.FtpDTO;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ftp工具类
 *
 * @author xu.qinyuan@ztesoft.com
 * @date 2019年03月7日
 */
public class FtpUtils {

    private static String LOCAL_CHARSET = "GBK";
    private static String SERVER_CHARSET = "ISO-8859-1";

    /**
     * ftp连接
     *
     * @param ftpDTO ftp连接实体类
     * @return
     */
    public static FTPClient connectServer(FtpDTO ftpDTO) {
        FTPClient ftpClient = new FTPClient();
        String ip = ftpDTO.getIp();
        String userName = ftpDTO.getUserName();
        String password = ftpDTO.getPassword();
        Integer port = ftpDTO.getPort();
        try {
            // 连接
            ftpClient.connect(ip, port);
            // 登录
            ftpClient.login(userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 读取文件
     * @param ftpDTO ftp实体对象
     * @return 每一行数据为一个list
     * @throws ParseException
     * @throws IOException
     */
    public static ResultVO<List<String>> readFile(FtpDTO ftpDTO) throws ParseException, IOException {
        List<String> result = new ArrayList<>();
        String path = ftpDTO.getFilePath();
        FTPClient ftpClient = connectServer(ftpDTO);
        if (path != null && path.length() > 0) {
            // 跳转到指定目录
            ftpClient.changeWorkingDirectory(path);
        }
        InputStream ins = null;
        // 从服务器上读取指定的文件
        // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
        if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
            LOCAL_CHARSET = "UTF-8";
        }
        ftpClient.enterLocalPassiveMode();
        ftpClient.setControlEncoding(SERVER_CHARSET);
        ins = ftpClient.retrieveFileStream(ftpDTO.getFileName());
        if (Objects.isNull(ins)) {
            return ResultVO.error("文件不存在");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        reader.close();
        ins.close();
        // 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
        ftpClient.getReply();
        closeServer(ftpClient);
        return ResultVO.success(result);
    }

    /**
     * 关闭连接
     *
     * @param ftpClient
     */
    public static void closeServer(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            FtpDTO ftpDTO = new FtpDTO();
            ftpDTO.setIp("134.176.97.50");
            ftpDTO.setUserName("fdfs");
            ftpDTO.setPassword("cl3AO&UXBdip");
            ftpDTO.setPort(21);
            ftpDTO.setFilePath("/channel/");
            ftpDTO.setFileName("BUSINESS_ENTITY_20190304.txt");
            ResultVO resultVO = FtpUtils.readFile(ftpDTO);
            if (!resultVO.isSuccess()) {
                System.out.println(resultVO.getResultMsg());
            }
            System.out.println(resultVO.getResultData());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
