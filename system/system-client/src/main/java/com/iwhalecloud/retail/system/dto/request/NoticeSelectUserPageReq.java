package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("消息新建选择用户时，根据条件查找 用户 分页列表")
public class NoticeSelectUserPageReq extends PageVO {
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String loginName;

    /**
     * 工号类型
     */
    @ApiModelProperty(value = "工号类型")
    private Integer userFounder;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String userName;

//    /**
//     * 状态
//     */
//    @ApiModelProperty(value = "状态")
//    private List<Integer> statusList;

}
