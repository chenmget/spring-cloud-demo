package com.iwhalecloud.retail.order.dto.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMemberModel implements Serializable{

    private String memberId;

    private String name;

    private String mobile;

    private String lvId;

    private String sex;
}
