package com.iwhalecloud.retail.system.entity;


import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SYS_LAN")
@KeySequence(value="seq_sys_lan_lan_id",clazz = String.class)
@ApiModel(value = "SYS_LAN, 对应实体Lan类")
public class Lan implements Serializable {
    public static final String TNAME = "SYS_LAN";

    @TableId
    @ApiModelProperty(value = "lanId")
    private String lanId;

    @ApiModelProperty(value = "lanName")
    private String lanName;

    @ApiModelProperty(value = "lanCode")
    private String lanCode;

}