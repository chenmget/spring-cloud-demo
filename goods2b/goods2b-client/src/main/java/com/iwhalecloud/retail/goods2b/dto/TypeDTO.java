package com.iwhalecloud.retail.goods2b.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * ProdType
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel(value = "对应模型prod_type, 对应实体ProdType类")
public class TypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID")
    private java.lang.String typeId;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称")
    private java.lang.String typeName;

    /**
     * 上级类型ID
     */
    @ApiModelProperty(value = "上级类型ID")
    private java.lang.String parentTypeId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private java.lang.Long typeOrder;

    /**
     * is_deleted
     */
    @ApiModelProperty(value = "is_deleted")
    private java.lang.String isDeleted;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createStaff;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private java.lang.String updateStaff;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;

    /**
     * sourceFrom
     */
    @ApiModelProperty(value = "sourceFrom")
    private java.lang.String sourceFrom;

    /**
     * CRM资源大类
     */
    @ApiModelProperty(value = "CRM资源大类")
    private java.lang.String crmResKind;

    /**
     * CRM资源小类
     */
    @ApiModelProperty(value = "CRM资源小类")
    private java.lang.String crmResType;

    /**
     * attrDTOS
     */
    @ApiModelProperty(value = "属性")
    private List<AttrSpecDTO> attrDTOS;

    /**
     * specDTOS
     */
    @ApiModelProperty(value = "规格")
    private List<AttrSpecDTO> specDTOS;

    @ApiModelProperty(value = "品牌Id List")
    private List<BrandReq> brandIds;

    @ApiModelProperty(value = "品牌")
    private List<BrandResp> brandResps;

    @Data
    public static class BrandReq implements Serializable{
        public BrandReq(){

        }
        private String brandId;
        private Long complexOrder;
        private String brandName;
    }

    @Data
    public static class BrandResp implements Serializable{
        public BrandResp(){

        }
        private String brandId;
        private Long complexOrder;
        private String brandName;
        /**
         * 品牌图片位置
         */
        private String brandImgPath;

    }

}
