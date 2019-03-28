package com.iwhalecloud.retail.goods.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.goods.dto.req.ProdCatComplexAddReq;
import com.iwhalecloud.retail.goods.entity.ProdCatComplex;
import com.iwhalecloud.retail.goods.mapper.ProdCatComplexMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


@Component
public class ProdCatComplexManager{
    @Resource
    private ProdCatComplexMapper prodCatComplexMapper;
    
    public Integer insert(ProdCatComplexAddReq dto){
    	ProdCatComplex t = new ProdCatComplex();
    	BeanUtils.copyProperties(dto, t);
    	t.setCreateTime(new Date());
    	return prodCatComplexMapper.insert(t);
    }

    /**
     * 根据分类ID删除记录
     * @param catId
     * @return
     */
    public Integer delete(String catId) {
        QueryWrapper<ProdCatComplex> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("cat_id",catId);
        return prodCatComplexMapper.delete(deleteWrapper);
    }
    
}
