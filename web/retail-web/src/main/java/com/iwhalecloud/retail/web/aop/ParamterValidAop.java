package com.iwhalecloud.retail.web.aop;

import com.iwhalecloud.retail.web.exception.ParamInvalidException;
import com.iwhalecloud.retail.web.utils.MessageSourceHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class ParamterValidAop {

	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

	@Autowired
	private MessageSourceHandler messageSourceHandler;
	
    @Before("execution(* com.iwhalecloud.retail.web.controller..*.*(..))")
    public void doBefore(JoinPoint point) throws ParamInvalidException {
        Object[] params = point.getArgs();
        
        //校验参数是否合法
        List<String> errors = this.validate(params);
        if (errors != null && !errors.isEmpty()) {
        	throw new ParamInvalidException(errors, messageSourceHandler);
        }
    }
    
    /**
     * 校验参数
     * @param params
     * @return
     */
    private List<String> validate(Object[] params) {
    	if (params==null || params.length==0) {
    		return null;
    	}
    	List<String> errors = new ArrayList<String>();
		for (Object obj : params) {

			if (obj == null) {
				continue;
			}
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
			for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
				errors.add(constraintViolation.getMessage());
			}
		}
    	return errors;
    }
}
