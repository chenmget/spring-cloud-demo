package com.iwhalecloud.retail.goods2b.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.iwhalecloud.retail.exception.RetailTipException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Aspect
@Component
@Slf4j
public class ServiceLogManagerAop {

    static{
        /**
         * jdk1.8下若接口的缺省方法未被重写，则fastjson反系列化可能报错：java.lang.IncompatibleClassChangeError: Found interface xxx, but class was expected
         * 原因：实现类中未实现xxx interface的default方法，导致asm中设值的method属于接口(本应属于实现类)，调用错误。
         * 解决方法：禁止fastjson使用asm反系列化。
         * by ji.kai
         */
        ParserConfig.getGlobalInstance().setAsmEnable(false);
        SerializeConfig.getGlobalInstance().setAsmEnable(false);
    }
    @Around("execution(* com.iwhalecloud.retail.goods2b.service.*.*(..))")
    public Object aroundExecuteService(ProceedingJoinPoint point) throws Throwable {
        long time = System.currentTimeMillis();
        log.info("interface=({}),gs_start={},url={},request{}",
                JSON.toJSONString(point.getSignature().getDeclaringType()), time, point.getSignature().getName(),
                JSON.toJSONString((point.getArgs())));
        try {
            Object result = point.proceed();
            log.info("interface=({}),gs_close={},timeConsuming={},request{},result={}",
                    JSON.toJSONString(point.getSignature().getDeclaringType()),time, (System.currentTimeMillis() - time),
                    JSON.toJSONString(point.getArgs()), JSON.toJSONString(result));
            return result;
        } catch (RetailTipException e) {
            log.error("ServiceLogManagerAop.aroundExecuteService",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return e.getTipResultVO();
        }

    }

}
