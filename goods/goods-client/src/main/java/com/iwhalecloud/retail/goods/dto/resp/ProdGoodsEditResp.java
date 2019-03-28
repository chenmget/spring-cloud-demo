package com.iwhalecloud.retail.goods.dto.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mzl
 * @date 2018/11/27
 */
@Data
@ApiModel(value = "编辑商品返回结果")
public class ProdGoodsEditResp implements Serializable {

    public Boolean result;
}
