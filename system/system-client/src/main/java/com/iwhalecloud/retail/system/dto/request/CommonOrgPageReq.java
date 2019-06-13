package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/13
 */
@Data
public class CommonOrgPageReq extends PageVO implements Serializable {
    private static final long serialVersionUID = 703067396226814322L;

    @ApiModelProperty(value = "通用组织信息标识id 集合")
    private List<String> orgIdList;

}
