package com.iwhalecloud.retail.oms.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDTO;
import com.iwhalecloud.retail.oms.entity.ShelfTemplates;
import com.iwhalecloud.retail.oms.mapper.ShelfTemplatesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/21 23:37
 * @Description:
 */

@Component
public class ShelfTemplatesManager {

    @Resource
    private ShelfTemplatesMapper shelfTemplatesMapper;

    public ShelfTemplatesDTO createShelfTemplates(ShelfTemplatesDTO dto) {
        dto.setGmtCreate(new Date());
        dto.setGmtModified(new Date());
        ShelfTemplates shelfTemplates = new ShelfTemplates();
        BeanUtils.copyProperties(dto, shelfTemplates);
        shelfTemplatesMapper.insert(shelfTemplates);
        dto.setId(shelfTemplates.getId());
        return dto;
    }

    public int updateShelfTemplatesStatus(ShelfTemplatesDTO dto) {
        ShelfTemplates shelfTemplates = new ShelfTemplates();
        BeanUtils.copyProperties(dto, shelfTemplates);
        dto.setGmtModified(new Date());
        QueryWrapper<ShelfTemplates> qw = new QueryWrapper<>();
        qw.eq(ShelfTemplates.FieldNames.tempNumber.getTableFieldName(), dto.getTempNumber());
        int t = shelfTemplatesMapper.update(shelfTemplates, qw);
        return t;
    }

    public Page<ShelfTemplatesDTO> queryShelfTemplates(ShelfTemplatesDTO request) {
        Page<ShelfTemplatesDTO> page = new Page<ShelfTemplatesDTO>(request.getPageNo(), request.getPageSize());
        return shelfTemplatesMapper.queryShelfTemplates(page, request);
    }

    public int deleteShelfTemplates(ShelfTemplates dto) {
        return shelfTemplatesMapper.deleteShelfTemplates(dto);
    }
}

