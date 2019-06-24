package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.BrandActivityReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandGetReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandPageReq;
import com.iwhalecloud.retail.goods2b.dto.req.BrandUpdateReq;
import com.iwhalecloud.retail.goods2b.dto.resp.BrandUrlResp;
import com.iwhalecloud.retail.goods2b.entity.Brand;
import com.iwhalecloud.retail.goods2b.mapper.BrandMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class BrandManager {
    @Resource
    private BrandMapper brandMapper;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    /**
     * 根据主键删除
     *
     * @param brandId
     * @return
     */
    public Integer deleteBrand(String brandId) {
        return brandMapper.deleteById(brandId);
    }


    /**
     * 添加品牌
     *
     * @param t
     * @return
     */
    public Integer addBrand(Brand t) {
        return brandMapper.insert(t);
    }

    /**
     * 部分更新
     *
     * @param record
     * @return
     */
    public Integer updateBrand(BrandUpdateReq record) {
        Brand t = new Brand();
        BeanUtils.copyProperties(record, t);
        Date now = new Date();
        t.setUpdateDate(now);
        return brandMapper.updateById(t);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<BrandUrlResp> listAll() {
        return brandMapper.listAll();
    }

    public Page<BrandUrlResp> getBrand(BrandGetReq req) {
        Page<BrandGetReq> page = new Page<BrandGetReq>(req.getPageNo(), req.getPageSize());
        return brandMapper.getBrand(page, req);
    }

    /**
     * 查询品牌图片
     *
     * @param brandIdList
     * @return
     */
    public List<BrandUrlResp> listBrandFileUrl(List brandIdList) {
        return brandMapper.listBrandFileUrl(brandIdList);
    }

    /**
     * 查询品牌图片
     *
     * @param req
     * @return
     */
    public Page<BrandUrlResp> pageBrandFileUrl(BrandPageReq req) {
        Page<BrandUrlResp> page =  new Page<BrandUrlResp>(req.getPageNo(), req.getPageSize());
        Page<BrandUrlResp> respPage = brandMapper.pageBrandFileUrl(page, req);
        return respPage;
    }

    /**
     * 查询品牌列表
     *
     * @param brandIdList
     * @return
     */
    public List<Brand> listBrand(List brandIdList) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in(Brand.FieldNames.brandId.getTableFieldName(), brandIdList);
        queryWrapper.eq("is_deleted", GoodsConst.NO_DELETE);
        return brandMapper.selectList(queryWrapper);
    }

    /**
     * 查询分类品牌
     *
     * @param catId
     * @return
     */
    public List<BrandUrlResp> listBrandByCatId(String catId) {
        return brandMapper.listBrandByCatId(catId);
    }

    public Page<ActivityGoodsDTO> listBrandActivityGoodsId(BrandActivityReq req) {
        Page<BrandActivityReq> page = new Page<BrandActivityReq>(req.getPageNo(), req.getPageSize());
        Page<ActivityGoodsDTO> activityGoodsDTOPage = brandMapper.listBrandActivityGoodsId(page, req);
        List<ActivityGoodsDTO> activityGoodsDTOs = activityGoodsDTOPage.getRecords();
        if (CollectionUtils.isNotEmpty(activityGoodsDTOs)) {
            for (ActivityGoodsDTO activityGoodsDTO : activityGoodsDTOs) {
                String imageUrl = activityGoodsDTO.getImageUrl();
                if (StringUtils.isNotEmpty(imageUrl)) {
                    activityGoodsDTO.setImageUrl(dfsShowIp + imageUrl);
                }
            }
        }
        return activityGoodsDTOPage;
    }

    public Brand getBrandByBrandId(String brandId) {
        return brandMapper.selectById(brandId);
    }
}
