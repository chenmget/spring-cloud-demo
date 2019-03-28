package com.iwhalecloud.retail.oms.service;

import com.iwhalecloud.retail.oms.dto.TRfidGoodsRelDTO;

import java.util.List;

public interface RfidGoodsRelService {

    /**
     * 添加 TRfidGoodsRel
     * @param rfidGoodsRelDTO
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    int addRfidGoodsRel(TRfidGoodsRelDTO rfidGoodsRelDTO);

    /**
     * 根据条件 修改TRfidGoodsRel
     * @param rfidGoodsRelDTO
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    int modifyRfidGoodsRel(TRfidGoodsRelDTO rfidGoodsRelDTO);

    /**
     * 根据条件 查询TRfidGoodsRel
     * @param rfidGoodsRelDTO
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    List<TRfidGoodsRelDTO> getRfidGoodsRel(TRfidGoodsRelDTO rfidGoodsRelDTO);

    /**
     * 根据条件 删除TRfidGoodsRel
     * @param rfidGoodsRelDTO
     * @return
     * @author Ji.kai
     * @date 2018/10/24 15:27
     */
    boolean removeRfidGoodsRel(TRfidGoodsRelDTO rfidGoodsRelDTO);

}
