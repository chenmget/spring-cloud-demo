package com.iwhalecloud.retail.goods.service.dubbo;


import com.iwhalecloud.retail.goods.dto.ResultVO;
import com.iwhalecloud.retail.goods.dto.resp.ProdGoodsSpecListResp;

public interface ProdGoodsSpecService{

    ResultVO<ProdGoodsSpecListResp> listProdGoodsSpec(String goodId);

}