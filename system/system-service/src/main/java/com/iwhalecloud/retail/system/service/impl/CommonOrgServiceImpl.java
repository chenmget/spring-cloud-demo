package com.iwhalecloud.retail.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.common.SystemConst;
import com.iwhalecloud.retail.system.dto.CommonOrgDTO;
import com.iwhalecloud.retail.system.dto.SysCommonOrg;
import com.iwhalecloud.retail.system.dto.SysCommonOrgReq;
import com.iwhalecloud.retail.system.dto.SysCommonOrgResp;
import com.iwhalecloud.retail.system.dto.request.CommonOrgListReq;
import com.iwhalecloud.retail.system.dto.request.CommonOrgPageReq;
import com.iwhalecloud.retail.system.entity.CommonOrg;
import com.iwhalecloud.retail.system.manager.CommonOrgManager;
import com.iwhalecloud.retail.system.service.CommonOrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 通用组织信息服务接口实现类
 * @author lipeng
 */
@Slf4j
@Service
public class CommonOrgServiceImpl implements CommonOrgService {

    @Autowired
    private CommonOrgManager commonOrgManager;

    /**
     * 跟orgId获取通用组织信息
     * @param orgId
     * @return
     */
    @Override
    public ResultVO<CommonOrgDTO> getCommonOrgById(String orgId) {
        log.info("CommonOrgServiceImpl.getCommonOrgById(), input：orgId={} ", orgId);

        CommonOrg commonOrg = commonOrgManager.getCommonOrgById(orgId);
        CommonOrgDTO commonOrgDTO = new CommonOrgDTO();
        if (commonOrg != null) {
            BeanUtils.copyProperties(commonOrg, commonOrgDTO);
        } else {
            commonOrgDTO = null;
        }
        log.info("CommonOrgServiceImpl.getCommonOrgById(), output：commonOrgDTO={} ", JSON.toJSONString(commonOrgDTO));
        return ResultVO.success(commonOrgDTO);
    }




    /**
     * 获取本地区域 列表
     * @param req
     * @param isSelectAll 是否查询全部
     * @return
     */
    @Override
    public ResultVO<List<CommonOrgDTO>> listCommonOrg(CommonOrgListReq req,boolean isSelectAll) {
        log.info("CommonOrgServiceImpl.listCommonOrg(), input：req={} ", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getParentOrgId())
                && CollectionUtils.isEmpty(req.getOrgIdList())
                && StringUtils.isEmpty(req.getOrgName())&&!isSelectAll) {
            // 三个条件都为空 默认查湖南的 第一级通用组织信息
            req.setParentOrgId(SystemConst.HN_DEFAULT_PARENT_ORG_ID);
        }
        List<CommonOrg> commonOrgList = commonOrgManager.listCommonOrg(req);
        //组装组织返回列表
        List<CommonOrgDTO> commonOrgDTOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(commonOrgList)) {
            commonOrgList.forEach(commonOrg -> {
                CommonOrgDTO commonOrgDTO = new CommonOrgDTO();
                BeanUtils.copyProperties(commonOrg, commonOrgDTO);
                commonOrgDTOList.add(commonOrgDTO);
            });
        }
        log.info("CommonOrgServiceImpl.listCommonOrg(), output：commonOrgDTOList={} ", JSON.toJSONString(commonOrgDTOList));
        return ResultVO.success(commonOrgDTOList);
    }


    /**
     * 分页 获取本地区域 列表
     * @param req
     * @return
     */
    @Override
    public ResultVO<Page<CommonOrgDTO>> pageCommonOrg(CommonOrgPageReq req) {
        log.info("CommonOrgServiceImpl.pageCommonOrg(), input：req={} ", JSON.toJSONString(req));
        Page<CommonOrgDTO> respPage = commonOrgManager.pageCommonOrg(req);
        log.info("CommonOrgServiceImpl.pageCommonOrg(), output：list={} ", JSON.toJSONString(respPage.getRecords()));
        return ResultVO.success(respPage);
    }
    
    /**
     * 获取经营单元
     */
    @Override
    public ResultVO<List<SysCommonOrgResp>> getSysCommonOrg(SysCommonOrg req) {
    	log.info("CommonOrgServiceImpl.getSysCommonOrg() start ******************************** ");
    	List<SysCommonOrgResp>  respList = commonOrgManager.getSysCommonOrg(req);
    	return ResultVO.success(respList);
    }

}