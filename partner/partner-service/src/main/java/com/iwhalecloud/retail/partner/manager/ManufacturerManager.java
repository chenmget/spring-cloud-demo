package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.partner.dto.ManufacturerDTO;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerGetReq;
import com.iwhalecloud.retail.partner.dto.req.ManufacturerPageReq;
import com.iwhalecloud.retail.partner.entity.Manufacturer;
import com.iwhalecloud.retail.partner.mapper.ManufacturerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Component
public class ManufacturerManager{
    @Resource
    private ManufacturerMapper manufacturerMapper;

    /**
     * 添加一个厂商信息
     * @param manufacturer
     * @return
     */
    public ManufacturerDTO insert(Manufacturer manufacturer){
        int resultInt = manufacturerMapper.insert(manufacturer);
        if(resultInt > 0){
            ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
            BeanUtils.copyProperties(manufacturer, manufacturerDTO);
            return manufacturerDTO;
        }
        return null;
    }


    /**
     * 根据条件 获取一个厂商信息
     * @param req
     * @return
     */
    public ManufacturerDTO getManufacturer(ManufacturerGetReq req){
        QueryWrapper<Manufacturer> queryWrapper = new QueryWrapper<Manufacturer>();
        Boolean paraIsNull = true;
        if(!StringUtils.isEmpty(req.getManufacturerId())){
            paraIsNull = false;
            queryWrapper.eq(Manufacturer.FieldNames.manufacturerId.getTableFieldName(), req.getManufacturerId());
        }
        if(!StringUtils.isEmpty(req.getManufacturerCode())){
            paraIsNull = false;
            queryWrapper.eq(Manufacturer.FieldNames.manufacturerCode.getTableFieldName(), req.getManufacturerCode());
        }

        if (paraIsNull) {
            return null;
        }
        List<Manufacturer> manufacturerList = manufacturerMapper.selectList(queryWrapper);
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        if(CollectionUtils.isEmpty(manufacturerList)){
            return null;
        }
        BeanUtils.copyProperties(manufacturerList.get(0), manufacturerDTO);
        return manufacturerDTO;
    }

    /**
     * 编辑厂商信息
     * @param manufacturer
     * @return
     */
    public int updateManufacturer(Manufacturer manufacturer){
        return manufacturerMapper.updateById(manufacturer);
    }

    /**
     * 删除厂商信息
     * @param id
     * @return
     */
    public int deleteManufacturerById(String id){
        return manufacturerMapper.deleteById(id);
    }

    /**
     * 厂商信息列表分页
     * @param req
     * @return
     */
    public Page<ManufacturerDTO> pageManufacturer(ManufacturerPageReq req){
        Page<ManufacturerDTO> page = new Page<>(req.getPageNo(), req.getPageSize());
        Page<ManufacturerDTO> manufacturerDTOPage = manufacturerMapper.pageManufacturer(page, req);
        return manufacturerDTOPage;
    }


}
