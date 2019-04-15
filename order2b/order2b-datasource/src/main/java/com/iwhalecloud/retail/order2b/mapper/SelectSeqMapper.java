package com.iwhalecloud.retail.order2b.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SelectSeqMapper {

    String  getSeq(@Param("seqName") String seqName);
}
