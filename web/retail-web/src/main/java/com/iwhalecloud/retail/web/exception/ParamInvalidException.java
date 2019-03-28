package com.iwhalecloud.retail.web.exception;

import java.util.List;


/**
 * 参数异常类
 * @author Z
 *
 */
public class ParamInvalidException extends Exception {

	private List<String> errors;
	private static final long serialVersionUID = 7841139998148892295L;

	public ParamInvalidException(String errMsg) {
		super(errMsg);
	}
	
	public ParamInvalidException(List<String> errors) {
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
}
