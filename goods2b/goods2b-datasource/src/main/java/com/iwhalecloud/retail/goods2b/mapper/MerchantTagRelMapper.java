package com.iwhalecloud.retail.goods2b.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.goods2b.dto.MerchantTagRelDTO;
import com.iwhalecloud.retail.goods2b.dto.req.MerchantTagRelListReq;
import com.iwhalecloud.retail.goods2b.entity.MerchantTagRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: MerchantTagRelMapper
 * @author autoCreate
 */
@Mapper
public interface MerchantTagRelMapper extends BaseMapper<MerchantTagRel>{

    List<MerchantTagRelDTO> listMerchantTagRel(@Param("req") MerchantTagRelListReq req);


}