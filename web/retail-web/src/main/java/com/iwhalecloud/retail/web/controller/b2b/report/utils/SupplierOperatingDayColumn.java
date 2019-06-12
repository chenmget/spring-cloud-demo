package com.iwhalecloud.retail.web.controller.b2b.report.utils;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/11
 */
public class SupplierOperatingDayColumn {

    public static List<ExcelTitleName> showColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
//        orderMap.add(new ExcelTitleName("itemDate", "指标日期"));
        orderMap.add(new ExcelTitleName("supplierCode", "供应商编码"));
        orderMap.add(new ExcelTitleName("supplierName", "供应商名称"));
//        orderMap.add(new ExcelTitleName("cityName", "地市"));
//        orderMap.add(new ExcelTitleName("countyName", "区县"));
//        orderMap.add(new ExcelTitleName("productBaseId", "型号ID"));
//        orderMap.add(new ExcelTitleName("productBaseName", "型号名称"));
//        orderMap.add(new ExcelTitleName("productId", "产品ID"));
//        orderMap.add(new ExcelTitleName("productName", "产品名称"));
//        orderMap.add(new ExcelTitleName("brandId", "品牌ID"));
//        orderMap.add(new ExcelTitleName("brandName", "品牌名称"));
//        orderMap.add(new ExcelTitleName("priceLevel", "档位"));
        orderMap.add(new ExcelTitleName("sellNum", "总销量"));
        orderMap.add(new ExcelTitleName("sellAmount", "销售额"));
        orderMap.add(new ExcelTitleName("purchaseAmount", "进货金额"));
        orderMap.add(new ExcelTitleName("purchaseNum", "交易进货量"));
        orderMap.add(new ExcelTitleName("manualNum", "手工录入量"));
        orderMap.add(new ExcelTitleName("transInNum", "调入量"));
        orderMap.add(new ExcelTitleName("transOutNum", "调出量"));
        orderMap.add(new ExcelTitleName("returnNum", "退库量"));
        orderMap.add(new ExcelTitleName("stockNum", "库存总量"));
        orderMap.add(new ExcelTitleName("stockAmount", "库存金额"));
        orderMap.add(new ExcelTitleName("totalInNum", "入库总量"));
        orderMap.add(new ExcelTitleName("totalOutNum", "出库总量"));
        orderMap.add(new ExcelTitleName("averageDailySales", "近7天日均销量"));
        return orderMap;
    }
}
