package com.iwhalecloud.retail.order2b.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberModel implements Serializable{

    private String memberId;

    private String name;

    private String mobile;

    private String lvId;

    private String sex;
}
