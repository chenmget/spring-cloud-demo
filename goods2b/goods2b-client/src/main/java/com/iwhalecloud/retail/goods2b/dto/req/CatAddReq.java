package com.iwhalecloud.retail.goods2b.dto.req;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author My
 * @Date 2018/12/21
 **/
@Data
public class CatAddReq extends AbstractRequest implements Serializable {

    @ApiModelProperty(value = "类别名称")
    @NotBlank
    private String catName;

    @ApiModelProperty(value = "上级类别ID")
    private String parentCatId;

    @ApiModelProperty(value = "类别路径")
    private String catPath;

    @ApiModelProperty(value = "排序")
    private Integer catOrder;

    @ApiModelProperty(value = "品牌Id List")
    private List<BrandReq> brandIds;

    @ApiModelProperty(value = "商品Id List")
    private List<GoodsReq> goodsIds;

    @ApiModelProperty(value = "类型ID")
    private String typeId;

    @ApiModelProperty(value = "类别图片")
    private String catImgPath;

    @ApiModelProperty(value = "创建人")
    private String createStaff;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @Data
    public static class BrandReq implements Serializable{
        public BrandReq(){

        }
        private Long targetOrder;
        private String brandId;
        private String brandName;
        private String brandImgPath;
    }
    @Data
    public static class GoodsReq implements Serializable{
        public GoodsReq(){

        }
        private Long targetOrder;
        private String goodsId;
        private String goodsName;
        private String goodsImgPath;
    }
}
