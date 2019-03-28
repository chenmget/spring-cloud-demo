package com.iwhalecloud.retail.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iwhalecloud.retail.member.dto.response.MemberAddressRespDTO;
import com.iwhalecloud.retail.member.entity.MemberAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author My
 * @Date 2018/11/28
 **/
@Mapper
public interface MemberAddressMapper extends BaseMapper<MemberAddress> {

    List<MemberAddressRespDTO> listMemberAddress(String memberId);
}
