package com.iwhalecloud.retail.web.controller.b2b.report.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
 * @author wenlong.zhong
 * @date 2019/6/11
 */
@Component
@Slf4j
public class ExcelToSupplierOperatingDayListUtils {

    /**
     * data 转化为Excel
     */
    public static void builderOrderExcel(Workbook book, List list, List<ExcelTitleName> map) {
        if (CollectionUtils.isEmpty(list)) {
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
        final String ITEM_DATE = "itemDate";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        // 时间转换
        if (filedName.equals(ITEM_DATE)) {
            try {
                Date date = new Date(Long.valueOf(value));
                String StringDate = format.format(date);
                return StringDate;
            } catch (Exception e) {
                log.error("时间解析错误", e);
            }
        }
        String finalValue = (value == null || "null".equals(value)) ? "" : value;
        return finalValue;
    }
}
