package com.iwhalecloud.retail.system.service.impl;

import javax.annotation.Resource;

import com.iwhalecloud.retail.system.common.SystemConst;
import lombok.extern.slf4j.Slf4j;

import com.alibaba.dubbo.config.annotation.Service;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.request.RandomLogAddReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogGetReq;
import com.iwhalecloud.retail.system.dto.request.RandomLogUpdateReq;
import com.iwhalecloud.retail.system.dto.response.RandomLogGetResp;
import com.iwhalecloud.retail.system.manager.RandomLogManager;
import com.iwhalecloud.retail.system.service.RandomLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @Author he.sw
 * @Date 2018/11/30
 **/
@Slf4j
@Service
public class RandomLogServiceImpl implements RandomLogService {
    @Resource
    private RandomLogManager randomLogManager;
	@Value("${effInterval}")
	private String effInterval;

	@Override
	public ResultVO<RandomLogGetResp> selectLogIdByRandomCode(RandomLogGetReq req) {
		ResultVO<RandomLogGetResp> resultVo = new ResultVO<RandomLogGetResp>();
		resultVo.setResultMsg("查询成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());

		//一定要设置这个时间为当前时间
		req.setEffDate(new Date());
		RandomLogGetResp resp = randomLogManager.selectLogIdByRandomCode(req);
		resultVo.setResultData(resp);
		return resultVo;
	}

	@Override
	public ResultVO<Integer> insertSelective(RandomLogAddReq randomLogAddReq) {
		ResultVO<Integer> resultVo = new ResultVO<Integer>();
		resultVo.setResultMsg("添加成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());

		// 写死一些默认值
		randomLogAddReq.setRandomCode("888888");
		randomLogAddReq.setValidStatus(SystemConst.ValidStatusEnum.NOT_VALID.getCode());
		Date now = new Date();
		randomLogAddReq.setCreateDate(now);
		//设置有效时间
		Date effDate = new Date(now.getTime() + Long.parseLong(effInterval));
		randomLogAddReq.setEffDate(effDate);
		randomLogAddReq.setSendTime(now);
		randomLogAddReq.setSendStatus(SystemConst.SendStatusEnum.HAVE_SEND.getCode());


		Integer num = randomLogManager.insertSelective(randomLogAddReq);
		resultVo.setResultData(num);
		return resultVo;
	}

	@Override
	public ResultVO<Integer> updateByPrimaryKey(RandomLogUpdateReq req) {
		ResultVO<Integer> resultVo = new ResultVO<Integer>();
		resultVo.setResultMsg("更新成功");
		resultVo.setResultCode(ResultCodeEnum.SUCCESS.getCode());

		Integer num = randomLogManager.updateByPrimaryKey(req);
		resultVo.setResultData(num);
		return resultVo;
	}

    
    
}
