package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.ShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailUpdateDTO;

import java.util.HashMap;
import java.util.List;

public interface ShelfDetailService {
    /**
     * 添加
     *
     * @param dto
     * @return
     */
    int createShelfDetail(ShelfDetailDTO dto);

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    int updateShelfDetailStatus(ShelfDetailUpdateDTO dto);

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    int deleteShelfDetail(ShelfDetailDTO dto);

    List<ShelfDetailDTO> queryCloudShelfDetailProductList(HashMap<String, Object> map);

    List<ShelfDetailDTO> queryCloudShelfDetailContentList(HashMap<String, Object> map);
}
