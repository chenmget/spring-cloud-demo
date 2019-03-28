package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.request.MemberAddressAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressListReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberAddressAddResp;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.entity.MemberAddress;
import com.iwhalecloud.retail.member.manager.MemberAddressManager;
import com.iwhalecloud.retail.member.service.MemberAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/29
 **/
@Service
@Slf4j
public class MemberAddressServiceImpl implements MemberAddressService {

    @Autowired
    private MemberAddressManager memberAddressManager;

    @Override
    public ResultVO<MemberAddressAddResp> addAddress(MemberAddressAddReq req) {
        try {
            MemberAddressAddResp resp = memberAddressManager.addAddress(req);
            if (resp == null) {
                return ResultVO.error("添加地址失败");
            } else {
                return ResultVO.success(resp);
            }
        }catch (Exception e){
           log.error("MemberAddressServiceImpl addAddress Exception={} ",e);
           return ResultVO.error("添加地址失败");
        }
    }

    @Override
    public ResultVO<Integer> deleteAddressById(String addressId) {
        try {
            int result = memberAddressManager.deleteAddressById(addressId);
            if (result > 0) {
                return ResultVO.success(result);
            } else {
                return ResultVO.error("删除地址失败");
            }
        }catch (Exception e){
            log.error("MemberAddressServiceImpl deleteAddress Exception={} ",e);
            return ResultVO.error("删除地址失败");
        }
    }

    @Override
    public ResultVO<Integer> updateAddress(MemberAddressUpdateReq req) {
        try {
            int result = memberAddressManager.updateAddress(req);
            if (result > 0) {
                return ResultVO.success(result);
            } else {
                return ResultVO.error("更新地址失败");
            }
        }catch (Exception e){
            log.error("MemberAddressServiceImpl updateAddress Exception={} ",e);
            return ResultVO.error("更新地址失败");
        }
    }

    @Override
    public ResultVO<List<MemberAddressRespDTO>> listMemberAddress(MemberAddressListReq req) {
        List<MemberAddressRespDTO> memberAddressRespDTOList = Lists.newArrayList();
        try {
            List<MemberAddress> memberAddressList = memberAddressManager.listMemberAddress(req);
            if(!CollectionUtils.isEmpty(memberAddressList)) {
                for (MemberAddress address : memberAddressList) {
                    MemberAddressRespDTO dto = new MemberAddressRespDTO();
                    BeanUtils.copyProperties(address, dto);
                    memberAddressRespDTOList.add(dto);
                }
            }
            memberAddressRespDTOList = exchangeListIndex(memberAddressRespDTOList);
        }catch (Exception e){
            log.error("MemberAddressServiceImpl listMemberAddress Exception={} ",e);
            return ResultVO.error("查询地址列表失败");
        }
        return ResultVO.success(memberAddressRespDTOList);
    }

    @Override
    public MemberAddressRespDTO queryAddress(String addrId) {
        MemberAddressRespDTO resp = null;
        try {
            resp = memberAddressManager.queryAddress(addrId);
            if(null == resp){
                return null;
            }
        }catch (Exception e){
            log.error("MemberAddressServiceImpl listMemberAddress Exception={} ",e);
            return null;
        }
        return resp;
    }

    /**
     * 交换list的元素下标
     * @param list
     * @return
     */
    public List<MemberAddressRespDTO> exchangeListIndex(List<MemberAddressRespDTO> list){
        if(CollectionUtils.isEmpty(list)){
            list = Lists.newArrayList();
            return list;
        }
        for (int i=0;i<list.size();i++){
            MemberAddressRespDTO memberAddress = list.get(0);
            if(StringUtils.equals(list.get(i).getIsDefault(), MemberConst.IsDefaultAddress.YES.getCode())){
                list.set(0,list.get(i));
                list.set(i,memberAddress);
            }
        }
        return list;
    }
}
