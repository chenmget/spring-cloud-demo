package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.dto.req.ProdGoodsCatsListByIdReq;
import com.iwhalecloud.retail.goods2b.entity.Cat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: CatMapper
 * @author autoCreate
 */
@Mapper
public interface CatMapper extends BaseMapper<Cat>{

	public List<Cat> listCats(ProdGoodsCatsListByIdReq dto);
}