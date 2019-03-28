package com.iwhalecloud.retail.rights.common;

/**
 * @author zwl
 * @date 2019/02/22
 */
public class RightsConst {

    /**
     * 通用 是否 枚举
     */
    public enum CommonYesOrNo {

        YES("1","是"),
        NO("0","否");

        private String code;
        private String name;

        CommonYesOrNo(String code,String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    /**
     * 优惠券类型
     */
    public enum MktResCouponType {

        PLATFORM("1","平台优惠券"),
        MERCHANT("2","商家优惠券"),
        PRODUCT("3","产品优惠券");

        private String type;
        private String name;

        MktResCouponType(String type,String name) {
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setCode(String type) {
            this.type = type;
        }
    }

    /**
     * 优惠券适用对象类型
     */
    public enum CouponApplyObjType {

        SALES_PRODUCT("1000","销售品"),
        GOODS("2000","商品"),
        INTEGRAL("3000","积分"),
        PRODUCT("4000","产品"),
        MERCHANT("5000","商家");

        private String type;
        private String name;

        CouponApplyObjType(String type,String name) {
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setCode(String type) {
            this.type = type;
        }
    }

    /**
     * 优惠类型
     */
    public enum CouponDiscountType {

        FIXED_LIMIT("1000","固定额度"), // 在原价的基础上减免固定的优惠额度
        FIXED_DISCOUNT("2000","固定折扣"), // 按原价的若干成计算
        RANDOM_CUT("3000","随机减"); // 在原价的基础上减免一定的优惠额度,额度为随机计算的

        private String type;
        private String name;

        CouponDiscountType(String type,String name) {
            this.type = type;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setCode(String type) {
            this.type = type;
        }
    }
}
