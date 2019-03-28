package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/30 14:31
 * @Description:
 */

@Data
@TableName("cloud_shelf_detail")
public class CloudShelfDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ID_WORKER)
    private Long id; //id

    private Date gmtCreate; //创建时间

    private Date gmtModified; //修改时间

    private String creator; //创建人

    private String modifier; //修改人

    private int isDeleted; //是否删除：0未删、1删除

    private String cloudShelfNumber; //云货架编号

    private String shelfCategoryId; //货架类目id:推荐、套餐、手机、智能家居、配件

    private String operatingPositionId; //运营位ID

    private String operatingPositionName; //运营位名称

    private String operatingPositionAdscription; //运营位归属:省、市

    private String operatingPositionType; //运营位类型

    private String operatingPositionProperty; //运营位属性

    private Date operatingPositionExpiryTime; //运营位过期时间

    private Integer operatingPositionPlayMode; //运营位播放方式: 0单图，N时间间隔大小，单位是秒

    private Integer operatingPositionSequence; //运营位排序

    private int isRecommended; //是否推荐：0否、1是

    private String defaultOperatingPositionId;//默认运营位ID
}

