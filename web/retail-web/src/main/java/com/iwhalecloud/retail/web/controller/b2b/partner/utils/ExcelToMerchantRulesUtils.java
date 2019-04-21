package com.iwhalecloud.retail.web.controller.b2b.partner.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.partner.response.MerchantRulesImportResp;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ResInsExcleImportResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ExcelToMerchantRulesUtils {

	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";

	/**
	 * 读取Excel并把数据返回，兼容 Excel 2003/2007/2010
	 * @throws Exception
	 */
	public static List<MerchantRulesImportResp> getData(InputStream inputStream) throws Exception {
		List<MerchantRulesImportResp> data = new ArrayList<MerchantRulesImportResp>();
		try {
			// 这种方式 Excel2003/2007/2010都是可以处理的
			Workbook workbook = WorkbookFactory.create(inputStream);
			// 只读第一页
			Sheet sheet = workbook.getSheetAt(0);
			//获得当前sheet的开始行
			int firstRowNum  = sheet.getFirstRowNum();
			//获取当前行
			Row firstRow = sheet.getRow(firstRowNum);
			//获得当前sheet的结束行
			int lastRowNum = sheet.getLastRowNum();
			//循环除了第一行的所有行
			for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
				//获得当前行
				Row row = sheet.getRow(rowNum);
				if(row == null){
					continue;
				}
				//获得当前行的开始列
				int firstCellNum = row.getFirstCellNum();
				//获得当前行的列数
				int lastCellNum = row.getPhysicalNumberOfCells();
				if(getCellValue(firstRow.getCell(firstCellNum + 1)) == null || "".equals(getCellValue(firstRow.getCell(firstCellNum + 1)))
						|| getCellValue(firstRow.getCell(firstCellNum)) == null || "".equals(getCellValue(firstRow.getCell(firstCellNum)))){
					continue;
				}
				MerchantRulesImportResp resp = new MerchantRulesImportResp();
				resp.setMerchantCode(getCellValue(row.getCell(firstCellNum)));
				// 2种模板
				if(lastCellNum > firstCellNum) {
					if("区域编码".equals(getCellValue(firstRow.getCell(firstCellNum + 1)))) {
						resp.setRegionId(getCellValue(row.getCell(firstCellNum + 1)));
					}else if("机型编码".equals(getCellValue(firstRow.getCell(firstCellNum + 1)))){
						resp.setSn(getCellValue(row.getCell(firstCellNum + 1)));
					}else if("对象编码".equals(getCellValue(firstRow.getCell(firstCellNum + 1)))){
						resp.setTargetMerchantCode(getCellValue(row.getCell(firstCellNum + 1)));
					}
				}
				data.add(resp);
			}
		} catch (Exception e) {
			log.error("解析excel异常", e);
			throw new Exception(e);
		}
		return data;
	}


	public static String getCellValue(Cell cell){
		String cellValue = "";
		if(cell == null){
			return cellValue;
		}
		//把数字当成String来读，避免出现1读成1.0的情况
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		//判断数据的类型
		switch (cell.getCellType()){
			//数字
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = String.valueOf(cell.getNumericCellValue());
				break;
			//字符串
			case Cell.CELL_TYPE_STRING:
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			//Boolean
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			//公式
			case Cell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			//空值
			case Cell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			//故障
			case Cell.CELL_TYPE_ERROR:
				cellValue = "非法字符";
				break;
			default:
				cellValue = "未知类型";
				break;
		}
		return cellValue;
	}


}

