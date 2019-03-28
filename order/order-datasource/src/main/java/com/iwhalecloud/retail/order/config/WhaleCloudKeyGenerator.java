package com.iwhalecloud.retail.order.config;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WhaleCloudKeyGenerator implements IKeyGenerator {
    @Override
    public String executeSql(String incrementerName) {
        return " select CONCAT("+ builderKeySeq()+",'')";
    }
    public String mysqlKeySeq(String seqName){
        return builderKeySeq();
    }

    public static String builderKeySeq() {
        String dateFormatType="yyyyMMddHHmmssSSS";
        int randomNum=4;
        StringBuilder randomBuffer = new StringBuilder();
        randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
        randomNum+=2;
        while (randomBuffer.length() - dateFormatType.length() <= randomNum) {
            randomBuffer.append(Math.round(Math.random() * 99));
        }
        return randomBuffer.toString().substring(0, dateFormatType.length() + randomNum);
    }

    public static void main(String[] args) {
        System.out.println(builderKeySeq());
        System.out.println(builderKeySeq().length());
    }
}
