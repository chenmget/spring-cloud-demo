package com.iwhalecloud.retail.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.oms.dto.CloudShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailDTO;
import com.iwhalecloud.retail.oms.dto.ShelfDetailUpdateDTO;
import com.iwhalecloud.retail.oms.entity.CloudShelfDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ShelfDetailMapper extends BaseMapper<CloudShelfDetail> {
    int createShelfDetail(ShelfDetailDTO dto);

    int editShelfDetail(ShelfDetailUpdateDTO dto);

    int deleteShelfDetail(ShelfDetailDTO dto);

    List<ShelfDetailDTO> queryCloudShelfDetailProductList(HashMap<String, Object> map);

    List<ShelfDetailDTO> queryCloudShelfDetailContentList(HashMap<String, Object> map);

    List<ShelfDetailDTO>  queryShelfDetailList(@Param("cloudShelfNumber") String cloudShelfNumber);

    int batchUpdateShelfDetai(@Param("list") List<CloudShelfDetailDTO> list);

    public List<ShelfDetailDTO> queryCloudShelfDetailList(CloudShelfDetailDTO cloudShelfDetailDTO);


    List<ShelfDetailDTO> queryCloudShelfDetailByProductId(@Param("productId") String productId);

}
