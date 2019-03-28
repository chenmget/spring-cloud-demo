package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductExtGetResp;

import java.util.List;

public interface ProductExtService{

    /**
     * 获取产品扩展参数
     * @param req
     * @return
     */
    ResultVO<ProductExtGetResp> getProductExt(ProductExtGetReq req);

    /**
     * 获取所有产品扩展参数
     * @return
     */
    ResultVO<List<ProductExtGetResp>> listAll();

    /**
     * 添加产品扩展参数
     * @param req
     * @return
     */
    ResultVO<Integer> addProductExt(ProductExtAddReq req);

    /**
     * 修改产品扩展参数
     * @param req
     * @return
     */
    ResultVO<Integer> updateProductExt(ProductExtUpdateReq req);

    /**
     * 删除产品扩展参数
     * @param req
     * @return
     */
    ResultVO<Integer> deleteProductExt(ProductExtDeleteReq req);
}