package com.iwhalecloud.retail.system.dto.request;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/6/5
 */
@Data
@ApiModel("本地区域列表分页查询 请求对象")
public class CommonRegionPageReq extends PageVO implements Serializable {
    private static final long serialVersionUID = -8100443420605774117L;

    @ApiModelProperty(value = "公共管理区域标识id 集合")
    private List<String> regionIdList;
}
