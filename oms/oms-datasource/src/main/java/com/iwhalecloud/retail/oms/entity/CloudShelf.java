package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/29 22:38
 * @Description:
 */
@Data
@ApiModel(value = "对应模型cloud_shelf, 对应实体CloudShelfDTO类")
@TableName("cloud_shelf")
public class CloudShelf implements Serializable {

    /**
     * 表名常量
     */
    public static final String TNAME = "cloud_shelf";
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(type = IdType.ID_WORKER)
    private Long id; //ID

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate; //创建时间

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified; //修改时间

    @ApiModelProperty(value = "创建人")
    private String creator; //创建人

    @ApiModelProperty(value = "修改人")
    private String modifier; //修改人

    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted; //是否删除：0未删、1删除

    @ApiModelProperty(value = "云货架编码")
    private String num; //云货架编码:全局唯一

    @ApiModelProperty(value = "云货架名称")
    private String name;

    @ApiModelProperty(value = "绑定的货架模板编码")
    private String shelfTemplatesNumber; //绑定的货架模板编码

    @ApiModelProperty(value = "设备状态")
    private Integer status; //设备状态:未激活、启用、停用

    @ApiModelProperty(value = "支持的设备类型")
    private String supportDeviceType; //支持的设备类型

    @ApiModelProperty(value = "所属厅店")
    private String adscriptionShopId; //所属厅店

    @ApiModelProperty(value = "所属城市")
    private String adscriptionCity; //所属城市
}

