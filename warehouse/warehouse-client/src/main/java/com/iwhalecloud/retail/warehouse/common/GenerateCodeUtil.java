package com.iwhalecloud.retail.warehouse.common;

import java.util.UUID;

/**
 * @Author My
 * @Date 2019/1/7
 **/
public class GenerateCodeUtil {

    public static void main(String[] args) {
        String str = UUID.randomUUID().toString().replace("-","");
        String c = String.valueOf(System.currentTimeMillis());
        System.out.println(str.toUpperCase().substring(0,16));
        System.out.println(str.length());
        System.out.println(c);
        System.out.println(c.length());
        System.out.println(generateCode());
    }
    /**
     * 生成唯一编码
     * @return
     */
    public static String generateCode(){
        StringBuilder b = new StringBuilder();
        String str = UUID.randomUUID().toString().replace("-","");
        str = str.toUpperCase().substring(0,16);
        String c = String.valueOf(System.currentTimeMillis());
        b.append(str);
        b.append(c);
        return b.toString();
    }
}
