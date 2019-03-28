package com.iwhalecloud.retail.goods2b.common;

/**
 * @author Z
 * @date 2018/11/27
 */
public class FileConst {

    /**
     * 附件的类型
     */
    public enum FileType {

        IMAGE_TYPE("1","图片"),
        VIDIO_TYPE("2","视频"),
        ZIP_TYPE("3","压缩包"),
        OTHER_TYPE("9","其它文件");

        private String type;
        private String name;

        FileType(String type,String name) {
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

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * 附件的目标类型
     */
    public enum TargetType {
        GOODS_TARGET("1","商品"),
        ORDER_TARGET("2","订单"),
        PRODUCT_TARGET("3","产品"),
        BRAND_TARGET("4","品牌"),
        TYPE_TARGET("5","分类");

        private String type;
        private String name;

        TargetType(String type,String name) {
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

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * 附件的子类型
     */
    public enum SubType {
        DEFAULT_SUB("1","默认图片"),
        ROLL_SUB("2","轮播图片"),
        DETAIL_SUB("3","详情图片"),
        ROLL_VIDIO_SUB("4","轮播图视频"),
        CAT_SUB("5","分类图片"),
        THUMBNAILS_SUB("6","缩略图"),
        MODEL_VIDIO_SUB("7","型号视频");

        private String type;
        private String name;

        SubType(String type,String name) {
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

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * 附件的子类型
     */
    public enum ProductSubType {
        DEFAULT_SUB("1","默认图片"),
        ROLL_SUB("2","轮播图片"),
        DETAIL_SUB("3","详情图片"),
        VIDEO_SUB("4","视频"),
        NAIL_SUB("6","缩略图片（规格缩略图"),
        MODEL_VIDIO_SUB("7","型号视频");

        private String type;
        private String name;

        ProductSubType(String type,String name) {
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

        public void setType(String type) {
            this.type = type;
        }
    }
}
