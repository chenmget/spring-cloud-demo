package com.iwhalecloud.retail.promo.dto.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手工补录的活动记录的明细记录
 *
 * @author li.yulong
 * @date 2019年03月6 日
 */

@Data
@ApiModel(value = "手工补录的活动记录的明细记录")
public class ActSupDetailResp implements Serializable {

    private static final long serialVersionUID = 3332776704916116434L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date gmtCreate;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private java.util.Date gmtModified;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creator;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String modifier;

    /**
     * 补录记录ID
     */
    @ApiModelProperty(value = "补录记录ID")
    private String recordId;

    /**
     * 商家类型，同商家表中商家类型
     */
    @ApiModelProperty(value = "商家类型，同商家表中商家类型")
    private String merchantType;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    /**
     * 订单的创建时间
     */
    @ApiModelProperty(value = "订单的创建时间")
    private java.util.Date orderTime;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private String orderId;

    /**
     * 订单项ID
     */
    @ApiModelProperty(value = "订单项ID")
    private String orderItemId;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;

    /**
     * 产品ID
     */
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private Double price;

    /**
     * 优惠额或补贴额
     */
    @ApiModelProperty(value = "优惠额或补贴额")
    private Double discount;

    /**
     * 订单中包含的串码
     */
    @ApiModelProperty(value = "订单中包含的串码")
    private String resNbr;

    /**
     * 串码的发货时间
     */
    @ApiModelProperty(value = "串码的发货时间")
    private String shipTime;

    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品型号
     */
    @ApiModelProperty(value = "产品型号")
    private String unitType;

    /**
     * 产品颜色
     */
    @ApiModelProperty(value = "产品颜色")
    private String color;

    /**
     * 产品内存
     */
    @ApiModelProperty(value = "产品内存")
    private String memory;

    /**
     * 产品编码
     */
    @ApiModelProperty(value = "产品编码")
    private String productCode;


    /**
     * 规格名称
     */
    @ApiModelProperty(value = "规格名称")
    private String specName;

}
