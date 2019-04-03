package com.iwhalecloud.retail.goods2b.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods2b.dto.ComplexInfoDTO;
import com.iwhalecloud.retail.goods2b.dto.InfoDTO;
import com.iwhalecloud.retail.goods2b.dto.req.ComplexInfoReq;
import com.iwhalecloud.retail.goods2b.entity.ComplexInfo;
import com.iwhalecloud.retail.goods2b.mapper.ComplexInfoMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/27.
 */
@Component
public class ComplexInfoManager  {
    @Resource
    private ComplexInfoMapper complexInfoMapper;

    public Boolean batchAddComplexInfo(ComplexInfoReq req){
        int num=0;
        List<InfoDTO> infoDTOList = req.getInfoDTOList();
        if(CollectionUtils.isEmpty(infoDTOList)){
            return false;
        }
        List<ComplexInfo> complexInfoList = new ArrayList<>();
        for(InfoDTO infoDTO: infoDTOList){
            ComplexInfo complexInfo = new ComplexInfo();
            complexInfo.setAGoodsId(req.getAGoodsId());
            complexInfo.setZGoodsId(infoDTO.getZGoodsId());
            complexInfo.setComplexInfo(infoDTO.getComplexInfo());
            complexInfoList.add(complexInfo);
        }
        int  i = complexInfoMapper.batchInsert(complexInfoList);
        if(i > 0) {
            return true;
        }
        return false;
    }

    public int addComplexInfo(ComplexInfoDTO complexInfoDTO){
        ComplexInfo complexInfo = new ComplexInfo();
        BeanUtils.copyProperties(complexInfoDTO, complexInfo);
        return complexInfoMapper.insert(complexInfo);
    }

    public int deleteOneComplexInfo(String complexInfoId){
        return complexInfoMapper.deleteById(complexInfoId);
    }

    public int deleteComplexInfoByGoodsId(String goodsId){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("a_goods_id",goodsId);
        return complexInfoMapper.delete(queryWrapper);
    }

    public int updateComplexInfo(ComplexInfoDTO complexInfoDTO){
        ComplexInfo complexInfo = new ComplexInfo();
        BeanUtils.copyProperties(complexInfoDTO, complexInfo);
        return complexInfoMapper.updateById(complexInfo);
    }

    public List<ComplexInfo> selectListByGoodsId(List<String> goodsIdList){
        QueryWrapper<ComplexInfo> queryWrapper = new QueryWrapper();
        queryWrapper.in("a_goods_id",goodsIdList);
        return complexInfoMapper.selectList(queryWrapper);
    }
}
