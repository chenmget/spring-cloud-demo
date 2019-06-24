package com.iwhalecloud.retail.partner.dto.req;

import com.iwhalecloud.retail.dto.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("根据条件查找 商家信息 分页列表")
public class MerchantPageReq extends PageVO {

    /**   非 par_merchant 表字段  **/

    // sys_user表字段
    @ApiModelProperty(value = "商家对应系统账号")
    private String loginName;

    @ApiModelProperty(value = "商家对应系统状态:   1有效、 0 禁用   2：失效(删除)  3:锁住（密码错误次数超限 等等）")
    private Integer userStatus;

    // par_invoice表字段
    @ApiModelProperty(value = "营业执照到期日期 开始时间 （跟结束时间一起 查 营业执照到期日期 在这个区间的")
    private Date startExpireDate;

    @ApiModelProperty(value = "营业执照到期日期 结束时间")
    private Date endExpireDate;

    // prod_merchant_tag_rel表字段
    @ApiModelProperty(value = "标签ID")
    private String tagId;


    /**   非 par_merchant 表字段  **/

    @ApiModelProperty(value = "系统用户id集合")
    private List<String> userIdList;


    @ApiModelProperty(value = "商家id集合")
    private List<String> merchantIdList;


    /**
     * 商家编码(权限过滤用，管理员调用时传值)
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantId;

    /**
     * 商家编码
     */
    @ApiModelProperty(value = "商家编码")
    private String merchantCode;

    /**
     * 商家名称
     */
    @ApiModelProperty(value = "商家名称")
    private String merchantName;

    /**
     * 商家类型:  1 厂商    2 地包商    3 省包商   4 零售商
     */
    @ApiModelProperty(value = "商家类型:  1 厂商    2 地包商    3 省包商   4 零售商")
    private String merchantType;

    @ApiModelProperty(value = "商家类型集合:  1 厂商    2 地包商    3 省包商   4 零售商")
    private List<String> merchantTypeList;

    /**
     * 渠道状态:  有效 1000 主动暂停 1001 异常暂停 1002 无效 1100 终止 1101 退出 1102 未生效 1200 已归档 1300 预退出 8922 冻结 8923
     */
    @ApiModelProperty(value = "渠道状态:" +
            " 有效1000  主动暂停1001  异常暂停1002 无效1100 终止1101 退出1102 未生效1200" +
            " 已归档1300 预退出8922 冻结8923 主动暂停1001  异常暂停1002   无效1100  终止1101  " +
            " 退出1102  未生效1200  已归档1300 预退出8922  冻结8923 ")
    private java.lang.String status;

    /**
     * 地市
     */
    @ApiModelProperty(value = "地市")
    private java.lang.String lanId;

    /**
     * 市县
     */
    @ApiModelProperty(value = "市县")
    private java.lang.String city;

    /**
     * 商家所属经营主体
     */
    @ApiModelProperty(value = "商家所属经营主体	")
    private java.lang.String businessEntityName;

    /**
     * 商家所属经营主体	编码
     */
    @ApiModelProperty(value = "商家所属经营主体	编码")
    private java.lang.String businessEntityCode;

    @ApiModelProperty(value = "地市ID集合")
    private List<String> lanIdList;

    @ApiModelProperty(value = "市县ID集合")
    private List<String> cityList;

    @ApiModelProperty(value = "销售点编码")
    private java.lang.String shopCode;

    @ApiModelProperty(value = "营业执照到期日期")
    private Date busiLicenceExpDate;

    @ApiModelProperty(value = "专票状态 1 未录入/2 审核通过/3 审核中/4 审核不通过/5 已过期")
    private java.lang.String vatInvoiceStatus;

    @ApiModelProperty(value = "渠道大类")
    private java.lang.String channelType;
}
