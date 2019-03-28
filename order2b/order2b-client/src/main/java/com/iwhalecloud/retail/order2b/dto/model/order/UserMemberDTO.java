package com.iwhalecloud.retail.order2b.dto.model.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserMemberDTO implements Serializable{

    private String userId;

    private String userName;

    private String phoneNo;

    private String userCode;

}
