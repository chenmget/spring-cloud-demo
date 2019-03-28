package com.iwhalecloud.retail.goods2b.manager;

import com.iwhalecloud.retail.goods2b.dto.req.ProductExtAddReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductExtUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductExtGetResp;
import com.iwhalecloud.retail.goods2b.entity.ProductExt;
import com.iwhalecloud.retail.goods2b.mapper.ProductExtMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProductExtManager{
    @Resource
    private ProductExtMapper productExtMapper;

    /**
     * 获取产品扩展参数
     * @param productBaseId
     * @return
     */
    public ProductExtGetResp getProductExt(String productBaseId){
        ProductExt t = productExtMapper.selectById(productBaseId);
        if (null == t){
            return  null;
        }
        ProductExtGetResp dto = new ProductExtGetResp();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }

    /**
     * 获取所有产品扩展参数
     * @return
     */
    public List<ProductExtGetResp> listAll(){
        List<ProductExt> list = productExtMapper.listAll();
        if (null == list || list.isEmpty()){
            return null;
        }
        List<ProductExtGetResp> dtoList = new ArrayList<ProductExtGetResp>(list.size());
        for (ProductExt t : list){
            ProductExtGetResp dto = new ProductExtGetResp();
            BeanUtils.copyProperties(t, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 添加产品扩展参数
     * @param req
     * @return
     */
    public Integer addProductExt(ProductExtAddReq req){
        ProductExt t = new ProductExt();
        BeanUtils.copyProperties(req, t);
        return productExtMapper.insert(t);
    }

    /**
     * 修改产品扩展参数
     * @param req
     * @return
     */
    public Integer updateProductExt(ProductExtUpdateReq req){
        ProductExt t = new ProductExt();
        BeanUtils.copyProperties(req, t);
        return productExtMapper.updateById(t);
    }

    /**
     * 删除产品扩展参数
     * @param productBaseId
     * @return
     */
    public Integer deleteProductExt(String productBaseId){
        return productExtMapper.deleteById(productBaseId);
    }
    
}
