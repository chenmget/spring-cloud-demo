package com.iwhalecloud.retail.order2b.dto.resquest.purapply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @auther lin.wenhui@iwhalecloud.com
 * @date 2019/4/24 10:28
 * @description 确认收货请求对象
 */

@Data
public class PurApplyReceivingReq implements Serializable {

    private static final long serialVersionUID = -684295989629572545L;

    /**
     * 申请单id
     */
    @ApiModelProperty("申请单id")
    private String applyId;

    /**
     * 商家标识
     */
    @ApiModelProperty("商家标识")
    private String merchantId;

    /**
     * 产品标识List
     */
    @ApiModelProperty("产品标识List")
    private List<String> mktResIdList;

    /**
     * 产品标识
     */
    @ApiModelProperty("产品标识")
    private String mktResId;

    /**
     * 串码 //    @NotEmpty(message = "串码List不能为空")
     */
    @ApiModelProperty("串码List")
    private List<String> mktResInstNbrs;

    /**
     * 营销资源仓库标识
     */
    @ApiModelProperty("营销资源仓库标识")
    private String mktResStoreId;

    /**
     * 区域标识
     */
    @ApiModelProperty("区域标识")
    private String regionId;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String statusCd;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createStaff;

    /**
     * 事件类型
     */
    @ApiModelProperty("事件类型")
    private String eventType;

    /**
     * 串码来源 1厂商，2 供应商，3零售商
     */
    @ApiModelProperty("事件类型")
    private String sourceType;

    /**
     * 入库类型
     */
    @ApiModelProperty("入库类型")
    private String storageType;

    /**
     * 资源类型
     */
    @ApiModelProperty("资源类型")
    private String mktResInstType;

    /**
     * lanId
     */
    @ApiModelProperty("lanId")
    private String lanId;

    /**
     * 资源类型
     */
//    @ApiModelProperty("资源类型")
//    private String mktResInstType;

}

