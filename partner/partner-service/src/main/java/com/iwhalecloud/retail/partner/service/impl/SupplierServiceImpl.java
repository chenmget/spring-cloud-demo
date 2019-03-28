package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.SupplierDTO;
import com.iwhalecloud.retail.partner.dto.req.SupplierGetReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierListReq;
import com.iwhalecloud.retail.partner.dto.req.SupplierQueryReq;
import com.iwhalecloud.retail.partner.entity.Supplier;
import com.iwhalecloud.retail.partner.manager.SupplierManager;
import com.iwhalecloud.retail.partner.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mzl
 * @date 2018/10/29
 */
@Slf4j
@Component("supplierService")
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierManager supplierManager;

//    @Override
//    public List<SupplierDTO> listSupplier() {
//        return supplierManager.listSupplier();
//    }

    @Override
    public Page<SupplierDTO> pageSupplier(SupplierQueryReq supplierQueryReq) {
        return supplierManager.pageSupplier(supplierQueryReq);

    }

    @Override
    public List<SupplierDTO> getSupplierListByIds(List<String> supplierIds) {
        List<Supplier> supplierList = supplierManager.getSupplierListByIds(supplierIds);
        if (CollectionUtils.isEmpty(supplierList)){
            return new ArrayList<>();
        }
        List<SupplierDTO> supplierDTOList = new ArrayList<>();
        for (Supplier supplier:supplierList){
            SupplierDTO supplierDTO = new SupplierDTO();
            BeanUtils.copyProperties(supplier, supplierDTO);
            supplierDTOList.add(supplierDTO);
        }
        return supplierDTOList;
    }

    @Override
    public ResultVO<SupplierDTO> getSupplier(SupplierGetReq req){
        log.info("SupplierServiceImpl.getSupplier(), 入参SupplierGetReq={} ", req);
        SupplierDTO supplierDTO = new SupplierDTO();
        Supplier supplier = supplierManager.getSupplier(req);
        if(supplier == null){
            supplierDTO = null;
        } else {
            BeanUtils.copyProperties(supplier, supplierDTO);
        }
        log.info("SupplierServiceImpl.getSupplier(), 出参supplierDTO={} ", supplierDTO);
        return ResultVO.success(supplierDTO);
    }


    @Override
    public ResultVO<List<SupplierDTO>> listSupplier(SupplierListReq supplierListReq){
        log.info("SupplierServiceImpl.listSupplier(), 入参supplierListReq={} ", supplierListReq);
        List<Supplier> supplierList = supplierManager.listSupplier(supplierListReq);
        List<SupplierDTO> supplierDTOList = new ArrayList<>();
        for (Supplier supplier:supplierList){
            SupplierDTO supplierDTO = new SupplierDTO();
            BeanUtils.copyProperties(supplier, supplierDTO);
            supplierDTOList.add(supplierDTO);
        }
        log.info("SupplierServiceImpl.listSupplier(), 出参supplierDTOList={} ", supplierDTOList);
        return ResultVO.success(supplierDTOList);
    }

}
