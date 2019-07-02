package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.dto.TagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseListReq;
import com.iwhalecloud.retail.goods2b.dto.req.ProductBaseUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductDetailResp;
import com.iwhalecloud.retail.goods2b.entity.ProductBase;
import com.iwhalecloud.retail.goods2b.mapper.ProductBaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class ProductBaseManager {
    @Resource
    private ProductBaseMapper productBaseMapper;

    @Resource
    private TagRelManager tagRelManager;

    /**
     * 根据产品基本信息ID获取产品产品基本信息对象
     * @param productBaseId 产品ID
     * @return 产品对象
     */
    public ProductBaseGetResp getProductBase(String productBaseId){
        ProductBase resp = productBaseMapper.getProductBase(productBaseId);
        if(null == resp){
            return null;
        }

        ProductBaseGetResp dto = new ProductBaseGetResp();
        BeanUtils.copyProperties(resp, dto);
        List<TagRelDTO> tagRelDTOs = tagRelManager.listTagByProductBaseId(productBaseId);
        List<String> tagList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(tagRelDTOs)){
            for(TagRelDTO tagRelDTO:tagRelDTOs){
                tagList.add(tagRelDTO.getTagId());
            }
        }
        dto.setTagList(tagList);
        return dto;
    }

    /**
     * 获取产品产品基本信息列表
     * @return
     */
    public Page<ProductBaseGetResp> getProductBaseList(ProductBaseListReq req){
        Page<ProductBaseGetResp> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ProductBaseGetResp> pageResp = productBaseMapper.getProductBaseList(page, req);
        List<ProductBaseGetResp> respList = pageResp.getRecords();
        for(ProductBaseGetResp dto : respList){
            String productBaseId = dto.getProductBaseId();
            if(StringUtils.isNotEmpty(productBaseId)){
                List<TagRelDTO> tagRelDTOs = tagRelManager.listTagByProductBaseId(productBaseId);
                List<String> tagList = new ArrayList<>();
                if(!CollectionUtils.isEmpty(tagRelDTOs)){
                    for(TagRelDTO tagRelDTO:tagRelDTOs){
                        tagList.add(tagRelDTO.getTagId());
                    }
                }
                dto.setTagList(tagList);
            }
        }

        return pageResp;
    }

    /**
     * 添加产品基本信息列表
     * @param t
     * @return
     */
    public Integer addProductBase(ProductBase t){
        return productBaseMapper.insert(t);
    }

    /**
     * 更新
     * @param req
     * @return
     */
    public Integer updateProductBase(ProductBaseUpdateReq req){
        ProductBase t = new ProductBase();
        t.setUpdateDate(new Date());
        BeanUtils.copyProperties(req, t);


        return productBaseMapper.updateById(t);
    }

    /**
     * 删除
     * @param productBaseId
     * @return
     */
    public Integer deleteProdProductBase(String productBaseId){
        return productBaseMapper.deleteById(productBaseId);
    }

    /**
     * 软删除
     * @param productBaseId
     * @return
     */
    public Integer softDelProdProductBase(String productBaseId){
        return productBaseMapper.softDelProdProductBase(productBaseId);
    }

    /**
     * 通用查询
     * @param req
     * @return
     */
    public List<ProductBaseGetResp> selectProductBase(ProductBaseGetReq req) {
        QueryWrapper<ProductBase> queryWrapper = new QueryWrapper<>();
        Boolean hasParam = false;
        if(StringUtils.isNotBlank(req.getProductBaseId())){
            hasParam = true;
            queryWrapper.eq("PRODUCT_BASE_ID", req.getProductBaseId());
        }
        if(StringUtils.isNotBlank(req.getTypeId())){
            hasParam = true;
            queryWrapper.eq("TYPE_ID", req.getTypeId());
        }
        if(StringUtils.isNotBlank(req.getCatId())){
            hasParam = true;
            queryWrapper.eq("CAT_ID", req.getCatId());
        }
        if(StringUtils.isNotBlank(req.getBrandId())){
            hasParam = true;
            queryWrapper.eq("BRAND_ID", req.getBrandId());
        }
        if(StringUtils.isNotBlank(req.getProductName())){
            hasParam = true;
            queryWrapper.like("PRODUCT_NAME", req.getProductName());
        }
        if(StringUtils.isNotBlank(req.getUnitType())){
            hasParam = true;
            queryWrapper.eq("UNIT_TYPE", req.getUnitType());
        }
        if(StringUtils.isNotBlank(req.getUnitTypeName())){
            hasParam = true;
            queryWrapper.eq("UNIT_TYPE_NAME", req.getUnitTypeName());
        }
        if(StringUtils.isNotBlank(req.getIsImei())){
            hasParam = true;
            queryWrapper.eq("IS_IMEI", req.getIsImei());
        }
        if(StringUtils.isNotBlank(req.getIsItms())){
            hasParam = true;
            queryWrapper.eq("IS_ITMS", req.getIsItms());
        }
        if(StringUtils.isNotBlank(req.getIsCtCode())){
            hasParam = true;
            queryWrapper.eq("IS_CT_CODE", req.getIsCtCode());
        }
        if(StringUtils.isNotBlank(req.getIsFixedLine())){
            hasParam = true;
            queryWrapper.eq("IS_FIXED_LINE", req.getIsFixedLine());
        }
        if(StringUtils.isNotBlank(req.getProductCode())){
            hasParam = true;
            queryWrapper.eq("PRODUCT_CODE", req.getProductCode());
        }
        if(StringUtils.isNotBlank(req.getIsDeleted())){
            hasParam = true;
            queryWrapper.eq("is_deleted", req.getIsDeleted());
        }
        if(StringUtils.isNotBlank(req.getManufacturerId())){
            hasParam = true;
            queryWrapper.eq("manufacturer_id", req.getManufacturerId());
        }
        if(StringUtils.isNotBlank(req.getSallingPoint())){
            hasParam = true;
            queryWrapper.eq("salling_point", req.getSallingPoint());
        }

        if ( !hasParam ){
            return null;
        }
        List<ProductBase> selectList = productBaseMapper.selectList(queryWrapper);

        if ( null == selectList || selectList.isEmpty() ){
            return null;
        }

        List dtoList = new ArrayList<ProductBaseGetResp>(selectList.size());
        for (ProductBase t : selectList){

            ProductBaseGetResp dto = new ProductBaseGetResp();
            BeanUtils.copyProperties(t,dto);
            List<TagRelDTO> tagRelDTOs = tagRelManager.listTagByProductBaseId(t.getProductBaseId());
            List<String> tagList = new ArrayList<>();
            if(!CollectionUtils.isEmpty(tagRelDTOs)){
                for(TagRelDTO tagRelDTO:tagRelDTOs){
                    tagList.add(tagRelDTO.getTagId());
                }
            }
            dto.setTagList(tagList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    /**
     * 产品详情
     * @param productBaseId
     * @return
     */
    public ProductDetailResp getProductDetail(String productBaseId){
        ProductDetailResp productDetailResp = productBaseMapper.getProductDetail(productBaseId);
//        List<TagRelDTO> tagRelDTOs = tagRelManager.listTagByProductBaseId(productBaseId);
//        List<String> tagList = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(tagRelDTOs)){
//            for(TagRelDTO tagRelDTO:tagRelDTOs){
//                tagList.add(tagRelDTO.getTagId());
//            }
//        }
//        //增加判空
//        if(!CollectionUtils.isEmpty(tagList) && null!=productDetailResp){
//            productDetailResp.setTagList(tagList);
//        }
        return productDetailResp;
    }

    /**
     * (估计已废弃）
     * 根据产品基本信息id查询省包平均供货价
     * @param productBaseId
     * @return
     */
    public Double getAvgSupplyPrice(String productBaseId) {
        return productBaseMapper.getAvgSupplyPrice(productBaseId);
    }
    
    public String selectisFixedLineByBatchId(String batchId) {
		return productBaseMapper.selectisFixedLineByBatchId(batchId);
	}

    public String getSeq(){return productBaseMapper.getSeq(); }
}
