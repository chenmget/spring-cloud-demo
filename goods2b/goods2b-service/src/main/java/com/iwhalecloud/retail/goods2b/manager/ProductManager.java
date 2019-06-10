package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.exception.RetailTipException;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.*;
import com.iwhalecloud.retail.goods2b.entity.Product;
import com.iwhalecloud.retail.goods2b.mapper.ProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    public Integer insert(Product req) {
        // 产品编码
        if (StringUtils.isNotBlank(req.getSn()) || StringUtils.isNotBlank(req.getUnitName())) {
            Boolean bothNotNull = StringUtils.isNotBlank(req.getSn()) && StringUtils.isNotBlank(req.getUnitName());
            ProductGetDuplicateReq dto = new ProductGetDuplicateReq();
            dto.setSn(req.getSn());
            dto.setUnitName(req.getUnitName());
            dto.setBothNotNull(bothNotNull);
            Integer num = productMapper.getDuplicate(dto);
            if(null != num && num > 0){
                throw new RetailTipException(ResultCodeEnum.ERROR.getCode(), "产品编码、营销资源名称不能重复");
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
     * 根据产品ID获取产品对象
     * @param productId 产品ID
     * @return
     */
    public ProductResp getProductInfo(String productId) {
        return productMapper.getProducts(productId);
    }

    /**
     * 根据产品编码获取产品对象
     * @param sn
     * @return
     */
    public ProductResp getProductBySn(String sn){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Product.FieldNames.sn.getFieldName(),sn);
        Product product = productMapper.selectOne(queryWrapper);
        ProductResp productResp = new ProductResp();
        if(product != null) {
            BeanUtils.copyProperties(product, productResp);
            return productResp;
        }
        return null;
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
     * 分页查询
     * @param req
     * @return
     */
    public Page<ProductPageResp> selectPageProductAdminAll(ProductsPageReq req) {
        Page<ProductPageResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        return productMapper.selectPageProductAdminAll(page, req);
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
    
    /**
     * 根据产品ID获取产品信息
     * @param queryProductInfoReqDTO
     * @return
     */
    public ProductPageResp getProductInfor(QueryProductInfoReqDTO queryProductInfoReqDTO){
        return productMapper.getProductInfor(queryProductInfoReqDTO);
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

    public int updateAttrValue10(ProductAuditStateUpdateReq req){
        return productMapper.updateAttrValue10(req);

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

    /**
     * 根据条件查询产品（返利使用）
     * @param req
     * @return
     */
    public List<ProductResp> getProductForRebate(ProductRebateReq req){
        return productMapper.getProductForRebate(req);
    }

    /**
     * 查询产品sn, is_fixed_line(对接营销资源用)
     * @param productId
     * @return
     */
    public ProductForResourceResp getProductForResource(String productId) {
        return productMapper.getProductForResource(productId);
    }

     /**
     * 根据产品名称或编码查询产品
     * @param request
     * @return
     */
    public Integer getDuplicate(ProductGetDuplicateReq request){
        return productMapper.getDuplicate(request);
    }

    /**
     * 根据产品Id列表查询产品信息
     * @param productIdList
     * @return
     */
    public List<ProductInfoResp> getProductInfoByIds( List<String> productIdList){
        return productMapper.getProductInfoByIds(productIdList);
    }
    /**
     * 根据产品Id更新产品状态
     * @param req
     * @return
     */
    public int updateAuditStateByProductId(ProductAuditReq req){
        return productMapper.updateAuditStateByProductId(req);

    }
}
