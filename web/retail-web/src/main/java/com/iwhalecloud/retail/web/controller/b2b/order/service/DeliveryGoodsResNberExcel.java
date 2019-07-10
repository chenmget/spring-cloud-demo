package com.iwhalecloud.retail.web.controller.b2b.order.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.tobato.fastdfs.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order2b.consts.OmsCommonConsts;
import com.iwhalecloud.retail.web.controller.b2b.fdfs.FileManagerService;
import com.iwhalecloud.retail.web.controller.b2b.fdfs.dto.FileManagerDTO;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.FileManagerRespDTO;
import com.iwhalecloud.retail.web.office.base.ReadExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryGoodsResNberExcel extends ReadExcel<String> {

    private static final String EXCEL_2007 = ".xls";

    @Autowired
    public FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FileManagerService fileManagerService;


    @Override
    public String builderObject2007(HSSFRow hssfRow) {
        HSSFCell no = hssfRow.getCell(0);
        return getValue(no);
    }

    @Override
    public String builderObject2010(XSSFRow hssfRow) {
        XSSFCell no = hssfRow.getCell(0);
        return getValue(no);
    }

    /**
     * 解析Excel
     */
    public List<String> readExcel(FileManagerRespDTO fileUrl) throws IOException {
        InputStream inputStream = fastFileStorageClient.downloadFile(fileUrl.getGroup(),
                fileUrl.getPath(), new DownloadCallback<InputStream>() {
                    @Override
                    public InputStream recv(InputStream inputStream) throws IOException {
                        return inputStream;
                    }
                });
        List<String> list = new ArrayList<>();
        list.addAll(readXlsx2010(inputStream,0));
        return list;

    }

    /**
     * data 转化为Excel
     */
    public void builderOrderExcel(Workbook book, List list,List<ExcelTitleName> map,String title) {
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(list));
        jsonArray.add(0, new Object()); //标题占位
        Sheet sheet1 = book.createSheet(title);
        Row titleRow = sheet1.createRow(0);
        for (int i = 0; i < map.size(); i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(map.get(i).getName());
        }

        for (int rowi = 0; rowi < jsonArray.size(); rowi++) {
            Row row = sheet1.createRow(rowi);
            for (int contentj = 0; contentj < map.size(); contentj++) {
                Cell cell = row.createCell(contentj);
                if (rowi == 0) {
                    //设置标题
                    cell.setCellValue(map.get(contentj).getName());
                } else {
                    //设置内容
                    Object value = jsonArray.getJSONObject(rowi).get(map.get(contentj).getValue());
                    if (value != null) {
                        cell.setCellValue(String.valueOf(value));
                    } else {
                        cell.setCellValue("");
                    }
                }
            }
        }

    }

    public ResultVO uploadExcel(Workbook book) {
        ResultVO resultVO = new ResultVO();
        InputStream inputStream = workbookConvertorStream(book);
        FileManagerDTO fileManagerDTO = new FileManagerDTO();
        fileManagerDTO.setFileName(System.currentTimeMillis() + EXCEL_2007);

        try {
            fileManagerDTO.setFileSize((long) inputStream.available());
            fileManagerDTO.setImage(inputStream);
            ResultVO  resp = fileManagerService.uploadImage(fileManagerDTO);
            resultVO.setResultCode(resp.getResultCode());
            resultVO.setResultMsg(resp.getResultMsg());
            resultVO.setResultData(resp.getResultData());
        } catch (Exception e) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg("生成Excel文件失败");
            e.printStackTrace();
        }
        return resultVO;
    }

    public void exportExcel(String fileName, Workbook workbook, HttpServletResponse response){
        try {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName+".xls", "UTF-8"));

            workbook.write(response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            ResultVO resultVO=new ResultVO();
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg("导出异常");
            outputResponse(response,resultVO);
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void outputResponse(HttpServletResponse response,ResultVO resultVO) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(resultVO));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }


}
