package com.iwhalecloud.retail.web.exception;

import com.iwhalecloud.retail.dto.ResultVO;

/**
 * 用户没关联商家异常
 * @author Z
 *
 */
public class UserNoMerchantException extends Exception {

	private static final long serialVersionUID = 7841139998148892295L;

	private String errorMsg;
	private String errorCode;

	public UserNoMerchantException(String errMsg) {
		super(errMsg);
	}

	public UserNoMerchantException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	 * 获取返回结果
	 * @return
	 */
	public ResultVO<Object> getResultVo() {
		ResultVO<Object> resultVO = new ResultVO<Object>();

        resultVO.setResultCode(errorCode);
        resultVO.setResultMsg(errorMsg);
        return resultVO; 
	}
}
