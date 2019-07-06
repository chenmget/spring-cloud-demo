package com.iwhalecloud.retail.system.common;

/**
 * @author wzy
 * @date 2019/7/1
 */
public class SysUserLoginConst {

     public static final int NEED_RESETPASSWDCODE =1001;
/*     public static final int USER_NOTEXIT_TRADING = 1002;
     public static final int USER_OTEXIT_MANAGEMENT = 2002;
     public static final int USER_PASSWORD_ERRO_TRADING = 1003;
     public static final int USER_PASSWORD_ERRO_MANAGEMENT = 2003;
     public static final int USER_STATUE_DISABLED_TRADING = 1004;
    public static final int USER_STATUE_DISABLED_MANAGEMENT = 2004;*/

     public enum platformFlag{
        TRADING_PLATFORM("0","交易平台"),
        MANAGEMENT_PLATFORM("1","管理平台");
        private String plaformCode;
        private String plaformName;

        platformFlag(String plaformCode, String plaformName) {
            this.plaformCode = plaformCode;
            this.plaformName = plaformName;
        }

        public String getPlaformCode() {
            return plaformCode;
        }

        public void setPlaformCode(String plaformCode) {
            this.plaformCode = plaformCode;
        }

        public String getPlaformName() {
            return plaformName;
        }

        public void setPlaformName(String plaformName) {
            this.plaformName = plaformName;
        }
    }


    public enum loginFail_TRADING{
        NEED_RESETPASSWD(NEED_RESETPASSWDCODE,"超过九十天未登入");
         private int code;
         private String msg;
        loginFail_TRADING(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }


}
