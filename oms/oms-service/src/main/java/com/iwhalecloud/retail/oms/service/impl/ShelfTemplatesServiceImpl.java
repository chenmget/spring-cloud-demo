package com.iwhalecloud.retail.oms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.consts.OmsConst;
import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDTO;
import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDetailDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.ShelfTemplatesDetailReq;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.ShelfTemplatesReq;
import com.iwhalecloud.retail.oms.entity.ShelfTemplates;
import com.iwhalecloud.retail.oms.manager.ShelfTemplatesDetailManager;
import com.iwhalecloud.retail.oms.manager.ShelfTemplatesManager;
import com.iwhalecloud.retail.oms.service.ShelfTemplatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: lin.wh
 * @Date: 2018/10/16 14:19
 * @Description: 云货架模版实现类
 */

@Slf4j
@Service
public class ShelfTemplatesServiceImpl implements ShelfTemplatesService {

    @Autowired
    private ShelfTemplatesManager shelfTemplatesManager;

    @Autowired
    private ShelfTemplatesDetailManager shelfTemplatesDetailManager;

    @Override
    @Transactional
    public ShelfTemplatesDTO createShelfTemplates(ShelfTemplatesReq req, String userId) {
        ShelfTemplatesDTO shelfTemplatesDTO = new ShelfTemplatesDTO();
        BeanUtils.copyProperties(req, shelfTemplatesDTO);
        shelfTemplatesDTO.setCreator(userId);
        shelfTemplatesDTO.setModifier(userId);
        Long id = System.currentTimeMillis();
        shelfTemplatesDTO.setTempNumber(id.toString());
        shelfTemplatesDTO.setIsDeleted(OmsConst.IsDeleted.NOT_DELETED.getCode());
        shelfTemplatesManager.createShelfTemplates(shelfTemplatesDTO);
        List<ShelfTemplatesDetailDTO> shelfTemplatesDetailDTOs = new ArrayList<>();
        shelfTemplatesDTO.setShelfTemplatesDetails(shelfTemplatesDetailDTOs);
        for(ShelfTemplatesDetailReq shelfTemplatesDetailReq: req.getShelfTemplatesDetails()){
            ShelfTemplatesDetailDTO shelfTemplatesDetailDTO = shelfTemplatesDetailManager.insert(shelfTemplatesDetailReq.getOperatingPositionId(),shelfTemplatesDetailReq.getDefCategoryId(),shelfTemplatesDTO.getTempNumber(),userId);
            shelfTemplatesDetailDTOs.add(shelfTemplatesDetailDTO);
        }
        return shelfTemplatesDTO;
    }

    @Override
    public ShelfTemplatesDTO updateShelfTemplatesStatus(ShelfTemplatesReq req, String userId) {
        ShelfTemplatesDTO shelfTemplatesDTO = new ShelfTemplatesDTO();
        BeanUtils.copyProperties(req, shelfTemplatesDTO);
        shelfTemplatesDTO.setModifier(userId);
        shelfTemplatesManager.updateShelfTemplatesStatus(shelfTemplatesDTO);
        List<ShelfTemplatesDetailDTO> shelfTemplatesDetailDTOs = new ArrayList<>();
        shelfTemplatesDTO.setShelfTemplatesDetails(shelfTemplatesDetailDTOs);
        for(ShelfTemplatesDetailReq shelfTemplatesDetailReq: req.getShelfTemplatesDetails()){
            if(OmsConst.ActionTypeEnum.ADD.getCode().equals(shelfTemplatesDetailReq.getAction())){
                ShelfTemplatesDetailDTO shelfTemplatesDetailDTO = shelfTemplatesDetailManager.insert(shelfTemplatesDetailReq.getOperatingPositionId(),shelfTemplatesDetailReq.getDefCategoryId(),shelfTemplatesDTO.getTempNumber(),userId);
                shelfTemplatesDetailDTOs.add(shelfTemplatesDetailDTO);
            }else if (OmsConst.ActionTypeEnum.DELETE.getCode().equals(shelfTemplatesDetailReq.getAction())){
                shelfTemplatesDetailManager.delete(shelfTemplatesDetailReq.getId());
            }
        }
        return shelfTemplatesDTO;
    }

    @Override
    public Page<ShelfTemplatesDTO> queryShelfTemplates(ShelfTemplatesDTO dto) {
        return shelfTemplatesManager.queryShelfTemplates(dto);
    }

    @Override
    public int deleteShelfTemplates(ShelfTemplatesDTO dto) {
        ShelfTemplates entity = new ShelfTemplates();
        BeanUtils.copyProperties(dto,entity);
        return shelfTemplatesManager.deleteShelfTemplates(entity);
    }
}

