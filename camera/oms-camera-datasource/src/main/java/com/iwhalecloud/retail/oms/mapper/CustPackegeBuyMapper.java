package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.entity.CustPackegeBuyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustPackegeBuyMapper extends BaseMapper<CustPackegeBuyEntity> {

    public List<CustPackegeBuyEntity> queryCustPackegeBuy(String custPhone);
}
