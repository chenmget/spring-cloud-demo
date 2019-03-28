package com.iwhalecloud.retail.warehouse.dto.request.markresswap;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @author 吴良勇
 * @date 2019/3/6 17:07
 */
@Data
@ApiModel("移动串码入库请求参数")
public class SyncTerminalSwapReq  implements Serializable {
        @NotEmpty(message = "串码列表不能为空")
        private List<SyncTerminalItemSwapReq> mktResList;

}
