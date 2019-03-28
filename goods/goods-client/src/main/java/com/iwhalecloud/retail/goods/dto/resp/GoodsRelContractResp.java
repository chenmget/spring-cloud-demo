package com.iwhalecloud.retail.goods.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/15
 */
@Data
@ApiModel(value = "关联合约信息查询结果")
public class GoodsRelContractResp implements Serializable {

    private static final long serialVersionUID = -3619752837459171412L;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goods_id;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;
    private String sn;
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String brand_id;
    private String brand_code;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private String cat_id;
    private Integer market_enable;
    private String image_default;
    private String create_time;
    private String last_modify;
    private Integer disabled;
    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    private String search_key;
    /**
     * 卖点
     */
    @ApiModelProperty(value = "卖点")
    private String selling_point;
    private String cat_name;
    private String userid;
    private String creator_user;
    private String creator_user_name;
    private String supper_id;
    private String pc_image_default;
    private String pc_image_file;
    private String mbl_image_default;
    private String mbl_image_file;
    private String mbl_remark;
    private String mbl_intors;
    private String wx_image_default;
    private String wx_image_file;
    private String wx_remark;
    private String wx_intors;

    /**
     * 规格，json格式
     */
    @ApiModelProperty(value = "规格")
    private String specs;

    /**
     * 合约期
     */
    @ApiModelProperty(value = "合约期")
    private Long contractPeriod;

    /**
     * 购机款
     */
    @ApiModelProperty(value = "购机款")
    private Double purchasePrice;

    /**
     * 话费预存
     */
    @ApiModelProperty(value = "话费预存")
    private Double deposit;

    /**
     * 首月返还
     */
    @ApiModelProperty(value = "首月返还")
    private Double firstReturn;

    /**
     * 次月起返还
     */
    @ApiModelProperty(value = "次月起返还")
    private Double nextReturn;

    private String p1;
    private String p2;
    private String p3;
    private String p4;
    private String p5;
    private String p6;
    private String p7;
    private String p8;
    private String p9;
    private String p10;
    private String p11;
    private String p12;
    private String p13;
    private String p14;
    private String p15;
    private String p16;
    private String p17;
    private String p18;
    private String p19;
    private String p20;
}
