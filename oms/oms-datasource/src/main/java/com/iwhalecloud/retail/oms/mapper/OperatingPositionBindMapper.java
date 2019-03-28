package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.BindContentDTO;
import com.iwhalecloud.retail.oms.dto.BindProductDTO;
import com.iwhalecloud.retail.oms.dto.resquest.cloud.OperatingPositionBindReq;
import com.iwhalecloud.retail.oms.entity.OperatingPositionBind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mapper
public interface OperatingPositionBindMapper extends BaseMapper<OperatingPositionBind> {

    int createBindProduct(BindProductDTO dto);

    int createBindContent(BindContentDTO dto);

    int setIsDeleted(@Param("operatingPositionId") String operatingPositionId);

    int batchUpdateOperatingPositionBind(@Param("list")List<OperatingPositionBindReq> operatingPositionBindReqs);

    int unbindContentId(@Param("contentId")String contentId);
}
