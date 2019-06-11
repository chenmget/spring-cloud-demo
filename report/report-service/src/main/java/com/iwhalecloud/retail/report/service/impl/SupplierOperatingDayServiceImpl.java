package com.iwhalecloud.retail.report.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.dto.SupplierOperatingDayDTO;
import com.iwhalecloud.retail.report.dto.request.SupplierOperatingDayPageReq;
import com.iwhalecloud.retail.report.manager.SupplierOperatingDayManager;
import com.iwhalecloud.retail.report.service.SupplierOperatingDayService;
import com.iwhalecloud.retail.system.dto.CommonRegionDTO;
import com.iwhalecloud.retail.system.dto.request.CommonRegionListReq;
import com.iwhalecloud.retail.system.service.CommonRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author wenlong.zhong
 * @date 2019/6/10
 */
@Service
@Slf4j
public class SupplierOperatingDayServiceImpl implements SupplierOperatingDayService {

    @Autowired
    private SupplierOperatingDayManager supplierOperatingDayManager;

    @Reference
    private CommonRegionService commonRegionService;

    /**
     * 地包进销存 数据 分页查询
     * @return
     */
    @Override
    public ResultVO<Page<SupplierOperatingDayDTO>> page(SupplierOperatingDayPageReq req) {
        log.info("SupplierOperatingDayServiceImpl.page(), input：req={} ", JSON.toJSONString(req));
        Page<SupplierOperatingDayDTO> respPage = supplierOperatingDayManager.page(req);

        /***** 其他表字段  统一获取  避免循环获取  不然导出调用时可能会出现超时问题 *****/

        if (!CollectionUtils.isEmpty(respPage.getRecords())) {

            // 取本地网  市县  ID集合
            HashSet<String> regionIdHashSet = new HashSet<>(); // 去重
            for (SupplierOperatingDayDTO dto : respPage.getRecords()) {
                regionIdHashSet.add(dto.getCityId());
                regionIdHashSet.add(dto.getCountyId());
            }

            Map<String, String> regionNamesMap = getRegionNamesMap(regionIdHashSet);

            // 取本地网名称  市县名称
            for (SupplierOperatingDayDTO dto : respPage.getRecords()) {
                // 取本地网名称  市县名称
                dto.setCityName(regionNamesMap.get(dto.getCityId()));
                dto.setCountyName(regionNamesMap.get(dto.getCountyId()));
            }
        }

        log.info("SupplierOperatingDayServiceImpl.page(), output：list={} ", JSON.toJSONString(respPage.getRecords()));
        return ResultVO.success(respPage);
    }


    /**
     * 根据regionId集合获取所有的  区域名称
     * @param regionIdHashSet
     * @return
     */
    private Map<String, String> getRegionNamesMap(HashSet<String> regionIdHashSet) {
        CommonRegionListReq req = new CommonRegionListReq();
        req.setRegionIdList(Lists.newArrayList(regionIdHashSet));
        List<CommonRegionDTO> dtoList = commonRegionService.listCommonRegion(req).getResultData();
        Map resultMap = new HashMap();
        if (!CollectionUtils.isEmpty(dtoList)) {
            dtoList.forEach(commonRegionDTO -> {
                resultMap.put(commonRegionDTO.getRegionId(), commonRegionDTO.getRegionName());
            });
        }
        return resultMap;
    }

}
