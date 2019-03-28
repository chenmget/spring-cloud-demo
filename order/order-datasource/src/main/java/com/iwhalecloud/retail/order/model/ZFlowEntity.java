package com.iwhalecloud.retail.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order.config.WhaleCloudDBKeySequence;
import lombok.Data;

import java.io.Serializable;

@Data
/**
 *
 */
@TableName("ord_order_Z_FLOW")
@WhaleCloudDBKeySequence(keySeqName = "flowId")
@KeySequence(clazz = String.class)
public class ZFlowEntity implements Serializable {


    @TableId(type = IdType.ID_WORKER_STR)
    private String flowId;

    private Integer sort;

    private String orderId;
    private String bindType;
    private String handlerId;
    private String isExe;
    private String flowType;
    private String updateTime;
    private String createTime;
    private String remindFlag;


}
