package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_rfid_goods_rel")
public class TRfidGoodsRel implements Serializable {

	private static final long serialVersionUID = -1023067332067257466L;
	
	@TableId(type = IdType.ID_WORKER)
	private Long relId;
    private String rfid;
    private String goodsId;
    private String goodsName;
    private String productId;
    private String shopId;

    private Date createDate;
    private Date updateDate;
    private String createStaff;
    private String updateStaff;

}
