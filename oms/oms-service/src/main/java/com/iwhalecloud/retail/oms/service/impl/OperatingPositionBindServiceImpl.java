package com.iwhalecloud.retail.oms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.oms.dto.BindContentDTO;
import com.iwhalecloud.retail.oms.dto.BindProductDTO;
import com.iwhalecloud.retail.oms.entity.OperatingPositionBind;
import com.iwhalecloud.retail.oms.manager.OperatingPositionBindManager;
import com.iwhalecloud.retail.oms.service.OperatingPositionBindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/19 14:30
 * @Description:
 */
@Slf4j
@Service
public class OperatingPositionBindServiceImpl implements OperatingPositionBindService {

    @Autowired
    private OperatingPositionBindManager operatingPositionBindManager;

    @Override
    public int createBindProduct(BindProductDTO dto) {
        int t = operatingPositionBindManager.createBindProduct(dto);
        return t;
    }

    @Override
    public int createBindContent(BindContentDTO dto) {
        int t = operatingPositionBindManager.createBindContent(dto);
        return t;
    }

    @Override
    public List<String> getGoodsIdsByAdscriptionShopId(String adscriptionShopId) {
        List<String> goodsIdList = new ArrayList<>();
        List<OperatingPositionBind> operatingPositionBinds = operatingPositionBindManager.getByAdscriptionShopId(adscriptionShopId);
        for(OperatingPositionBind operatingPositionBind : operatingPositionBinds){
            if(operatingPositionBind.getProductNumber() != null){
                List<String> goodsIds = Arrays.asList(operatingPositionBind.getProductNumber().split(","));
                for(String goodsId: goodsIds){
                    if(!goodsIdList.contains(goodsId)){
                        goodsIdList.add(goodsId);
                    }
                }
            }
        }
        return goodsIdList;
    }

    @Override
    public int unbindContentId(String contentId){
        int t = operatingPositionBindManager.unbindContentId(contentId);
        return t;
    }
}

