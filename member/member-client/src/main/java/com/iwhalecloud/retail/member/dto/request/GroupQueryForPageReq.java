package com.iwhalecloud.retail.member.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月05日
 * @Description:
 **/
@Data
@ApiModel(value = "对应模型mem_group, 对应实体Group类")
public class GroupQueryForPageReq extends PageVO {

    private static final long serialVersionUID = -6569156968732429791L;

    //属性 begin

    /**
     * 会员群名称
     */
    @ApiModelProperty(value = "会员群名称")
    private String groupName;

    /**
     * 会员群类型 (值待确定)
     */
    @ApiModelProperty(value = "会员群类型 (值待确定)")
    private String groupType;

    /**
     * 商圈
     */
    @ApiModelProperty(value = "商圈")
    private String tradeName;

    /**
     * 会员群状态  1有效   0无效
     */
    @ApiModelProperty(value = "会员群状态  1有效   0无效")
    private String status;

    /**
     * 群描述(备注)
     */
    @ApiModelProperty(value = "群描述(备注)")
    private String meno;

    /**
     * 创建开始时间
     */
    @ApiModelProperty(value = "创建开始时间")
    private java.util.Date createStartDate;

    /**
     * 创建结束时间
     */
    @ApiModelProperty(value = "创建结束时间")
    private java.util.Date createEndDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;
}
