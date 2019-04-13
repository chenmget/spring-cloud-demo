package com.iwhalecloud.retail.warehouse.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author My
 * @Date 2019/1/10
 **/
@Data
public class ResourceRequestAddReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请单编码
     */
    @ApiModelProperty(value = "申请单编码")
    private String reqCode;

    /**
     * 申请单名称
     */
    @ApiModelProperty(value = "申请单名称")
    private String reqName;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     *串码实例列表
     */
    @ApiModelProperty(value = "串码实例列表")
    private List<ResourceRequestInst> instList;
    /**
     * 申请单类型
     01 入库申请
     02 调拨申请
     03 退库申请
     */
    @ApiModelProperty(value = "申请单类型01 入库申请 02 调拨申请03 退库申请")
    private String reqType;

    /**
     * 申请单内容描述
     */
    @ApiModelProperty(value = "申请单内容描述")
    private String content;

    /**
     * 目标营销资源仓库标识
     */
    @ApiModelProperty(value = "目标营销资源仓库标识")
    private String mktResStoreId;

    /**
     * 目标营销资源仓库
     */
    @ApiModelProperty(value = "目标营销资源仓库")
    private String destStoreId;

    /**
     * 记录要求完成时间
     */
    @ApiModelProperty(value = "记录要求完成时间")
    private java.util.Date completeDate;

    /**
     * 本地网标识
     */
    @ApiModelProperty(value = "本地网标识")
    private String lanId;

    /**
     * 指向公共管理区域标识
     */
    @ApiModelProperty(value = "指向公共管理区域标识")
    private String regionId;

    /**
     * 记录状态。LOVB=RES-C-0010
     */
    @ApiModelProperty(value = "记录状态。LOVB=RES-C-0010")
    private String statusCd;
    /**
     * 记录事件明细出入库类型,LOVB=RES-C-0012
     */
    @ApiModelProperty(value = "记录事件明细出入库类型,LOVB=RES-C-0012")
    private java.lang.String chngType;
    /**
     * 记录首次创建的用户标识。
     */
    @ApiModelProperty(value = "记录首次创建的用户标识。")
    private String createStaff;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录每次修改的用户标识。
     */
    @ApiModelProperty(value = "记录每次修改的用户标识。")
    private String updateStaff;

    /**
     * 记录每次修改的时间。
     */
    @ApiModelProperty(value = "记录每次修改的时间。")
    private java.util.Date updateDate;

    /**
     * 记录状态变更的时间。
     */
    @ApiModelProperty(value = "记录状态变更的时间。")
    private java.util.Date statusDate;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 拓展字段，存商家ID
     */
    @ApiModelProperty(value = "拓展字段，存商家ID")
    private String extend1;

    @Data
    public static class ResourceRequestInst implements Serializable{
        /**
         * 营销资源实例的标识，主键
         */
        @ApiModelProperty(value = "营销资源实例的标识，主键")
        private java.lang.String mktResInstId;

        /**
         * 记录营销资源实例编码。
         */
        @ApiModelProperty(value = "记录营销资源实例编码。")
        private java.lang.String mktResInstNbr;
        /**
         * 营销资源标识，记录product_id
         */
        @ApiModelProperty(value = "营销资源标识，记录product_id")
        private String mktResId;
        /**
         * 抽检标识
         */
        @ApiModelProperty(value = "抽检标识")
        private String isInspection;

    }
}
