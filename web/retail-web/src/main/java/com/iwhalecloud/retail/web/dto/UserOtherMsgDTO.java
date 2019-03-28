package com.iwhalecloud.retail.web.dto;

import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 存 登录用户的其他信息
 */
@Data
@ApiModel(value = "存 登录用户的其他信息")
public class UserOtherMsgDTO implements Serializable {
    private static final long serialVersionUID = 1783506762137275983L;

    @ApiModelProperty(value = "用户信息")
    private UserDTO user;

    @ApiModelProperty(value = "商家信息")
    private MerchantDTO merchant;

//    @ApiModelProperty(value = "厅店信息")
//    private PartnerShopDTO partnerShop;
//
//    @ApiModelProperty(value = "分销商信息")
//    private PartnerDTO partner;
//
//    @ApiModelProperty(value = "供应商信息")
//    private SupplierDTO supplier;

    @ApiModelProperty(value = "经营主体信息")
    private BusinessEntityDTO businessEntity;

//    @ApiModelProperty(value = "用户角色信息")
//    private List<UserRoleDTO> userRoleList;

//    @ApiModelProperty(value = "token信息")
//    private String token;

}
