package com.iwhalecloud.retail.partner.service.impl;

import java.io.*;
import java.util.*;


public class ReplaceChart {
	
	private static Map<String,String> keyWordMappings = new HashMap<String,String>();
	private static String sourceDir = "";
	private static String targetDir = "";
	private static List<String> ignoreFileNames = new ArrayList<String>();
	private static List<String> ignoreDirNames = new ArrayList<String>();
	static {
		//1、设置需要替换的关键字（包括文件名称、文件夹名称、文件内容）
		keyWordMappings.put("partner", "partner");
		keyWordMappings.put("Partner", "Partner");
    	
		//2、设置需要导出的文件夹和目标目录
    	sourceDir = "D:\\02-project\\01-ztesoft\\NewRetail\\V1.0.1\\partner";  //windows下一定得“\\”格式
    	targetDir = "D:\\02-project\\01-ztesoft\\NewRetail\\V1.0.1\\partner";												 	 //windows下一定得“\\”格式
    	
    	//3、设置忽略拷贝的文件名称
    	ignoreFileNames.add(".classpath");
    	ignoreFileNames.add(".project");
    	ignoreFileNames.add(".class");
    	
    	//4、设置忽略拷贝的文件夹名称
    	ignoreDirNames.add(".settings");
    	ignoreDirNames.add("target");
    	ignoreDirNames.add(".svn");
    	ignoreDirNames.add("bin");
	}
	
	
    public static void main(String[] args) {
    	
        File f = new File(sourceDir);
        print(f, 0);
    }

    /**
     * 遍历目录
     *
     * @param f
     * @param len
     */
    public static void print(File f, int len) {
    	
    	//如果是需要忽略的文件夹
    	if (judeIsIgnoreDir(f.getName()) ) {
        	return;
        }
    	
        File[] file = f.listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory()) { //推断是否目录
                print(file[i], len + 1);
            }
            
            // 为防止输出文件覆盖源文件，所以更改输出盘路径 也可自行设置其它路径
            File outPath = new File(file[i].getParent().replace(sourceDir,targetDir));
            File readfile = new File(file[i].getAbsolutePath());

            if (!readfile.isDirectory()) {
                String filename = readfile.getName(); // 读到的文件名称
                String absolutepath = readfile.getAbsolutePath(); // 文件的绝对路径
                readFile(absolutepath, filename, i, outPath); // 调用 readFile
            }
        }
    }


	/**
     * 读取目录下的文件
     *
     * @return
     */
    public static void readFile(String absolutepath, String fileName, int index, File outPath) {
    	//如果是需要忽略的文件
        if (judeIsIgnoreFile(fileName) ) {
        	return;
        }
    	
        
        try {
        	
        	File nativeOutPath = new File(replaceKeyWord(outPath.getPath()));	//替换关键字后实际输出的文件路径
        	String nativeFileName = replaceKeyWord(fileName);						//替换关键字后实际输出的文件名称
        	
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(absolutepath), "UTF-8")); // 数据流读取文件

            StringBuffer strBuffer = new StringBuffer();
            for (String temp = null; (temp = bufReader.readLine()) != null; temp = null) {
                strBuffer.append(replaceKeyWord(temp));
                strBuffer.append(System.getProperty("line.separator")); // 换行符
            }

            bufReader.close();

            if (nativeOutPath.exists() == false) { // 检查输出目录是否存在，若不存在先创建
            	
            	nativeOutPath.mkdirs();
                System.out.println("已成功创建输出目录：" + nativeOutPath);
            }

            PrintWriter printWriter = new PrintWriter(nativeOutPath + "\\" +  nativeFileName, "UTF-8"); // 替换后输出文件路径
            printWriter.write(strBuffer.toString().toCharArray()); //又一次写入
            printWriter.flush();
            printWriter.close();
            System.out.println("第 " + (index + 1) + " 个文件   " + absolutepath + "  已成功输出到    " + nativeOutPath + "\\" + nativeFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回替换后的字符串
     * @param str
     * @return
     */
	private static String replaceKeyWord(String str) {
		String replacedStr = str;
		Iterator<String> it = keyWordMappings.keySet().iterator();
		while (it.hasNext()) {
			String oldKey = it.next();
			replacedStr = replacedStr.replace(oldKey, keyWordMappings.get(oldKey)); // 此处进行替换
		}
		
		return replacedStr;
	}
	
	private static boolean judeIsIgnoreFile(String fileName) {
		Iterator<String> it = ignoreFileNames.iterator();
		while (it.hasNext()) {
			if (fileName.equals(it.next())) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean judeIsIgnoreDir(String dirName) {
		Iterator<String> it = ignoreDirNames.iterator();
		while (it.hasNext()) {
			if (dirName.equals(it.next())) {
				return true;
			}
		}
		return false;
	}
}
