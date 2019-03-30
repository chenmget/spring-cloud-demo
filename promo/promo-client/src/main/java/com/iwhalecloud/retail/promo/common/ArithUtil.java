package com.iwhalecloud.retail.promo.common;


import java.math.BigDecimal;

/**
 * 描述 : 提供精确的小数位四舍五入处理（针对后台返回的钱以厘为计算单位，但前台展现以元为单位）<br>
 */

public class ArithUtil {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    //这个类不能实例化
    private ArithUtil() {
        ;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字(String形式的，因为后台返回的是String类型)
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (v == null || v.trim().equals("")){
            return "";
        }

        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1000");//后台钱是以厘为单位的，但前台以元为单位
        double result = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        String money = String.valueOf(result);
        if (money.equals("0.0")) {
            return "0";
        }
        return money;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字(String形式的，因为后台返回的是String类型)
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String roundOne(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (v == null || v.trim().equals("")){
            return "";
        }

        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        double result = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        String money = String.valueOf(result);
        if (money.equals("0.0")) {
            return "0";
        }
        return money;
    }

    /**
     * 提供精确的小数位四舍五入处理,希望以后用这个,通用
     *
     * @param v        需要四舍五入的数字(String形式的，因为后台返回的是String类型)
     * @param scale    小数点后保留几位
     * @param devideBy 除数
     * @return 四舍五入后的结果
     */
    public static String roundByshu(String v, int scale, int devideBy) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (v == null || v.trim().equals("")){
            return "";
        }

        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal(devideBy);
        double result = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        String money = String.valueOf(result);
        if (money.equals("0.0")) {
            return "0";
        }
        return money;
    }

    /**
     * 提供精确的小数位四舍五入处理,希望以后用这个,通用
     *
     * @param num       需要四舍五入的数字(String形式的，因为后台返回的是String类型)
     * @param scale    小数点后保留几位
     * @param devideBy 除数
     * @return 四舍五入后的结果
     */
    public static String roundLongByshu(long num, int scale, String devideBy) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        String v = String.valueOf(num);
        if (v == null || v.trim().equals(""))
            return "";
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal(devideBy);
        double result = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        String money = String.valueOf(result);
        if (money.equals("0.0")) {
            return "0";
        }
        return money;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param num     需要四舍五入的数字(long形式的)
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String roundLong(long num, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        String v = String.valueOf(num);
        if (v == null || v.trim().equals(""))
            return "";
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1000");//后台钱是以厘为单位的，但前台以元为单位
        double result = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        String money = String.valueOf(result);
        if (money.equals("0.0")) {
            return "0";
        }
        return money;
    }

    public static String roundInt(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        if (v == null || v.trim().equals("")){
            return null;
        }

        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("60");
        double result = b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        String money = String.valueOf(result);
        String zheng = "";
        String xiaoshu = "";
        int dot = money.indexOf(".");
        if (dot != -1) {
            int shu = 0;
            zheng = money.substring(0, dot);
            xiaoshu = money.substring(dot + 1);
            if (xiaoshu != null && !xiaoshu.equals("")) {
                shu = Integer.parseInt(xiaoshu);
            }
            if (shu > 0) {
                shu = Integer.parseInt(zheng) + 1;
            } else{
                shu = Integer.parseInt(zheng);
            }

            zheng = String.valueOf(shu);
        } else {
            zheng = money;
        }
        return zheng;
    }

    /**
     * 提供把元转换为厘。
     *
     * @param v 需要转换的数字(String形式的，因为后台返回的是String类型)
     * @return 结果
     */
    public static String yuanToLi(String v) {
        if (v == null || v.trim().equals("")) {
            return "0";
        }

        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1000");//后台钱是以厘为单位的，但前台以元为单位
        long result = b.multiply(one).longValue();
        String money = String.valueOf(result);
        return money;
    }

    /**
     * 提供把厘转换为元。
     *
     * @param v 需要转换的数字(String形式的，因为后台返回的是String类型)
     * @return 结果
     */
    public static String liToYuan(String v) {
        if (v == null || v.trim().equals("")){
            return "0";
        }

        double result = Double.parseDouble(v) / 1000;
        String money;
        if (result - (int) result > 0) {
            money = String.valueOf(result);
        } else {
            money = String.valueOf((int) result);
        }
        return money;
    }

    public static String fenToYuan(String v) {
        if (v == null || v.trim().equals("")){
            return "0";
        }

        double result = Double.parseDouble(v) / 100;
        String money;
        if (result - (int) result > 0) {
            money = String.valueOf(result);
        } else {
            money = String.valueOf((int) result);
        }
        return money;
    }

    public static void main(String[] args) {

        System.out.println(fenToYuan("501"));
    }
}
