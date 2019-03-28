package com.iwhalecloud.retail.goods2b.service.impl.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.GoodsConst;
import com.iwhalecloud.retail.goods2b.dto.GoodsRightDTO;
import com.iwhalecloud.retail.goods2b.dto.RightDTO;
import com.iwhalecloud.retail.goods2b.dto.req.GoodsRightAddReq;
import com.iwhalecloud.retail.goods2b.entity.GoodsRight;
import com.iwhalecloud.retail.goods2b.manager.GoodsRightManager;
import com.iwhalecloud.retail.goods2b.mapper.GoodsRightMapper;
import com.iwhalecloud.retail.goods2b.service.dubbo.GoodsRightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/23.
 */
@Service
@Component("GoodsRightService")
@Slf4j
public class GoodsRightServiceImpl implements GoodsRightService {

    @Autowired
    private GoodsRightManager goodsRightManager;

    @Override
    public ResultVO<Boolean> batchInsertGoodsRight(GoodsRightAddReq req) {
        List<RightDTO> rightDTOList = req.getRightsList();
        boolean result = false;
        if(CollectionUtils.isNotEmpty(rightDTOList)){
            List<GoodsRight> goodsRightList = Lists.newArrayList();
            for(RightDTO rightDTO: rightDTOList){
                GoodsRight goodsRight = new GoodsRight();
                goodsRight.setStatus(GoodsConst.EFFECTIVE);
                goodsRight.setRightsName(rightDTO.getRightsName());
                goodsRight.setRightsId(rightDTO.getRightsId());
                goodsRight.setGoodsId(req.getGoodsId());
                goodsRightList.add(goodsRight);
            }
            result = goodsRightManager.batchInsertGoodsRight(goodsRightList);
        }

        return ResultVO.success(result);
    }

    @Override
    public int insertGoodsRight(GoodsRightAddReq req) {
        List<RightDTO> rightDTOList = req.getRightsList();
        GoodsRight goodsRight = new GoodsRight();
        int num = 0;
        if(CollectionUtils.isNotEmpty(rightDTOList)){
            RightDTO rightDTO = rightDTOList.get(0);
            goodsRight.setStatus(GoodsConst.EFFECTIVE);
            goodsRight.setRightsName(rightDTO.getRightsName());
            goodsRight.setRightsId(rightDTO.getRightsId());
            goodsRight.setGoodsId(req.getGoodsId());
            num = goodsRightManager.addGoodsRight(goodsRight);
        }
        return num;
    }

    @Override
    public int updateGoodsRight(GoodsRightDTO req) {
        int num = 0;
        String goodsRightsId = req.getGoodsRightsId();
        if(!StringUtils.isEmpty(goodsRightsId)){
            num =   goodsRightManager.updateGoodsRight(req);
        }

        return num;
    }

    @Override
    public int deleteGoodsRight(GoodsRightDTO goodsRightDTO) {
        String goodsId = goodsRightDTO.getGoodsId();
        return goodsRightManager.deleteGoodsRightByGoodsId(goodsId);
    }

    @Override
    public int deleteOneGoodsRight(GoodsRightDTO goodsRightDTO) {
        String goodsRightId = goodsRightDTO.getGoodsRightsId();
        return goodsRightManager.deleteOneGoodsRight(goodsRightId);
    }

    @Override
    public List<GoodsRightDTO> listByGoodsId(GoodsRightDTO req) {
        String goodsId = req.getGoodsId();
        List<GoodsRight> goodsRights = goodsRightManager.listAll(goodsId);
        List<GoodsRightDTO> goodsRightDTOList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(goodsRights)){
            for(GoodsRight goodsRight: goodsRights){
                GoodsRightDTO goodsRightDTO = new GoodsRightDTO();
                goodsRightDTO.setGoodsId(goodsRight.getGoodsId());
                goodsRightDTO.setRightsId(goodsRight.getRightsId());
                goodsRightDTO.setRightsName(goodsRight.getRightsName());
                goodsRightDTO.setGoodsRightsId(goodsRight.getGoodsRightsId());
                goodsRightDTO.setStatus(goodsRight.getStatus());
                goodsRightDTOList.add(goodsRightDTO);
            }
        }
        return goodsRightDTOList;
    }
}
