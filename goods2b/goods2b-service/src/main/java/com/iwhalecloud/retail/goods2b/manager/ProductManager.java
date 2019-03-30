package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResourceResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductResp;
import com.iwhalecloud.retail.goods2b.dto.resp.QueryProductInfoResqDTO;
import com.iwhalecloud.retail.goods2b.entity.Product;
import com.iwhalecloud.retail.goods2b.exception.ProductException;
import com.iwhalecloud.retail.goods2b.mapper.ProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProductManager {
    @Resource
    private ProductMapper productMapper;

    /**
     * 添加产品
     * @param req
     * @return
     */
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public Integer insert(Product req) throws ProductException {
        // 产品编码
        if (StringUtils.isNotBlank(req.getSn()) || StringUtils.isNotBlank(req.getUnitName())) {
            Boolean bothNotNull = StringUtils.isNotBlank(req.getSn()) && StringUtils.isNotBlank(req.getUnitName());
            ProductGetDuplicateReq dto = new ProductGetDuplicateReq();
            dto.setSn(req.getSn());
            dto.setUnitName(req.getUnitName());
            dto.setBothNotNull(bothNotNull);
            Integer num = productMapper.getDuplicate(dto);
            if(null != num && num > 0){
                List<String> errors = new ArrayList<String>(1);
                errors.add("产品编码、营销资源名称不能重复");
                throw new ProductException(errors);
            }
        }
        return productMapper.insert(req);
    }

    /**
     * 根据产品ID获取产品对象
     * @param productId 产品ID
     * @return
     */
    public String getMerChantByProduct(String productId) {
        return productMapper.getMerChantByProduct(productId);
    }

    /**
     * 根据产品ID获取产品对象
     * @param productId 产品ID
     * @return
     */
    public ProductResp getProduct(String productId) {
        return productMapper.getProduct(productId);
    }

    /**
     * 删除
     * @param productId
     * @return
     */
    public Integer updateProdProductDelete(String productId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setIsDeleted(ProductConst.IsDelete.YES.getCode());
        return productMapper.updateById(product);
//        此处应当软删除，而非Delete
//        return productMapper.deleteById(productId);
    }

    /**
     * 删除产品(Delete操作)
     * @param productId
     * @return
     */
    public Integer deleteProdProduct(String productId) {
        return productMapper.deleteById(productId);
    }
    
    /**
     * 更新
     * @param req
     * @return
     */
    public Integer updateProdProduct(ProductUpdateReq req){
        Product t = new Product();
        BeanUtils.copyProperties(req, t);
        return productMapper.updateById(t);
    }

    /**
     * 通用查询
     * @param req
     * @return
     */
    public Page<ProductDTO> selectProduct(ProductGetReq req) {
        Page<ProductGetReq> page = new Page<>(req.getPageNo(), req.getPageSize());
        return productMapper.selectProduct(page, req);
    }

    /**
     * 分页查询
     * @param req
     * @return
     */
    public Page<ProductPageResp> selectPageProductAdmin(ProductsPageReq req) {
        Page<ProductPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return productMapper.selectPageProductAdmin(page, req);
    }

    /**
     * 查询产品Id
     * @param req
     * @return
     */
    public List<ProductResourceResp> getProductResource(ProductResourceInstGetReq req){
        return productMapper.getProductResource(req);
    }

    public int getProductCountByManufId(String manufacturerId){
        return productMapper.getProductCountByManufId(manufacturerId);
    }

    /**
     * 根据产品ID获取产品对象
     * @param productIdList 产品ID列表
     * @return
     */
    public List<Product> getProductListByIds(List<String> productIdList) {
        return productMapper.selectBatchIds(productIdList);
    }

    /**
     * 根据产品ID获取产品信息
     * @param queryProductInfoReqDTO
     * @return
     */
    public ProductPageResp getProductInfo(QueryProductInfoReqDTO queryProductInfoReqDTO){
        return productMapper.getProductInfo(queryProductInfoReqDTO);
    }
    public int updateAuditStateByProductBaseId(ProductAuditStateUpdateReq req){
//        Product record = new Product();
//        record.setProductId(productId);
//        record.setStatus(auditState);
//        record.setUpdateDate(new Date());
//        record.setUpdateStaff(updateStaff);
////        productMapper.update()
//        productMapper.
        return productMapper.updateAuditStateByProductBaseId(req);

    }

    /**
     * 权限过滤用
     * @param req
     * @return
     */
    public List<ProductResp> getProductByProductIdsAndBrandIds(ProductAndBrandGetReq req){
        return productMapper.getProductByProductIdsAndBrandIds(req);
    }

    /**
     * 根据productBaseId查询
     * @param productBaseId
     * @return
     */
    public List<String> listProduct(String productBaseId) {
        return productMapper.listProduct(productBaseId);
    }
}
