package com.iwhalecloud.retail.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.member.dto.MemberDTO;
import com.iwhalecloud.retail.member.dto.request.*;
import com.iwhalecloud.retail.member.dto.response.MemberLoginResp;
import com.iwhalecloud.retail.member.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.member.dto.response.MemberIsExistsResp;
import com.iwhalecloud.retail.member.dto.response.MemberResp;
import com.iwhalecloud.retail.member.manager.MemberManager;
import com.iwhalecloud.retail.member.service.MemberService;

import java.util.Date;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberManager memberManager;

    @Override
    public MemberLoginResp login(MemberLoginReq req) {
        MemberLoginResp resp = new MemberLoginResp();

        //1、根据手机号获取会员信息
        MemberGetReq memberGetReq = new MemberGetReq();
        memberGetReq.setMobile(req.getMobile());
        memberGetReq.setUname(req.getUserName());
        Member member = memberManager.getMember(memberGetReq);
        //2、是否存在
        if(member == null){
            resp.setErrorMessage("用户不存在，请确认输入账号是否正确");
            return resp;
        }
        //3、 判断登录密码（暂时没有）
//        if (StringUtils.isEmpty(req.getPassword())
//                || !StringUtils.equals(member.getPassword(), StringUtils.md5(req.getPassword()))) {
//            // 密码错误 登录失败次数 failLoginCnt +1
//            member.setFailLoginCnt(user.getFailLoginCnt() + 1);
//            userManager.updateUser(user);
//            resp.setErrorMessage("用户名或密码错误");
//            return resp;
//        }

        //4、操作成功后的逻辑，修改当前登录时间，和上次登录时间 ，登录次数1+
        Long loginCount = member.getLoginCount() == null ? 0 : member.getLoginCount();
        member.setLoginCount(loginCount + 1);
        member.setLastLoginTime(new Date());
        memberManager.updateMember(member);

        resp.setErrorMessage("登录成功");
        resp.setIsLoginSuccess(true);
        MemberDTO memberDTO = new MemberDTO();
        BeanUtils.copyProperties(member, memberDTO);
        resp.setMemberDTO(memberDTO);

        return resp;
    }

    @Override
	public Page<MemberDTO> pageMember(MemberPageReq req) {
		return memberManager.pageMember(req);
	}

    public ResultVO<MemberDTO> getMember(MemberGetReq req){
		log.info("MemberServiceImpl.getMember()  input: req={} ", JSON.toJSONString(req));
		MemberDTO memberDTO = new MemberDTO();
        Member member = memberManager.getMember(req);
        if(member == null){
			memberDTO = null;
        } else {
			BeanUtils.copyProperties(member, memberDTO);
		}
		log.info("MemberServiceImpl.getMember()  output: MemberDTO={} ", JSON.toJSONString(memberDTO));
		return ResultVO.success(memberDTO);
    }


    @Override
	public MemberResp getMember(String memberId) {
		return memberManager.getMemberById(memberId);
	}

	@Override
	public MemberIsExistsResp isExists(MemberIsExistsReq req) {
		return memberManager.memberIsExists(req);
	}

	@Override
	@Transactional
	public ResultVO register(MemberAddReq member) {
		ResultVO resultVo = new ResultVO();
		resultVo.setResultMsg("注册成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());
		
		if (StringUtils.isNoneBlank(member.getMemberId())) {
			MemberResp dto = memberManager.getMemberById(member.getMemberId());
			if (null != dto) {
				resultVo.setResultMsg(member.getMemberId()+"已注册过");
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				return resultVo;
			}
		}
		if (StringUtils.isNoneBlank(member.getUname())) {
			MemberResp dto = memberManager.getMemberByUname(member.getUname());
			if (null != dto) {
				resultVo.setResultMsg(member.getUname()+"已注册过");
				resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
				return resultVo;
			}
		}

		Integer register = memberManager.register(member);
		if (register <= 0) {
			resultVo.setResultMsg("注册失败");
			resultVo.setResultCode(ResultCodeEnum.ERROR.getCode());
			return resultVo;
		}
		return resultVo;
	}

	@Override
	public int checkPayAccount(String merchantId) {
		int number = memberManager.getParMerchantAccountByMerchantId(merchantId);
		return number;
	}
    
}