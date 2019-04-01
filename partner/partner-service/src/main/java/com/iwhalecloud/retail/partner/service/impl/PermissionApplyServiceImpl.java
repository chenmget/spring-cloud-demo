package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.dto.PermissionApplyDTO;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.entity.PermissionApply;
import com.iwhalecloud.retail.partner.manager.PermissionApplyManager;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PermissionApplyServiceImpl implements PermissionApplyService {

    @Autowired
    private PermissionApplyManager permissionApplyManager;

    /**
     * 新增商家权限申请单
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> savePermissionApply(PermissionApplySaveReq req) {
        log.info("PermissionApplyServiceImpl.savePermissionApply(), input: PermissionApplySaveReq={} ", JSON.toJSONString(req));
        int result = permissionApplyManager.savePermissionApply(req);
        ResultVO<Integer> resultVO = ResultVO.success(result);
        if (result <= 0) {
            resultVO = ResultVO.error("新增商家权限申请单失败");
        }
        log.info("PermissionApplyServiceImpl.savePermissionApply(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 修改商家权限申请单
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> updatePermissionApply(PermissionApplyUpdateReq req) {
        log.info("PermissionApplyServiceImpl.updatePermissionApply(), input: PermissionApplyUpdateReq={} ", JSON.toJSONString(req));
        int result = permissionApplyManager.updatePermissionApply(req);
        ResultVO<Integer> resultVO = ResultVO.success(result);
        if (result <= 0) {
            resultVO = ResultVO.error("修改商家权限申请单失败");
        }
        log.info("PermissionApplyServiceImpl.updatePermissionApply(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 商家权限申请单列表查询
     * @param req
     * @return
     */
    @Override
    public ResultVO<List<PermissionApplyDTO>> listPermissionApply(PermissionApplyListReq req) {
        log.info("PermissionApplyServiceImpl.listPermissionApply(), input: PermissionApplyUpdateReq={} ", JSON.toJSONString(req));
        List<PermissionApply> entityList = permissionApplyManager.listPermissionApply(req);
        List<PermissionApplyDTO> dtoList = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(entityList)) {
            for (PermissionApply entity : entityList) {
                PermissionApplyDTO  dto = new PermissionApplyDTO();
                BeanUtils.copyProperties(entity, dto);
                dtoList.add(dto);
            }
        }
        ResultVO resultVO = ResultVO.success(dtoList);
        log.info("PermissionApplyServiceImpl.listPermissionApply(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return resultVO;
    }

    /**
     * 商家权限申请单审核通过
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> passPermissionApply(PermissionApplyPassReq req) {
        log.info("PermissionApplyServiceImpl.passPermissionApply(), input: PermissionApplyUpdateReq={} ", JSON.toJSONString(req));
        PermissionApply permissionApply = permissionApplyManager.getPermissionApplyById(req.getApplyId());
        if (Objects.isNull(permissionApply)) {

        }

        // 1、取出申请单对应的子项列表

        return null;
    }

    /**
     * 商家权限申请单审核不通过
     * @param req
     * @return
     */
    @Override
    public ResultVO<Integer> notPassPermissionApply(PermissionApplyNotPassReq req) {
        return null;
    }


}