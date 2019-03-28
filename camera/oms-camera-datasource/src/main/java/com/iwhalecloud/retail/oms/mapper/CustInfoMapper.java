package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.entity.CustInfoEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustInfoMapper extends BaseMapper<CustInfoEntity> {
    public CustInfoEntity queryCustInfo(String custPhone);
}
