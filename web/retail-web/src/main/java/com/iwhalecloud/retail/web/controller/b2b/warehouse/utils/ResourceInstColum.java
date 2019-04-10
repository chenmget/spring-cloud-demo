package com.iwhalecloud.retail.web.controller.b2b.warehouse.utils;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;

import java.util.ArrayList;
import java.util.List;

public class ResourceInstColum {

    public static List<ExcelTitleName> supplierColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("storageType", "入库类型"));
        orderMap.add(new ExcelTitleName("statusCd", "在库状态"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitName", "营销资源名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("sn", "营销资源编码"));
        orderMap.add(new ExcelTitleName("mktResInstType", "串码类型"));
        orderMap.add(new ExcelTitleName("sourceType", "串码来源"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("specName", "规格"));
        orderMap.add(new ExcelTitleName("ctCode", "CT码"));
        orderMap.add(new ExcelTitleName("orderId", "订单编号"));
        orderMap.add(new ExcelTitleName("createTime", "下单时间"));
        orderMap.add(new ExcelTitleName("supplierName", "供应商名称"));
        orderMap.add(new ExcelTitleName("supplierCode", "供应商编码"));
        orderMap.add(new ExcelTitleName("merchantName", "商家名称"));
        orderMap.add(new ExcelTitleName("merchantCode", "商家编码"));
        orderMap.add(new ExcelTitleName("lanName", "商家所属地市"));
        orderMap.add(new ExcelTitleName("regionName", "商家所属区县"));
        orderMap.add(new ExcelTitleName("businessEntityName", "商家所属经营主体名称"));
        orderMap.add(new ExcelTitleName("createDate", "入库时间"));
        return orderMap;
    }

    public static List<ExcelTitleName> retailerColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("statusCd", "CRM状态"));
        orderMap.add(new ExcelTitleName("typeName", "产品分类"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("unitName", "营销资源名称"));
        orderMap.add(new ExcelTitleName("sn", "产品编码"));
        orderMap.add(new ExcelTitleName("lanName", "所属地市"));
        orderMap.add(new ExcelTitleName("createDate", "入库时间"));
        return orderMap;
    }

    public static List<ExcelTitleName> merchantColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("ctCode", "CT码"));
        orderMap.add(new ExcelTitleName("storageType", "入库类型"));
        orderMap.add(new ExcelTitleName("statusCd", "在库状态"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitName", "营销资源名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("sn", "营销资源编码"));
        orderMap.add(new ExcelTitleName("mktResInstType", "串码类型"));
        orderMap.add(new ExcelTitleName("sourceType", "串码来源"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("specName", "规格"));
        orderMap.add(new ExcelTitleName("createDate", "入库时间"));
        return orderMap;
    }
}
