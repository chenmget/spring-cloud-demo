package com.iwhalecloud.retail.web.controller.b2b.fdfs.consts;

public class FileSubfixConstants {

    public enum videoSubfixEnum {

        AVI("avi"),
        RMVB("rmvb"),
        RM("rm"),
        ASF("asf"),
        DIVX("divx"),
        MPG("mpg"),
        MPEG("mpeg"),
        MPE("mpe"),
        WMV("wmv"),
        MP4("mp4"),
        MKV("mkv"),
        VOB("vob"),
        MOV("mov");

        private String subfix;
        videoSubfixEnum(String subfix){
            this.subfix = subfix;
        }

        public String getSubfix(){
            return subfix;
        }
    }

    public static boolean checkContainSubfix(String subfix){
        for (videoSubfixEnum subfixEnum : videoSubfixEnum.values()){
            if (subfix.equalsIgnoreCase(subfixEnum.getSubfix())){
                return true;
            }
        }
        return false;
    }
}
