package com.iwhalecloud.retail.goods2b.service.dubbo;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ProductDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.*;

import java.util.List;

public interface ProductService {

    /**
     * 根据产品ID获取归属商家
     * @param req 产品ID
     * @return 产品对象
     */
    ResultVO<String> getMerchantByProduct(MerChantGetProductReq req);

    /**
     * 根据产品ID获取产品对象
     * @param req 产品ID
     * @return 产品对象
     */
    ResultVO<ProductResp> getProduct(ProductGetByIdReq req);

    /**
     * 根据产品ID获取产品对象
     * @param req 产品ID
     * @return 产品对象
     */
    ResultVO<ProductResp> getProductInfo(ProductGetByIdReq req);
    
    /**
     * 根据产品编码获取产品对象
     * @param sn
     * @return
     */
    ResultVO<ProductResp> getProductBySn(String sn);


    /**
     * 添加产品
     * @param req
     * @return
     */
    public String addProduct (ProductAddReq req);

//    /**
//     * 根据产品编码获取产品对象
//     * @param sn
//     * @return
//     */
//    ResultVO<ProductResp> getProductBySn(String sn);

    /**
     * 添加产品-中台
     * @param req
     * @return
     */
    public ResultVO<String> addProductByZT (ProductAddReq req);

    /**
     * 根据productId删除
     * @param req
     * @return
     */
    public ResultVO<Integer> deleteProdProduct(PrdoProductDeleteReq req);

    /**
     * 根据productId删除
     * @param req
     * @return
     */
    public ResultVO<Integer> batchDeleteProdProduct(List<PrdoProductDeleteReq> req);

    /**
     * 根据productId软删除
     * @param req
     * @return
     */
    public ResultVO<Integer> updateProdProductDelete(PrdoProductDeleteReq req);

    /**
     * 更新
     * @param req
     * @return
     */
    public ResultVO<Integer> updateProdProduct(ProductUpdateReq req);

    /**
     * 更新
     * @param req
     * @return
     */
    public ResultVO<Integer> batchUpdateProdProduct(List<ProductUpdateReq> req);

    /**
     * 通用查询
     * @param req
     * @return
     */
    public ResultVO<Page<ProductDTO>> selectProduct(ProductGetReq req);

    /**
     * 条件分页查询
     * @param req
     * @return
     */
    ResultVO<Page<ProductPageResp>> selectPageProductAdmin(ProductsPageReq req);
    
    /**
     * 条件分页查询(政企价格导出)
     * @param req
     * @return
     */
    ResultVO<Page<ProductPageResp>> selectPageProductAdminDc(ProductsPageReq req);

    /**
     * 条件分页查询-All
     * @param req
     * @return
     */
    ResultVO<Page<ProductPageResp>> selectPageProductAdminAll(ProductsPageReq req);

    /**
     * 查询产品Id
     * @param req
     * @return
     */
    ResultVO<List<ProductResourceResp>> getProductResource(ProductResourceInstGetReq req);

    /**
     * 通过厂商ID查询产品数量
     * @param req
     * @return
     */
    ResultVO<Integer> getProductCountByManufId(ProductCountGetReq req);

    /**
     * 根据产品ID列表获取产品对象列表
     * @param req 产品ID列表
     * @return 产品对象列表
     */
    ResultVO<List<ProductDTO>> getProductListByIds(ProductListGetByIdsReq req);

    /**
     *查询产品信息
     * @param queryProductInfoReqDTO
     * @return
     */
    ResultVO<QueryProductInfoResqDTO> getProductInfo(QueryProductInfoReqDTO queryProductInfoReqDTO);

    /**
     *查询产品信息
     * @param queryProductInfoReqDTO
     * @return
     */
    ResultVO<QueryProductInfoResqDTO> getProductInfor(QueryProductInfoReqDTO queryProductInfoReqDTO);

    
    /**
     * 添加产品标签
     * @param req
     * @return
     */
    ResultVO<Boolean> addProductTags(ProductTagsAddReq req);

    /**
     * 修改产品状态
     * @param req
     * @return
     */
    ResultVO updateAuditState(ProductAuditStateUpdateReq req);

    ResultVO updateAttrValue10(ProductAuditStateUpdateReq req);

//    /**
//     * 审核通过，事务控制:
//     * 1.修改产品状态为审核通过
//     * 2.调用营销资源接口进行同步，写在后面
//     * @param updateAuditStateReq
//     * @return
//     * @throws BusinessException
//     */
//    ResultVO<ProductOperateResp> auditPass(UpdateProductAuditStateReq updateAuditStateReq)throws BusinessException;

    /**
     * 权限过滤用
     * @param req
     * @return
     */
    ResultVO<List<ProductResp>> getProductByProductIdsAndBrandIds(ProductAndBrandGetReq req);

    /**
     * 返利使用
     * @param req
     * @return
     */
    ResultVO<List<ProductResp>> getProductForRebate(ProductRebateReq req);

    /**
     * 查询产品sn, is_fixed_line(对接营销资源用)
     * @param req
     * @return
     */
    ResultVO<ProductForResourceResp> getProductForResource(ProductGetByIdReq req);

    /**
     * 根据产品名称或编码查询产品
     * @param req
     * @return
     */
    ResultVO<Integer> getDuplicate (ProductGetDuplicateReq req);

    /**
     * 根据产品id列表查询产品信息
     * @param productIdList
     * @return
     */
    List<ProductInfoResp> getProductInfoByIds( List<String> productIdList);

    String selectNextChangeId();

    String selectNextChangeDetailId();

    String selectisFixedLineByBatchId(String batchId);

    /**
     * 根据条件查询产品ID集合（单表查询）
     * @param req
     * @return
     */
    ResultVO<List<String>> listProductId(ProductListReq req);
    public ProductApplyInfoResp getProductApplyInfo(String productId);

    public List<String> getProductIdListForApply(ProductGetIdReq req);


}