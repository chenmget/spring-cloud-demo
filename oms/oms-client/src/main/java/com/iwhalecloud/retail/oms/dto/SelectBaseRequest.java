package com.iwhalecloud.retail.oms.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

@Data
public class SelectBaseRequest implements Serializable {

	private static final long serialVersionUID = -7186801523276046150L;

	@ApiModelProperty(value = "查询页数，默认为1")
	private Integer pageNo = 1;

	@ApiModelProperty(value = "每条大小，默认为5")
    private Integer pageSize = 5;

	@ApiModelProperty(value = "电商平台登录后的sessionId",hidden = true)
    private String userSessionId;
	@ApiModelProperty(value = "会员ID",hidden = true)
    private String memberId;

    public void checkPage() {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }

        if(pageSize==null || pageSize<1){
            pageSize=5;
        }
    }
}
