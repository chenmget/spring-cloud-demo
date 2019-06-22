package com.iwhalecloud.retail.goods2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.dto.ProdFileDTO;
import com.iwhalecloud.retail.goods2b.entity.ProdFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProdFileMapper extends BaseMapper<ProdFile> {
	
	public ProdFileDTO queryGoodsImageHD(@Param("goodsId") String goodsId);
	
	public List<ProdFileDTO> queryGoodsImageHDdetail(@Param("goodsId") String goodsId) ;
	
	public List<ProdFileDTO> queryGoodsImage(@Param("goodsId") String goodsId) ;
}