package com.iwhalecloud.retail.partner.service.impl;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.partner.PartnerServiceApplication;
import com.iwhalecloud.retail.partner.common.PartnerConst;
import com.iwhalecloud.retail.partner.dto.req.*;
import com.iwhalecloud.retail.partner.entity.PermissionApplyItem;
import com.iwhalecloud.retail.partner.service.PermissionApplyItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wenlong.zhong
 * @date 2019/3/30
 */
@SpringBootTest(classes = PartnerServiceApplication.class)
@RunWith(SpringRunner.class)
public class PermissionApplyItemServiceTest {
    @Autowired
    private PermissionApplyItemService permissionApplyItemService;

    @Test
    public void save() {
        PermissionApplyItemSaveReq req = new PermissionApplyItemSaveReq();
        req.setApplyId("10120517");
        req.setMerchantId("12345");
        req.setOperationType(PartnerConst.PermissionApplyItemOperationTypeEnum.ADD.getType());
        req.setRuleType("1");
        req.setTargetType("1");
        req.setTargetId("12345");
        ResultVO resultVO = permissionApplyItemService.savePermissionApplyItem(req);
        System.out.print("结果：" + resultVO.toString());
    }


    @Test
    public void update() {
        PermissionApplyItemUpdateReq req = new PermissionApplyItemUpdateReq();
        req.setApplyItemId("10120547");
//        req.setOperationType(PartnerConst.PermissionApplyItemOperationTypeEnum.UPDATE.getType());
//        req.setMerchantId("1234566");
        ResultVO resultVO = permissionApplyItemService.updatePermissionApplyItem(req);
        System.out.print("结果：" + resultVO.toString());
    }

    @Test
    public void list() {
        PermissionApplyItemListReq req = new PermissionApplyItemListReq();
        req.setApplyId("10120517");
        ResultVO resultVO = permissionApplyItemService.listPermissionApplyItem(req);
        System.out.print("结果：" + resultVO.toString());
    }
}
