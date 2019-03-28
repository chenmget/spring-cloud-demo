package com.iwhalecloud.retail.web.controller.partner.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: wang.jiaxin
 * @date: 2019年03月18日
 * @description:
 **/
@Slf4j
@Component
public class ExcelToMerchantListUtils {

    /**
     * data 转化为Excel
     */
    public static void builderOrderExcel(Workbook book, List list, List<ExcelTitleName> map) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        String sheetName = "sheet";
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(list));
        //标题占位
        jsonArray.add(0, new Object());
        Sheet sheet1 = book.createSheet(sheetName);
        Row titleRow = sheet1.createRow(0);
        for (int i = 0; i < map.size(); i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(map.get(i).getName());
        }
        // 行数量根据数据条数设置
        for (int rowi = 0; rowi < jsonArray.size(); rowi++) {
            Row row = sheet1.createRow(rowi);
            for (int contentj = 0; contentj < map.size(); contentj++) {
                Cell cell = row.createCell(contentj);
                if (rowi == 0) {
                    //设置标题
                    cell.setCellValue(map.get(contentj).getName());
                } else {
                    //设置内容
                    Object originalValue = jsonArray.getJSONObject(rowi).get(map.get(contentj).getValue());
                    String filedName = map.get(contentj).getValue();
                    String finalValue = transferValue(filedName, String.valueOf(originalValue));
                    cell.setCellValue(finalValue);
                }
            }
        }

    }


    private static String transferValue(String filedName, String value) {
        final String MERCHANT_TYPE = "merchantType";
        final String STATUS = "status";
        final String EFF_DATE = "effDate";
        final String EXP_DATE = "expDate";
        final String LAST_UPDATE_DATE = "lastUpdateDate";
        final String BUSILICENCE_EXP_DATE = "busiLicenceExpDate";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        // 渠道状态转换
        if (filedName.equals(STATUS)) {
            if(PartnerConst.MerchantStatusEnum.VALID.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.VALID.getName();
            } else if(PartnerConst.MerchantStatusEnum.INVALID.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.INVALID.getName();
            } else if(PartnerConst.MerchantStatusEnum.PAUSE.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.PAUSE.getName();
            } else if(PartnerConst.MerchantStatusEnum.EXCEPTION_PAUSE.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.EXCEPTION_PAUSE.getName();
            } else if(PartnerConst.MerchantStatusEnum.TERMINATION.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.TERMINATION.getName();
            } else if(PartnerConst.MerchantStatusEnum.QUIT.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.QUIT.getName();
            } else if(PartnerConst.MerchantStatusEnum.NOT_EFFECT.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.NOT_EFFECT.getName();
            } else if(PartnerConst.MerchantStatusEnum.ARCHIVED.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.ARCHIVED.getName();
            } else if(PartnerConst.MerchantStatusEnum.PRE_QUIT.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.PRE_QUIT.getName();
            } else if(PartnerConst.MerchantStatusEnum.FREEZE.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantStatusEnum.FREEZE.getName();
            }
        }
        // 商家类型转换
        if (filedName.equals(MERCHANT_TYPE)) {
            if(PartnerConst.MerchantTypeEnum.MANUFACTURER.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantTypeEnum.MANUFACTURER.getName();
            } else if(PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantTypeEnum.SUPPLIER_GROUND.getName();
            } else if(PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantTypeEnum.SUPPLIER_PROVINCE.getName();
            } else if(PartnerConst.MerchantTypeEnum.PARTNER.getType().equalsIgnoreCase(value)){
                return PartnerConst.MerchantTypeEnum.PARTNER.getName();
            }
        }
        // 时间转换
        if (filedName.equals(EFF_DATE) || filedName.equals(EXP_DATE) || filedName.equals(LAST_UPDATE_DATE) || filedName.equals(BUSILICENCE_EXP_DATE)) {
            try {
                Date date = new Date(Long.valueOf(value));
                String StringDate = format.format(date);
                return StringDate;
            }catch (Exception e){
                log.error("时间解析错误",e);
            }
        }
        String finalValue = (value == null || "null".equals(value))? "" : value;
        return finalValue;
    }
}
