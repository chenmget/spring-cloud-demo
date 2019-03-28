package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.CataLogDTO;

import java.util.List;

public interface ContentMenuService {

    /**
     * 新增
     *
     * @param dto
     * @return
     */
    int createContentMenu(CataLogDTO dto);

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    int deleteContentMenu(CataLogDTO dto);

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    int editContentMenu(CataLogDTO dto);

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    List<CataLogDTO> queryContentMenuList(CataLogDTO dto);

    CataLogDTO queryContentMenuDetail(CataLogDTO dto);

    int updateContentBase(CataLogDTO dto);

    int moveContentMenu(CataLogDTO dto);

    /**
     * 根据父类ID和目录名称查询
     * @param dto
     * @return
     */
    List<CataLogDTO> queryMenuByParentAndName(CataLogDTO dto);
}
