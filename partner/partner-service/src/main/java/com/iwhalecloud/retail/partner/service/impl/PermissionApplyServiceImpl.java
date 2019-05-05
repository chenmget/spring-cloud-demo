package com.iwhalecloud.retail.partner.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.PermissionApplyDTO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemSaveReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplySaveDTO;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplySaveReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyUpdateReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyAuditReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemUpdateReq;
import com.iwhalecloud.retail.partner.entity.MerchantRules;
import com.iwhalecloud.retail.partner.entity.PermissionApply;
import com.iwhalecloud.retail.partner.entity.PermissionApplyItem;
import com.iwhalecloud.retail.partner.manager.MerchantRulesManager;
import com.iwhalecloud.retail.partner.manager.PermissionApplyItemManager;
import com.iwhalecloud.retail.partner.manager.PermissionApplyManager;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import com.iwhalecloud.retail.workflow.common.WorkFlowConst;
import com.iwhalecloud.retail.workflow.dto.req.ProcessStartReq;
import com.iwhalecloud.retail.workflow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PermissionApplyServiceImpl implements PermissionApplyService {

    @Autowired
    private PermissionApplyManager permissionApplyManager;

    @Autowired
    private PermissionApplyItemManager permissionApplyItemManager;

    @Autowired
    private MerchantRulesManager merchantRulesManager;

    @Reference
    private TaskService taskService;

    /**
     * 新增商家权限申请单
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public ResultVO<String> savePermissionApply(PermissionApplySaveDTO req) throws Exception {
        log.info("PermissionApplyServiceImpl.savePermissionApply(), input: PermissionApplySaveReq={} ", JSON.toJSONString(req));
        // 判空
        if (StringUtils.isEmpty(req.getUserId())) {
            return ResultVO.error("用户ID不能为空");
        }
        if (StringUtils.isEmpty(req.getMerchantId())) {
            return ResultVO.error("商家ID不能为空");
        }
        if (Objects.isNull(req.getPermissionApplySaveReq())) {
            return ResultVO.error("商家权限申请单信息不能为空");
        }
        if (CollectionUtils.isEmpty(req.getItemList())) {
            return ResultVO.error("商家权限申请单项列表不能为空");
        }

        // 1、新增申请单信息 (设置必要信息）
        PermissionApplySaveReq applySaveReq = req.getPermissionApplySaveReq();
        applySaveReq.setCreateStaff(req.getUserId());
        applySaveReq.setUpdateStaff(req.getUserId());
        applySaveReq.setMerchantId(req.getMerchantId());
        applySaveReq.setStatusCd(PartnerConst.PermissionApplyStatusEnum.AUDITING.getCode());
//        applySaveReq.setApplyType(PartnerConst.PermissionApplyTypeEnum.PERMISSION_APPLY.getCode());
        String applyId = permissionApplyManager.savePermissionApply(applySaveReq);
        if (StringUtils.isEmpty(applyId)) {
           return ResultVO.error("新增商家权限申请单失败");
        }

        // 2、新增申请单项列表信息
        // 批量插入
        List<PermissionApplyItem> entityList = Lists.newArrayList();
        for (PermissionApplyItemSaveReq item : req.getItemList()) {
            PermissionApplyItem entity = new PermissionApplyItem();
            BeanUtils.copyProperties(item, entity);
            // 设置其他信息
            entity.setApplyId(applyId);
            entity.setMerchantId(req.getMerchantId());
            entity.setStatusCd(PartnerConst.TelecomCommonState.VALID.getCode());
            entity.setOperationType(PartnerConst.PermissionApplyItemOperationTypeEnum.ADD.getType());
            entity.setCreateStaff(req.getUserId());
            entity.setUpdateStaff(req.getUserId());
            entity.setCreateDate(new Date());
            entity.setUpdateDate(new Date());

            entityList.add(entity);
        }
        if (!permissionApplyItemManager.saveBatch(entityList)) {
            log.info("PermissionApplyServiceImpl.savePermissionApply(), 商家权限 申请单项列表 批量插入失败");
            throw new Exception("商家权限 申请单项列表 批量插入失败");
        }

        // 启动审核工作流
        // 3.调用外部接口 发起审核流程
        // 发起流程到零售，由零售商决定是否接受
        // 流程ID写死 11，业务ID 存申请单ID
        ProcessStartReq processStartDTO = new ProcessStartReq();
        processStartDTO.setTitle("商家权限申请审核流程");
        //创建流程者， 参数需要提供
        processStartDTO.setApplyUserId(req.getUserId());
        processStartDTO.setApplyUserName(req.getName());
        processStartDTO.setProcessId("11"); // 写死（商家权限申请审核流程ID）
        processStartDTO.setFormId(applyId);
//        processStartDTO.setExtends1(extends1);
        //TASK_SUB_TYPE_2060 商家权限申请审核流程
        processStartDTO.setTaskSubType(WorkFlowConst.TASK_SUB_TYPE.TASK_SUB_TYPE_2060.getTaskSubType());
        // 指定下一环节处理人 (可以不指定 流程里面有配置）
//                HandlerUser user = new HandlerUser();
//                user.setHandlerUserId(merchantDTO.getUserId());
//                user.setHandlerUserName(merchantDTO.getMerchantName());
//                List<HandlerUser> uerList = new ArrayList<HandlerUser>(1);
//                processStartDTO.setNextHandlerUser(uerList);
        ResultVO taskServiceRV = new ResultVO();
        try {
            //开启流程
            taskServiceRV = taskService.startProcess(processStartDTO);
//            return ResultVO.success();
        }
        catch (Exception ex) {
            return ResultVO.error("开启申请审核流程失败");
        }
        finally {
            log.info("TaskService.startProcess req={},resp={}",
                    JSON.toJSONString(processStartDTO), JSON.toJSONString(taskServiceRV));
        }

//        log.info("PermissionApplyServiceImpl.savePermissionApply(), output: resultVO={} ", JSON.toJSONString(resultVO));
        return ResultVO.success(applyId);
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
        if (!CollectionUtils.isEmpty(entityList)) {
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
     * 商家权限申请单审核（通过或不通过）
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackForClassName = "Exception")
    public ResultVO<Integer> auditPermissionApply(PermissionApplyAuditReq req) throws Exception {
        log.info("PermissionApplyServiceImpl.auditPermissionApply(), input: PermissionApplyUpdateReq={} ", JSON.toJSONString(req));
        PermissionApply permissionApply = permissionApplyManager.getPermissionApplyById(req.getApplyId());
        if (Objects.isNull(permissionApply)) {
            log.info("PermissionApplyServiceImpl.auditPermissionApply(), ID为{}的商家权限申请单信息获取有误 ", JSON.toJSONString(req.getApplyId()));
            return ResultVO.error("商家权限申请单信息有误");
        }
        if (StringUtils.equals(permissionApply.getStatusCd(), PartnerConst.PermissionApplyStatusEnum.AUDITING.getCode())) {
            log.info("PermissionApplyServiceImpl.auditPermissionApply(), ID为{}的商家权限申请单状态为非审核中，不能进行审核操作 ", JSON.toJSONString(req.getApplyId()));
            return ResultVO.error("商家权限申请单状态为非审核中，不能进行审核操作");
        }

        // 1、取出申请单对应的有效子项列表
        PermissionApplyItemListReq itemListReq = new PermissionApplyItemListReq();
        itemListReq.setApplyId(req.getApplyId());
        itemListReq.setStatusCd(PartnerConst.TelecomCommonState.VALID.getCode());
        List<PermissionApplyItem> itemList = permissionApplyItemManager.listPermissionApplyItem(itemListReq);
        // 2 更新申请单项
        if (!CollectionUtils.isEmpty(itemList)) {
            // 申请单项（申请单审核 通过或不通过） 都更新为失效
            if (!updateItemStatus(req.getApplyId(), PartnerConst.TelecomCommonState.INVALID.getCode())) {
                log.info("PermissionApplyServiceImpl.passPermissionApply(), 申请单项 更新失败 ");
                throw new RuntimeException("申请单项 更新失败");
            }

            // 3、审核通过 添加数据到 商家权限表
            if (StringUtils.equals(req.getStatusCd(), PartnerConst.PermissionApplyStatusEnum.PASS.getCode())) {
                if (!addItemToRules(itemList)) {
                    log.info("PermissionApplyServiceImpl.passPermissionApply(), 添加数据到 商家权限表 失败");
                    throw new Exception("添加数据到 商家权限表 失败");
                }
            }
        }

        // 4、更新申请单 状态
        PermissionApplyUpdateReq updateReq = new PermissionApplyUpdateReq();
        updateReq.setApplyId(req.getApplyId());
        updateReq.setUpdateStaff(req.getUpdateStaff());
        updateReq.setStatusCd(req.getStatusCd());
        int updateInt = permissionApplyManager.updatePermissionApply(updateReq);
        if (updateInt <= 0) {
            log.info("PermissionApplyServiceImpl.passPermissionApply(), 商家权限 申请单{} 状态 更新失败", JSON.toJSONString(req.getApplyId()));
            throw new Exception("商家权限 申请单 状态 更新失败");
        }

        return ResultVO.successMessage("审核成功");
    }


    /**
     * 根据申请单ID 批量 修改商家权限申请单子项状态
     * @param applyId 申请单ID
     * @param status 状态
     * @return
     */
    private Boolean updateItemStatus(String applyId, String status) {
        PermissionApplyItemUpdateReq updateReq = new PermissionApplyItemUpdateReq();
        updateReq.setApplyId(applyId);
        updateReq.setStatusCd(status);
        int i = permissionApplyItemManager.updateStatus(updateReq);
        return i > 0;
    }

    /**
     * 批量插入到 商家权限表
     * @param itemList
     * @return
     */
    private Boolean addItemToRules(List<PermissionApplyItem> itemList) {
        // 批量插入
        List<MerchantRules> merchantRulesList = Lists.newArrayList();
        for (PermissionApplyItem item : itemList) {
            MerchantRules merchantRules = new MerchantRules();
            merchantRules.setMerchantId(item.getMerchantId());
            merchantRules.setRuleType(item.getRuleType());
            merchantRules.setTargetType(item.getTargetType());
            merchantRules.setTargetId(item.getTargetId());
            merchantRulesList.add(merchantRules);
        }
        return merchantRulesManager.saveBatch(merchantRulesList);
    }

}