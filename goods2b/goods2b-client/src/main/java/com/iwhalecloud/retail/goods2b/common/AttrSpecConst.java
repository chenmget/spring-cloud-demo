package com.iwhalecloud.retail.goods2b.common;

import java.util.HashMap;
import java.util.Map;

public class AttrSpecConst {

    /**
     * 属性类型
     */
    public static final String ATTR_TYPE = "2";

    /**
     * 规格类型
     */
    public static final String SPEC_TYPE = "1";

    /**
     * 属性规格状态——有效
     */
    public static final String ATTR_SPEC_STATUSCD_USEFUL = "1000";

    /**
     * 属性规格状态——无效
     */
    public static final String ATTR_SPEC_STATUSCD_USELESS = "1001";


    /**
     * booCheck,booEdit,booNull
     * 允许
     */
    public static final Long BOO_VALIDATE_YES = 1L;

    /**
     * booCheck,booEdit,booNull
     * 不允许
     */
    public static final Long BOO_VALIDATE_NO = 0L;

    /**
     * 产品属性
     */
    public static String getAttrValue(String attrName){
        Map<String, String> map  = new HashMap<>();
        map.put("ATTR_VALUE1", "attrValue1");
        map.put("ATTR_VALUE2", "attrValue2");
        map.put("ATTR_VALUE3", "attrValue3");
        map.put("ATTR_VALUE4", "attrValue4");
        map.put("ATTR_VALUE5", "attrValue5");
        map.put("ATTR_VALUE6", "attrValue6");
        map.put("ATTR_VALUE7", "attrValue7");
        map.put("ATTR_VALUE8", "attrValue8");
        map.put("ATTR_VALUE9", "attrValue9");
        map.put("ATTR_VALUE10", "attrValue10");
        String attrValue = map.get(attrName);
        return attrValue;
    }
}
