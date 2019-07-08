package com.iwhalecloud.retail.warehouse.util;

import com.iwhalecloud.retail.warehouse.dto.FtpDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ftp工具类
 *
 * @author sutingning
 * @date 2019年07月04日
 */
@Slf4j
public class FtpUtils {

    /**
     * 获取ftp连接
     * @param ftpDTO
     * @return
     * @throws Exception
     */
    public static FTPClient connectFtp(FtpDTO ftpDTO) throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpDTO.getIpAddr(), ftpDTO.getPort());
            ftpClient.login(ftpDTO.getUserName(), ftpDTO.getPassword());
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.getReplyCode();
            }
        } catch (Exception e) {
            log.error("ftp创建连接失败！");
        }
        return ftpClient;
    }

    /**
     * 关闭ftp连接
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

    /**
     * 递归遍历目录下面指定的文件名
     *
     * @param filePath 需要遍历的目录，必须以"/"开始和结束
     * @param ftp      ftp客户端
     * @param ext      文件后缀
     * @throws IOException
     */
    public static List<String> fileList(FTPClient ftp, String filePath, String ext) {
        List<String> list = new ArrayList<>();
        try {
            if (filePath.startsWith("/") && filePath.endsWith("/")) {
                //更换目录到当前目录
                ftp.changeWorkingDirectory(filePath);
                FTPFile[] files = ftp.listFiles();
                for (FTPFile file : files) {
                    if (file.isFile()) {
                        if (file.getName().endsWith(ext)) {
                            list.add(file.getName());
                        }
                    } else if (file.isDirectory()) {
                        //路径下级文件
                    /*if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        filePathList(pathName + file.getName() + "/", ext);
                    }*/
                    }

                }
            }
        }catch (IOException e){

        }
        return list;
    }
    /** * 移动文件 *
     * 进入文件所在目录
     * @param pathname 路径+文件名;同级目录输入新文件名
     * @param filename 文件名称
     * @return */
    public static Boolean copyFile(FTPClient ftpClient,String pathname, String filename){
        boolean flag = false;
        try {
//            log.info("开始移动文件");
            ftpClient.rename(filename,pathname);
            flag = true;
        } catch (Exception e) {
            log.error("移动"+filename+"文件失败:"+e.getMessage());
        }
        return flag;
    }

    /**
     * 删除路径下文件
     * @param file
     */
    public static void delTempChild(File file){
        if (file.isDirectory()) {
            //获取文件夹下所有子文件夹
            String[] children = file.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                delTempChild(new File(file, children[i]));
            }
        }
        // 目录空了，进行删除
        file.delete();
    }

    /**
     * 传送文件
     *
     * @param files
     */
    public static void sendFileToFtp(FTPClient ftpClient, String sendDir, List<String> files, String basePath) throws Exception{
        InputStream is = null;
        try {
            Boolean flag = ftpClient.changeWorkingDirectory(sendDir);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //传输模式
            ftpClient.enterLocalPassiveMode();
            for (int i = 0; i < files.size(); i++) {
                is = new FileInputStream(basePath + File.separator + files.get(i));
                //字节数组
                ftpClient.storeFile(files.get(i), is);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 进入指定目录
     * @param ftpClient
     * @param filePath
     * @return
     */
    public static FTPClient changeFtpDir(FTPClient ftpClient, String filePath) {
        try {
            Boolean flag = ftpClient.changeWorkingDirectory(filePath);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //传输模式
            ftpClient.enterLocalPassiveMode();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return ftpClient;
    }

    public static ArrayList<String> readFile(FTPClient ftpClient, String fileName) {
        InputStream ins = null;
//        StringBuilder builder = new StringBuilder();
        ArrayList<String> lineLists = new ArrayList<>();
        try {
            // 从服务器上读取指定的文件
//            System.out.println("文件名："+fileName);
            ins = ftpClient.retrieveFileStream(fileName);
            if(ins != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                String line;
//            builder = new StringBuilder(150);
                while ((line = reader.readLine()) != null) {
                    if(line.isEmpty()){
                        continue;
                    }
                    lineLists.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != ins) {
                try {
                    ins.close();
                    ftpClient.completePendingCommand();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return lineLists;
    }
}
