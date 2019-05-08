package com.iwhalecloud.retail.web.exception;

import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;

/**
 * 用户没关联商家异常
 * @author Z
 *
 */
public class UserNoMerchantException extends Exception {

	private static final long serialVersionUID = 7841139998148892295L;

	private static String NO_MERCHANT = "用户没有关联商家，请确认";

	public UserNoMerchantException(String errMsg) {
		super(errMsg);
	}
	/**
	 * 获取返回结果
	 * @return
	 */
	public ResultVO<Object> getResultVo() {
		ResultVO<Object> resultVO = new ResultVO<Object>();

        resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
        resultVO.setResultMsg(NO_MERCHANT);
        return resultVO; 
	}
}
