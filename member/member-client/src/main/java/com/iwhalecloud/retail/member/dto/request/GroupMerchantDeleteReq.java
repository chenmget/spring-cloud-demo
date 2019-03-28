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
public class GroupMerchantDeleteReq extends AbstractRequest implements Serializable {

    private static final long serialVersionUID = 5399990443302279L;

    /**
     * 商家ID
     */
    @ApiModelProperty(value = "商家ID")
    private String merchId;

    /**
     * 会员群ID
     */
    @ApiModelProperty(value = "会员群ID")
    private String groupId;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateStaff;
}
