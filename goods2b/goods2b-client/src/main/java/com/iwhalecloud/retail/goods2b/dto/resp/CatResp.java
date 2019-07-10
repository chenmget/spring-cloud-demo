package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/12/24
 **/
@Data
public class CatResp implements Serializable {


    @ApiModelProperty(value = "类别名称")
    private String catName;

    @ApiModelProperty(value = "上级类别ID")
    private String parentCatId;

    @ApiModelProperty(value = "类别路径")
    private String catPath;

    @ApiModelProperty(value = "排序")
    private Integer catOrder;

    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "类别图片")
    private String catImgPath;

    @ApiModelProperty(value = "品牌列表")
    private List<BrandResp> brandRespList;

    @ApiModelProperty(value = "商品列表")
    private List<GoodsResp> goodsRespList;

    @ApiModelProperty(value = "分类条件关联列表")
    private List<CatConditionDTO> catConditionList;


    @Data
    public class BrandResp implements Serializable{
        public BrandResp(){

        }
        /**
         * 品牌ID
         */
        private String brandId;
        /**
         * 品牌图片位置
         */
        private String imgPath;
        /**
         * 品牌名称
         */
        private String brandName;
    }
     @Data
     public class GoodsResp implements Serializable{
        /**
         * 商品ID
         */
        private String goodsId;
        /**
         * 商品图片位置
         */
        private String imgPath;
        /**
         * 商品名称
         */
        private String goodsName;
    }
}
