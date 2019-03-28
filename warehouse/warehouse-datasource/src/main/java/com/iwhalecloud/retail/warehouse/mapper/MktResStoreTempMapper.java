package com.iwhalecloud.retail.warehouse.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.warehouse.dto.MktResStoreTempDTO;
import com.iwhalecloud.retail.warehouse.dto.request.markres.SynMarkResStoreToFormalReq;
import com.iwhalecloud.retail.warehouse.entity.MktResStoreTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class: MktResStoreTempMapper
 * @author autoCreate
 */
@Mapper
public interface MktResStoreTempMapper extends BaseMapper<MktResStoreTemp>{

    public int updateMktResStoreTemp(MktResStoreTemp data);

    Page<MktResStoreTempDTO> listSynMktResStoreTempDTOList(Page<MktResStoreTempDTO> page, @Param("req") SynMarkResStoreToFormalReq req);


    public int updateMktResStoreTempSyn(List<String> list);

}