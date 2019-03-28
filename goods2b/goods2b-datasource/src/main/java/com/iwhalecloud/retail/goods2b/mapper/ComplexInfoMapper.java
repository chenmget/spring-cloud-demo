package com.iwhalecloud.retail.goods2b.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.entity.ComplexInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2019/2/27.
 */
@Mapper
public interface ComplexInfoMapper extends BaseMapper<ComplexInfo> {

    public int batchInsert(List<ComplexInfo> list);

}
