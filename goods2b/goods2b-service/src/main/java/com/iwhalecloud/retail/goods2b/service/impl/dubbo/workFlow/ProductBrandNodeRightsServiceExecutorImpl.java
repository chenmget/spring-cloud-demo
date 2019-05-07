package com.iwhalecloud.retail.goods2b.service.impl.dubbo.workFlow;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.goods2b.common.ProductConst;
import com.iwhalecloud.retail.goods2b.dto.resp.ProductBaseGetResp;
import com.iwhalecloud.retail.goods2b.entity.Brand;
import com.iwhalecloud.retail.goods2b.manager.BrandManager;
import com.iwhalecloud.retail.goods2b.manager.ProductBaseManager;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.request.UserGetReq;
import com.iwhalecloud.retail.system.service.UserService;
import com.iwhalecloud.retail.workflow.dto.req.HandlerUser;
import com.iwhalecloud.retail.workflow.extservice.WFServiceExecutor;
import com.iwhalecloud.retail.workflow.extservice.params.ServiceParamContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2019/5/6.
 */
@Service
@Slf4j
public class ProductBrandNodeRightsServiceExecutorImpl implements WFServiceExecutor {
    @Autowired
    private ProductBaseManager productBaseManager;

    @Autowired
    private BrandManager brandManager;

    @Reference
    private UserService userService;

    @Override
    public ResultVO<List<HandlerUser>> execute(ServiceParamContext context) {
        List<HandlerUser> handlerUsers = Lists.newArrayList();

        log.info("业务ID=" + context.getBusinessId());
        log.info("动态参数=" + context.getDynamicParam());
        System.out.println("--------------");

        String productBaseId = context.getBusinessId();
        ProductBaseGetResp productBaseGetResp = productBaseManager.getProductBase(productBaseId);
        if(null!=productBaseGetResp){
            HandlerUser handlerUser = new HandlerUser();
            String brandId = productBaseGetResp.getBrandId();
            Brand brand = brandManager.getBrandByBrandId(brandId);
            if(null!=brand && StringUtils.isNotEmpty(brand.getName())){
                UserGetReq userGetReq = new UserGetReq();
                if(ProductConst.BrandAuditPeople.HUWEI.getValue().equals(brand.getName())){
                    userGetReq.setLoginName(ProductConst.BrandAuditPeople.HUWEI.getCode());
                }else if (ProductConst.BrandAuditPeople.OPPO.getValue().equals(brand.getName())){
                    userGetReq.setLoginName(ProductConst.BrandAuditPeople.OPPO.getCode());
                }else if(ProductConst.BrandAuditPeople.VIVO.getValue().equals(brand.getName())){
                    userGetReq.setLoginName(ProductConst.BrandAuditPeople.VIVO.getCode());
                }else if(ProductConst.BrandAuditPeople.XIAOMI.getValue().equals(brand.getName()) ||
                        ProductConst.BrandAuditPeople.RONGYAO.getValue().equals(brand.getName())){
                    userGetReq.setLoginName(ProductConst.BrandAuditPeople.XIAOMI.getCode());
                }else{
                    userGetReq.setLoginName(ProductConst.BrandAuditPeople.OTHER.getCode());
                }
                UserDTO userDTO = userService.getUser(userGetReq);
                if(null!=userDTO){
                    handlerUser.setHandlerUserName(userDTO.getUserName());
                    handlerUser.setHandlerUserId(userDTO.getUserId());
                }
                handlerUsers.add(handlerUser);
            }
        }
        log.info("ProductBrandNodeRightsServiceExecutorImpl resp={}",handlerUsers);
        //以下实现获取处理人的逻辑
        return ResultVO.success(handlerUsers);
    }
}
