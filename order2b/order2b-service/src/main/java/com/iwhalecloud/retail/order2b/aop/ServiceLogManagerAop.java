package com.iwhalecloud.retail.order2b.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.iwhalecloud.retail.order2b.config.Order2bContext;
import com.iwhalecloud.retail.order2b.dto.base.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Aspect
@Component
@Slf4j
public class ServiceLogManagerAop {

    static {
        /**
         * jdk1.8下若接口的缺省方法未被重写，则fastjson反系列化可能报错：java.lang.IncompatibleClassChangeError: Found interface xxx, but class was expected
         * 原因：实现类中未实现xxx interface的default方法，导致asm中设值的method属于接口(本应属于实现类)，调用错误。
         * 解决方法：禁止fastjson使用asm反系列化。
         * by ji.kai
         */
        ParserConfig.getGlobalInstance().setAsmEnable(false);
        SerializeConfig.getGlobalInstance().setAsmEnable(false);
    }

    @Around("execution(* com.iwhalecloud.retail.order2b.busiservice.*.*(..))")
    public Object aroundExecuteService(ProceedingJoinPoint point) throws Throwable {

        long time = System.currentTimeMillis();
        log.info("interface=({}),dubboTime={},gs_start={},url={},request{}",
                JSON.toJSONString(point.getSignature().getDeclaringType()),
                Order2bContext.getDubboRequest().getHttpId(),
                time,
                point.getSignature().getName(),
                JSON.toJSONString(point.getArgs()));

        Object result = point.proceed();
        log.info("interfaceMethod=({}),dubboTime={},gs_close={},timeConsuming={},result={}",
                point.getSignature().getDeclaringType().getSimpleName().concat(point.getSignature().getName()),
                Order2bContext.getDubboRequest().getHttpId(),
                time,
                (System.currentTimeMillis() - time),
                JSON.toJSONString(result));

        return result;

    }

    @Around("execution(* com.iwhalecloud.retail.order2b.service.impl.*.*(..))")
    public Object aroundDubboService(ProceedingJoinPoint point) throws Throwable {

        if (point.getArgs().length <= 0) {
            return point.proceed();
        }

        String dubboService = JSON.toJSONString(point.getSignature().getDeclaringType());
        Object result;
        try {
            /**
             * 获取分片字段
             */
            Order2bContext.setDBLanId(point.getArgs()[0]);
            OrderRequest orderRequest=Order2bContext.getDubboRequest();
            long time = orderRequest.getHttpId();
            log.info("DubboInterface=({}),gs_start={},url={},request{}",
                    dubboService, time, point.getSignature().getName(),
                    JSON.toJSONString((point.getArgs())));
             result = point.proceed();
            log.info("DubboInterface=({}),gs_close={},timeConsuming={},request{},result={}",
                    dubboService, time, (System.currentTimeMillis() - time),
                    JSON.toJSONString(point.getArgs()), JSON.toJSONString(result));
        }finally {
            Order2bContext.remove();
        }
        return result;

    }

    private String inputExceptionToLOG(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

}
