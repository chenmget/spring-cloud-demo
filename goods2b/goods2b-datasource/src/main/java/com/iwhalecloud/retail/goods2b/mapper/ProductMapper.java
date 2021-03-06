package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.*;
import com.iwhalecloud.retail.goods2b.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: ProdProductMapper
 * @author autoCreate
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product>{

    /**
     * 条件分页查询,2B
     * @param page
     * @param request
     * @return
     */
    Page<ProductPageResp> selectPageProductAdmin(Page<ProductPageResp> page, @Param("pageReq")ProductsPageReq request);

    /**
     * 条件分页查询,2B
     * @param page
     * @param request
     * @return
     */
    Page<ProductPageResp> selectPageProductAdminAll(Page<ProductPageResp> page, @Param("pageReq")ProductsPageReq request);

    /**
     * 条件分页查询
     * @param page
     * @param request
     * @return
     */
    Page<ProductDTO> selectProduct(Page<ProductGetReq> page, @Param("req")ProductGetReq request);

    /**
     * 查询产品Id
     * @param req
     * @return
     */
    public List<ProductResourceResp> getProductResource(ProductResourceInstGetReq req);

    /**
     * 根据IdId
     * @param productId
     * @return
     */
    public String getMerChantByProduct(@Param("productId")String productId);

    /**
     * 根据IdId
     * @param productId
     * @return
     */
    public ProductResp getProduct(@Param("productId")String productId);
    
    /**
     * 根据IdId
     * @param productId
     * @return
     */
    public ProductResp getProducts(@Param("productId")String productId);
    
    /**
     * 根据厂商ID获取产品数量
     * @param manufacturerId
     * @return
     */
    public int getProductCountByManufId(@Param("manufacturerId") String manufacturerId);

    /**
     * 根据产品id查询产品信息
     * @param queryProductInfoReqDTO
     * @return
     */
     ProductPageResp getProductInfo(QueryProductInfoReqDTO queryProductInfoReqDTO);
     
     /**
      * 根据产品id查询产品信息
      * @param queryProductInfoReqDTO
      * @return
      */
     ProductPageResp getProductInfor(QueryProductInfoReqDTO queryProductInfoReqDTO);
      
    /**
     * 根据产品名称或编码查询产品
     * @param request
     * @return
     */
    public Integer getDuplicate(ProductGetDuplicateReq request);


    public Integer updateAuditStateByProductBaseId(ProductAuditStateUpdateReq request);
    /**
     * 权限过滤用
     * @param req
     * @return
     */
    public List<ProductResp> getProductByProductIdsAndBrandIds(ProductAndBrandGetReq req);

    /**
     * 根据productBaseId查询
     * @param productBaseId
     * @return
     */
    List<String> listProduct(@Param("productBaseId")String productBaseId);

    /**
     * 查询产品(返利使用)
     * @param req
     * @return
     */
    List<ProductResp> getProductForRebate(@Param("req")ProductRebateReq req);

    /**
     * 查询产品sn, is_fixed_line(对接营销资源用)
     * @param productId
     * @return
     */
    ProductForResourceResp getProductForResource(String productId);

    Integer updateAttrValue10(ProductAuditStateUpdateReq request);

    /**
     * 查询产品信息列表根据产品id
     * @param productIds
     * @return
     */

    List<ProductInfoResp> getProductInfoByIds(@Param("productIds")List<String>productIds);


    String selectNextChangeId();
    
    String selectNextChangeDetailId();
    
    String selectisFixedLineByBatchId(String batchId);

    /**
     * 根据条件查询产品ID集合（单表查询）
     * @param req
     * @return
     */
    List<String> listProductId(@Param("req")ProductListReq req);

    ProductApplyInfoResp getProductApplyInfo(@Param("productId")String productId);

    List<String> getProductIdListForApply(@Param("req") ProductGetIdReq req);

    List<ProductApplyInfoResp> getDeliveryInfo(@Param("productIds") List<String> productIds);

}