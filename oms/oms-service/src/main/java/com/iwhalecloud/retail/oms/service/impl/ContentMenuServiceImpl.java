package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.CataLogDTO;
import com.iwhalecloud.retail.oms.manager.ContentMenuManager;
import com.iwhalecloud.retail.oms.service.ContentMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/25 14:23
 * @Description:
 */

@Slf4j
@Service
public class ContentMenuServiceImpl implements ContentMenuService {
    @Autowired
    private ContentMenuManager contentMenuManager;

    @Override
    public int createContentMenu(CataLogDTO dto) {
        int t = contentMenuManager.createContentMenu(dto);
        return t;
    }

    @Override
    public int deleteContentMenu(CataLogDTO dto) {
        int t = contentMenuManager.deleteContentMenu(dto);
        return t;
    }

    @Override
    public int editContentMenu(CataLogDTO dto) {
        int t = contentMenuManager.editContentMenu(dto);
        return t;
    }

    @Override
    public List<CataLogDTO> queryContentMenuList(CataLogDTO dto) {
        return contentMenuManager.queryContentMenuList(dto);
    }

    @Override
    public CataLogDTO queryContentMenuDetail(CataLogDTO dto) {
        return contentMenuManager.queryContentMenuDetail(dto);
    }

    @Override
    public int updateContentBase(CataLogDTO dto) {
        return contentMenuManager.updateContentBase(dto);
    }

    @Override
    public int moveContentMenu(CataLogDTO dto) {
        return contentMenuManager.moveContentMenu(dto);
    }

    @Override
    public List<CataLogDTO> queryMenuByParentAndName(CataLogDTO dto) {
        return contentMenuManager.queryMenuByParentAndName(dto);
    }
}

