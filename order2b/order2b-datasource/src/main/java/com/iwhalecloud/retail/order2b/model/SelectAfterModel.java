package com.iwhalecloud.retail.order2b.model;

import com.iwhalecloud.retail.order2b.dto.resquest.order.SelectAfterSalesReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Data
public class SelectAfterModel extends SelectAfterSalesReq {

    private String applyUserCode;

    private String handlerCode;

    private List<String> orderList;

    private List<String> statusList;

    private List<String> lanIdList;

    private List<String> itemList;

    private List<String> serviceTypeList;

    @ApiModelProperty("申请人id集合")
    private List<String> applicantIdList;

    public List<String> getServiceTypeList() {
        if(StringUtils.isEmpty(super.getServiceType())){
            return serviceTypeList;
        }
        return Arrays.asList(super.getServiceType().split(","));
    }
}
