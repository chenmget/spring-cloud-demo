package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import com.iwhalecloud.retail.oms.dto.CloudShelfBindUserDTO;
import com.iwhalecloud.retail.oms.dto.DefaultCategoryDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.CloudDeviceReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class CloudShelfRequestDTO  implements Serializable {

    @ApiModelProperty(value = "云货架编码")
    private String num;
    @ApiModelProperty(value = "货架名称")
    private String shelfName;
    @ApiModelProperty(value = "设备状态")
    private Integer status;
    @ApiModelProperty(value = "关联设备列表ID集合")
    private List<String> cloudDevice;
    @ApiModelProperty(value = "货架类目列表")
    private List<DefaultCategoryDTO> shelfCategory;
    @ApiModelProperty(value="关联人员ID列表")
    private List<String> userIds;
}
