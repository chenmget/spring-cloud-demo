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
public class MemberMerchantUpdateReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 5135182327355269060L;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    private String memId;

    /**
     * 会员状态  1有效  0无效
     */
    @ApiModelProperty(value = "会员状态  1有效  0无效")
    private String status;

    /**
     * 会员等级ID
     */
    @ApiModelProperty(value = "会员等级ID")
    private Integer lvId;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateStaff;

}
