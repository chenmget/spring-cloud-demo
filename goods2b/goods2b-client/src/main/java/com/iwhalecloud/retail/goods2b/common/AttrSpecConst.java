package com.iwhalecloud.retail.goods2b.common;

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
}
