package com.iwhalecloud.retail.goods2b.exception;

import java.util.List;


/**
 * 参数异常类
 * @author Z
 *
 */
public class ProductException extends Exception {

	private List<String> errors;
	private static final long serialVersionUID = 7841139998148892295L;

	public ProductException(String errMsg) {
		super(errMsg);
	}

	public ProductException(List<String> errors) {
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return this.errors;
	}

	@Override
	public String getMessage(){
		StringBuffer errorStr = new StringBuffer();
		if(this.errors!=null&&!this.errors.isEmpty()){
			for (String error : errors) {
				errorStr.append(error+",");
			}
			errorStr.delete(errorStr.length()-1,errorStr.length());
		}
		if(errorStr.length()<1){
			return super.getMessage();
		}
		return errorStr.toString();
	}
}
