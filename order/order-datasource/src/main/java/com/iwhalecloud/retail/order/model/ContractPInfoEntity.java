package com.iwhalecloud.retail.order.model;

import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import lombok.Data;

import java.io.Serializable;

@Data
@WhaleCloudDBKeySequence(keySeqName = "contractId")
public class ContractPInfoEntity implements Serializable {

    private String contractId;
    private String orderId;
    private String contractName;
    private String contractPhone;
    private String icNum;
    private String authentication;
    private String icType="身份证";
    private String phone;
    private String phoneStatus;


}
