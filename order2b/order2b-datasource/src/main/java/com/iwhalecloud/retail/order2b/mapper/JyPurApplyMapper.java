package com.iwhalecloud.retail.order2b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.order2b.dto.response.purapply.JyPurApplyResp;
import com.iwhalecloud.retail.order2b.dto.response.purapply.PurApplyResp;
import com.iwhalecloud.retail.order2b.dto.resquest.purapply.PurApplyReq;
import com.iwhalecloud.retail.order2b.entity.PurApply;

@Mapper
public interface JyPurApplyMapper extends BaseMapper<PurApply>  {

	public Page<JyPurApplyResp> jycgSearchApply(Page<JyPurApplyResp> page,@Param("req") PurApplyReq purApplyReq) ;
}
