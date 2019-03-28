package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.common.GoodsResultCodeEnum;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.ActivityGoodsDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.BrandUrlResp;
import com.iwhalecloud.retail.goods2b.entity.Brand;
import com.iwhalecloud.retail.goods2b.entity.ProdFile;
import com.iwhalecloud.retail.goods2b.manager.BrandManager;
import com.iwhalecloud.retail.goods2b.manager.ProdFileManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.BrandService;
import com.iwhalecloud.retail.goods2b.utils.FastDFSImgStrJoinUtil;
import com.iwhalecloud.retail.partner.dto.MerchantDTO;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandManager brandManager;
    @Autowired
    private ProdFileManager fileManager;
    @Value("${fdfs.showUrl}")
    private String dfsShowIp;
    @Reference
    private MerchantRulesService merchantRulesService;
    @Reference
    private MerchantService merchantService;

    @Override
    public ResultVO<Page<BrandUrlResp>> getBrand(BrandGetReq req) {
        return ResultVO.success(brandManager.getBrand(req));
    }

    @Override
    public ResultVO<List<BrandUrlResp>> listAll() {
        return ResultVO.success(brandManager.listAll());
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO<Integer> addBrand(BrandAddReq req) {
        Brand t = new Brand();
        BeanUtils.copyProperties(req, t);
        Date now = new Date();
        t.setCreateDate(now);
        t.setUpdateDate(now);
        t.setIsDeleted(ProductConst.IsDelete.NO.getCode());
        Integer num = brandManager.addBrand(t);

        String fileUrl = req.getFileUrl();
        String targetId = t.getBrandId();
        String targetType = FileConst.TargetType.BRAND_TARGET.getType();
        String subType = FileConst.SubType.DEFAULT_SUB.getType();
        ProdFile file = new ProdFile();
        file.setTargetId(targetId);
        file.setTargetType(targetType);
        file.setFileUrl(fileUrl);
        file.setSubType(subType);

        fileManager.addFile(file);
        return ResultVO.success(num);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO<Integer> updateBrand(BrandUpdateReq req) {
        String fileUrl = req.getFileUrl();
        String targetId = req.getBrandId();

        if (StringUtils.isNotBlank(fileUrl)){
            String targetType = FileConst.TargetType.BRAND_TARGET.getType();
            String subType = FileConst.SubType.DEFAULT_SUB.getType();
            Integer num = fileManager.deleteFile(targetId, targetType, subType);
            ProdFile file = new ProdFile();
            file.setTargetId(targetId);
            file.setTargetType(targetType);
            file.setFileUrl(fileUrl);
            file.setSubType(subType);
            Integer num2 = fileManager.addFile(file);
        }
        return ResultVO.success(brandManager.updateBrand(req));
    }

    @Override
    public ResultVO<Integer> deleteBrand(BrandQueryReq brandQueryReq) {
        String brandId = brandQueryReq.getBrandId();
		return ResultVO.success(brandManager.deleteBrand(brandId));
    }

	@Override
	public ResultVO<List<BrandUrlResp>> listBrandFileUrl(BrandQueryReq brandQueryReq){
        List brandIdList = brandQueryReq.getBrandIdList();
        List<BrandUrlResp> list = brandManager.listBrandFileUrl(brandIdList);
        if (null != list && !list.isEmpty()) {
            for(BrandUrlResp resp:list){
                if(StringUtils.isEmpty(resp.getFileUrl())){
                    continue;
                }
                String newImageFile = FastDFSImgStrJoinUtil.fullImageUrl(resp.getFileUrl(), dfsShowIp, true);
                resp.setFileUrl(newImageFile);
            }
        }
        return ResultVO.success(list);
	}

    @Override
	public ResultVO<List<BrandUrlResp>> listBrandByCatId( BrandQueryReq brandQueryReq){
        String catId = brandQueryReq.getCatId();
		return ResultVO.success(brandManager.listBrandByCatId(catId));
	}

    @Override
    public ResultVO<Page<ActivityGoodsDTO>> listBrandActivityGoodsId(BrandActivityReq brandActivityReq) {
//        List<ActivityGoodsDTO> activityGoodsDTOs = new ArrayList<>();
        Page<ActivityGoodsDTO> activityGoodsDTOPage = new Page<ActivityGoodsDTO>(brandActivityReq.getPageNo(), brandActivityReq.getPageSize());
        if(null == brandActivityReq){
            return ResultVO.successMessage("入参不能为空");
        }
        String regionId = brandActivityReq.getRegionId();
        String brandId = brandActivityReq.getBrandId();
        String supplierId = brandActivityReq.getSupplierId();
        String lanId = brandActivityReq.getLanId();
        log.info("BrandServiceImpl listBrandActivityGoodsId req={} ", brandActivityReq);
        if(StringUtils.isEmpty(brandId)){
            return  ResultVO.successMessage("入参品牌ID为空");
        }
        List<String> productIdList = new ArrayList<>();
        try{
            ResultVO<List<String>> listResultVO = merchantRulesService.getBusinessModelPermission(supplierId);
//            if(listResultVO.isSuccess()){
//                productIdList = listResultVO.getResultData();
//            }
            productIdList = listResultVO.getResultData();
        }catch (Exception ex) {
            log.error("BrandServiceImpl.listBrandActivityGoodsId merchantRulesService.getBusinessModelPermission throw exception ex={}", ex);
            
//            return ResultVO.error(GoodsResultCodeEnum.INVOKE_PARTNER_SERVICE_EXCEPTION);
        }
        brandActivityReq.setProductIdList(productIdList);
        activityGoodsDTOPage = brandManager.listBrandActivityGoodsId(brandActivityReq);
        List<ActivityGoodsDTO> activityGoodsDTOs = activityGoodsDTOPage.getRecords();
        if(CollectionUtils.isEmpty(activityGoodsDTOs)){
            return ResultVO.successMessage("根据入参区域ID和品牌ID没有查到数据");
        }
        for(ActivityGoodsDTO activityGoodsDTO:activityGoodsDTOs){
            if(StringUtils.isNotEmpty(activityGoodsDTO.getSupplierId())) {
                try {
                    ResultVO<MerchantDTO> merchantDTOResultVO = merchantService.getMerchantById(activityGoodsDTO.getSupplierId());
                    if (merchantDTOResultVO.isSuccess() && merchantDTOResultVO.getResultData() != null) {
                        MerchantDTO merchantDTO = merchantDTOResultVO.getResultData();
                        activityGoodsDTO.setSupplierName(merchantDTO.getMerchantName());
                    }
                } catch (Exception ex) {
                    log.error("BrandServiceImpl.listBrandActivityGoodsId getMerchantById throw exception ex={}", ex);
                    return ResultVO.error(GoodsResultCodeEnum.INVOKE_PARTNER_SERVICE_EXCEPTION);
                }
            }
        }

        return ResultVO.success(activityGoodsDTOPage);
    }

    @Override
    public ResultVO<BrandUrlResp> getBrandByBrandId(String brandId){
        BrandUrlResp brandUrlResp = new BrandUrlResp();
        Brand brand = brandManager.getBrandByBrandId(brandId);
        BeanUtils.copyProperties(brand,brandUrlResp);
        return ResultVO.success(brandUrlResp);
    }
}