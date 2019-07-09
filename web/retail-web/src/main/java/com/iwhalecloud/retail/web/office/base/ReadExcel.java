package com.iwhalecloud.retail.web.office.base;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gongsheng
 */
public abstract class ReadExcel<T> {

    /**
     * Read the Excel 2010
     *
     * @return
     * @throws IOException
     */
    public List<T> readXlsx2010(InputStream is,int page) throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<T> list = new ArrayList<>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }

            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    list.add(builderObject2010(xssfRow));
                }
            }
        }

        return list;
    }

    /**
     * Read the Excel 2003-2007
     *
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<T> readXls2007(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<T> list = new ArrayList<>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    list.add(builderObject2007(hssfRow));
                }
            }
        }
        return list;
    }

   public abstract T builderObject2007(HSSFRow cell);
   public abstract T builderObject2010(XSSFRow cell);

    public String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    public String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    public  InputStream workbookConvertorStream(Workbook workbook) {
        InputStream in = null;
        try{
            //临时缓冲区
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //创建临时文件
            workbook.write(out);
            byte [] bookByteAry = out.toByteArray();
            in = new ByteArrayInputStream(bookByteAry);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return in;
    }
}
