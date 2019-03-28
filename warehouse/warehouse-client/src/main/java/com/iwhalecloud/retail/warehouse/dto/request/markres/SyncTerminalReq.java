package com.iwhalecloud.retail.warehouse.dto.request.markres;

import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("移动串码入库请求参数")
public class SyncTerminalReq implements Serializable {
    @NotEmpty(message = "串码列表不能为空")
    private List<SyncTerminalItemReq> mktResList;

}
