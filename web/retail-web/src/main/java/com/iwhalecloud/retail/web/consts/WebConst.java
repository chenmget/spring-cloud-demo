package com.iwhalecloud.retail.web.consts;

/**
 * @author zwl
 * @date 2018/12/27
 */
public class WebConst {

    // sys_config_info表中 AES 加密密钥的配置ID
    public static final String AES_KEY_CONFIG_ID = "AES_KEY";

    /**
     * 会员SESSION字符串定义
     */
    public static final String SESSION_MEMBER = "MEMBER";

    /**
     * 系统用户SESSION字符串定义
     */
    public static final String SESSION_USER = "USER";

    /**
     * 国际化语言SESSION字符串定义
     */
    public static final String SESSION_LANGUAGE = "LANGUAGE";

    /**
     * 系统用户其他信息SESSION字符串定义
     */
    public static final String SESSION_USER_OTHER_MSG = "USER_OTHER_MSG";

    /**
     * 系统用户的店铺SESSION字符串定义
     */
//    public static final String SESSION_SHOP = "SHOP";

    /**
     * 微信openId session字符串定义
     */
    public static final String SESSION_WX_OPEN_ID = "WX_OPEN_ID";

    /**
     * 微信昵称 session字符串定义
     */
    public static final String SESSION_WX_NICK_NAME = "WX_NICK_NAME";

    /**
     * 登录成功后的TOKEN 符串定义
     */
    public static final String SESSION_TOKEN = "TOKEN";

    /**
     * web接口来源
     */
    public static final String SOURCE_FROM = "YHJ";

    /**
     * 国际化配置文件里当前语言
     */
    public static final String LOCALE_CODE = "Locale";

    /**
     * 登录类型枚举
     */
    public enum LoginTypeEnum{
        /**
         * 密码登陆
         */
        PASSWORD("1","密码登陆"),
        /**
         * 验证码登陆
         */
        VERIFICATION_CODE("2","校验验证码");
        private String value;
        private String code;
        LoginTypeEnum(String code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 登录状态枚举
     */
    public enum loginStatusEnum{
        /**
         * 未登陆
         */
        NOT_LOGIN("0","未登录"),
        /**
         * 已经登陆
         */
        HAVE_LOGIN("1","已登陆"),
        /**
         * 已经登陆
         */
        LOGIN_WRONG_PLATFORM("-1","登陆平台错误");
        private String value;
        private String code;
        loginStatusEnum(String code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * 语言类型枚举
     */
    public enum LanguageEnum{
        CHINESE("CHINESE","中文"),
        ENGLISH("ENGLISH","英文");
        private String value;
        private String code;
        LanguageEnum(String code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }
}
