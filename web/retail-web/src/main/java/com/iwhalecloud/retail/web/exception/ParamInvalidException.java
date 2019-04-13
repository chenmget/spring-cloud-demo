package com.iwhalecloud.retail.web.exception;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.web.utils.MessageSourceHandler;

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
	
	public ParamInvalidException(List<String> errors, MessageSourceHandler messageSourceHandler) {
		List<String> errorList = Lists.newArrayList();
		for (String key : errors) {
			String messageValue = messageSourceHandler.getMessage(key);
			errorList.add(messageValue);
		}
		this.errors = errorList;
	}
	
	public List<String> getErrors() {
		return this.errors;
	}
}
