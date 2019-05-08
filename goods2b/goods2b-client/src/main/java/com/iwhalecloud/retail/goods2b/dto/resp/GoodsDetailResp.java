package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.TagsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsTargetReq;
import com.iwhalecloud.retail.goods2b.dto.req.RegionReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2018/12/25
 **/
@Data
public class GoodsDetailResp implements Serializable {

    private static final long serialVersionUID = -7840612815570117594L;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码")
    private String sn;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private String goodsCatId;

    @ApiModelProperty(value = "类别名称")
    private String catName;

    /**
     * 上下架状态
     */
    @ApiModelProperty(value = "上下架状态")
    private Integer marketEnable;

    /**
     * 用户是否收藏
     */
    @ApiModelProperty(value = "用户是否收藏")
    private Boolean goodsIsCollection;

    /**
     * 供应商是否收藏
     */
    @ApiModelProperty(value = "供应商是否收藏")
    private Boolean SuppliIsCollection;

    /**
     * 查看次数
     */
    @ApiModelProperty(value = "查看次数")
    private Long viewCount;

    /**
     * 购买次数
     */
    @ApiModelProperty(value = "购买次数")
    private Long buyCount;
    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktprice;
    /**
     * 供货商ID
     */
    @ApiModelProperty(value = "供货商ID")
    private String supplierId;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private java.lang.String supplierName;

    /**
     * 供应商类型
     */
    @ApiModelProperty(value = "供应商类型")
    private java.lang.String merchantType;

    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String auditState;

    /**
     * 搜索关键字
     */
    @ApiModelProperty(value = "搜索关键字")
    private String searchKey;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;
    /**
     * regionName
     */
    @ApiModelProperty(value = "regionNames")
    private List<String> regionNames;

    /**
     * 商品型号视频
     */
    @ApiModelProperty(value = "商品型号视频")
    private String modelVideo;

    /**
     * 商品缩略图
     */
    @ApiModelProperty(value = "商品缩略图")
    private String thumbnailsFile;
    /**
     * 营销活动列表
     */
    private List<GoodActRelResp> goodActRelResps;
    /**
     * 产品列表
     */
    private List<ProductResp> productResps;

    /**
     * 商品规格列表
     */
    private List<GoodsAttrResp> attrRespList;

    /**
     * 商品属性列表
     */
    private List<GoodsParamResp> paramRespList;

    /**
     * 商品规格列表
     */
    private List<AttrSpecResp> attrSpecResps;
    /**
     * 产品规格列表
     */
    private List<AttrSpecResp.GoodsSpec> goodsSpecList;

    /**
     * 发布地市
     */
    @NotEmpty
    @ApiModelProperty(value = "发布地市")
    private List<RegionReq> regionList;

    /**
     * 生效日期
     */
    @NotNull
    @ApiModelProperty(value = "生效日期")
    private java.util.Date effDate;

    /**
     * 失效日期
     */
    @NotNull
    @ApiModelProperty(value = "失效日期")
    private java.util.Date expDate;

    /**
     * 零售商标签
     */
    @NotEmpty
    @ApiModelProperty(value = "零售商标签")
    private List<TagsDTO> tagList;

    /**
     * 品牌名称
     */
    @NotBlank
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    /**
     * 产品列表
     */
    @ApiModelProperty(value = "产品列表")
    private List<GoodsProductRelDTO> goodsProductRelList;

    /**
     * 类型Id
     */
    @ApiModelProperty(value = "类型Id")
    private String typeId;

    /**
     * 渠道类型
     */
    @ApiModelProperty(value = "渠道类型")
    private String channelType;


    /**
     * 是否商家确认
     */
    @ApiModelProperty(value = "是否商家确认")
    private Integer isMerchantConfirm;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private List<ProdFileDTO> prodFiles;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private String payments;

    /**
     * 是否分货
     */
    @ApiModelProperty(value = "是否分货")
    private Integer isAllot;

    /**
     * 来源平台
     */
    @ApiModelProperty(value = "来源平台")
    private String sourceFrom;

    /**
     * 是否预售商品
     */
    @ApiModelProperty(value = "是否预售商品,商品是否为预售商品，预售商品可以无库存发布\n" +
            "1.是 0.否")
    private Integer isAdvanceSale;

    /**
     * 是否预售商品
     */
    @ApiModelProperty(value = "是否前置补贴商品 1.是 0.否")
    private Integer isSubsidy;

    /**
     * 商品归属商家所在地市
     */
    @NotBlank
    @ApiModelProperty(value = "商品归属商家所在地市")
    private String cityName;

    /**
     * 发布对象
     */
    @ApiModelProperty(value = "发布对象")
    private List<GoodsTargetReq> targetList;

    @ApiModelProperty(value = "商品发布对象")
    private String targetType;
}
