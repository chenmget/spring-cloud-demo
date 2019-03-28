package com.iwhalecloud.retail.member.dto.request;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: wang.jiaxin
 * @Date: 2019年03月05日
 * @Description:
 **/
@Data
@ApiModel(value = "对应模型mem_group, 对应实体Group类")
public class GroupAddReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = -1886845599429969461L;

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
     * 群描述(备注)
     */
    @ApiModelProperty(value = "群描述(备注)")
    private String meno;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createStaff;
}
