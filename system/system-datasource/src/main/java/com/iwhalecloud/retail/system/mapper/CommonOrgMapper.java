package com.iwhalecloud.retail.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.system.dto.SysCommonOrgRequest;
import com.iwhalecloud.retail.system.dto.SysCommonOrgResp;
import com.iwhalecloud.retail.system.entity.CommonOrg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author autoCreate
 * @Class: CommonOrgMapper
 */
@Mapper
public interface CommonOrgMapper extends BaseMapper<CommonOrg> {

    public List<SysCommonOrgResp> getSysCommonOrg(@Param("req") SysCommonOrgRequest req);
}