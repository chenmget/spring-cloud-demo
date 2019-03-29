package com.iwhalecloud.retail.promo.common;

/**
 * @author 吴良勇
 * @date 2019/3/25 16:15
 */
public final class RebateConst {

    public static final String ZERO = "0";


    public static final String EXP_DATE_DEF="2037-12-31 00:00:00";

    public static final String BALANCE_TYPE_NAME_END = "返利";

    public static final String SPLIT_V = "_";

    public static enum Const{

        ACCOUNT_BALANCE_TYPE_MERCHANT("10","商家","余额对象类型"),
        ACCOUNT_BALANCE_TYPE_PRODUCT("20","产品","余额对象类型"),

        CYCLE_TYPE_ALL("1","总额封顶","限额类型"),
        CYCLE_TYPE_OBJ("2","余额对象封顶","限额类型"),
        CYCLE_TYPE_ACCOUNT("3","帐户封顶","限额类型"),
        CYCLE_TYPE_SERVICE("4","服务封顶顶","限额类型"),
        CYCLE_TYPE_PER("5","消费比例封顶","限额类型"),


        ACCOUNT_BALANCE_DETAIL_OPER_TYPE_ADD("1","存（收入）","账户余额来源明细-操作类型"),
        ACCOUNT_BALANCE_DETAIL_OPER_TYPE_TURN("2","转（收入）","账户余额来源明细-操作类型"),
        ACCOUNT_BALANCE_DETAIL_OPER_TYPE_REPAIR("3","补（收入）","账户余额来源明细-操作类型"),
        ACCOUNT_BALANCE_DETAIL_OPER_TYPE_CORRECT("4","冲正","账户余额来源明细-操作类型"),
        ACCOUNT_BALANCE_DETAIL_OPER_TYPE_ACCT_TRANSFER("5","调帐","账户余额来源明细-操作类型"),

        ACCOUNT_BALANCE_DETAIL_ACCT_TYPE_REBATE("20","返利账户","账户类型"),


        ACCOUNT_BALANCE_DETAIL_BALANCE_SOURCE_TYPE_ID("3001","来源类型标识","来源类型标识"),


        STATUS_USE("1000","有效","状态标识"),
        STATUS_UN_USE("1100","无效","状态标识"),


        RULE_TYPE_REBATE("10","返利使用规则","规则类型");

        private String value;
        private String name;
        private String desc;
        Const(String value,String name,String desc){
            this.value=value;
            this.name=name;
            this.desc=name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
