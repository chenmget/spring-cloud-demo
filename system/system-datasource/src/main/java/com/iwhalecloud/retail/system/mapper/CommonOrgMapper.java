package com.iwhalecloud.retail.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.system.dto.SysCommonOrgResp;
import com.iwhalecloud.retail.system.entity.CommonOrg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Class: CommonOrgMapper
 * @author autoCreate
 */
@Mapper
public interface CommonOrgMapper extends BaseMapper<CommonOrg>{

	public List<SysCommonOrgResp> getSysCommonOrg();
}