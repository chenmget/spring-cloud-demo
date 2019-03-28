package com.iwhalecloud.retail.goods2b.dto.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "热门搜索关键字查询请求参数")
public class ProdKeywordsPageQueryReq extends AbstractPageReq{
}
