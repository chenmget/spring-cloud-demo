package com.iwhalecloud.retail.goods2b.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.iwhalecloud.retail.dto.AbstractRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/25.
 */
@Data
@ApiModel(value = "商品组与商品之间关系组装数据，GoodsGroupRelDTO")
public class GoodsGroupRelDTO extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *关联ID
     */
    @ApiModelProperty(value = "关联ID")
    private String groupRelId;
    /**
     *商品id
     */
    @ApiModelProperty(value = "商品id")
    private String goodsId;
    /**
     *商品组id
     */
    @ApiModelProperty(value = "商品组id")
    private String groupId;
}
