package com.iwhalecloud.retail.system.manager;

import com.iwhalecloud.retail.system.dto.request.RegionsGetReq;
import com.iwhalecloud.retail.system.dto.request.RegionsListReq;
import com.iwhalecloud.retail.system.dto.response.RegionsGetResp;
import com.iwhalecloud.retail.system.entity.Regions;
import com.iwhalecloud.retail.system.mapper.RegionsMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RegionsManager{
    @Resource
    private RegionsMapper regionsMapper;


    /**
     * 查询省、市、区列表
     * @param req
     * @return
     */
    public List<RegionsGetResp> listRegions(RegionsListReq req){
        List<String> regionGrades = req.getRegionGrades();
        if (null != regionGrades && !regionGrades.isEmpty()) {
            return regionsMapper.listDepartment(regionGrades);
        } else {
            return regionsMapper.listChildren(req);
        }
    }

    /**
     * 根据Id获得所属区域
     * @param req
     * @return
     */
    public RegionsGetResp getRegion(RegionsGetReq req){
        if (RegionsGetReq.ID_COND_TYPE.equals(req.getRegionCondType())) {
            Regions t = regionsMapper.selectById(req.getRegionId());
            if (null == t) {
                return null;
            }
            RegionsGetResp dto = new RegionsGetResp();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }else {
            return regionsMapper.getRegionsByName(req.getRegionName());
        }
    }

    /**
     * 根据Id获得所属区域
     * @param regionId
     * @return
     */
    public RegionsGetResp getPregionId(String regionId){

        Regions t = regionsMapper.selectById(regionId);
        if (null == t) {
            return null;
        }
        RegionsGetResp dto = new RegionsGetResp();
        BeanUtils.copyProperties(t, dto);
        return dto;
    }

    /**
     * 根据ID集合查询
     * @param regionIds
     * @return
     */
    public List<RegionsGetResp> getRegionList(List<String> regionIds){
        return regionsMapper.getRegionList(regionIds);
    }
}
