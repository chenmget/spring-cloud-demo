package com.iwhalecloud.retail.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CommonOrg
 *
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("sys_common_org")
@ApiModel(value = "对应模型sys_common_org, 对应实体CommonOrg类")
public class CommonOrg implements Serializable {
    /**
     * 表名常量
     */
    public static final String TNAME = "sys_common_org";
    private static final long serialVersionUID = 1L;


    //属性 begin
    /**
     * 电信组织标识主键
     */
    @TableId(type = IdType.ID_WORKER)
    @ApiModelProperty(value = "电信组织标识主键")
    private String orgId;

    /**
     * 上级组织标识,直接记录组织的直接管理上级标识
     */
    @ApiModelProperty(value = "上级组织标识,直接记录组织的直接管理上级标识")
    private String parentOrgId;

    /**
     * 上级组织名称,直接记录组织的直接管理上级组织名称
     */
    @ApiModelProperty(value = "上级组织名称,直接记录组织的直接管理上级组织名称")
    private String parentOrgName;

    /**
     * 电信组织编码
     */
    @ApiModelProperty(value = "电信组织编码")
    private String orgCode;

    /**
     * 电信组织名称
     */
    @ApiModelProperty(value = "电信组织名称")
    private String orgName;

    /**
     * 本地网id
     */
    @ApiModelProperty(value = "本地网id")
    private String lanId;

    /**
     * 本地网名称
     */
    @ApiModelProperty(value = "本地网名称")
    private String lan;

    /**
     * 公用管理区域标识,记录区域唯一标识
     */
    @ApiModelProperty(value = "公用管理区域标识,记录区域唯一标识")
    private String regionId;

    /**
     * 公用管理区域标识名称
     */
    @ApiModelProperty(value = "公用管理区域标识名称")
    private String region;

    /**
     * 电信组织级别,从0开始,0级为最高级
     */
    @ApiModelProperty(value = "电信组织级别,从0开始,0级为最高级")
    private Long orgLevel;

    /**
     * 记录状态变更时间
     */
    @ApiModelProperty(value = "记录状态变更时间")
    private java.util.Date statusDate;

    /**
     * 记录状态:lovb=pub-c-0001
     */
    @ApiModelProperty(value = "记录状态:lovb=pub-c-0001")
    private String statusCd;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 记录首次创建的员工标识
     */
    @ApiModelProperty(value = "记录首次创建的员工标识")
    private String createStaff;

    /**
     * 记录首次创建的时间。
     */
    @ApiModelProperty(value = "记录首次创建的时间。")
    private java.util.Date createDate;

    /**
     * 记录每次修改的员工标识
     */
    @ApiModelProperty(value = "记录每次修改的员工标识")
    private String updateStaff;

    /**
     * 记录每次修改的时间
     */
    @ApiModelProperty(value = "记录每次修改的时间")
    private java.util.Date updateDate;

    /**
     * 层次路径编码
     */
    @ApiModelProperty(value = "层次路径编码")
    private java.lang.String pathCode;


    //属性 end

    /**
     * 字段名称枚举.
     */
    public enum FieldNames {
        /**
         * 电信组织标识主键.
         */
        orgId("orgId", "org_id"),

        /**
         * 上级组织标识,直接记录组织的直接管理上级标识.
         */
        parentOrgId("parentOrgId", "parent_org_id"),

        /**
         * 上级组织名称,直接记录组织的直接管理上级组织名称.
         */
        parentOrgName("parentOrgName", "parent_org_name"),

        /**
         * 电信组织编码.
         */
        orgCode("orgCode", "org_code"),

        /**
         * 电信组织名称.
         */
        orgName("orgName", "org_name"),

        /**
         * 本地网id.
         */
        lanId("lanId", "lan_id"),

        /**
         * 本地网名称.
         */
        lan("lan", "lan"),

        /**
         * 公用管理区域标识,记录区域唯一标识.
         */
        regionId("regionId", "region_id"),

        /**
         * 公用管理区域标识名称.
         */
        region("region", "region"),

        /**
         * 电信组织级别,从0开始,0级为最高级.
         */
        orgLevel("orgLevel", "org_level"),

        /**
         * 记录状态变更时间.
         */
        statusDate("statusDate", "status_date"),

        /**
         * 记录状态:lovb=pub-c-0001.
         */
        statusCd("statusCd", "status_cd"),

        /**
         * 备注.
         */
        remark("remark", "remark"),

        /**
         * 记录首次创建的员工标识.
         */
        createStaff("createStaff", "create_staff"),

        /**
         * 记录首次创建的时间。.
         */
        createDate("createDate", "create_date"),

        /**
         * 记录每次修改的员工标识.
         */
        updateStaff("updateStaff", "update_staff"),

        /**
         * 记录每次修改的时间.
         */
        updateDate("updateDate", "update_date"),

        /**
         * 层次路径编码
         */
        pathCode("pathCode", "path_code");

        private String fieldName;
        private String tableFieldName;

        FieldNames(String fieldName, String tableFieldName) {
            this.fieldName = fieldName;
            this.tableFieldName = tableFieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getTableFieldName() {
            return tableFieldName;
        }
    }

}
