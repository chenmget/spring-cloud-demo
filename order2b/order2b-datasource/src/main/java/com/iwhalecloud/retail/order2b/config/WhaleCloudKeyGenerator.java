package com.iwhalecloud.retail.order2b.config;

import com.iwhalecloud.retail.order2b.mapper.SelectSeqMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WhaleCloudKeyGenerator{

    @Resource
    private SelectSeqMapper selectSeqMapper;

    private final static String DB_TYPE_TEL = "TelDB";
    private final static String DB_TYPE_ORACLE = "Oracle";
    private final static String DB_TYPE_MYSQL = "MySql";

    @Value("${db-type}")
    private String dataSourceType;


    public String tableKeySeq(String seqName) {
//        seqName="seq_retail_all_tables.nextval";
//        return builderKeySeq().concat(selectSeqMapper.getSeq(seqName));
        if (StringUtils.isEmpty(seqName)) {
            return builderKeySeqD();
        }
        if (DB_TYPE_TEL.equals(dataSourceType)) {
            DBTableSequence dbTableSequence = DBTableSequence.matchOpCode(seqName);
            if (StringUtils.isEmpty(dbTableSequence.getCode())) {
                return builderKeySeqD();
            }
            return builderKeySeq().concat(selectSeqMapper.getSeq(seqName));
        } else {
            return builderKeySeqD();
        }
    }

    private static String builderKeySeq() {
        String dateFormatType = "yyyyMMddSSS";
        String seq = new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis()));
        return seq.substring(0, dateFormatType.length()-1);
    }

    private static String builderKeySeqD() {
        String dateFormatType = "yyyyMMddHHmmssSSS";
        int randomNum = 4;
        StringBuilder randomBuffer = new StringBuilder();
        randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
        randomNum += 2;
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
