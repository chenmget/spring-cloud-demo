package com.iwhalecloud.retail.order2b.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iwhalecloud.retail.order2b.config.DBTableSequence;
import com.iwhalecloud.retail.order2b.config.TableTimeValidate;
import com.iwhalecloud.retail.order2b.config.WhaleCloudDBKeySequence;
import lombok.Data;

import java.io.Serializable;

@Data
/**
 *
 */
@TableName("ord_order_Z_FLOW")
@WhaleCloudDBKeySequence(keySeqName = "flowId",
        keySeqValue = DBTableSequence.ORD_ORDER_Z_FLOW,
fragmentField = "lanId")
@KeySequence(clazz = String.class)
public class ZFlow implements Serializable {
    private String lanId;

    @TableId(type = IdType.ID_WORKER_STR)
    private String flowId;

    private Integer sort;

    private String orderId;
    private String bindType;
    private String handlerId;
    private String isExe;
    private String flowType;
    @TableTimeValidate
    private String updateTime;
    private String createTime;
    private String remindFlag;
    private String  sourceFrom;



}
