package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplySaveReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyUpdateReq;
import com.iwhalecloud.retail.partner.entity.PermissionApply;
import com.iwhalecloud.retail.partner.mapper.PermissionApplyMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class PermissionApplyManager{
    @Resource
    private PermissionApplyMapper permissionApplyMapper;

    /**
     * 新增商家权限申请单
     * @param req
     * @return
     */
    public Integer savePermissionApply(PermissionApplySaveReq req) {
        PermissionApply permissionApply = new PermissionApply();
        BeanUtils.copyProperties(req, permissionApply);
        permissionApply.setCreateDate(new Date());
        permissionApply.setUpdateDate(new Date());
        permissionApply.setStatusDate(new Date());
        return permissionApplyMapper.insert(permissionApply);
    }

    /**
     * 修改商家权限申请单
     * @param req
     * @return
     */
    public Integer updatePermissionApply(PermissionApplyUpdateReq req) {
        PermissionApply permissionApply = new PermissionApply();
        BeanUtils.copyProperties(req, permissionApply);
        permissionApply.setUpdateDate(new Date());
        return permissionApplyMapper.updateById(permissionApply);
    }

    /**
     * 商家权限申请单列表查询
     * @param req
     * @return
     */
    public List<PermissionApply> listPermissionApply(PermissionApplyListReq req) {
        QueryWrapper<PermissionApply> queryWrapper = new QueryWrapper<PermissionApply>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getMerchantId())){
            hasParam = true;
            queryWrapper.eq(PermissionApply.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if(!StringUtils.isEmpty(req.getApplyType())){
            hasParam = true;
            queryWrapper.like(PermissionApply.FieldNames.applyType.getTableFieldName(), req.getApplyType());
        }

        if (!hasParam) {
            return Lists.newArrayList();
        }
        return permissionApplyMapper.selectList(queryWrapper);
    }


}
