package com.iwhalecloud.retail.order2b.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SelectSeqMapper {

    String  getSeq(String seqName);
}
