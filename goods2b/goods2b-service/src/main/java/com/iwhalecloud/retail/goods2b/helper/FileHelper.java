package com.iwhalecloud.retail.goods2b.helper;

import com.iwhalecloud.retail.goods2b.common.FileConst;
import org.springframework.util.StringUtils;

/**
 * 附件表的辅助类
 * @author Z
 * @date 2018/12/5
 */
public class FileHelper {


    /**
     * 获取数据库中保存文件的类型
     * @param fileName 文件名称
     * @return 文件类型
     */
    public static String getFileType (String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return "";
        }

        String extention = StringUtils.getFilenameExtension(fileName).toLowerCase();
        if ("bmp,jpg,jpeg,png,gif,tiff,tga".indexOf(extention) > -1) {
            return FileConst.FileType.IMAGE_TYPE.getType();
        } else if ("rm,rmvb,mov,mtv,dat,wmv,avi,3gp,amv,dmv,flv".indexOf(extention) > -1) {
            return FileConst.FileType.VIDIO_TYPE.getType();
        } else if ("zip,rar".indexOf(extention) > -1) {
            return FileConst.FileType.ZIP_TYPE.getType();
        } else {
            return FileConst.FileType.OTHER_TYPE.getType();
        }
    }

}
