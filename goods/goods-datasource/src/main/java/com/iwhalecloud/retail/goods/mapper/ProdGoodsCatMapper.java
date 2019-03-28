package com.iwhalecloud.retail.goods.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods.dto.req.ProdGoodsCatsListByIdReq;
import com.iwhalecloud.retail.goods.entity.ProdGoodsCat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Class: ProdGoodsCatMapper
 * @author autoCreate
 */
@Mapper
public interface ProdGoodsCatMapper extends BaseMapper<ProdGoodsCat>{

	public List<ProdGoodsCat> listCats(ProdGoodsCatsListByIdReq dto);
}