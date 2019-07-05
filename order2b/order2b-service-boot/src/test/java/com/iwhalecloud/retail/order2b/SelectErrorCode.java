package com.iwhalecloud.retail.order2b;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectErrorCode {

    static int count = 0;

    public static void main(String[] args) throws Exception {


        ArrayList<String> strArray = new ArrayList<String>();
        strArray.add("goods2b"); //王振宇
        strArray.add("member");  //王振宇
        strArray.add("order2b"); //王振宇
        --strArray.add("partner");//王振宇
//        strArray.add("payment");
        strArray.add("promo");   //王振宇

        strArray.add("report");  //陈斌
        strArray.add("rights");  //陈斌
        strArray.add("system");  //陈斌
        strArray.add("warehouse");//陈斌
//        --strArray.add("web");
        strArray.add("workflow");//陈斌


        ArrayList<File> fileArray = new ArrayList<File>();
        for (int i = 0; i < strArray.size(); i++) {
            fileArray.add(new File("D:\\02-project\\01-ztesoft\\02-git\\retail\\", strArray.get(i)));
            findFolder(fileArray.get(i));
        }

        System.out.println(count);

    }


    private static void findFolder(File srcFolder) throws Exception {
        // 获取该目录下的所有文件或者文件夹的File数组
        File[] fileArray = srcFolder.listFiles();

        if (fileArray != null) {
            // 遍历该File数组，得到每一个File对象
            for (File file : fileArray) {
                // 判断该File对象是否是文件夹
                if (file.isDirectory()) {
//                    System.out.println(file.getAbsoluteFile());
                    findFolder(file);
                } else {
                    if (file.getName().endsWith(".java")) {
//                        System.out.println(file.getName());
                        findChinese(file);
                    }
                }
            }
        }
    }

    private static void findChinese(File file) throws Exception {

        if (file.getName().endsWith("Test.java")) {
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line1 = null;
        boolean booFoundPackage = false;
        boolean outputFileName = false;
        while ((line1 = br.readLine()) != null) {
            String line = line1.trim();
            //过滤掉entity文件
            if (!booFoundPackage && line.contains("package")) {
                booFoundPackage = true;
                if (line.contains("entity") || line.contains("dto")) {
                    return;
                }
            }
            if (!line.startsWith("//") && !line.startsWith("*") && !line.startsWith("log.") /*&& !line.startsWith("throw ")*/ && !line.startsWith("/**") && !line.startsWith("@")) {
//                if (isContainChinese(line) && line.startsWith("return ResultVO.error")){
                if (isContainChinese(line)) {
//                    String line2[] = line.split("\"");

                    System.out.println(">>>文件>>>" + file.getName());

                    if (!outputFileName) {
                        System.out.println(line);
                    }
//                    System.out.println(line2[1]);
//                    System.out.println(file.getAbsoluteFile());
                    count++;
                }

            }
        }

        br.close();
    }


    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


}
