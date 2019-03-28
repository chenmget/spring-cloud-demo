package com.iwhalecloud.retail.oms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.oms.dto.DefaultOperatingDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.DefaultOperatingQueryReq;
import com.iwhalecloud.retail.oms.entity.DefaultOperating;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Class: DefaultOperatingMapper
 * @author autoCreate
 */
@Mapper
public interface DefaultOperatingMapper extends BaseMapper<DefaultOperating>{


    /**
     * 查询内容基础信息
     * @param defaultCategoryId
     * @return
     */
    List<DefaultOperatingDTO> qryDefaultOperation(@Param("defaultCategoryId") String defaultCategoryId);

    int editDefaultOperation(DefaultOperatingDTO defaultOperatingDTO);


    Page<DefaultOperatingDTO> queryoperatingPostionListDTO(Page<DefaultOperatingDTO> page, @Param("pageReq") DefaultOperatingQueryReq defaultOperatingQueryReq)throws Exception;

}