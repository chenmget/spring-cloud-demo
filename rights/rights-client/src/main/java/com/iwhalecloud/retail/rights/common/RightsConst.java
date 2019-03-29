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


    /**
     * 管理类型
     */
    public enum ManagerType {
        MANAGER_TYPE_1000("1000","内部"),
        MANAGER_TYPE_2000("2000","外部"),
        MANAGER_TYPE_3000("3000","电信出资"),
        MANAGER_TYPE_4000("4000","商家出资"),
        MANAGER_TYPE_5000("5000","共同出资");

        private String type;
        private String name;

        ManagerType(String type,String name) {
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
     * 管理类型
     */
    public enum ReleaseMode {
        RELEASE_MODE_1("1","手动领取"),
        RELEASE_MODE_2("2","主动推送");

        private String type;
        private String name;

        ReleaseMode(String type,String name) {
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
     * 领取总量或是数量的限制标识
     */
    public enum LimitFlg {
        LIMIT_FLG_0("0","不限制"),
        LIMIT_FLG_1("1","限制");

        private String type;
        private String name;

        LimitFlg(String type,String name) {
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
     * 领取总量或是数量的限制标识
     */
    public enum TaskStatusCd {
        TASK_STATUS_CD_0("0","待处理"),
        TASK_STATUS_CD_1("1","已处理"),
        TASK_STATUS_CD_1_("-1","处理异常");

        private String type;
        private String name;

        TaskStatusCd(String type,String name) {
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
