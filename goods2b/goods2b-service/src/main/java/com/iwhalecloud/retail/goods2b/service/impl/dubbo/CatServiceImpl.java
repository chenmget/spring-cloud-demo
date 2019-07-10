package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.CatComplexConst;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.CatComplexDTO;
import com.iwhalecloud.retail.goods2b.dto.CatConditionDTO;
import com.iwhalecloud.retail.goods2b.dto.CatDTO;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.CatListResp;
import com.iwhalecloud.retail.goods2b.dto.resp.CatResp;
import com.iwhalecloud.retail.goods2b.dto.resp.GoodsPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.entity.Brand;
import com.iwhalecloud.retail.goods2b.entity.Cat;
import com.iwhalecloud.retail.goods2b.entity.CatComplex;
import com.iwhalecloud.retail.goods2b.entity.Goods;
import com.iwhalecloud.retail.goods2b.manager.*;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatConditionService;
import com.iwhalecloud.retail.goods2b.service.dubbo.CatService;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class CatServiceImpl implements CatService {

    @Autowired
    private CatManager catManager;
    @Autowired
    private ProdFileManager prodFileManager;
    @Autowired
    private BrandManager brandManager;
    @Autowired
    private GoodsManager goodsManager;
    @Autowired
    private CatComplexManager catComplexManager;
    @Autowired
    private CatConditionManager catConditionManager;
    @Reference
    private ProductService productService;

    @Reference
    private CatConditionService catConditionService;

    private static final String OPERATION_TYPE = "update";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ResultVO<Boolean> addProdCat(CatAddReq req) {

        String catId = catManager.addProdcat(req);
        log.info("CatServiceImpl addProdCat catId={}", catId);
        String catPath = "";
        if (GoodsConst.ROOT_DIR.equals(req.getParentCatId())) {
            catPath += GoodsConst.ROOT_DIR + "|";
        } else {
            String parentCatId = req.getParentCatId();
            Cat cat = catManager.queryProdCat(parentCatId);
            catPath += cat.getCatPath();
        }
        catPath += catId + "|";
        CatUpdateReq catUpdateReq = new CatUpdateReq();
        catUpdateReq.setCatId(catId);
        catUpdateReq.setCatPath(catPath);
        catManager.updateProdcat(catUpdateReq);
        log.info("CatServiceImpl addProdCat catId={},catPath={}", catId, catPath);
        if (StringUtils.isEmpty(catId)) {
            return ResultVO.error("新增类别失败");
        }
        int num = prodFileManager.addGoodsImage(catId, FileConst.SubType.CAT_SUB, req.getCatImgPath());
        log.info("CatServiceImpl addProdCat addGoodsImage num={}", num);
        //批量新增关联商品和品牌
        batchAddCatComplex("add", catId, req.getBrandIds(), req.getGoodsIds());

        // 新增分类条件
        batchAddCatCondition(catId, req.getCreateStaff(), req.getCatConditionList());

        if (num < 1) {
            return ResultVO.error("新增类别图片失败");
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<String> addProdCatByZT(CatAddReq req) {
        String catId = catManager.addProdcat(req);
        log.info("CatServiceImpl addProdCatByZT catId={}", catId);
        String catPath = "";
        if (GoodsConst.ROOT_DIR.equals(req.getParentCatId())) {
            catPath += GoodsConst.ROOT_DIR + "|";
        } else {
            String parentCatId = req.getParentCatId();
            Cat cat = catManager.queryProdCat(parentCatId);
            catPath += cat.getCatPath();
        }
        catPath += catId + "|";
        CatUpdateReq catUpdateReq = new CatUpdateReq();
        catUpdateReq.setCatId(catId);
        catUpdateReq.setCatPath(catPath);
        catManager.updateProdcat(catUpdateReq);
        log.info("CatServiceImpl addProdCatByZT catId={},catPath={}", catId, catPath);
        if (StringUtils.isEmpty(catId)) {
            return ResultVO.error("新增类别失败");
        }
        if (!StringUtils.isEmpty(req.getCatImgPath())) {
            int num = prodFileManager.addGoodsImage(catId, FileConst.SubType.CAT_SUB, req.getCatImgPath());
            log.info("CatServiceImpl addProdCatByZT addGoodsImage num={}", num);
            if (num < 1) {
                return ResultVO.error("新增类别图片失败");
            }
        }

        //批量新增关联商品和品牌
        batchAddCatComplex("add", catId, req.getBrandIds(), req.getGoodsIds());
        return ResultVO.success(catId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultVO<Boolean> updateProdCat(CatUpdateReq req) {
        String operation = "update";
        String catId = req.getCatId();
        String catPath = "";
        if (GoodsConst.ROOT_DIR.equals(req.getParentCatId())) {
            catPath += GoodsConst.ROOT_DIR + "|";
        } else {
            String parentCatId = req.getParentCatId();
            Cat cat = catManager.queryProdCat(parentCatId);
            catPath += cat.getCatPath();
        }
        catPath += catId + "|";
        req.setCatPath(catPath);
        int num = catManager.updateProdcat(req);
        log.info("CatServiceImpl updateProdCat resultNum={}", JSON.toJSONString(num));
        prodFileManager.deleteByGoodsId(req.getCatId());
        prodFileManager.addGoodsImage(catId, FileConst.SubType.CAT_SUB, req.getCatImgPath());
        //先删除关联品牌 商品
        catComplexManager.delCatComplexByTargetId(catId, CatComplexConst.TargetType.CAT_BRAND_TARGET.getType());
        catComplexManager.delCatComplexByTargetId(catId, CatComplexConst.TargetType.CAT_RECOMMEND_TARGET.getType());
        //新增关联品牌 商品
        batchAddCatComplex(operation, catId, req.getBrandIds(), req.getGoodsIds());

        //先删除 分类条件
        CatConditionDeleteReq catConditionDeleteReq = new CatConditionDeleteReq();
        catConditionDeleteReq.setCatId(catId);
        catConditionService.deleteCatCondition(catConditionDeleteReq);
        // 新增分类条件
        batchAddCatCondition(catId, req.getUpdateStaff(), req.getCatConditionList());

        if (num < 1) {
            return ResultVO.error();
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<Boolean> updateProdCatByZT(CatUpdateReq req) {
        String operation = "update";
        String catId = req.getCatId();
        String catPath = "";
        if (StringUtils.isEmpty(req.getParentCatId())) {
            req.setParentCatId("-1");
        }
        if (GoodsConst.ROOT_DIR.equals(req.getParentCatId())) {
            catPath += GoodsConst.ROOT_DIR + "|";
        } else {
            String parentCatId = req.getParentCatId();
            Cat cat = catManager.queryProdCat(parentCatId);
            catPath += cat.getCatPath();
        }
        catPath += catId + "|";
        req.setCatPath(catPath);
        int num = catManager.updateProdcat(req);
        log.info("CatServiceImpl updateProdCatByZT resultNum={}", JSON.toJSONString(num));
        if (num < 1) {
            return ResultVO.error();
        }
        return ResultVO.success(true);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVO<Boolean> batchUpdateProdCat(CatQueryReq catQueryReq) {
        List<CatUpdateReq> updateReqList = catQueryReq.getUpdateReqList();
        for (CatUpdateReq catUpdateReq : updateReqList) {
            int num = catManager.updateProdcat(catUpdateReq);
            if (num < 1) {
                return ResultVO.error("修改失败");
            }
        }
        return ResultVO.success(true);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ResultVO<Boolean> deleteProdCat(CatQueryReq catQueryReq) {
        String catId = catQueryReq.getCatId();
        int num = catManager.deleteProdcat(catId);
        ProductsPageReq req = new ProductsPageReq();
        req.setCatId(catId);
        ResultVO<Page<ProductPageResp>> productVO = productService.selectPageProductAdmin(req);
        if (productVO.isSuccess() && productVO.getResultData() != null && CollectionUtils.isEmpty(productVO.getResultData().getRecords())
                && productVO.getResultData().getRecords().size() > 0) {
            return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "使用中，不允许删除");
        }

        GoodsPageReq goodsReq = new GoodsPageReq();
        goodsReq.setGoodsCatId(catId);
        Page<GoodsPageResp> goodsPage = goodsManager.queryPageByConditionAdmin(goodsReq);
        if (null != goodsPage && CollectionUtils.isEmpty(goodsPage.getRecords()) && goodsPage.getRecords().size() > 0) {
            return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "使用中，不允许删除");
        }

        log.info("CatServiceImpl deleteProdCat resultNum={}", JSON.toJSONString(num));
        //删除与类别关联的品牌 商品 图片
        String brandTargetType = CatComplexConst.TargetType.CAT_BRAND_TARGET.getType();
        //取品牌的id列表
        List<CatComplexDTO> catComplexDTOS = catComplexManager.queryCatComplexbyCatId(catId, brandTargetType);
        //取商品的id列表
        String goodsTargetType = CatComplexConst.TargetType.CAT_RECOMMEND_TARGET.getType();
        List<CatComplexDTO> catComplexList = catComplexManager.queryCatComplexbyCatId(catId, goodsTargetType);
        for (CatComplexDTO complexDTO : catComplexDTOS) {
            prodFileManager.deleteByGoodsSubType(complexDTO.getTargetId(), FileConst.SubType.CAT_SUB);
        }
        for (CatComplexDTO complexDTO : catComplexList) {
            prodFileManager.deleteByGoodsSubType(complexDTO.getTargetId(), FileConst.SubType.CAT_SUB);
        }
        //删除与类别关联的品牌 商品
        catComplexManager.delCatComplexByTargetId(catId, CatComplexConst.TargetType.CAT_BRAND_TARGET.getType());
        catComplexManager.delCatComplexByTargetId(catId, CatComplexConst.TargetType.CAT_RECOMMEND_TARGET.getType());

        //删除 分类条件
        CatConditionDeleteReq catConditionDeleteReq = new CatConditionDeleteReq();
        catConditionDeleteReq.setCatId(catId);
        catConditionService.deleteCatCondition(catConditionDeleteReq);

        if (num < 1) {
            return ResultVO.error();
        }
        return ResultVO.success(true);
    }

    @Override
    public ResultVO<IPage> listProdCatByCatName(CatQueryReq catQueryReq) {
        Long pageNum = catQueryReq.getPageNum();
        Long pageSize = catQueryReq.getPageSize();
        String catName = catQueryReq.getCatName();
        IPage<Cat> page = catManager.listProdcat(pageNum, pageSize, catName);
        log.warn("CatServiceImpl listProdCatByCatName page={}", JSON.toJSONString(page));
        if (null == page) {
            return ResultVO.successMessage("查询为空");
        }
        CatListResp resp = new CatListResp();
        List<CatDTO> prodCatList = Lists.newArrayList();
        for (Cat cat : page.getRecords()) {
            CatDTO prodCatDTO = new CatDTO();
            BeanUtils.copyProperties(cat, prodCatDTO);
            prodCatList.add(prodCatDTO);
        }
        resp.setProdCatList(prodCatList);
        return ResultVO.success(page);
    }

    @Override
    public ResultVO<CatResp> queryProdCat(CatQueryReq catQueryReq) {
        String catId = catQueryReq.getCatId();
        CatResp resp = new CatResp();
        List<CatResp.BrandResp> brandRespList = Lists.newArrayList();
        List<CatResp.GoodsResp> goodsRespList = Lists.newArrayList();
        List<CatConditionDTO> catConditionList = Lists.newArrayList();
        Cat cat = catManager.queryProdCat(catId);
        if (null == cat) {
            resp.setGoodsRespList(goodsRespList);
            resp.setBrandRespList(brandRespList);
            resp.setCatConditionList(catConditionList);
            return ResultVO.success(resp);
        }
        BeanUtils.copyProperties(cat, resp);
        List<ProdFileDTO> prodFileDTOS = prodFileManager.queryGoodsImage(catId, FileConst.SubType.CAT_SUB);
        log.info("CatServiceImpl.queryProdCat prodFileManager.queryGoodsImage-->prodFileDTOS={}", JSON.toJSONString(prodFileDTOS));
        if (!CollectionUtils.isEmpty(prodFileDTOS)) {
            resp.setCatImgPath(prodFileDTOS.get(0).getFileUrl());
        }
        String brandTargetType = CatComplexConst.TargetType.CAT_BRAND_TARGET.getType();
        //取品牌的id列表
        List<CatComplexDTO> catComplexDTOS = catComplexManager.queryCatComplexbyCatId(catId, brandTargetType);
        log.info("CatServiceImpl.queryProdCat catComplexManager.queryCatComplexbyCatId-->catComplexDTOS={}", JSON.toJSONString(catComplexDTOS));
        //取商品的id列表
        String goodsTargetType = CatComplexConst.TargetType.CAT_RECOMMEND_TARGET.getType();
        List<CatComplexDTO> catComplexList = catComplexManager.queryCatComplexbyCatId(catId, goodsTargetType);
        log.info("CatServiceImpl.queryProdCat catComplexManager.queryCatComplexbyCatId-->catComplexList={}", JSON.toJSONString(catComplexList));
        List<String> brandList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(catComplexDTOS)) {
            for (CatComplexDTO catComplexDTO : catComplexDTOS) {
                brandList.add(catComplexDTO.getTargetId());
            }
            //取品牌的数据
            List<Brand> respList = brandManager.listBrand(brandList);
            for (Brand brand : respList) {
                CatResp.BrandResp brandResp = resp.new BrandResp();
                brandResp.setBrandId(brand.getBrandId());
                brandResp.setBrandName(brand.getName());
                List<ProdFileDTO> imgPathList = prodFileManager.queryGoodsImage(brand.getBrandId(), FileConst.SubType.CAT_SUB);
                if (!CollectionUtils.isEmpty(imgPathList)) {
                    brandResp.setImgPath(imgPathList.get(0).getFileUrl());
                }
                brandRespList.add(brandResp);
            }
        }
        List<String> goodStrList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(catComplexList)) {
            for (CatComplexDTO catComplexDTO : catComplexList) {
                goodStrList.add(catComplexDTO.getTargetId());
            }
            // 取商品的数据
            List<Goods> goodsList = goodsManager.listGoods(goodStrList);
            for (Goods goods : goodsList) {
                CatResp.GoodsResp goodsResp = resp.new GoodsResp();
                goodsResp.setGoodsId(goods.getGoodsId());
                goodsResp.setGoodsName(goods.getGoodsName());
                List<ProdFileDTO> imgPathList = prodFileManager.queryGoodsImage(goods.getGoodsId(), FileConst.SubType.CAT_SUB);
                if (!CollectionUtils.isEmpty(imgPathList)) {
                    goodsResp.setImgPath(imgPathList.get(0).getFileUrl());
                }
                goodsRespList.add(goodsResp);
            }
        }

        // 获取分类条件关联列表
        CatConditionListReq catConditionListReq = new CatConditionListReq();
        catConditionListReq.setCatId(catId);
        catConditionList = catConditionManager.listCatCondition(catConditionListReq);

        resp.setCatConditionList(catConditionList);
        resp.setBrandRespList(brandRespList);
        resp.setGoodsRespList(goodsRespList);
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<CatListResp> queryCatList(CatQueryReq catQueryReq) {
        String parentCatId = catQueryReq.getParentCatId();
        String typeId = catQueryReq.getTypeId();
        CatListResp resp = new CatListResp();
        List<CatDTO> prodCatList = Lists.newArrayList();
        List<Cat> catList = catManager.catList(parentCatId, typeId);
        for (Cat cat : catList) {
            CatDTO prodCatDTO = new CatDTO();
            BeanUtils.copyProperties(cat, prodCatDTO);
            prodCatList.add(prodCatDTO);
        }
        resp.setProdCatList(prodCatList);
        return ResultVO.success(resp);
    }

    @Override
    public ResultVO<List<CatDTO>> listProdCatByIds(List<CatQueryReq> catQueryReqList) {
        if (catQueryReqList == null) {
            return ResultVO.error();
        }
        List<CatDTO> catDTOList = Lists.newArrayList();
        for (CatQueryReq catQueryReq : catQueryReqList) {
            String catId = catQueryReq.getCatId();
            Cat cat = catManager.queryProdCat(catId);
            if (cat == null) {
                continue;
            }
            CatDTO catDTO = new CatDTO();
            BeanUtils.copyProperties(cat, catDTO);
            catDTOList.add(catDTO);
        }

        return ResultVO.success(catDTOList);
    }


    /**
     * 批量新增关联品牌 商品
     *
     * @param catId
     * @param brandReqs
     * @param goodsReqs
     */
    public void batchAddCatComplex(String operation, String catId, List<CatAddReq.BrandReq> brandReqs, List<CatAddReq.GoodsReq> goodsReqs) {
        List<CatComplex> catComplexes = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(brandReqs)) {
            for (CatAddReq.BrandReq brandReq : brandReqs) {
                CatComplex catComplex = new CatComplex();
                String targetType = CatComplexConst.TargetType.CAT_BRAND_TARGET.getType();
                catComplex.setCatId(catId);
                catComplex.setTargetId(brandReq.getBrandId());
                catComplex.setTargetType(targetType);
                catComplex.setTargetName(brandReq.getBrandName());
                catComplex.setTargetOrder(brandReq.getTargetOrder());
                if (OPERATION_TYPE.equals(operation)) {
                    prodFileManager.deleteByGoodsSubType(brandReq.getBrandId(), FileConst.SubType.CAT_SUB);
                }
                prodFileManager.addGoodsImage(brandReq.getBrandId(), FileConst.SubType.CAT_SUB, brandReq.getBrandImgPath());
                catComplexes.add(catComplex);
            }
        }
        if (!CollectionUtils.isEmpty(goodsReqs)) {
            for (CatAddReq.GoodsReq goodsReq : goodsReqs) {
                CatComplex catComplex = new CatComplex();
                String targetType = CatComplexConst.TargetType.CAT_RECOMMEND_TARGET.getType();
                catComplex.setCatId(catId);
                catComplex.setTargetId(goodsReq.getGoodsId());
                catComplex.setTargetType(targetType);
                catComplex.setTargetName(goodsReq.getGoodsName());
                catComplex.setTargetOrder(goodsReq.getTargetOrder());
                if (OPERATION_TYPE.equals(operation)) {
                    prodFileManager.deleteByGoodsSubType(goodsReq.getGoodsId(), FileConst.SubType.CAT_SUB);
                }
                prodFileManager.addGoodsImage(goodsReq.getGoodsId(), FileConst.SubType.CAT_SUB, goodsReq.getGoodsImgPath());
                catComplexes.add(catComplex);
            }
        }
        if (!CollectionUtils.isEmpty(catComplexes)) {
            catComplexManager.addCatComplex(catComplexes);
        }
    }


    /**
     * 批量新增 分类条件 关联
     *
     * @param catId
     * @param createStaff
     * @param catConditionSaveReqList
     */
    public void batchAddCatCondition(String catId, String createStaff, List<CatConditionSaveReq> catConditionSaveReqList) {
        if (CollectionUtils.isEmpty(catConditionSaveReqList)) {
            return;
        }
        for (CatConditionSaveReq saveReq : catConditionSaveReqList) {
            saveReq.setCatId(catId);
            saveReq.setCreateStaff(createStaff);
            catConditionService.saveCatCondition(saveReq);
        }
    }
}