package com.iwhalecloud.retail.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.ShelfTemplatesDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.ShelfTemplatesReq;


/**
 * @Auther: lin.wh
 * @Date: 2018/10/16
 * @Description: 云货架模版
 */
public interface ShelfTemplatesService {

    /**
     * 添加
     *
     * @param req
     * @return
     */
    ShelfTemplatesDTO createShelfTemplates(ShelfTemplatesReq req, String userId);

    /**
     * 修改
     *
     * @param req
     * @return
     */
    ShelfTemplatesDTO updateShelfTemplatesStatus(ShelfTemplatesReq req, String userId);

    /**
     * 云货架模版分页查询
     *
     * @param dto
     * @return
     */
    Page<ShelfTemplatesDTO> queryShelfTemplates(ShelfTemplatesDTO dto);

    /**
     * 云货架模版删除
     *
     * @param dto
     * @return
     */
    int deleteShelfTemplates(ShelfTemplatesDTO dto);
}
