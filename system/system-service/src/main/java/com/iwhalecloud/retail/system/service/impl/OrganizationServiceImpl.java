package com.iwhalecloud.retail.system.service.impl;

import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.system.dto.OrganizationDTO;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.OrganizationsQueryReq;
import com.iwhalecloud.retail.system.entity.Organization;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.system.manager.OrganizationManager;
import com.iwhalecloud.retail.system.service.OrganizationService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationManager organizationManager;

    @Override
    public ResultVO saveOrganization(OrganizationDTO organizationDTO) {
        int ret = organizationManager.savaOrganization(organizationDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO listOrganization(String parentId) {
        List<Organization> list = organizationManager.listOrganization(parentId);
        List<OrganizationDTO> OrganizationDTOList = new ArrayList<>();
        for(Organization m : list){
            OrganizationDTO dto = new OrganizationDTO();
            BeanUtils.copyProperties(m, dto);
            OrganizationDTOList.add(dto);
        }
        return ResultVO.success(OrganizationDTOList);
    }

    @Override
    public ResultVO getOrganization(String orgId) {
        OrganizationDTO dto = organizationManager.getOrganization(orgId);
        if(dto == null ){
            return ResultVO.error();
        }else{
            return ResultVO.success(dto);
        }
    }

    @Override
    public ResultVO queryOrganizationsForPage(OrganizationsQueryReq organizationsQueryReq) {
        if (organizationsQueryReq.getPageNo() == null || organizationsQueryReq.getPageSize() == null) {
            ResultVO.errorEnum(ResultCodeEnum.LACK_OF_PARAM);
        }
        Page<OrganizationDTO> organizationDTOPage = organizationManager.queryOrganizationsForPage(organizationsQueryReq);
        return ResultVO.success(organizationDTOPage);
    }

    @Override
    public ResultVO deleteOrganization(String orgId) {
        int ret = organizationManager.deleteOrganization(orgId);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }

    @Override
    public ResultVO updateOrganization(OrganizationDTO OrganizationDTO) {
        OrganizationDTO.setUpdateDate(new Date());
        int ret = organizationManager.updateOrganization(OrganizationDTO);
        if(ret > 0){
            return ResultVO.errorEnum(ResultCodeEnum.SUCCESS);
        }else{
            return ResultVO.errorEnum(ResultCodeEnum.ERROR);
        }
    }
}