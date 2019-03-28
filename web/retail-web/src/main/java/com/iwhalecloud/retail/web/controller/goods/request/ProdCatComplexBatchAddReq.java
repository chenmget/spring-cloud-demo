package com.iwhalecloud.retail.web.controller.goods.request;

import com.iwhalecloud.retail.goods.dto.req.ProdCatComplexAddReq;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "批量添加分类管理")
public class ProdCatComplexBatchAddReq implements Serializable {

    
	private static final long serialVersionUID = 1L;
	
	private List<ProdCatComplexAddReq> catComplexList;
}
