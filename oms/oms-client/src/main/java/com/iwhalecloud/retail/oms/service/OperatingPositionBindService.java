package com.iwhalecloud.retail.oms.service;


import com.iwhalecloud.retail.oms.dto.BindContentDTO;
import com.iwhalecloud.retail.oms.dto.BindProductDTO;

import java.util.List;

public interface OperatingPositionBindService{
    //运营位绑定商品
    int createBindProduct(BindProductDTO dto);
    //运营位绑定内容
    int createBindContent(BindContentDTO dto);
    /**
     * 根据门店Id查询商品Id
     * @param adscriptionShopId
     * @return
     * @author Ji.kai
     * @date 2018/11/8 15:27
     */
    public List<String> getGoodsIdsByAdscriptionShopId(String adscriptionShopId);

    int unbindContentId(String contentId);

}