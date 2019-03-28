package com.iwhalecloud.retail.web.exception;

import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;

/**
 * 用户未登录异常
 * @author Z
 *
 */
public class UserNotLoginException extends Exception {

	private static final long serialVersionUID = 7841139998148892295L;

	public UserNotLoginException(String errMsg) {
		super(errMsg);
	}
	/**
	 * 获取返回结果
	 * @return
	 */
	public ResultVO<Object> getResultVo() {
		ResultVO<Object> resultVO = new ResultVO<Object>();

        resultVO.setResultCode(ResultCodeEnum.NOT_LOGIN.getCode());
        resultVO.setResultMsg(ResultCodeEnum.NOT_LOGIN.getDesc());
        return resultVO; 
	}
}
