package com.iwhalecloud.retail.goods2b.dto.resp;

import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenlong.zhong
 * @date 2019/7/11
 */
@Data
@ApiModel(value = "商品分类条件详情, relType=1的会带出 type详情")
public class CatConditionDetailResp implements Serializable {

    private static final long serialVersionUID = 5999483192120555715L;

    @ApiModelProperty(value = "分类条件列表")
    private List<CatConditionDTO> catConditionList;

    @ApiModelProperty(value = "类型详情，relType=1 时才有值")
    private TypeDTO typeDetail;

}
