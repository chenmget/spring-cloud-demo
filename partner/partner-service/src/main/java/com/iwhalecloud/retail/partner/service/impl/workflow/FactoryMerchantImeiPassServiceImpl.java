package com.iwhalecloud.retail.partner.service.impl.workflow;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.PermissionApplyItemDTO;
import com.iwhalecloud.retail.partner.dto.req.MerchantRulesDeleteReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemListReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyItemUpdateReq;
import com.iwhalecloud.retail.partner.dto.req.PermissionApplyUpdateReq;
import com.iwhalecloud.retail.partner.manager.PermissionApplyItemManager;
import com.iwhalecloud.retail.partner.service.MerchantRulesService;
import com.iwhalecloud.retail.partner.service.PermissionApplyItemService;
import com.iwhalecloud.retail.partner.service.PermissionApplyService;
import com.iwhalecloud.retail.partner.service.workflow.FactoryMerchantImeiPassService;
import com.iwhalecloud.retail.workflow.config.InvokeRouteServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Service
public class FactoryMerchantImeiPassServiceImpl implements FactoryMerchantImeiPassService {
    @Autowired
    private PermissionApplyService permissionApplyService;

    @Autowired
    private PermissionApplyItemService permissionApplyItemService;

    @Autowired
    private MerchantRulesService merchantRulesService;

    @Autowired
    private PermissionApplyItemManager permissionApplyItemManager;

    @Override
    public ResultVO run(InvokeRouteServiceRequest params) {
        log.info("FactoryMerchantImeiPassServiceImpl.run params={}", JSON.toJSONString(params));
        if (params == null) {
            return ResultVO.error(ResultCodeEnum.LACK_OF_PARAM);
        }
        String applyId = params.getBusinessId();
        //将权限申请表状态改为通过
        PermissionApplyUpdateReq permissionApplyUpdateReq = new PermissionApplyUpdateReq();
        permissionApplyUpdateReq.setApplyId(applyId);
        permissionApplyUpdateReq.setStatusCd(PartnerConst.PermissionApplyStatusEnum.PASS.getCode());
        permissionApplyService.updatePermissionApply(permissionApplyUpdateReq);
        //根据applyId获取申请明细列表
        PermissionApplyItemListReq req = new PermissionApplyItemListReq();
        req.setApplyId(applyId);
        ResultVO<List<PermissionApplyItemDTO>> resultVO = permissionApplyItemService.listPermissionApplyItem(req);
        if (!resultVO.isSuccess() || null == resultVO.getResultData()) {
            return ResultVO.error("未找到相关申请单");
        }
        List<PermissionApplyItemDTO> itemList = resultVO.getResultData();
        List<String> merchantRuleIdList = itemList.stream().map(t -> t.getMerchantRuleId()).collect(Collectors.toList());
        //将厂商的权限移除
        if (!CollectionUtils.isEmpty(itemList)) {
            MerchantRulesDeleteReq merchantRulesDeleteReq = new MerchantRulesDeleteReq();
            merchantRulesDeleteReq.setMerchantRuleIdList(merchantRuleIdList);
            merchantRulesService.deleteMerchantRules(merchantRulesDeleteReq);
        }
        //将权限申请明细状态改为失效
        PermissionApplyItemUpdateReq updateReq = new PermissionApplyItemUpdateReq();
        updateReq.setApplyId(applyId);
        updateReq.setStatusCd(PartnerConst.TelecomCommonState.INVALID.getCode());
        permissionApplyItemManager.updateStatus(updateReq);
        return ResultVO.success();
    }
}
