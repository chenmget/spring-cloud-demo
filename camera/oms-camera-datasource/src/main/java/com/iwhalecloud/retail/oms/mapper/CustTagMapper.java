package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.entity.CustTagEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustTagMapper extends BaseMapper<CustTagEntity> {

    public List<CustTagEntity> queryCustTag(String custPhone);
}
