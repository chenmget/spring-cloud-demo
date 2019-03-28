package com.iwhalecloud.retail.goods2b.dto.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fadeaway on 2018/12/24.
 */
@Data
public class ProductBaseActivityQueryResp implements Serializable {

    @ApiModelProperty(value = "产品型号page对象")
    Page<ProductBaseGetResp> productBaseGetResp;


    @ApiModelProperty(value = "营销活动与商家过滤后允许配置的产品ID")
    List<String> productIds;
}
