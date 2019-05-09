package com.iwhalecloud.retail.partner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.partner.dto.req.LSSAddControlReq;
import com.iwhalecloud.retail.partner.dto.resp.ProductIdListResp;

@Mapper
public interface NewParMerchAddProdMapper extends BaseMapper {
	
	public List<String> selectProductIdList();
	
	public void addProd(@Param("req") LSSAddControlReq req);

}
