package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/29 17:13
 * @Description:
 */
@Data
@TableName("t_tag")
public class TTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER)
    private java.lang.Long tagId; //标签ID

    private java.lang.String tagName; //标签名称

    private java.lang.String tagDesc; //标签说明

    private java.lang.Integer tagType; //标签样式

    private java.lang.String tagColor; //标签颜色

    private Date updDate; //更新时间
}

