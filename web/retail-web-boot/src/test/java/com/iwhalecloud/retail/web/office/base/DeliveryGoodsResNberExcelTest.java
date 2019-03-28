package com.iwhalecloud.retail.web.office.base;

import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.RetailWebApplication;
import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.dto.response.FileManagerRespDTO;
import com.iwhalecloud.retail.oms.dto.resquest.FileManagerDTO;
import com.iwhalecloud.retail.oms.service.FileManagerService;
import com.iwhalecloud.retail.order2b.dto.model.order.OrderDTO;
import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;
import com.iwhalecloud.retail.web.controller.b2b.order.service.DeliveryGoodsResNberExcel;
import com.iwhalecloud.retail.web.controller.b2b.order.service.OrderExportUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailWebApplication.class)
public class DeliveryGoodsResNberExcelTest {

    @Autowired
    private FileManagerService fileManagerService;

    @Test
    public  void uuloadTest(){
        File file=new File("C:\\workSpace\\NewSales\\tst\\test.xlsx");
        FileManagerDTO fileManagerDTO=new FileManagerDTO();
        fileManagerDTO.setFileName(file.getName());
        fileManagerDTO.setFileSize(file.length());
        try {
            fileManagerDTO.setImage(new FileInputStream(file));
            CommonResultResp resp= fileManagerService.uploadImage(fileManagerDTO);
            System.out.println(JSON.toJSONString(resp));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Autowired
    private DeliveryGoodsResNberExcel deliveryGoodsResNberExcel;
    @Test
    public void readExcel() throws IOException {
        FileManagerRespDTO fileManagerRespDTO=new FileManagerRespDTO();
        fileManagerRespDTO.setPath("M00/00/12/Ci0vWlw0Rm-ASqBfAAAjqOapwUc58.xlsx");
        fileManagerRespDTO.setGroup("group1");
        deliveryGoodsResNberExcel.readExcel(fileManagerRespDTO);
    }


    @Test
    public   void builderExcel() {
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

        InputStream inputStream=workbookConvertorStream(book);

        FileManagerDTO fileManagerDTO=new FileManagerDTO();
        fileManagerDTO.setFileName(System.currentTimeMillis()+".xls");

        try {
            fileManagerDTO.setFileSize((long) inputStream.available());
            fileManagerDTO.setImage(inputStream);
            CommonResultResp resp= fileManagerService.uploadImage(fileManagerDTO);
            System.out.println(JSON.toJSONString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream workbookConvertorStream(Workbook workbook) {
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

    @Test
    public void builderOrder1(){
        Workbook workbook=new HSSFWorkbook();
        List<OrderDTO> orderDTOS=new ArrayList<>();
        for (int i=0;i<10;i++){
            OrderDTO dto=new OrderDTO();
            dto.setOrderId(System.currentTimeMillis()+"");
            orderDTOS.add(dto);
        }
        deliveryGoodsResNberExcel.builderOrderExcel(workbook,orderDTOS,OrderExportUtil.getOrder(),"订单");

        try {
            workbook.write(new FileOutputStream("C:\\workSpace\\NewSales\\tst\\" + (System.currentTimeMillis()) + ".xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}