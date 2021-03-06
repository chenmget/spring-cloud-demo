package com.iwhalecloud.retail.web.controller.b2b.warehouse.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.UpdateCorporationPriceReq;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.ExcelResourceReqDetailDTO;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ExcelToNbrAndCteiResp;
import com.iwhalecloud.retail.web.controller.b2b.warehouse.response.ExcelToNbrAndMacResp;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ExcelToNbrUtils {

	private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * 读取Excel并把数据返回，兼容 Excel 2003/2007/2010
     * @throws Exception
     */
    public static List<ResInsExcleImportResp> getData(InputStream inputStream) throws Exception {
		List<ResInsExcleImportResp> data = new ArrayList<ResInsExcleImportResp>();
        try {
			// 这种方式 Excel2003/2007/2010都是可以处理的
            Workbook workbook = WorkbookFactory.create(inputStream);
            // 只读第一页
			Sheet sheet = workbook.getSheetAt(0);
			//获得当前sheet的开始行
			int firstRowNum  = sheet.getFirstRowNum();
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
				ResInsExcleImportResp resp = new ResInsExcleImportResp();
				resp.setMktResInstNbr(replaceBlank(getCellValue(row.getCell(firstCellNum))));
				// 2种模板
				if(lastCellNum > firstCellNum) {
					resp.setCtCode(getCellValue(row.getCell(firstCellNum + 1)));
				}
				data.add(resp);
			}
		} catch (Exception e) {
			log.error("解析excel异常", e);
			throw new Exception(e);
		}
		return data;
    }

    public static List<UpdateCorporationPriceReq> getPriceData(InputStream inputStream) throws Exception {
		List<UpdateCorporationPriceReq> data = new ArrayList<UpdateCorporationPriceReq>();
        try {
			// 这种方式 Excel2003/2007/2010都是可以处理的
            Workbook workbook = WorkbookFactory.create(inputStream);
            // 只读第一页
			Sheet sheet = workbook.getSheetAt(0);
			//获得当前sheet的开始行
			int firstRowNum  = sheet.getFirstRowNum();
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
				UpdateCorporationPriceReq resp = new UpdateCorporationPriceReq();
				resp.setSn(getCellValue(row.getCell(firstCellNum)));
				// 2种模板
				if(lastCellNum > firstCellNum) {
					resp.setCorporationPrice(getCellValue(row.getCell(firstCellNum + 1)));
//					String snPrice = getCellValue(row.getCell(firstCellNum)) +"|"+ getCellValue(row.getCell(firstCellNum + 1));
//					resp.setSnPrice(snPrice);
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
	 *
	 * @param book
	 * @param list 导出数据
	 * @param map  导出数据对应字段
	 * @param isRetailer 是否零售商 true是；false否
	 */
	public static void builderOrderExcel(Workbook book, List list,List<ExcelTitleName> map, Boolean isRetailer) {
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
					String finalValue = transferValue(filedName, String.valueOf(originalValue), isRetailer);
					cell.setCellValue(finalValue);
				}
			}
		}
	}


	private static String transferValue(String filedName, String value, Boolean isRetailer) {
		final String MKT_RES_INST_TYPE = "mktResInstType";
		final String STORAGE_TYPE = "storageType";
		final String STATUS_CD = "statusCd";
		final String SOURCE_TYPE = "sourceType";
		final String CREATE_TIME = "createTime";
		final String CREATE_DATE = "createDate";
		final String CRM_STATUS = "statusCd";
		final String RESULT = "result";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
		if (filedName.equals(MKT_RES_INST_TYPE)) {
			return ResourceConst.MKTResInstType.getMKTResInstTypeName(value);
		}

		if (filedName.equals(STORAGE_TYPE)) {
			return ResourceConst.STORAGETYPE.getStorageTypeName(value);
		}

		if (filedName.equals(STATUS_CD)) {
			log.info("STATUS_CD={}", value);
			if (isRetailer) {
				return ResourceConst.CRM_STATUS.getCrmStatusName(value);
			}
			return ResourceConst.STATUSCD.getName(value);
		}

		if (filedName.equals(SOURCE_TYPE)) {
			return ResourceConst.SOURCE_TYPE.getName(value);
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

		if (filedName.equals(RESULT)) {
			if (ResourceConst.CONSTANT_YES.equals(value)) {
				return "是";
			}else {
				return "否";
			}
		}
		String finalValue = (value == null || "null".equals(value))? "" : value;
		return finalValue;
	}


	/**
	 * 读取Excel并把数据返回，兼容 Excel 2003/2007/2010
	 * @throws Exception
	 */
	public static List<ExcelToNbrAndMacResp> getNbrAndMac(InputStream inputStream) throws Exception {
		List<ExcelToNbrAndMacResp> data = new ArrayList<ExcelToNbrAndMacResp>();
		try {
			// 这种方式 Excel2003/2007/2010都是可以处理的
			Workbook workbook = WorkbookFactory.create(inputStream);
			// 只读第一页
			Sheet sheet = workbook.getSheetAt(0);
			//获得当前sheet的开始行
			int firstRowNum  = sheet.getFirstRowNum();
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
				ExcelToNbrAndMacResp resp = new ExcelToNbrAndMacResp();
				resp.setMktResInstNbr(getCellValue(row.getCell(firstCellNum)));
				resp.setSnCode(getCellValue(row.getCell(firstCellNum + 1)));
				resp.setMacCode(getCellValue(row.getCell(firstCellNum + 2)));
				data.add(resp);
			}
		} catch (Exception e) {
			log.error("解析excel异常", e);
			throw new Exception(e);
		}
		return data;
	}

	/**
	 * 读取Excel并把数据返回，兼容 Excel 2003/2007/2010
	 * @throws Exception
	 */
	public static List<ExcelToNbrAndCteiResp> getNbrAndCtei(InputStream inputStream) throws Exception {
		List<ExcelToNbrAndCteiResp> data = new ArrayList<ExcelToNbrAndCteiResp>();
		try {
			// 这种方式 Excel2003/2007/2010都是可以处理的
			Workbook workbook = WorkbookFactory.create(inputStream);
			// 只读第一页
			Sheet sheet = workbook.getSheetAt(0);
			//获得当前sheet的开始行
			int firstRowNum  = sheet.getFirstRowNum();
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
				ExcelToNbrAndCteiResp resp = new ExcelToNbrAndCteiResp();
				resp.setMktResInstNbr(getCellValue(row.getCell(firstCellNum)));
				resp.setSnCode(getCellValue(row.getCell(firstCellNum + 1)));
				resp.setCtCode(getCellValue(row.getCell(firstCellNum + 2)));
				data.add(resp);
			}
		} catch (Exception e) {
			log.error("解析excel异常", e);
			throw new Exception(e);
		}
		return data;
	}

	public static List<ExcelResourceReqDetailDTO> getNbrDetailData(InputStream inputStream) throws Exception {
		List<ExcelResourceReqDetailDTO> data = new ArrayList<ExcelResourceReqDetailDTO>();
		try {
			// 这种方式 Excel2003/2007/2010都是可以处理的
			Workbook workbook = WorkbookFactory.create(inputStream);
			// 只读第一页
			Sheet sheet = workbook.getSheetAt(0);
			//获得当前sheet的开始行
			int firstRowNum  = sheet.getFirstRowNum();
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
//				int firstCellNum = row.getFirstCellNum();
//				//获得当前行的列数
//				int lastCellNum = row.getPhysicalNumberOfCells();
				ExcelResourceReqDetailDTO resp = new ExcelResourceReqDetailDTO();
//				resp.setMktResReqDetailId(getCellValue(row.getCell(firstCellNum)));//第一列申请单号
//				resp.setReqCode(getCellValue(row.getCell(1)));//第2列申请单号
//				resp.setMktResInstNbr(getCellValue(row.getCell(2)));//第3列串码
//				//resp.setStatusCdName(ResourceConst.REQ_DETAIL_STATUS.getCodeByName(getCellValue(row.getCell(8))));//第9列审核结果
//				resp.setStatusCdName(getCellValue(row.getCell(9)));//第10列审核结果
//				resp.setRemark(getCellValue(row.getCell(10)));//第11列状态说明
				resp.setMktResInstNbr(getCellValue(row.getCell(0)));//第1列串码
				resp.setStatusCdName(getCellValue(row.getCell(1)));//第2列审核结果
				resp.setRemark(getCellValue(row.getCell(2)));//第3列状态说明
				data.add(resp);
			}
		} catch (Exception e) {
			log.error("解析excel异常", e);
			throw new Exception(e);
		}
		return data;
	}

	/**
	 * 删除文件中的空格，换行，制表符
	 * @param str
	 * @return
	 */
	private static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

}

