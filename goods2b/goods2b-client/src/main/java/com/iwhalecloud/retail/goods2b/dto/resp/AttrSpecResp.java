package com.iwhalecloud.retail.goods2b.dto.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/12/26
 **/
@Data
public class AttrSpecResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性所属的分组
     */
    @ApiModelProperty(value = "属性所属的分组")
    private java.lang.String attrGroupId;

    /**
     * 属性所属的分组名称
     */
    @ApiModelProperty(value = "属性所属的分组名称")
    private java.lang.String attrGroupName;
    /**
     * 商品规格
     */
    @ApiModelProperty(value = "属性所属的分组名称")
    private List<GoodsSpec> goodsSpecList;

    @Data
    public static class GoodsSpec implements Serializable{
        /**
         * 属性名
         */
        @ApiModelProperty(value = "属性名")
        private String attrName;
        /**
         * 商品规格值
         */
        @ApiModelProperty(value = "商品规格值")
        private String instValue;
    }
}
