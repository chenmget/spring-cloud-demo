package com.iwhalecloud.retail.oms.service.impl;

import com.iwhalecloud.retail.oms.dto.ShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailUpdateDTO;
import com.iwhalecloud.retail.oms.manager.ShelfDetailManager;
import com.iwhalecloud.retail.oms.service.ShelfDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Auther: lin.wh
 * @Date: 2018/10/23 22:46
 * @Description:
 */

@Slf4j
@Service
public class ShelfDetailServiceImpl implements ShelfDetailService {
    @Autowired
    private ShelfDetailManager shelfDetailManager;

    @Override
    public int createShelfDetail(ShelfDetailDTO dto) {
        int t = shelfDetailManager.createShelfDetail(dto);
        return t;
    }

    @Override
    public int updateShelfDetailStatus(ShelfDetailUpdateDTO dto) {
        int t = shelfDetailManager.editShelfDetail(dto);
        return t;
    }

    @Override
    public int deleteShelfDetail(ShelfDetailDTO dto) {
        int t = shelfDetailManager.deleteShelfDetail(dto);
        return t;
    }

    @Override
    public List<ShelfDetailDTO> queryCloudShelfDetailProductList(HashMap<String, Object> map) {
        return shelfDetailManager.queryCloudShelfDetailProductList(map);
    }

    @Override
    public List<ShelfDetailDTO> queryCloudShelfDetailContentList(HashMap<String, Object> map) {
        return shelfDetailManager.queryCloudShelfDetailContentList(map);
    }
}

