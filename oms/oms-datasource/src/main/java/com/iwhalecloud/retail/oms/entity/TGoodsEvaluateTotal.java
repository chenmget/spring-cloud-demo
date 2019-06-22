package com.iwhalecloud.retail.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class TGoodsEvaluateTotal implements Serializable {
	@TableId(type = IdType.ID_WORKER_STR)
    private Long id;
    private String goodsId;
    private Integer tagsId;
    private String tagsName;
    private Integer counts;

}
