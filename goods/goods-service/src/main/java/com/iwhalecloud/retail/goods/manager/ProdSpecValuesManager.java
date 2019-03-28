package com.iwhalecloud.retail.goods.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecValuesGetReq;
import com.iwhalecloud.retail.goods.dto.req.ProdSpecificationDeleteReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdSpecValuesGetResp;
import com.iwhalecloud.retail.goods.entity.ProdSpecValues;
import com.iwhalecloud.retail.goods.mapper.ProdSpecValuesMapper;


@Component
public class ProdSpecValuesManager{
    @Resource
    private ProdSpecValuesMapper prodSpecValuesMapper;

    public ProdSpecValues selectSpecValuesByValueName(String specValue) {
        QueryWrapper<ProdSpecValues> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SPEC_VALUE",specValue);
        return prodSpecValuesMapper.selectOne(queryWrapper);
    }

    public ProdSpecValues selectSpecValueBySpecId(String specValueId) {
        QueryWrapper<ProdSpecValues> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("SPEC_VALUE_ID", specValueId);
        ProdSpecValues prodGoodsSpec = prodSpecValuesMapper.selectOne(queryWrapper);
        return prodGoodsSpec;
    }
    
    /**
     * 通过规格ID获取规格值
     * @param specId
     * @return
     */
    public List<ProdSpecValuesGetResp> querySpecValueBySpecId(String specId) {
    	QueryWrapper<ProdSpecValues> queryWrapper = new QueryWrapper<>();
    	queryWrapper.eq("SPEC_ID", specId);
    	List<ProdSpecValues> specList = prodSpecValuesMapper.selectList(queryWrapper);
    	if (null == specList || specList.isEmpty()) {
			return null;
		}
    	List<ProdSpecValuesGetResp> specValues = new ArrayList<>(specList.size());
    	for (ProdSpecValues t : specList) {
			ProdSpecValuesGetResp dto = new ProdSpecValuesGetResp();
			BeanUtils.copyProperties(t, dto);
			specValues.add(dto);
		}
    	return specValues;
    }
    
    /**
     * 查询规格值（关联查询了规格表的规格类型）
     * @param req
     * @return
     */
    public ProdSpecValuesGetResp getSpecValues(String specValueId) {
        
        ProdSpecValues specValue = prodSpecValuesMapper.getSpecValues(specValueId);
        if (null == specValue) {
			return null;
		}
        ProdSpecValuesGetResp dto = new ProdSpecValuesGetResp();
        BeanUtils.copyProperties(specValue, dto);
        return dto;
    }
    
    /**
     * 添加规格值
     * @param req
     * @return
     */
    public Integer addSpecValue(ProdSpecValuesAddReq req) {
    	ProdSpecValues t = new ProdSpecValues();
    	BeanUtils.copyProperties(req, t);
    	return prodSpecValuesMapper.insert(t);
    }
    
    /**
     * 删除规格值
     * @param req
     * @return
     */
    public Integer deleteSpecValue(ProdSpecificationDeleteReq req) {
    	return prodSpecValuesMapper.deleteSpecValues(req.getIdArray());
    }
    
    /**
     * 批量添加规格值
     * @param idArray
     * @return
     */
    public Integer insertBatchSpecValues(List<ProdSpecValuesGetReq> valuesList){
    	List<ProdSpecValues> list = new ArrayList<>(valuesList.size());
    	for (ProdSpecValuesGetReq dto : valuesList) {
			ProdSpecValues t = new ProdSpecValues();
			BeanUtils.copyProperties(dto, t);
			list.add(t);
		}
    	return prodSpecValuesMapper.insertBatchSpecValues(list);
    }
}
