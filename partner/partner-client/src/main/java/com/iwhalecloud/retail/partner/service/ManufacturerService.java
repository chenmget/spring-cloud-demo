package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.ManufacturerDTO;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerGetReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerSaveReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerUpdateReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerPageReq;

public interface ManufacturerService{

    /**
     * 添加一个厂商信息
     * @param req
     * @return
     */
    ResultVO<ManufacturerDTO> saveManufacturer(ManufacturerSaveReq req);

    /**
     * 获取一个厂商信息
     * @param id
     * @return
     */
    ResultVO<ManufacturerDTO> getManufacturerById(String id);

    /**
     * 获取一个厂商信息
     * @param req
     * @return
     */
    ResultVO<ManufacturerDTO> getManufacturer(ManufacturerGetReq req);

    /**
     * 编辑厂商信息
     * @param req
     * @return
     */
    ResultVO<Integer> updateManufacturer(ManufacturerUpdateReq req);

    /**
     * 删除厂商信息
     * @param id
     * @return
     */
    ResultVO<Integer> deleteManufacturerById(String id);

    /**
     * 厂商信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<ManufacturerDTO>> pageManufacturer(ManufacturerPageReq pageReq);

}