package com.iwhalecloud.retail.goods.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandAddReq;
import com.iwhalecloud.retail.goods.dto.req.ProdBrandUpdateReq;
import com.iwhalecloud.retail.goods.dto.resp.ProdBrandGetResp;
import com.iwhalecloud.retail.goods.entity.ProdBrand;
import com.iwhalecloud.retail.goods.mapper.ProdBrandMapper;


@Component
public class ProdBrandManager{
    @Resource
    private ProdBrandMapper prodBrandMapper;
    
    /**
     * 根据主键删除
     * @param brandId
     * @return
     */
    public Integer deleteByPrimaryKey(String brandId){
    	return prodBrandMapper.deleteByPrimaryKey(brandId);
    }

    
    /**
     * 全量插入
     * @param record
     * @return
     */
    public Integer insert(ProdBrandAddReq record){
    	ProdBrand t = new ProdBrand();
    	BeanUtils.copyProperties(record, t);
    	return prodBrandMapper.insert(t);
    }

    /**
     * 添加
     * @param t
     * @return
     */
    public Integer insertSelective(ProdBrand t){
//    	ProdBrand t = new ProdBrand();
//    	BeanUtils.copyProperties(record, t);
    	return prodBrandMapper.insert(t);
    }

    /**
     * 根据主键查询
     * @param brandId
     * @return
     */
    public ProdBrandGetResp selectByPrimaryKey(String brandId){
    	ProdBrand prodBrand = prodBrandMapper.selectByPrimaryKey(brandId);
    	if (null == prodBrand) {
			return null;
		}
    	ProdBrandGetResp dto = new ProdBrandGetResp();
    	BeanUtils.copyProperties(prodBrand, dto);
    	return dto;
    }

    /**
     * 部分更新
     * @param record
     * @return
     */
    public Integer updateByPrimaryKeySelective(ProdBrandUpdateReq record){
    	ProdBrand t = new ProdBrand();
    	BeanUtils.copyProperties(record, t);
    	return prodBrandMapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 全量更新
     * @param record
     * @return
     */
    public Integer updateByPrimaryKey(ProdBrandUpdateReq record){
    	ProdBrand t = new ProdBrand();
    	BeanUtils.copyProperties(record, t);
    	return prodBrandMapper.updateByPrimaryKey(t);
    }
    
    /**
     * 查询全部
     * @return
     */
    public List<ProdBrandGetResp> listAll(){
    	List<ProdBrand> listAll = prodBrandMapper.listAll();
    	if (null == listAll || listAll.isEmpty()) {
			return null;
		}
    	List<ProdBrandGetResp> brandGetRespList = new ArrayList<ProdBrandGetResp>();
    	for (ProdBrand prodBrand : listAll) {
    		ProdBrandGetResp dto = new ProdBrandGetResp();
			BeanUtils.copyProperties(prodBrand, dto);
			brandGetRespList.add(dto);
		}
    	return brandGetRespList;
    }
    
    public List<ProdBrand> selectSpecByName(String name) {
        QueryWrapper<ProdBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NAME", name);
        List<ProdBrand> selectList = prodBrandMapper.selectList(queryWrapper);
        return selectList;
    }
    
}
