package com.iwhalecloud.retail.order2b.interceptor;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.iwhalecloud.retail.order2b.consts.OrderManagerConsts;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author mzl
 * @date 2019/4/11
 */
@Activate(
        group = {"consumer"},
        order = -100
)
public class LocaleConsumerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale != null) {
            String language = locale.getLanguage();
            RpcContext.getContext().setAttachment(OrderManagerConsts.LOCALE_CODE, language);
        }
        return invoker.invoke(invocation);
    }
}
