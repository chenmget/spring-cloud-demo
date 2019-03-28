package com.iwhalecloud.retail.order.reference;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.order.dto.base.CommonResultResp;
import com.iwhalecloud.retail.order.dto.resquest.BuilderOrderRequest;
import com.iwhalecloud.retail.rights.dto.request.CheckRightsRequestDTO;
import com.iwhalecloud.retail.rights.dto.request.UserightsRequestDTO;
import com.iwhalecloud.retail.rights.dto.response.CheckRightsResponseDTO;
import com.iwhalecloud.retail.rights.service.CouponInstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CouponManagerReference {

    @Reference
    private CouponInstService couponInstService;

    /**
     * 权益核销校验
     *
     * @return
     */
    public CheckRightsResponseDTO checkRights(String memberId, String couponCode, double amount) {
        CheckRightsRequestDTO checkReq = new CheckRightsRequestDTO();
        checkReq.setCustNum(memberId);
        checkReq.setOrderPrice(amount);
        checkReq.setCouponInstId(couponCode);
        ResultVO resultVO = couponInstService.checkRights(checkReq);
        if (!resultVO.isSuccess()) {
            return null;
        }
        return (CheckRightsResponseDTO) resultVO.getResultData();
    }

    /**
     * 权益核销
     *
     * @return
     */
    public CommonResultResp userights(BuilderOrderRequest requestDTO, String status) {
        CommonResultResp resp=new CommonResultResp();
        UserightsRequestDTO userightsRequestDTO =new UserightsRequestDTO();
        userightsRequestDTO.setCustNum(requestDTO.getMemberId());
        userightsRequestDTO.setCouponInstId(requestDTO.getCouponCode());
        userightsRequestDTO.setCreateStaff(requestDTO.getUserId());
       // RightsStatusConsts.RIGHTS_STATUS_OCCUPY 预占
       // RightsStatusConsts.RIGHTS_STATUS_UNUSED  待使用
       // RightsStatusConsts.RIGHTS_STATUS_USED  已使用
        userightsRequestDTO.setStatusCd(status);
        userightsRequestDTO.setOrderId(requestDTO.getCouponOrderId());
        ResultVO resultVO = couponInstService.userights(userightsRequestDTO);
        log.info("gs_10010_userights,request={},resultVO={}",
                JSON.toJSONString(userightsRequestDTO),JSON.toJSONString(resultVO));
        BeanUtils.copyProperties(resultVO,resp);
        return resp;
    }

}
