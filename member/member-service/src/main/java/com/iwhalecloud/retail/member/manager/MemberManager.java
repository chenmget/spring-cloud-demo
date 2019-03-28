package com.iwhalecloud.retail.member.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.common.MemberConst;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.MemberAddReq;
import com.iwhalecloud.retail.member.dto.request.MemberGetReq;
import com.iwhalecloud.retail.member.dto.request.MemberIsExistsReq;
import com.iwhalecloud.retail.member.dto.request.MemberPageReq;
import com.iwhalecloud.retail.member.dto.response.MemberIsExistsResp;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.member.entity.Member;
import com.iwhalecloud.retail.member.mapper.MemberMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
public class MemberManager{
    @Resource
    private MemberMapper memberMapper;


    public Page<MemberDTO> pageMember(MemberPageReq req) {
		Page<MemberDTO> page = new Page<MemberDTO>(req.getPageNo(), req.getPageSize());
	    return memberMapper.pageMember(page, req);
	}

    /**
     * 根据条件（精确）查找单个会员
     * @param req
     * @return
     */
    public Member getMember(MemberGetReq req){
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        if(!StringUtils.isEmpty(req.getMemberId())){
            queryWrapper.eq(Member.FieldNames.memberId.getTableFieldName(), req.getMemberId());
        }
        if(!StringUtils.isEmpty(req.getUname())){
            queryWrapper.eq(Member.FieldNames.uname.getTableFieldName(), req.getUname());
        }
        if(!StringUtils.isEmpty(req.getMobile())){
            queryWrapper.eq(Member.FieldNames.mobile.getTableFieldName(), req.getMobile());
        }
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(memberList)){
            return null;
        }
        return memberList.get(0);
    }

    /**
     * 更新会员信息
     * @param member
     * @return
     */
    public Integer updateMember(Member member){
        return memberMapper.updateById(member);
    }



		/**
         * 通过手机号查询会员是否存在
         * @param req(传 mobile 字段）
         * @return
         */
    public MemberIsExistsResp memberIsExists(MemberIsExistsReq req){
        MemberGetReq memberGetReq = new MemberGetReq();
        memberGetReq.setUname(req.getUname());
        memberGetReq.setMobile(req.getMobile());
        MemberIsExistsResp dto = new MemberIsExistsResp();
        Boolean isExists = getMember(memberGetReq) !=null ? true : false;
        dto.setExists(isExists);
        return dto;

    };

    /**
	 * 会员注册
	 * @param dto
	 * @return
	 */
    public Integer register(MemberAddReq dto){
    	Member member = new Member();
    	BeanUtils.copyProperties(dto, member);

    	//设置一些默认值
    	member.setLoginCount(0L);
    	member.setAddDate(new Date());
    	member.setUpdateDate(new Date());
    	member.setStatus(MemberConst.CommonState.VALID.getCode());
//		member.setLvId(MemberConst.LvTypeEnum.LV_0.getCode());
//		member.setLvName(MemberConst.LvTypeEnum.LV_0.getValue());
    	return memberMapper.insert(member);
    }
    
    /**
	 * 根据会员名称获取用户
	 * @param uname
	 * @return
	 */
    public MemberResp getMemberByUname(String uname){
        MemberGetReq req = new MemberGetReq();
        req.setUname(uname);
    	Member member = getMember(req);
    	if (null == member) {
			return null;
		}
    	MemberResp t = new MemberResp();
    	BeanUtils.copyProperties(member, t);
    	return t;
    }
    
    /**
     * 根据会员主键获取用户
     * @param memberId
     * @return
     */
    public MemberResp getMemberById(String memberId){
    	Member member = memberMapper.selectById(memberId);
    	if (null == member) {
			return null;
		}
    	MemberResp dto = new MemberResp();
    	BeanUtils.copyProperties(member, dto);
    	return dto;
    }

}
