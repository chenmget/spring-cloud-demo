package com.iwhalecloud.retail.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.system.entity.Lan;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LanMapper extends BaseMapper<Lan> {
    int insert(Lan record);

    int insertSelective(Lan record);
}