package com.iwhalecloud.retail.warehouse.dto.request.markres;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author 吴良勇
 * @date 2019/3/7 15:35
 */
@Data
@ApiModel("仓库信息同步接口")
public class SynMarkResStoreItemReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "仓库ID不能为空")
    @ApiModelProperty(value = "仓库ID")
    private String mktResStoreId;
    @NotEmpty(message = "仓库名称不能为空")
    @ApiModelProperty(value = "仓库名称")
    private String mktResStoreName;
    @NotEmpty(message = "盘存时间不能为空")
    @ApiModelProperty(value = "盘存时间")
    private String checkDate;
    @NotEmpty(message = "创建时间不能为空")
    @ApiModelProperty(value = "创建时间")
    private String createDate;
    @ApiModelProperty(value = "上级仓库标识")
    private String parStoreId;
    @NotEmpty(message = "组织ID不能为空")
    @ApiModelProperty(value = "组织ID")
    private String orgId;
    @ApiModelProperty(value = "营销资源仓库管理员ID")
    private String staffId;
    @ApiModelProperty(value = "区域标识")
    private String regionId;
    @NotEmpty(message = "渠道业务编码不能为空")
    @ApiModelProperty(value = "渠道业务编码")
    private String channelId;
    @NotEmpty(message = "仓库类型不能为空")
    @ApiModelProperty(value = "仓库类型")
    private String storeType;
    @NotEmpty(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private String statusCd;
    @ApiModelProperty(value = "状态时间")
    private String statusDate;
    @ApiModelProperty(value = "备注")
    private String remark;
    @NotEmpty(message = "仓库编码不能为空")
    @ApiModelProperty(value = "仓库编码")
    private String mktResStoreNbr;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String rStorageCode1;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String rStorageCode2;
    @NotEmpty(message = "本地网不能为空")
    @ApiModelProperty(value = "本地网")
    private String lanId;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String cOperId;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String address;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String vbatchcode;
    @ApiModelProperty(value = "物资类型")
    private String rcType;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String familyId;
    @ApiModelProperty(value = "创建人")
    private String createStaff;
    @ApiModelProperty(value = "普通(0)、县级直供中心(1)、新小偏网点(2)")
    private String directSupply;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String provider;
    @ApiModelProperty(value = "2.0多出无名氏")
    private String providerName;
    @ApiModelProperty(value = "回收仓库标识")
    private String recStoreId;
    @ApiModelProperty(value = "回收方式")
    private String recType;
    @ApiModelProperty(value = "回收期限")
    private String recDay;
    @ApiModelProperty(value = "仓库细类")
    private String storeSubType;
    @ApiModelProperty(value = "仓库层级")
    private String storeGrade;
    @NotEmpty(message = "生效时间不能为空")
    @ApiModelProperty(value = "生效时间")
    private String effDate;
    @NotEmpty(message = "失效时间不能为空")
    @ApiModelProperty(value = "失效时间")
    private String expDate;
    @ApiModelProperty(value = "修改人")
    private String updateStaff;
    @ApiModelProperty(value = "修改时间")
    private String updateDate;

}
