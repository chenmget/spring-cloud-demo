package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.PermissionApplyDTO;
import com.iwhalecloud.retail.partner.dto.PermissionApplyItemDTO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemSaveReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemUpdateReq;
import com.iwhalecloud.retail.partner.entity.PermissionApplyItem;
import com.iwhalecloud.retail.partner.manager.PermissionApplyItemManager;
import com.iwhalecloud.retail.partner.service.PermissionApplyItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class PermissionApplyItemServiceImpl implements PermissionApplyItemService {

    @Autowired
    private PermissionApplyItemManager permissionApplyItemManager;

    /**
     * 新增商家权限申请单子项
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> savePermissionApplyItem(PermissionApplyItemSaveReq req) {
        log.info("PermissionApplyItemServiceImpl.savePermissionApplyItem(), input: PermissionApplyItemSaveReq={} ", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getStatusCd())) {
            req.setStatusCd(PartnerConst.TelecomCommonState.VALID.getCode());
        }
        int result = permissionApplyItemManager.savePermissionApplyItem(req);
        ResultVO<Integer> resultVO = ResultVO.success(result);
        if (result <= 0) {
            resultVO = ResultVO.error("新增商家权限申请单子项失败");
        }
        log.info("PermissionApplyItemServiceImpl.savePermissionApplyItem(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 修改商家权限申请单子项
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updatePermissionApplyItem(PermissionApplyItemUpdateReq req) {
        log.info("PermissionApplyItemServiceImpl.updatePermissionApplyItem(), input: PermissionApplyUpdateReq={} ", JSON.toJSONString(req));
        int result = permissionApplyItemManager.updatePermissionApplyItem(req);
        ResultVO<Integer> resultVO = ResultVO.success(result);
        if (result <= 0) {
            resultVO = ResultVO.error("修改商家权限申请单子项失败");
        }
        log.info("PermissionApplyItemServiceImpl.updatePermissionApplyItem(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 商家权限申请单列表查询子项
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<PermissionApplyItemDTO>> listPermissionApplyItem(PermissionApplyItemListReq req) {
        log.info("PermissionApplyItemServiceImpl.listPermissionApplyItem(), input: PermissionApplyItemListReq={} ", JSON.toJSONString(req));
        List<PermissionApplyItem> entityList = permissionApplyItemManager.listPermissionApplyItem(req);
        List<PermissionApplyItemDTO> dtoList = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(entityList)) {
            for (PermissionApplyItem entity : entityList) {
                PermissionApplyItemDTO  dto = new PermissionApplyItemDTO();
                BeanUtils.copyProperties(entity, dto);
                dtoList.add(dto);
            }
        }
        ResultVO resultVO = ResultVO.success(dtoList);
        log.info("PermissionApplyItemServiceImpl.listPermissionApplyItem(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }




}