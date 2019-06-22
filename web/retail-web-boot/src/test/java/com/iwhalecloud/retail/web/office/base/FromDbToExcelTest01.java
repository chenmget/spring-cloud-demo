package com.iwhalecloud.retail.web.office.base;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.OrderExportUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.util.List;

public class FromDbToExcelTest01 {
    public static void main(String[] args) throws Exception {
        Workbook book = new HSSFWorkbook();

        Sheet sheet1 = book.createSheet("订单列表");
        List<ExcelTitleName> map = OrderExportUtil.getOrder();
        //设置标题
        Row titleRow=sheet1.createRow(0);
        for (int i=0;i<map.size();i++){
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(map.get(i).getName());
        }

        for (int rowi=0;rowi<100;rowi++){
            Row row = sheet1.createRow(rowi);
            for (int contentj=0;contentj<map.size();contentj++){
                Cell cell = row.createCell(contentj);
                if (rowi == 0) {
                    //设置标题
                    cell.setCellValue(map.get(contentj).getName());
                } else {
                    //设置内容
                    cell.setCellValue(map.get(contentj).getValue() + rowi);
                }
            }
        }

        book.write(new FileOutputStream("C:\\workSpace\\NewSales\\tst\\" + (System.currentTimeMillis()) + ".xls"));
    }
}