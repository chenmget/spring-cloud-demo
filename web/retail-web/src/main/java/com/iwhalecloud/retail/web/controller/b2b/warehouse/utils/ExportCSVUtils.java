package com.iwhalecloud.retail.web.controller.b2b.warehouse.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class ExportCSVUtils {

	/** CSV文件列分隔符 */
	private static final String CSV_COLUMN_SEPARATOR = ",";

	/** CSV文件列分隔符 */
	private static final String CSV_RN = "\r\n";

	/** CSV文件列分隔符 */
	private static final String CSV_T = "\t";

	/** CSV文件动态显示字段 */
	private static final String CHANGE_FIELD = "attrValue";

	/**
	 * @param output
	 * @param list 导出数据
	 * @param map  导出数据对应字段
	 * @param isRetailer 是否零售商 true是；false否
	 */
	public static void doExport(OutputStream output, List list,List<ExcelTitleName> map, List<AttrSpecDTO> changeFiled, Boolean isRetailer) {
		try {
			StringBuffer buf = new StringBuffer();

			// 完成数据csv文件的封装
			// 输出列头
			map = getChangeField(changeFiled, map);
			for (ExcelTitleName excelTitleName : map) {
				buf.append(excelTitleName.getName()).append(CSV_COLUMN_SEPARATOR);
			}
			buf.append(CSV_RN);
			JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(list));
			for (int rowi = 0; rowi < jsonArray.size(); rowi++) {
				for (int contentj = 0; contentj < map.size(); contentj++) {
					//设置内容
					Object originalValue = jsonArray.getJSONObject(rowi).get(map.get(contentj).getValue());
					String filedName = (String)map.get(contentj).getValue();
					String finalValue = transferValue(filedName, String.valueOf(originalValue), isRetailer);
					buf.append(finalValue).append(CSV_COLUMN_SEPARATOR);
					if (contentj == (map.size()-1)) {
						buf.append(CSV_RN);
					}
				}
			}
			// 写出响应
			output.write(buf.toString().getBytes("GBK"));
			output.flush();
		} catch (Exception e) {
			log.error("doExport错误...", e);
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
		final String NBR = "mktResInstNbr";
		final String SN = "sn";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
		String finalValue = (value == null || "null".equals(value))? "" : value;
		if (MKT_RES_INST_TYPE.equals(filedName)) {
			finalValue = ResourceConst.MKTResInstType.getMKTResInstTypeName(value);
		}

		if (STORAGE_TYPE.equals(filedName)) {
			finalValue = ResourceConst.STORAGETYPE.getStorageTypeName(value);
		}

		if (STATUS_CD.equals(filedName)) {
			log.info("STATUS_CD={}", value);
			if (isRetailer) {
				finalValue = ResourceConst.CRM_STATUS.getCrmStatusName(value);
			}
			finalValue = ResourceConst.STATUSCD.getName(value);
		}

		if (SOURCE_TYPE.equals(filedName)) {
			finalValue = ResourceConst.SOURCE_TYPE.getName(value);
		}

		if (CREATE_TIME.equals(filedName) || CREATE_DATE.equals(filedName)) {
			try {
				Date date = new Date(Long.valueOf(value));
				String StringDate = format.format(date);
				finalValue =  StringDate;
			}catch (Exception e){
				log.error("时间解析错误",e);
			}
		}

		if (RESULT.equals(filedName)) {
			if (ResourceConst.CONSTANT_YES.equals(value)) {
				finalValue = "是";
			}else {
				finalValue = "否";
			}
		}
		finalValue = finalValue == "" ? finalValue : finalValue + CSV_T;
		return finalValue;
	}

	private static List<ExcelTitleName> getChangeField(List<AttrSpecDTO> changeFiled, List<ExcelTitleName> map){

		Integer index = map.indexOf(CHANGE_FIELD);
		map.remove(index);
		StringBuffer buf = new StringBuffer();
		Integer i = 0;
		for (AttrSpecDTO dto : changeFiled) {
			ExcelTitleName titleName = new ExcelTitleName(dto.getFiledName(), dto.getCname());
			map.add(index+i, titleName);
			i++;
		}
		return map;

	}

}

