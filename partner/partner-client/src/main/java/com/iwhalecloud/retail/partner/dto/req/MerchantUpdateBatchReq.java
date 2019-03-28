package com.iwhalecloud.retail.partner.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("批量更新商家信息")
public class MerchantUpdateBatchReq implements Serializable {

    private static final long serialVersionUID = 5906659063734070374L;

    @ApiModelProperty(value = "商家ID集合")
    @NotEmpty(message = "商家ID集合不能为空")
    private List<String> merchantIdList;

    /**
     * 商家类型:  1 厂商    2 地包商    3 省包商   4 零售商
     */
    @ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
    private String merchantType;

    /**
     * 关联sys_user表user_id
     */
    @ApiModelProperty(value = "关联sys_user表user_id")
    private String userId;

    /**
     * 渠道状态
     */
    @ApiModelProperty(value = "渠道状态:" +
            " 有效1000  主动暂停1001  异常暂停1002 无效1100 终止1101 退出1102 未生效1200" +
            " 已归档1300 预退出8922 冻结8923 主动暂停1001  异常暂停1002   无效1100  终止1101  " +
            " 退出1102  未生效1200  已归档1300 预退出8922  冻结8923 ")
    private String status;
}