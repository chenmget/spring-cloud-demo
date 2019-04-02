package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author mzl
 * @date 2018/12/24
 */
@Data
@ApiModel(value = "商品分页查询请求参数")
public class GoodsForPageQueryReq extends AbstractPageReq {

    private static final long serialVersionUID = 107595296814210206L;

    /**
     * 商品ID列表
     */
    @ApiModelProperty(value = "商品ID列表")
    private List<String> ids;

    /**
     * 商品分类id
     */
    @ApiModelProperty(value = "商品分类id")
    private List<String> catIdList;

    /**
     * 品牌ID
     */
    @ApiModelProperty(value = "品牌ID")
    private List<String> brandIdList;

    /**
     * 开始价格
     */
    @ApiModelProperty(value = "开始价格")
    private Long startPrice;

    /**
     * 结束价格
     */
    @ApiModelProperty(value = "结束价格")
    private Long endPrice;

    /**
     * 关键词搜索
     */
    @ApiModelProperty(value = "关键词搜索")
    private String searchKey;

    /**
     * 供应商ID
     */
    @ApiModelProperty(value = "供应商ID")
    private List<String> supplierIdList;

    /**
     * 排序类型值
     * com.iwhalecloud.retail.goods.common.GoodsConst.SortTypeEnum
     */
    private String sortType;

    /**
     * 经营主体编码或店中商编码
     */
    private List<String> targetCodeList;

    /**
     * 扩展查询条件
     */
    private List<AttrSpecValueReq> attrSpecValueList;

    /**
     * 是否登陆
     */
    @ApiModelProperty(value = "是否登陆")
    private Boolean isLogin;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 是否有库存
     */
    @ApiModelProperty(value = "是否有库存")
    private Integer isHaveStock;

    /**
     * 产品ID列表
     */
    @ApiModelProperty(value = "产品ID列表")
    private List<String> productIds;

    /**
     * 区域ID
     */
    @ApiModelProperty(value = "区域ID")
    private String regionId;

    /**
     * 本地网ID
     */
    @ApiModelProperty(value = "本地网ID")
    private String lanId;


    /**
     * 对象ID
     */
    @ApiModelProperty(value = "对象ID")
    private String targetId;

    /** 功能描述
    * 用户类型
    */
    @ApiModelProperty(value = "用户类型")
    private Integer userFounder;

    /**
     * 商家类型
     */
    @ApiModelProperty(value = "商家类型")
    private String merchantType;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private String payments;
}
