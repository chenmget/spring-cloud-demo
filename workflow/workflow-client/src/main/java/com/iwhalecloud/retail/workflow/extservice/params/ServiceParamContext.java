package com.iwhalecloud.retail.workflow.extservice.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务调用参数容器
 * @author z
 * @date 2019/3/26
 */
@Data
public class ServiceParamContext implements Serializable {

    @ApiModelProperty(value = "业务ID")
    private String businessId;

    @ApiModelProperty(value = "当前处理用户ID")
    private String handlerUserId;

    @ApiModelProperty(value = "当前处理用户名称")
    private String handlerUserName;

    @ApiModelProperty(value = "服务动态参数，直接读取数据库配置，这里只是透传过去")
    private String dynamicParam;

    @ApiModelProperty(value = "-1：无参数\n" +
            "1：json\n" +
            "2：字符串")
    private java.lang.Integer paramsType;

    @ApiModelProperty(value = "业务参数类型在启动流程的时候传入，便于在环节流转时直接获取需要的数据。")
    private java.lang.String paramsValue;

    /**
     * 服务分组（订单：order，商品：goods，合作伙伴：partner）
     */
    @ApiModelProperty(value = "分组 ")
    private String serviceGroup;
    /**
     * 服务类路径
     */
    @ApiModelProperty(value = "服务类路径 ")
    private String classPath;

}
