package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.entity.TRandomLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RandomLogMapper extends BaseMapper<TRandomLog> {

    String selectLogIdByRandomCode(TRandomLog req);

    int insertSelective(TRandomLog req);

    int updateByPrimaryKey(TRandomLog req);
}
