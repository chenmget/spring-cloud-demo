package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.ManufacturerDTO;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerGetReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerPageReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerSaveReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerUpdateReq;
import com.iwhalecloud.retail.partner.entity.Manufacturer;
import com.iwhalecloud.retail.partner.manager.ManufacturerManager;
import com.iwhalecloud.retail.partner.service.ManufacturerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerManager manufacturerManager;

    /**
     * 添加一个厂商信息
     * @param req
     * @return
     */
    @Override
    public ResultVO<ManufacturerDTO> saveManufacturer(ManufacturerSaveReq req){
        log.info("ManufacturerServiceImpl.saveManufacturer(), 入参ManufacturerSaveReq={} ", req);
        Manufacturer manufacturer = new Manufacturer();
        BeanUtils.copyProperties(req, manufacturer);
        ManufacturerDTO manufacturerDTO =  manufacturerManager.insert(manufacturer);;
        log.info("ManufacturerServiceImpl.saveManufacturer(), 出参manufacturerDTO={} ", manufacturerDTO);
        if (manufacturerDTO == null){
            return ResultVO.error("新增厂商信息失败");
        }
        return ResultVO.success(manufacturerDTO);
    }

    /**
     * 获取一个厂商信息
     * @param id
     * @return
     */
    @Override
    public ResultVO<ManufacturerDTO> getManufacturerById(String id){
        ManufacturerGetReq req = new ManufacturerGetReq();
        req.setManufacturerId(id);
        ManufacturerDTO manufacturerDTO =  manufacturerManager.getManufacturer(req);
        return ResultVO.success(manufacturerDTO);
    }

    /**
     * 获取一个厂商信息
     * @param req
     * @return
     */
    @Override
    public ResultVO<ManufacturerDTO> getManufacturer(ManufacturerGetReq req){
        log.info("ManufacturerServiceImpl.getManufacturer(), 入参ManufacturerGetReq={} ", req);
        ManufacturerDTO manufacturerDTO =  manufacturerManager.getManufacturer(req);
        log.info("ManufacturerServiceImpl.getManufacturer(), 出参manufacturerDTO={} ", manufacturerDTO);
        return ResultVO.success(manufacturerDTO);
    }

    /**
     * 编辑厂商信息
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updateManufacturer(ManufacturerUpdateReq req){
        log.info("ManufacturerServiceImpl.updateManufacturer(), 入参ManufacturerUpdateReq={} ", req);
        Manufacturer manufacturer = new Manufacturer();
        BeanUtils.copyProperties(req, manufacturer);
        int result = manufacturerManager.updateManufacturer(manufacturer);
        log.info("ManufacturerServiceImpl.updateManufacturer(), 出参对象(更新影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("编辑厂商信息失败");
        }
        return ResultVO.success(result);
    }

    /**
     * 删除厂商信息
     * @param id
     * @return
     */
    @Override
    public ResultVO<Integer> deleteManufacturerById(String id){
        log.info("ManufacturerServiceImpl.deleteManufacturerById(), 入参ManufacturerId={} ", id);
        int result =manufacturerManager.deleteManufacturerById(id);
        log.info("ManufacturerServiceImpl.deleteManufacturerById(), 出参对象(删除影响数据条数）={} ", result);
        if (result <= 0){
            return ResultVO.error("删除厂商信息失败");
        }
        return ResultVO.success(result);
    }

    /**
     * 厂商信息列表分页
     * @param pageReq
     * @returns
     */
    @Override
    public ResultVO<Page<ManufacturerDTO>> pageManufacturer(ManufacturerPageReq pageReq){
        log.info("ManufacturerServiceImpl.pageManufacturer(), 入参ManufacturerPageReq={} ", pageReq);
        Page<ManufacturerDTO> page = manufacturerManager.pageManufacturer(pageReq);
        log.info("ManufacturerServiceImpl.pageManufacturer(), 出参page={} ", page);
        return ResultVO.success(page);
    }
}