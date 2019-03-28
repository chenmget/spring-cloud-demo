/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2017 All Rights Reserved.
 */
package com.iwhalecloud.retail.order2b.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yangbo2018
 * @version Id: ExceptionHandlerAdvice.java, v 0.1 2017/12/8 16:57 yangbo2018 Exp $$
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("error");// error页面
        modelAndView.addObject("errorMessage", exception.getMessage());
        return modelAndView;
    }
}
