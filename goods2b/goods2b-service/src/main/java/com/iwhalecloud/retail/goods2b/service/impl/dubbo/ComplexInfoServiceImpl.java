package com.iwhalecloud.retail.goods2b.service.impl.dubbo;


import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.dto.ComplexInfoDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoQueryReq;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoReq;
import com.iwhalecloud.retail.goods2b.entity.ComplexInfo;
import com.iwhalecloud.retail.goods2b.manager.ComplexInfoManager;
import com.iwhalecloud.retail.goods2b.mapper.ComplexInfoMapper;
import com.iwhalecloud.retail.goods2b.service.dubbo.ComplexInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/27.
 */
@Slf4j
@Component("ComplexInfoService")
@Service
public class ComplexInfoServiceImpl implements ComplexInfoService {

    @Autowired
    private ComplexInfoManager complexInfoManager;

    @Override
    public Boolean batchAddComplexInfo(ComplexInfoReq req) {
        return complexInfoManager.batchAddComplexInfo(req);
    }

    @Override
    public int insertComplexInfo(ComplexInfoDTO complexInfoDTO) {
        return complexInfoManager.addComplexInfo(complexInfoDTO);
    }

    @Override
    public int deleteOneComplexInfo(ComplexInfoDTO complexInfoDTO) {
        String complex_info_id = complexInfoDTO.getComplexInfoId();
        return complexInfoManager.deleteOneComplexInfo(complex_info_id);
    }

    @Override
    public int deleteComplexInfoByGoodsId(ComplexInfoDTO complexInfoDTO) {
        String goodsId = complexInfoDTO.getAGoodsId();
        return complexInfoManager.deleteComplexInfoByGoodsId(goodsId);
    }

    @Override
    public int updateComplexInfo(ComplexInfoDTO complexInfoDTO) {
        return complexInfoManager.updateComplexInfo(complexInfoDTO);
    }

    @Override
    public ResultVO<List<ComplexInfoDTO>> queryComplexInfo(ComplexInfoQueryReq complexInfoQueryReq) {
        List<String> goodsIdList = complexInfoQueryReq.getGoodsIdList();
        List<ComplexInfo> complexInfoList = complexInfoManager.selectListByGoodsId(goodsIdList);
        List<ComplexInfoDTO> complexInfoDTOList = new ArrayList<>();
        if(CollectionUtils.isEmpty(complexInfoList)){
            return ResultVO.error("没有根据商品ID查询到推荐信息");
        }
        for(ComplexInfo complexInfo: complexInfoList){
            ComplexInfoDTO complexInfoDTO = new ComplexInfoDTO();
            BeanUtils.copyProperties(complexInfo, complexInfoDTO);
            complexInfoDTOList.add(complexInfoDTO);
        }
        return ResultVO.success(complexInfoDTOList);
    }
}
