package com.iwhalecloud.retail.web.controller.b2b.warehouse.utils;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;

import java.util.ArrayList;
import java.util.List;

public class ResourceInstColum {

    public static List<ExcelTitleName> supplierColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("statusCd", "在库状态"));
        orderMap.add(new ExcelTitleName("storageType", "入库类型"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("attrValue", "动态字段"));
        orderMap.add(new ExcelTitleName("sn", "营销资源编码"));
        orderMap.add(new ExcelTitleName("ctCode", "CT码"));
        orderMap.add(new ExcelTitleName("mktResInstType", "串码类型"));
        orderMap.add(new ExcelTitleName("sourceType", "串码来源"));
        orderMap.add(new ExcelTitleName("orderId", "订单编号"));
        orderMap.add(new ExcelTitleName("createTime", "下单时间"));
        orderMap.add(new ExcelTitleName("supplierName", "供应商名称"));
        orderMap.add(new ExcelTitleName("supplierCode", "供应商编码"));
        orderMap.add(new ExcelTitleName("merchantName", "店中商名称"));
        orderMap.add(new ExcelTitleName("merchantCode", "店中商编码"));
        orderMap.add(new ExcelTitleName("lanName", "店中商所属地市"));
        orderMap.add(new ExcelTitleName("regionName", "店中商所属区县"));
        orderMap.add(new ExcelTitleName("businessEntityName", "店中商所属经营主体名称"));
        orderMap.add(new ExcelTitleName("createDate", "入库时间"));
        return orderMap;
    }

    public static List<ExcelTitleName> retailerColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("statusCd", "CRM状态"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("attrValue", "动态字段"));
        orderMap.add(new ExcelTitleName("sn", "资源编码"));
        orderMap.add(new ExcelTitleName("lanName", "所属地市"));
        orderMap.add(new ExcelTitleName("createDate", "入库时间"));
        return orderMap;
    }

    public static List<ExcelTitleName> merchantColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("ctCode", "CT码"));
        orderMap.add(new ExcelTitleName("statusCd", "在库状态"));
        orderMap.add(new ExcelTitleName("storageType", "入库类型"));
        orderMap.add(new ExcelTitleName("mktResInstType", "串码类型"));
        orderMap.add(new ExcelTitleName("sourceType", "串码来源"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("attrValue", "动态字段"));
        orderMap.add(new ExcelTitleName("sn", "营销资源编码"));
        orderMap.add(new ExcelTitleName("createDate", "入库时间"));
        return orderMap;
    }

    public static List<ExcelTitleName> reqDetailColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("unitName", "产品名称"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("sn", "产品编码"));
        orderMap.add(new ExcelTitleName("ctCode", "CT码"));
        return orderMap;
    }

    public static List<ExcelTitleName> failNbrColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "失败的串码"));
        orderMap.add(new ExcelTitleName("result", "是否异常"));
        orderMap.add(new ExcelTitleName("resultDesc", "状态描述"));
        return orderMap;
    }

    public static List<ExcelTitleName> itmsColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("mktResInstNbr", "串码"));
        orderMap.add(new ExcelTitleName("typeName", "产品类型"));
        orderMap.add(new ExcelTitleName("brandName", "品牌"));
        orderMap.add(new ExcelTitleName("unitType", "产品型号"));
        orderMap.add(new ExcelTitleName("productName", "产品名称"));
        orderMap.add(new ExcelTitleName("operType", "操作类型"));
        orderMap.add(new ExcelTitleName("origlanName", "变更前的地市"));
        orderMap.add(new ExcelTitleName("lanName", "录入地市"));
        orderMap.add(new ExcelTitleName("maxSerialNum", "限额"));
        orderMap.add(new ExcelTitleName("createStaffName", "录入人"));
        orderMap.add(new ExcelTitleName("createDate", "录入时间"));
        orderMap.add(new ExcelTitleName("statusCd", "状态"));
        return orderMap;
    }
}
