package com.iwhalecloud.retail.goods2b.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.goods2b.dto.GoodsActRelDTO;
import com.iwhalecloud.retail.goods2b.dto.GoodsProductRelDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/12.
 */
@Data
@ApiModel(value = "B2B编辑商品请求参数-中台")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodsEditByZTReq  extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @NotBlank
    @ApiModelProperty(value = "商品id")
    private String goodsId;

    /**
     * 商品名称
     */
    @NotBlank
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String sellingPoint;

    /**
     * 上下架状态
     */
    @ApiModelProperty(value = "上下架状态")
    private Integer marketEnable;

    /**
     * 商品编码
     */
    @ApiModelProperty(value = "商品编码")
    private String sn;

    /**
     * 类型Id
     */
    @ApiModelProperty(value = "类型Id")
    private String typeId;

    /**
     * 市场价
     */
    @ApiModelProperty(value = "市场价")
    private Double mktprice;

    /**
     * 发布地市
     */
    @ApiModelProperty(value = "发布地市")
    private List<RegionReq> regionList;

    /**
     * 零售商标签
     */
    @ApiModelProperty(value = "零售商标签")
    private List<String> tagList;

    /**
     * 渠道类型
     */
    @ApiModelProperty(value = "渠道类型")
    private String channelType;

    /**
     * 生效日期
     */
    @ApiModelProperty(value = "生效日期")
    private java.util.Date effDate;

    /**
     * 失效日期
     */
    @ApiModelProperty(value = "失效日期")
    private java.util.Date expDate;

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private String goodsCatId;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private String brandId;

    /**
     * 供货商ID
     */
    @ApiModelProperty(value = "供货商ID")
    private String supplierId;

    /**
     * 供应商类型
     */
    @ApiModelProperty(value = "供应商类型")
    private String merchantType;

    /**
     * 审批状态
     */
    @ApiModelProperty(value = "审批状态")
    private String auditState;

    /**
     * 产品列表
     */
    @ApiModelProperty(value = "产品列表")
    private List<GoodsProductRelDTO> goodsProductRelList;

    /**
     * 是否商家确认
     */
    @ApiModelProperty(value = "是否商家确认")
    private Integer isMerchantConfirm;

    /**
     * 图片文件
     */
    @ApiModelProperty(value = "图片文件")
    private List<FileAddReq> fileAddReqList;

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

    @ApiModelProperty(value = "配置的营销活动信息")
    private List<GoodsActRelDTO> goodsActs;

    /**
     * 商品发布对象
     */
    @ApiModelProperty(value = "商品发布对象")
    private String targetType;

    /**
     * 发布对象
     */
    @ApiModelProperty(value = "发布对象")
    private List<GoodsTargetReq> targetList;
}
