package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.AttrSpecConst;
import com.iwhalecloud.retail.goods2b.common.FileConst;
import com.iwhalecloud.retail.goods2b.common.TypeConst;
import com.iwhalecloud.retail.goods2b.dto.AttrSpecDTO;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.dto.TypeDTO;
import com.iwhalecloud.retail.goods2b.dto.req.*;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductPageResp;
import com.iwhalecloud.retail.goods2b.dto.resp.TypeDetailResp;
import com.iwhalecloud.retail.goods2b.entity.Brand;
import com.iwhalecloud.retail.goods2b.entity.ProdTypeComplex;
import com.iwhalecloud.retail.goods2b.entity.Type;
import com.iwhalecloud.retail.goods2b.manager.AttrSpecManager;
import com.iwhalecloud.retail.goods2b.manager.BrandManager;
import com.iwhalecloud.retail.goods2b.manager.ProdFileManager;
import com.iwhalecloud.retail.goods2b.manager.TypeManager;
import com.iwhalecloud.retail.goods2b.service.dubbo.ProductService;
import com.iwhalecloud.retail.goods2b.service.dubbo.TypeService;
import com.iwhalecloud.retail.goods2b.utils.FastDFSImgStrJoinUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author chengxu
 */
@Service
@Slf4j
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeManager typeManager;

    @Autowired
    private AttrSpecManager attrSpecManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandManager brandManager;

    @Autowired
    private ProdFileManager prodFileManager;

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    @Value("${type.moblie}")
    private String moblie;

    @Value("${type.router}")
    private String router;

    @Value("${type.intelligentTermina}")
    private String intelligentTermina;

    @Value("${type.fusionTerminal}")
    private String fusionTerminal;

    @Value("${type.setTopBox}")
    private String setTopBox;

    @Value("${type.opticalModem}")
    private String opticalModem;

    @Override
    @Transactional
    public ResultVO saveType(TypeDTO typeDTO) {
        typeDTO.setCreateDate(new Date());
        String typeId = typeManager.saveType(typeDTO);
        if(!ResultCodeEnum.SUCCESS.getCode().equals(typeId)){
            saveAttrSpec(typeDTO, typeId);
            return ResultVO.success(typeId);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);

        }
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO updateType(TypeDTO typeDTO) {
        typeDTO.setUpdateDate(new Date());
        String typeId = typeDTO.getTypeId();
        int ret = typeManager.updateType(typeDTO);
        TypeIsUsedQueryByIdReq typeIsUsedQueryByIdReq = new TypeIsUsedQueryByIdReq();
        typeIsUsedQueryByIdReq.setTypeId(typeId);
        ResultVO<Boolean> isUsedVO = this.typeIsUsed(typeIsUsedQueryByIdReq);
        // 该类型有关联的产品，下面的子类只能修改，不能删除
        List<AttrSpecDTO> attrDTOS = typeDTO.getSpecDTOS();
        List<AttrSpecDTO> specDTOS = typeDTO.getAttrDTOS();
        if (isUsedVO.isSuccess() && isUsedVO.getResultData() && null != attrDTOS) {
            for(AttrSpecDTO dto : attrDTOS) {
                if (StringUtils.isNotBlank(dto.getIsDeleted())) {
                    return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "关联类型不允许删除");
                }
            }
        }
        if (isUsedVO.isSuccess() && isUsedVO.getResultData() && null != specDTOS) {
            for(AttrSpecDTO dto : specDTOS) {
                if (StringUtils.isNotBlank(dto.getIsDeleted())) {
                    return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "关联类型不允许删除");
                }
            }
        }
        if(ret > 0){
            int num = attrSpecManager.deleteAttrSpecByTypeId(typeId);
            attrSpecManager.delProdTypeComplexByTargetId(typeId);
            saveAttrSpec(typeDTO, typeId);
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    private void saveAttrSpec(TypeDTO typeDTO, String typeId) {
        List<AttrSpecDTO> attrDTOS = typeDTO.getSpecDTOS();
        if(!CollectionUtils.isEmpty(attrDTOS)){
            for (AttrSpecDTO dto : attrDTOS) {
                dto.setTypeId(typeId);
                dto.setAttrType(AttrSpecConst.SPEC_TYPE);
                dto.setStatusCd("1000");
                attrSpecManager.addAttrSpec(dto);
            }
        }
        
        List<AttrSpecDTO> specDTOS = typeDTO.getAttrDTOS();
        if(!CollectionUtils.isEmpty(specDTOS)){
            for (AttrSpecDTO dto : specDTOS) {
                dto.setTypeId(typeId);
                dto.setAttrType(AttrSpecConst.ATTR_TYPE);
                dto.setStatusCd("1000");
                attrSpecManager.addAttrSpec(dto);
            }
        }


        List<TypeDTO.BrandReq> brandReqS = typeDTO.getBrandIds();
        if(!CollectionUtils.isEmpty(brandReqS)){
            for (TypeDTO.BrandReq dto : brandReqS) {
                ProdTypeComplex prodTypeComplex = new ProdTypeComplex();
                prodTypeComplex.setTypeId(typeId);
                prodTypeComplex.setBrandId(dto.getBrandId());
                prodTypeComplex.setComplexOrder(dto.getComplexOrder());
                prodTypeComplex.setCreateStaff(typeDTO.getCreateStaff());
                prodTypeComplex.setCreateDate(typeDTO.getCreateDate());
                attrSpecManager.addProdTypeComplex(prodTypeComplex);

            }
        }

    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO deleteType(TypeDeleteByIdReq req) {
        TypeIsUsedQueryByIdReq typeIsUsedQueryByIdReq = new TypeIsUsedQueryByIdReq();
        typeIsUsedQueryByIdReq.setTypeId(req.getTypeId());
        ResultVO<Boolean> productListVO = this.typeIsUsed(typeIsUsedQueryByIdReq);
        if (productListVO.isSuccess() && productListVO.getResultData()) {
            return ResultVO.error(ResultCodeEnum.ERROR.getCode(), "该产品类型已使用");
        }
        int ret = typeManager.deleteType(req.getTypeId());
        if(ret > 0){
            List<AttrSpecDTO> attrSpecDTOS = attrSpecManager.queryAttrSpecList(req.getTypeId());
            for(AttrSpecDTO attrSpecDTO : attrSpecDTOS){
                attrSpecManager.deleteAttrSpec(attrSpecDTO.getAttrId());
            }

            // 删除类型品牌关联信息
            attrSpecManager.delProdTypeComplexByTargetId(req.getTypeId());
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO listType(TypeListByNameReq req) {
        List<Type> list = typeManager.listTypeByName(req.getTypeName());
        List<TypeDTO> typeDTOS = new ArrayList<>();
        for(Type type : list){
            TypeDTO dto = new TypeDTO();
            BeanUtils.copyProperties(type, dto);
            String typeId = dto.getTypeId();
            List<AttrSpecDTO> attrSpecDTOS = attrSpecManager.queryAttrSpecList(typeId);
            List<AttrSpecDTO> attrDTOS = new ArrayList<>();
            List<AttrSpecDTO> specDTOS = new ArrayList<>();
            for(AttrSpecDTO attrSpecDTO : attrSpecDTOS){
                if("1".equals(attrSpecDTO.getAttrType())){
                    specDTOS.add(attrSpecDTO);
                }
                if("2".equals(attrSpecDTO.getAttrType())){
                    attrDTOS.add(attrSpecDTO);
                }
            }

            dto.setAttrDTOS(attrDTOS);
            dto.setSpecDTOS(specDTOS);
            typeDTOS.add(dto);
        }
        return ResultVO.success(typeDTOS);
    }

    private  List<TypeDTO.BrandResp> queryTypeBrand(String typeId) {
        List<TypeDTO.BrandResp> brandRespList = Lists.newArrayList();
        List<ProdTypeComplex> prodTypeComplexList = attrSpecManager.queryProdTypeComplexbyTypeId(typeId);
        log.info("TypeServiceImpl.queryTypeBrand attrSpecManager.queryProdTypeComplexbyTypeId req={}, resp={}", typeId, JSON.toJSONString(prodTypeComplexList));
        List<String> brandList = Lists.newArrayList();
        Map brandMap = new HashMap<String,Long>();
        if(!CollectionUtils.isEmpty(prodTypeComplexList)) {
            for(ProdTypeComplex prodTypeComplex:prodTypeComplexList ) {
                brandList.add(prodTypeComplex.getBrandId());
                if(!Objects.isNull(prodTypeComplex.getComplexOrder())) {
                    brandMap.put(prodTypeComplex.getBrandId(), prodTypeComplex.getComplexOrder());
                }
            }
        }

        //取品牌的数据
        List<Brand> respList = brandManager.listBrand(brandList);
        for (Brand brand:respList){
            TypeDTO.BrandResp  brandResp = new TypeDTO.BrandResp();
            brandResp.setBrandId(brand.getBrandId());
            brandResp.setBrandName(brand.getName());
            if (!Objects.isNull(brandMap.get(brand.getBrandId()))) {
                brandResp.setComplexOrder(Long.valueOf(String.valueOf(brandMap.get(brand.getBrandId()))));
            }

            List<ProdFileDTO> prodFiles = prodFileManager.getFile(brand.getBrandId(), FileConst.TargetType.BRAND_TARGET.getType(),null);
            if (CollectionUtils.isEmpty(prodFiles)) {
                continue;
            }
            for (ProdFileDTO resp:prodFiles){
                if (StringUtils.isEmpty(resp.getFileUrl())){
                    continue;
                }
                // 如果查询地址为完整路径不做特殊处理，否则补全地址路径
                if (resp.getFileUrl().startsWith("http")) {
                    brandResp.setBrandImgPath(resp.getFileUrl());
                } else {
                    String newImageFile = FastDFSImgStrJoinUtil.fullImageUrl(resp.getFileUrl(), dfsShowIp, true);
                    brandResp.setBrandImgPath(newImageFile);
                }

            }

            brandRespList.add(brandResp);
        }
        return brandRespList;

    }

    @Override
    public ResultVO selectById(TypeSelectByIdReq req) {
        Type type = typeManager.selectById(req.getTypeId());
        if(null != type){
            List<TypeDTO> typeDTOS = new ArrayList<>();
            TypeDTO dto = new TypeDTO();
            BeanUtils.copyProperties(type, dto);
            List<AttrSpecDTO> attrSpecDTOS = attrSpecManager.queryAttrSpecList(req.getTypeId());
            List<AttrSpecDTO> attrDTOS = new ArrayList<>();
            List<AttrSpecDTO> specDTOS = new ArrayList<>();
            for(AttrSpecDTO attrSpecDTO : attrSpecDTOS){
                if("1".equals(attrSpecDTO.getAttrType())){
                    specDTOS.add(attrSpecDTO);
                }
                if("2".equals(attrSpecDTO.getAttrType())){
                    attrDTOS.add(attrSpecDTO);
                }
            }
            //获取品牌信息
            List<TypeDTO.BrandResp> brandRespList = queryTypeBrand(req.getTypeId());
            dto.setBrandResps(brandRespList);
            dto.setAttrDTOS(attrDTOS);
            dto.setSpecDTOS(specDTOS);
            typeDTOS.add(dto);
            return ResultVO.success(typeDTOS);
        }else{
            return ResultVO.success();
        }
    }

    @Override
    public ResultVO<List<TypeDTO>> selectAll() {
        List<TypeDTO> typeDTOs = new ArrayList<>();
        List<Type> types = typeManager.selectAll();
        if(!CollectionUtils.isEmpty(types)){
            for(Type type:types){
                TypeDTO dto = new TypeDTO();
                BeanUtils.copyProperties(type, dto);
                typeDTOs.add(dto);
            }
        }
        return ResultVO.success(typeDTOs);
    }


    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ResultVO<Boolean> typeIsUsed(TypeIsUsedQueryByIdReq typeIsUsedQueryByIdReq){
        ProductsPageReq req = new ProductsPageReq();
        req.setTypeId(typeIsUsedQueryByIdReq.getTypeId());
        ResultVO<Page<ProductPageResp>> productListVO = productService.selectPageProductAdmin(req);
        if (productListVO.isSuccess() && null != productListVO.getResultData().getRecords() && !productListVO.getResultData().getRecords().isEmpty()) {
            return ResultVO.success(true);
        }else{
            return ResultVO.success(false);
        }
    }

    @Override
    public ResultVO<TypeDetailResp> getDetailType(TypeSelectByIdReq req){
        String rootTypeId = "-1";
        Type type = null;
        String parentId = req.getTypeId();
        while (true){
            type = typeManager.selectById(parentId);
            log.info("TypeServiceImpl.queryTypeBrand attrSpecManager.queryProdTypeComplexbyTypeId req={}, resp={}", parentId, JSON.toJSONString(type));
            if (null == type) {
                break;
            }
            parentId = type.getParentTypeId();
            if (StringUtils.isNotBlank(parentId) || rootTypeId.equals(parentId)) {
                break;
            }
        }
        if (null == type) {
            return ResultVO.success();
        }
        TypeDetailResp typeDetailResp = new TypeDetailResp();
        BeanUtils.copyProperties(type, typeDetailResp);
        String typeId = type.getTypeId();
        if (moblie.equals(typeId)) {
            typeDetailResp.setDetailCode(TypeConst.TYPE_DETAIL.MOBLIE.getCode());
            typeDetailResp.setDetailName(TypeConst.TYPE_DETAIL.MOBLIE.getValue());
        } else if(router.equals(typeId)) {
            typeDetailResp.setDetailCode(TypeConst.TYPE_DETAIL.ROUTER.getCode());
            typeDetailResp.setDetailName(TypeConst.TYPE_DETAIL.ROUTER.getValue());
        } else if(intelligentTermina.equals(typeId)) {
            typeDetailResp.setDetailCode(TypeConst.TYPE_DETAIL.INTELLIGENT_TERMINA.getCode());
            typeDetailResp.setDetailName(TypeConst.TYPE_DETAIL.INTELLIGENT_TERMINA.getValue());
        }else if(fusionTerminal.equals(typeId)) {
            typeDetailResp.setDetailCode(TypeConst.TYPE_DETAIL.FUSION_TERMINAL.getCode());
            typeDetailResp.setDetailName(TypeConst.TYPE_DETAIL.FUSION_TERMINAL.getValue());
        }else if(setTopBox.equals(typeId)) {
            typeDetailResp.setDetailCode(TypeConst.TYPE_DETAIL.SET_TOP_BOX.getCode());
            typeDetailResp.setDetailName(TypeConst.TYPE_DETAIL.SET_TOP_BOX.getValue());
        }else if(opticalModem.equals(typeId)) {
            typeDetailResp.setDetailCode(TypeConst.TYPE_DETAIL.OPTICAL_MODEM.getCode());
            typeDetailResp.setDetailName(TypeConst.TYPE_DETAIL.OPTICAL_MODEM.getValue());
        }
        return ResultVO.success(typeDetailResp);
    }
}