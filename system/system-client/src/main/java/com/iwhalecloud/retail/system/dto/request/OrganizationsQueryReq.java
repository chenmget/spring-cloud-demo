package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.system.dto.AbstractPageReq;
import lombok.Data;

@Data
public class OrganizationsQueryReq extends AbstractPageReq {

    private String orgId;

    private String orgName;

    private String lanId;


}
