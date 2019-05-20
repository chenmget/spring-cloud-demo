package com.iwhalecloud.retail.web.controller.partner.utils;

import com.iwhalecloud.retail.web.controller.b2b.order.dto.ExcelTitleName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wang.jiaxin
 * @date: 2019年03月19日
 * @description:
 **/
public class MerchantColumn {

    /**
     * 商家表的要导出的基本信息  标题 和 对应的字段
     * @return
     */
    public static List<ExcelTitleName> merchantColumn() {
        List<ExcelTitleName> orderMap = new ArrayList<>();
        orderMap.add(new ExcelTitleName("lanName", "本地网"));
        orderMap.add(new ExcelTitleName("cityName", "市县"));
        orderMap.add(new ExcelTitleName("loginName", "系统账号"));
        orderMap.add(new ExcelTitleName("merchantId", "商家ID"));
        orderMap.add(new ExcelTitleName("merchantCode", "商家编码"));
        orderMap.add(new ExcelTitleName("merchantName", "商家名称"));
        orderMap.add(new ExcelTitleName("status", "渠道状态"));
        orderMap.add(new ExcelTitleName("merchantType", "商家类型"));
        orderMap.add(new ExcelTitleName("mktResInstType", "关联用户ID"));
        orderMap.add(new ExcelTitleName("userId", "串码来源"));
        orderMap.add(new ExcelTitleName("lastUpdateDate", "最后更新时间"));
        orderMap.add(new ExcelTitleName("businessEntityName", "商家所属经营主体名称"));
        orderMap.add(new ExcelTitleName("businessEntityCode", "商家所属经营主体编码"));
        orderMap.add(new ExcelTitleName("customerCode", "客户编码"));
        orderMap.add(new ExcelTitleName("lanId", "地市"));
        orderMap.add(new ExcelTitleName("city", "市县"));
        orderMap.add(new ExcelTitleName("subBureau", "分局/县部门"));
        orderMap.add(new ExcelTitleName("marketCenter", "营销中心/支局"));
        orderMap.add(new ExcelTitleName("shopCode", "销售点编码"));
        orderMap.add(new ExcelTitleName("shopName", "销售点名称"));
        orderMap.add(new ExcelTitleName("selfShopLevel", "自营厅级别"));
        orderMap.add(new ExcelTitleName("channelType", "渠道大类"));
        orderMap.add(new ExcelTitleName("channelMediType", "渠道小类"));
        orderMap.add(new ExcelTitleName("channelSubType", "渠道子类"));
        orderMap.add(new ExcelTitleName("shopAddress", "销售点地址"));
        orderMap.add(new ExcelTitleName("parCrmOrgId", "(商家)CRM组织ID"));
        orderMap.add(new ExcelTitleName("parCrmOrgCode", "(商家)CRM组织编码"));
        orderMap.add(new ExcelTitleName("linkman", "(商家)联系人"));
        orderMap.add(new ExcelTitleName("phoneNo", "(商家)联系电话"));
        orderMap.add(new ExcelTitleName("supplierName", "供应商名称"));
        orderMap.add(new ExcelTitleName("supplierCode", "供应商编码"));
        orderMap.add(new ExcelTitleName("shopAddress", "(商家)生效时间"));
        orderMap.add(new ExcelTitleName("effDate", "(商家)失效时间"));
        return orderMap;
    }

    /**
     * 零售商的 要导出的 额外字段  标题 和 对应的字段
     * @return
     */
    public static  List<ExcelTitleName> retailMerchantFields() {
        List<ExcelTitleName> orderMap = merchantColumn();
        orderMap.add(new ExcelTitleName("taxCode","纳税人识别号"));
        orderMap.add(new ExcelTitleName("busiLicenceCode","营业执照号"));
        orderMap.add(new ExcelTitleName("busiLicenceExpDate","营业执照到期日期"));
        orderMap.add(new ExcelTitleName("registerBankAcct","银行账号"));
        orderMap.add(new ExcelTitleName("tagNames","标签组名"));
        return orderMap;
    }

    /**
     * 供应商的 要导出的 额外字段  标题 和 对应的字段
     * @param orderMap
     * @return
     */
    public static  List<ExcelTitleName> complementSupplyMerchantFileds(List<ExcelTitleName> orderMap) {
        orderMap.add(new ExcelTitleName("taxCode","纳税人识别号"));
        orderMap.add(new ExcelTitleName("busiLicenceCode","营业执照号"));
        orderMap.add(new ExcelTitleName("busiLicenceExpDate","营业执照到期日期"));
        orderMap.add(new ExcelTitleName("registerBankAcct","银行账号"));
        orderMap.add(new ExcelTitleName("account","商家账号"));
        return orderMap;
    }


}
