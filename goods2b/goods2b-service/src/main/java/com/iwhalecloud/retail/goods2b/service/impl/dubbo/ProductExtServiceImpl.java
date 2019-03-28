package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtDeleteReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductExtGetResp;
import com.iwhalecloud.retail.goods2b.manager.ProductExtManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductExtServiceImpl implements ProductExtService {

    @Autowired
    private ProductExtManager productExtManager;

    @Override
    public ResultVO<ProductExtGetResp> getProductExt(ProductExtGetReq req){
        return ResultVO.success(productExtManager.getProductExt(req.getProductBaseId()));
    }

    /**
     * 获取所有产品扩展参数
     * @return
     */
    @Override
    public ResultVO<List<ProductExtGetResp>> listAll(){
        return ResultVO.success(productExtManager.listAll());
    }

    /**
     * 添加产品扩展参数
     * @param req
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public ResultVO<Integer> addProductExt(ProductExtAddReq req){
        return ResultVO.success(productExtManager.addProductExt(req));
    }

    /**
     * 修改产品扩展参数
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateProductExt(ProductExtUpdateReq req){
        return ResultVO.success(productExtManager.updateProductExt(req));
    }

    /**
     * 删除产品扩展参数
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> deleteProductExt(ProductExtDeleteReq req){
        return ResultVO.success(productExtManager.deleteProductExt(req.getProductBaseId()));
    }

}