package com.iwhalecloud.retail.member.service;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.member.dto.request.MemberAddressAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressListReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberAddressAddResp;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/29
 **/
public interface MemberAddressService {
    /**
     * 添加地址
     * @param req
     * @return
     */
    ResultVO<MemberAddressAddResp> addAddress(MemberAddressAddReq req);

    /**
     * 删除地址
     * @param addressId
     * @return
     */
    ResultVO<Integer> deleteAddressById(String addressId);

    /**
     * 修改地址
     * @param req
     * @return
     */
    ResultVO<Integer> updateAddress(MemberAddressUpdateReq req);

    /**
     * 查询地址
     * @param req
     * @return
     */
    ResultVO<List<MemberAddressRespDTO>> listMemberAddress(MemberAddressListReq req);

    /**
     * 查询地址
     * @param addrId
     * @return
     */
    MemberAddressRespDTO queryAddress(String addrId);
}
