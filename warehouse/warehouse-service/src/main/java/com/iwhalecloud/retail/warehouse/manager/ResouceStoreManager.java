package com.iwhalecloud.retail.warehouse.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iwhalecloud.retail.warehouse.dto.ResouceStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.AllocateStorePageReq;
import com.iwhalecloud.retail.warehouse.dto.request.StoreGetStoreIdReq;
import com.iwhalecloud.retail.warehouse.dto.request.StorePageReq;
import com.iwhalecloud.retail.warehouse.entity.ResouceStore;
import com.iwhalecloud.retail.warehouse.mapper.ResouceStoreMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class ResouceStoreManager extends ServiceImpl<ResouceStoreMapper, ResouceStore> {
    @Resource
    private ResouceStoreMapper resouceStoreMapper;

    public ResouceStoreDTO getStore(String merchantId, String storeSubType) {
        ResouceStoreDTO resouceStoreDTO = resouceStoreMapper.getStore(merchantId, storeSubType);
        return resouceStoreDTO;
    }

    public Page<ResouceStoreDTO> pageStore(StorePageReq req) {
        Page<ResouceStoreDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ResouceStoreDTO> resouceStoreDTOPage = resouceStoreMapper.pageStore(page, req);
        return resouceStoreDTOPage;
    }

    public Page<ResouceStoreDTO> pageAllocateStore(AllocateStorePageReq req) {
        Page<ResouceStoreDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ResouceStoreDTO> resouceStoreDTOPage = resouceStoreMapper.pageAllocateStore(page, req);
        return resouceStoreDTOPage;
    }

    public int saveStore(ResouceStoreDTO dto){
        ResouceStore resouceStore = new ResouceStore();
        BeanUtils.copyProperties(dto, resouceStore);
        return resouceStoreMapper.insert(resouceStore);
    }

    public ResouceStore getResouceStore(String storeId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ResouceStore.FieldNames.mktResStoreId.getTableFieldName(),storeId);
        return resouceStoreMapper.selectOne(queryWrapper);
    }

    public int updateStore(ResouceStoreDTO dto){
        ResouceStore resouceStore = new ResouceStore();
        BeanUtils.copyProperties(dto, resouceStore);
        return resouceStoreMapper.updateStore(resouceStore);
    }

    public int updateStoreById(ResouceStoreDTO dto){
        ResouceStore resouceStore = new ResouceStore();
        BeanUtils.copyProperties(dto, resouceStore);
        QueryWrapper<ResouceStore> queryWrapperSuppler = new QueryWrapper<>();
        queryWrapperSuppler.eq(ResouceStore.FieldNames.mktResStoreId.getTableFieldName(), dto.getMktResStoreId());
        return resouceStoreMapper.update(resouceStore, queryWrapperSuppler);
    }

    public String getStoreId(StoreGetStoreIdReq req){
        return resouceStoreMapper.getStoreId(req);
    }
}
