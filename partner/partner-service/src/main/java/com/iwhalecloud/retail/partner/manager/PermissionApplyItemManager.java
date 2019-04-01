package com.iwhalecloud.retail.partner.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemSaveReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemUpdateReq;
import com.iwhalecloud.retail.partner.entity.PermissionApplyItem;
import com.iwhalecloud.retail.partner.mapper.PermissionApplyItemMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class PermissionApplyItemManager{
    @Resource
    private PermissionApplyItemMapper permissionApplyItemMapper;

    /**
     * 新增商家权限申请单子项
     * @param req
     * @return
     */
    public Integer savePermissionApplyItem(PermissionApplyItemSaveReq req) {
        PermissionApplyItem entity = new PermissionApplyItem();
        BeanUtils.copyProperties(req, entity);
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        entity.setStatusDate(new Date());
        return permissionApplyItemMapper.insert(entity);
    }

    /**
     * 修改商家权限申请单子项
     * @param req
     * @return
     */
    public Integer updatePermissionApplyItem(PermissionApplyItemUpdateReq req) {
        PermissionApplyItem entity = new PermissionApplyItem();
        BeanUtils.copyProperties(req, entity);
        entity.setUpdateDate(new Date());

        return permissionApplyItemMapper.updateById(entity);
    }

    /**
     * 商家权限申请单列表查询子项
     * @param req
     * @return
     */
    public List<PermissionApplyItem> listPermissionApplyItem(PermissionApplyItemListReq req) {
        QueryWrapper<PermissionApplyItem> queryWrapper = new QueryWrapper<PermissionApplyItem>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getMerchantId())){
            hasParam = true;
            queryWrapper.eq(PermissionApplyItem.FieldNames.merchantId.getTableFieldName(), req.getMerchantId());
        }
        if(!StringUtils.isEmpty(req.getApplyId())){
            hasParam = true;
            queryWrapper.eq(PermissionApplyItem.FieldNames.applyId.getTableFieldName(), req.getApplyId());
        }

        if (!hasParam) {
            return Lists.newArrayList();
        }
        return permissionApplyItemMapper.selectList(queryWrapper);

    }


}
