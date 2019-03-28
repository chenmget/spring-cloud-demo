package com.iwhalecloud.retail.oms.manager;

import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import com.iwhalecloud.retail.oms.entity.TCatalog;
import com.iwhalecloud.retail.oms.mapper.ContentMenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/25 14:28
 * @Description:
 */

@Component
public class ContentMenuManager {
    @Resource
    private ContentMenuMapper contentMenuMapper;

    public int createContentMenu(CataLogDTO dto) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dto.setUpdDate(new Date());
        dto.setCataid(null);
        TCatalog total = new TCatalog();
        BeanUtils.copyProperties(dto, total);
        return contentMenuMapper.createContentMenu(total);
    }

    public int deleteContentMenu(CataLogDTO dto) {
        return contentMenuMapper.deleteContentMenu(dto);
    }

    public int editContentMenu(CataLogDTO dto) {
        dto.setUpdDate(new Date());
        return contentMenuMapper.editContentMenu(dto);
    }

    public List<CataLogDTO> queryContentMenuList(CataLogDTO dto) {
        return contentMenuMapper.queryContentMenuList(dto);
    }

    public CataLogDTO queryContentMenuDetail(CataLogDTO dto) {
        return contentMenuMapper.queryContentMenuDetail(dto);
    }

    public int updateContentBase(CataLogDTO dto) {
        dto.setUpdDate(new Date());
        return contentMenuMapper.updateContentBase(dto);
    }

    public int moveContentMenu(CataLogDTO dto) {
        dto.setUpdDate(new Date());
        return contentMenuMapper.moveContentMenu(dto);
    }
    public List<CataLogDTO> queryMenuByParentAndName(CataLogDTO dto){
        return contentMenuMapper.queryMenuByParentAndName(dto);
    }
}

