package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.entity.CustPackegeFitEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustPackegeFitMapper extends BaseMapper<CustPackegeFitEntity> {

    public List<CustPackegeFitEntity> queryCustPackegeFit(String custPhone);

}
