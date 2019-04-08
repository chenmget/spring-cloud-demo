package com.iwhalecloud.retail.member.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.request.MemberAddressAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressListReq;
import com.iwhalecloud.retail.member.dto.request.MemberAddressUpdateReq;
import com.iwhalecloud.retail.member.dto.response.MemberAddressAddResp;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.entity.MemberAddress;
import com.iwhalecloud.retail.member.mapper.MemberAddressMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author My
 * @Date 2018/11/28
 **/
@Component
public class MemberAddressManager {

    @Resource
    private MemberAddressMapper memberAddressMapper;

    /**
     * 修改其它地址为非默认
     */
    private void updateMemberOtherAddrUnDef(String memberId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.orderBy(true,false,"last_update");
        List<MemberAddress> list = memberAddressMapper.selectList(queryWrapper);
        if(list!=null && list.size()>0){
            for(MemberAddress addr : list){
                QueryWrapper query = new QueryWrapper();
                query.eq(MemberAddress.FieldNames.addrId.getTableFieldName(),addr.getAddrId());
                addr.setMemberId(null);
                addr.setIsDefault(MemberConst.IsDefaultAddress.NO.getCode());
                memberAddressMapper.update(addr,query);
            }
        }
    }

    /**
     * 添加地址
     * @param req
     */
    public MemberAddressAddResp addAddress(MemberAddressAddReq req) {
        // 判断是否为默认地址
        if(StringUtils.equals(req.getIsDefault(), MemberConst.IsDefaultAddress.YES.getCode())){
            updateMemberOtherAddrUnDef(req.getMemberId());
        }
        MemberAddress memberAddress = new MemberAddress();
        MemberAddressAddResp resp = new MemberAddressAddResp();
        BeanUtils.copyProperties(req, memberAddress);
        memberAddress.setLastUpdate(new Date());
        int num = memberAddressMapper.insert(memberAddress);
        if(num<0){
            return null;
        }
        BeanUtils.copyProperties(memberAddress, resp);
        return resp;
    }

    /**
     * 删除地址
     * @param addrId
     */
    public int deleteAddressById(String addrId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("addr_id",addrId);
        return memberAddressMapper.delete(queryWrapper);
    }

    /**
     * 修改地址
     */
    public int updateAddress(MemberAddressUpdateReq req) {

        //如果有设置默认地址，则将其它地址设置为非默认

        if(StringUtils.equals(req.getIsDefault(), MemberConst.IsDefaultAddress.YES.getCode())){
            updateMemberOtherAddrUnDef(req.getMemberId());
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("addr_id",req.getAddrId());
        MemberAddress memberAddress = new MemberAddress();
        BeanUtils.copyProperties(req, memberAddress);
        memberAddress.setLastUpdate(new Date());
        memberAddress.setMemberId(null);
        return memberAddressMapper.update(memberAddress, queryWrapper);
    }

    /**
     * 查询地址列表
     * @param req
     * @return
     */
    public List<MemberAddress> listMemberAddress(MemberAddressListReq req){
        QueryWrapper<MemberAddress> queryWrapper = new QueryWrapper<MemberAddress>();
        Boolean hasParam = false;
        if(!StringUtils.isEmpty(req.getMemberId())){
            hasParam = true;
            queryWrapper.eq(MemberAddress.FieldNames.memberId.getTableFieldName(), req.getMemberId());
        }
        if(!StringUtils.isEmpty(req.getAddrId())){
            hasParam = true;
            queryWrapper.eq(MemberAddress.FieldNames.addrId.getTableFieldName(), req.getAddrId());
        }
        if(!StringUtils.isEmpty(req.getIsDefault())){
            queryWrapper.eq(MemberAddress.FieldNames.isDefault.getTableFieldName(), req.getIsDefault());
        }

        queryWrapper.orderByDesc(MemberAddress.FieldNames.lastUpdate.getTableFieldName());

        if (!hasParam) {
            return null;
        }
        List<MemberAddress> memberAddressList = memberAddressMapper.selectList(queryWrapper);
        return memberAddressList;
//        return memberAddressMapper.listMemberAddress(memberId);
    }

    /**
     * 查询地址
     * @param addrId
     * @return
     */
    public MemberAddressRespDTO queryAddress(String addrId){
        MemberAddressRespDTO memberAddressRespDTO = new MemberAddressRespDTO();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("addr_id",addrId);
        MemberAddress memberAddress = memberAddressMapper.selectOne(queryWrapper);
        BeanUtils.copyProperties(memberAddress, memberAddressRespDTO);
        return memberAddressRespDTO;
    }
}
