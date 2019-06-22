package com.iwhalecloud.retail.goods.service.impl;

import com.iwhalecloud.retail.goods.dto.req.ProdGoodsAddReq;
import com.iwhalecloud.retail.goods.entity.GoodsExt;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POIUtils {

	private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx";  
  
    /** 
     * 判断Excel的版本,获取Workbook 
     * @param in 
     * @param filename 
     * @return 
     * @throws IOException 
     */  
    public static Workbook getWorkbok(InputStream in,File file) throws IOException{  
        Workbook wb = null;  
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003  
            wb = new HSSFWorkbook(in);  
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010  
            wb = new XSSFWorkbook(in);  
        }  
        return wb;  
    }  
  
    /** 
     * 判断文件是否是excel 
     * @throws Exception  
     */  
    public static void checkExcelVaild(File file) throws Exception{  
        if(!file.exists()){  
            throw new Exception("文件不存在");  
        }  
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){  
            throw new Exception("文件不是Excel");  
        }  
    }  
    
    /** 
     * 读取Excel测试，兼容 Excel 2003/2007/2010 
     * @throws Exception  
     */  
    public static Map<String, Object> getData(String filePath) {  
        try {  
            // 同时支持Excel 2003、2007  
            File excelFile = new File(filePath); // 创建文件对象  
            FileInputStream in = new FileInputStream(excelFile); // 文件流  
            checkExcelVaild(excelFile);  
            Workbook workbook = getWorkbok(in,excelFile);  
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的  
  
            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量  
            /** 
             * 设置当前excel中sheet的下标：0开始 
             */  
            Sheet sheet = null;   // 遍历第三个Sheet  
            
            //获取总行数
//          System.out.println(sheet.getLastRowNum());
            
            // 为跳过第一行目录设置count  
            Map<String, Object> data = new HashMap<>(2);
            List<ProdGoodsAddReq> goodsList = new ArrayList<>();
            List<GoodsExt> extList = new ArrayList<>();
            for (int i = 0; i < sheetCount; i++) {
            	
            	sheet = workbook.getSheetAt(i);
            	int count = 0;
            	for (Row row : sheet) {
            		try {
            			// 跳过第一和第二行的目录  
            			if(count < 3 ) {
            				count++;  
            				continue;  
            			}
            			
            			//如果当前行没有数据，跳出循环  
//                    if(row.getCell(0).toString().equals("")){  
//                    	return;
//                    }
            			
            			//获取总列数(空格的不计算)
            			int columnTotalNum = row.getPhysicalNumberOfCells();
            			System.out.println("总列数：" + columnTotalNum);
            			
            			System.out.println("最大列数：" + row.getLastCellNum());
            			
            			//for循环的，不扫描空格的列
//                    for (Cell cell : row) { 
//                    	System.out.println(cell);
//                    }
            			int end = row.getLastCellNum();
            			ProdGoodsAddReq dto = new ProdGoodsAddReq();
            			GoodsExt goodsExt = new GoodsExt();
            			for (int j = 0; j < end; j++) {
            				Cell cell = row.getCell(j);
            				if(cell == null) {
            					System.out.print("null" + "\t");
            					continue;
            				}
            				
            				setValue(cell, i, j, dto, goodsExt);
            			}
            			goodsList.add(dto);
            			extList.add(goodsExt);
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
            	}  
			}
            data.put("goodsList", goodsList);
            data.put("extList", extList);
            return data;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return null;
    }
    
    private static void setValue(Cell cell, Integer num, Integer snum, ProdGoodsAddReq dto, GoodsExt goodsExt) {
    	Object obj = null;
    	switch (cell.getCellTypeEnum()) {
	        case BOOLEAN:
	            obj = cell.getBooleanCellValue(); 
	            break;
	        case ERROR:
	            obj = cell.getErrorCellValue(); 
	            break;
	        case NUMERIC:
	            obj = cell.getNumericCellValue(); 
	            break;
	        case STRING:
	            obj = cell.getStringCellValue(); 
	            break;
	        default:
	            break;
    	}
    	System.out.print(obj + "\t");
    	if (obj == null) {
			return;
		}
    	
    	switch (snum) {
        case 1:
        	if (0 == snum) {
        		dto.setCatId("1070214264070545410");
        	}else if(1 == snum){
        		dto.setCatId("1070214527305043970");
        	}else if(2 == snum){
        		dto.setCatId("150922161500073797");
        	}else if(3 == snum){
        		dto.setCatId("1070214947486232578");
        	}
            break;
        case 2:
        	dto.setBrandId(obj.toString());
            break;
        case 3:
        	dto.setName(obj.toString());
            break;
        case 4:
            dto.setPrice(Double.valueOf(obj.toString()));
            dto.setMktprice(Double.valueOf(obj.toString()));
            break;
        case 5:
        	dto.setSellingPoint(obj.toString());
            break;
        case 6:
        	goodsExt.setParam1(obj.toString());
            break;
        case 7:
        	goodsExt.setParam2(obj.toString());
            break;
        case 8:
        	goodsExt.setParam3(obj.toString());
            break;
        case 9:
        	goodsExt.setParam4(obj.toString()); 
            break;
        case 10:
        	goodsExt.setParam5(obj.toString());
            break;
        case 11:
        	goodsExt.setParam6(obj.toString());
            break;
        case 12:
            dto.setRollVideo(fixPath(obj.toString(), num));; 
            break;
        case 13:
        	dto.setRollImageFile(fixPath(obj.toString(), num)); 
            break;
        case 14:
        	dto.setDetailImageFile(fixPath(obj.toString(), num)); 
            break;
        default:
            break;
	}
   }
    
    private static String fixPath(String url, Integer index){
    	if (null == url) {
			return null;
		}
    	String bUrl = "http://gz.iwhalecloud.com:6197/goods/";
    	switch (index) {
	        case 0:
	        	bUrl += "fznzd/"; 
	            break;
	        case 1:
	        	bUrl += "wlzd/"; 
	            break;
	        case 2:
	        	bUrl += "sj/";  
	            break;
	        case 3:
	        	bUrl += "hlcp/"; 
	            break;
	        default:
	            break;
    	}
    	if (url.startsWith("/")) {
    		url = (String)url.subSequence(1, url.length());
		}
    	String[] split = url.split(",");
    	StringBuilder sUrl = new StringBuilder(1000);
    	for (int i = 0; i < split.length; i++) {
    		String b = split[i];
    		if (i == (split.length - 1)) {
    			sUrl.append(bUrl).append(b);
			}else {
				sUrl.append(bUrl).append(b).append(",");
			}
		}
    	return sUrl.toString();
    }
    
    public static void main(String[] args) {
		String test = "";
		for (int i = 0; i < 100; i++) {
			test = test + "test.npg"+i;
		}
		System.out.println(test);
	}
}

