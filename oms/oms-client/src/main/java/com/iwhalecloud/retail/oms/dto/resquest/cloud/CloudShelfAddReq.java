package com.iwhalecloud.retail.oms.dto.resquest.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: ji.kai
 * @Date: 2018/10/31 16:24
 * @Description:
 */
@Data
@ApiModel(value = "新增云货架请求参数")
public class CloudShelfAddReq implements Serializable {
    @ApiModelProperty(value = "货架名称")
    private String shelfName;
    @ApiModelProperty(value = "绑定的货架模板编码")
    private String shelfTemplatesNumber;
    @ApiModelProperty(value = "厅店ID")
    private String adscriptionShopId;
    @ApiModelProperty(value = "所属地市")
    private String adscriptionCity;
    @ApiModelProperty(value = "设备列表")
    private List<String> devices;
    @ApiModelProperty(value = "绑定的货架类目")
    private List<String> shelfCategoryIds;
    @ApiModelProperty(value = "绑定用户列表")
    private List<String> userIds;
}
