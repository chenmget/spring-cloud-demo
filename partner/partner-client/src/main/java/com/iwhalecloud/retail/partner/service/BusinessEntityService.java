package com.iwhalecloud.retail.partner.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.BusinessEntityDTO;
import com.iwhalecloud.retail.partner.dto.req.*;

import java.util.List;

public interface BusinessEntityService{

    /**
     * 添加一个经营主体
     * @param req
     * @return
     */
    ResultVO<BusinessEntityDTO> saveBusinessEntity(BusinessEntitySaveReq req);

    /**
     * 获取一个经营主体
     * @param req
     * @return
     */
    ResultVO<BusinessEntityDTO> getBusinessEntity(BusinessEntityGetReq req);

    /**
     * 编辑经营主体信息
     * @param req
     * @return
     */
    ResultVO<Integer> updateBusinessEntity(BusinessEntityUpdateReq req);

    /**
     * 删除经营主体
     * @param id
     * @return
     */
    ResultVO<Integer> deleteBusinessEntityById(String id);

    /**
     * 经营主体信息列表分页
     * @param pageReq
     * @return
     */
    ResultVO<Page<BusinessEntityDTO>> pageBusinessEntity(BusinessEntityPageReq pageReq);

    /**
     * 经营主体信息列表
     * @param req
     * @return
     */
    ResultVO<List<BusinessEntityDTO>> listBusinessEntity(BusinessEntityListReq req);

    /**
     * 查询有权限的经营主体
     * @param req
     * @return
     */
    ResultVO<Page<BusinessEntityDTO>> pageBusinessEntityByRight(BusinessEntityPageByRightsReq req);

}