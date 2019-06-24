package com.iwhalecloud.retail.web.controller.b2b.partner.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.partner.response.MerchantRulesImportResp;
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

	/**
	 * data 转化为Excel
	 */
	public static void builderOrderExcel(Workbook book, List list,List<ExcelTitleName> map) {
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
					String filedName = (String)map.get(contentj).getValue();
					String finalValue = transferValue(filedName, String.valueOf(originalValue));
					cell.setCellValue(finalValue);
				}
			}
		}

	}


	private static String transferValue(String filedName, String value) {
		final String MKT_RES_INST_TYPE = "mktResInstType";
		final String STORAGE_TYPE = "storageType";
		final String STATUS_CD = "statusCd";
		final String SOURCE_TYPE = "sourceType";
		final String CREATE_TIME = "createTime";
		final String CREATE_DATE = "createDate";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
		if (filedName.equals(MKT_RES_INST_TYPE) && ResourceConst.MKTResInstType.TRANSACTION.getCode().equals(value)) {
			return ResourceConst.MKTResInstType.TRANSACTION.getName();
		}else if(filedName.equals(MKT_RES_INST_TYPE) && ResourceConst.MKTResInstType.NONTRANSACTION.getCode().equals(value)){
			return ResourceConst.MKTResInstType.NONTRANSACTION.getName();
		}else if(filedName.equals(MKT_RES_INST_TYPE) && ResourceConst.MKTResInstType.STANDBYMACHINE.getCode().equals(value)){
			return ResourceConst.MKTResInstType.STANDBYMACHINE.getName();
		}

		if (filedName.equals(STORAGE_TYPE) && ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getCode().equals(value)) {
			return ResourceConst.STORAGETYPE.TRANSACTION_WAREHOUSING.getName();
		}else if(filedName.equals(STORAGE_TYPE) && ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getCode().equals(value)){
			return ResourceConst.STORAGETYPE.ALLOCATION_AND_WAREHOUSING.getName();
		}else if(filedName.equals(STORAGE_TYPE) && ResourceConst.STORAGETYPE.LEADING_INTO_STORAGE.getCode().equals(value)){
			return ResourceConst.STORAGETYPE.LEADING_INTO_STORAGE.getName();
		}else if(filedName.equals(STORAGE_TYPE) && ResourceConst.STORAGETYPE.GREEN_CHANNEL.getCode().equals(value)){
			return ResourceConst.STORAGETYPE.GREEN_CHANNEL.getName();
		}else if(filedName.equals(STORAGE_TYPE) && ResourceConst.STORAGETYPE.MANUAL_ENTRY.getCode().equals(value)){
			return ResourceConst.STORAGETYPE.MANUAL_ENTRY.getName();
		}

		if (filedName.equals(STATUS_CD) && ResourceConst.STATUSCD.AUDITING.getCode().equals(value)) {
			return ResourceConst.STATUSCD.AUDITING.getName();
		}else if(filedName.equals(STATUS_CD) && ResourceConst.STATUSCD.AVAILABLE.getCode().equals(value)){
			return ResourceConst.STATUSCD.AVAILABLE.getName();
		}else if(filedName.equals(STATUS_CD) && ResourceConst.STATUSCD.ALLOCATIONING.getCode().equals(value)){
			return ResourceConst.STATUSCD.ALLOCATIONING.getName();
		}else if(filedName.equals(STATUS_CD) && ResourceConst.STATUSCD.RESTORAGEING.getCode().equals(value)){
			return ResourceConst.STATUSCD.RESTORAGEING.getName();
		}else if(filedName.equals(STATUS_CD) && ResourceConst.STATUSCD.RESTORAGED.getCode().equals(value)){
			return ResourceConst.STATUSCD.RESTORAGED.getName();
		}else if(filedName.equals(STATUS_CD) && ResourceConst.STATUSCD.SALED.getCode().equals(value)){
			return ResourceConst.STATUSCD.SALED.getName();
		}else if(filedName.equals(STATUS_CD) && ResourceConst.STATUSCD.DELETED.getCode().equals(value)){
			return ResourceConst.STATUSCD.DELETED.getName();
		}


		if (filedName.equals(CREATE_TIME) || filedName.equals(CREATE_DATE)) {
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

