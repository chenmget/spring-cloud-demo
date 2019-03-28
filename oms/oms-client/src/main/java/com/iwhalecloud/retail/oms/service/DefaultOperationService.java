package com.iwhalecloud.retail.oms.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.DefaultOperatingDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.DefaultOperatingQueryReq;

import java.util.List;

public interface DefaultOperationService{

    /**
     * 云货架运营位模版新增
     * @param defaultOperatingDTO
     * @return
     */
    int createDefaultOperation(DefaultOperatingDTO defaultOperatingDTO);


    /**
     * 云货架运营位模版修改
     * @param defaultOperatingDTO
     * @return
     */
    int editDefaultOperation(DefaultOperatingDTO defaultOperatingDTO);


    /**
     * 云货架运营位模版删除
     * @param id
     * @return
     */
    int deleteDefaultOperation(Long id);

    /**
     * 查询默认运营位列表
     * @param defaultOperatingQueryReq
     * @return
     */
    Page<DefaultOperatingDTO> queryoperatingPostionListDTO(DefaultOperatingQueryReq defaultOperatingQueryReq)throws Exception;

}