package com.iwhalecloud.retail.web.controller.b2b.system.response;

import com.iwhalecloud.retail.system.dto.MenuDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.web.dto.UserOtherMsgDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "系统登录返回对象")
public class LoginResp implements Serializable {
    private static final long serialVersionUID = 2616570260223622980L;

    @ApiModelProperty(value = "用户信息")
    private UserDTO adminUser;

    @ApiModelProperty(value = "用户的其他信息")
    private UserOtherMsgDTO userOtherMsg;

    @ApiModelProperty(value = "菜单信息")
    private List<MenuDTO> userMenu;

    // b2c部分
//    @ApiModelProperty(value = "用户厅店信息")
//    private PartnerShopDTO shopInfo;

    @ApiModelProperty(value = "token信息")
    private String token;

    @ApiModelProperty(value = "登录状态 0未登录  1已登录")
    private String loginStatusCode = "0";

    @ApiModelProperty(value = "登录信息  未登录   已登录")
    private String loginStatusMsg = "未登录";

    @ApiModelProperty(value = "changePwdCount")
    private Integer changePwdCount;

    @ApiModelProperty(value = "错误码")
    private Integer failCode = 0;

    @ApiModelProperty(value = "错误码")
    private String failMsg;
}