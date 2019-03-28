package com.iwhalecloud.retail.web.utils;

/**
 * @Author My
 * @Date 2018/12/21
 **/
public class FastDFSImgStrJoinUtil {


    public static FastDFSImgStrJoinUtil getInstance(){
        return new FastDFSImgStrJoinUtil();
    }
    /**
     * 拼接完整的地址
     * @param imagePath
     * @param showUrl
     * @param flag 为true时，拼接完整地址，为false时，是截取地址
     * @return
     */
    public static String fullImageUrl(String imagePath, String showUrl, boolean flag) {
        String aftPath = "";
        if (flag) {
            String[] pathArr = imagePath.split(",");
            for (String befPath : pathArr) {
                if (org.springframework.util.StringUtils.isEmpty(aftPath)) {
                    if (!befPath.startsWith("http")) {
                        aftPath += showUrl + befPath;
                    } else {
                        aftPath += befPath;
                    }
                } else {
                    if (!befPath.startsWith("http")) {
                        aftPath += "," + showUrl + befPath;
                    } else {
                        aftPath += "," + befPath;
                    }
                }
            }
        } else {
            if (!org.springframework.util.StringUtils.isEmpty(imagePath)) {
                aftPath = imagePath.replaceAll(showUrl, "");
            }
        }
        return aftPath;
    }
}
