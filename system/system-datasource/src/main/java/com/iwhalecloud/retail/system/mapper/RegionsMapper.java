package com.iwhalecloud.retail.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.system.dto.request.RegionsListReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.entity.Regions;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionsMapper extends BaseMapper<Regions> {

    /**
     *查询有上级行政区的地址列表
     * @param req
     * @return
     */
    List<RegionsGetResp> listChildren(RegionsListReq req);

    /**
     *查询省、市、区列表
     * @param regionGrades
     * @return
     */
    List<RegionsGetResp> listDepartment(List<String> regionGrades);

    /**
     * 根据localName获得所属区域
     * @param localName
     * @return
     */
    RegionsGetResp getRegionsByName(String localName);

    /**
     * 根据ID集合查询
     * @param regionIds
     * @return
     */
    public List<RegionsGetResp> getRegionList(List<String> regionIds);
}