package com.iwhalecloud.retail.web.controller.b2b.promo.utils;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhr 2019-03-29 15:01:30
 */
public class ReBateExcelColum {

    public static List<ExcelTitleName> reBateDetailColumn() {
        List<ExcelTitleName> reBateDetailColumn = new ArrayList<>();
        reBateDetailColumn.add(new ExcelTitleName("lanName", "地市"));
        reBateDetailColumn.add(new ExcelTitleName("productName", "产品名称"));
        reBateDetailColumn.add(new ExcelTitleName("unitType", "产品型号"));
        reBateDetailColumn.add(new ExcelTitleName("specName", "规格型号"));
        reBateDetailColumn.add(new ExcelTitleName("productSn", "机型编码"));
        reBateDetailColumn.add(new ExcelTitleName("unitType", "产品型号"));;
        reBateDetailColumn.add(new ExcelTitleName("rewardPrice", "返利单价"));
        reBateDetailColumn.add(new ExcelTitleName("amount", "入账金额"));;
        reBateDetailColumn.add(new ExcelTitleName("statusCd", "状态"));
        reBateDetailColumn.add(new ExcelTitleName("actName", "活动名称"));;
        reBateDetailColumn.add(new ExcelTitleName("effDate", "生效时间"));
        reBateDetailColumn.add(new ExcelTitleName("payoutDesc", "备注"));
        return reBateDetailColumn;
    }
}
