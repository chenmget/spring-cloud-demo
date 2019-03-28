package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierGetReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierListReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierQueryReq;
import com.iwhalecloud.retail.partner.entity.Supplier;
import com.iwhalecloud.retail.partner.mapper.SupplierMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mzl
 * @date 2018/10/29
 */
@Component
public class SupplierManager {

    @Resource
    private SupplierMapper supplierMapper;

//    public List<SupplierDTO> listSupplier() {
//        return supplierMapper.selectAll();
//    }

    public Page<SupplierDTO> pageSupplier(SupplierQueryReq supplierQueryReq){
        Page<SupplierDTO> supplierDTOPage = new Page<SupplierDTO>(supplierQueryReq.getPageNo(),supplierQueryReq.getPageSize());
        return supplierMapper.pageSupplier(supplierDTOPage,supplierQueryReq);
    }

    public List<Supplier> getSupplierListByIds(List<String> supplierIds){
        return supplierMapper.selectBatchIds(supplierIds);
    }

    /**
     * 根据条件（精确）查找单个供应商
     * @param req
     * @return
     */
    public Supplier getSupplier(SupplierGetReq req){
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<Supplier>();
        if(!StringUtils.isEmpty(req.getSupplierId())){
            queryWrapper.eq(Supplier.FieldNames.supplierId.getTableFieldName(), req.getSupplierId());
        }
        if(!StringUtils.isEmpty(req.getUserId())){
            queryWrapper.eq(Supplier.FieldNames.userId.getTableFieldName(), req.getUserId());
        }
        if(!StringUtils.isEmpty(req.getSupplierCode())){
            queryWrapper.eq(Supplier.FieldNames.supplierCode.getTableFieldName(), req.getSupplierCode());
        }
        List<Supplier> supplierList = supplierMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(supplierList)){
            return null;
        }
        return supplierList.get(0);
    }

    /**
     * 查询供应商列表
     * @param req
     * @return
     */
    public List<Supplier> listSupplier(SupplierListReq req){
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(req.getSupplierName())){
            queryWrapper.like(Supplier.FieldNames.supplierName.getTableFieldName(), req.getSupplierName());
        }
        if (!StringUtils.isEmpty(req.getSupplierState())){
            queryWrapper.eq(Supplier.FieldNames.supplierState.getTableFieldName(), req.getSupplierState());
        }
        if (!StringUtils.isEmpty(req.getSupplierType())){
            queryWrapper.eq(Supplier.FieldNames.supplierType.getTableFieldName(), req.getSupplierType());
        }
        return supplierMapper.selectList(queryWrapper);
    }
}
