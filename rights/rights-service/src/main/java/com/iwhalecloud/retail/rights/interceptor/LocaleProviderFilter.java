package com.iwhalecloud.retail.rights.interceptor;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.iwhalecloud.retail.rights.common.RightsConst;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * @author mzl
 * @date 2019/4/11
 */
@Activate(
        group = {"provider"},
        order = -100
)
public class LocaleProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String language = invocation.getAttachment(RightsConst.LOCALE_CODE);
        Locale locale;
        if (!StringUtils.isEmpty(language)) {
            locale = new Locale(language);
        } else {
            locale = LocaleContextHolder.getLocale();
        }
        LocaleContextHolder.setLocale(locale);
        Result result = invoker.invoke(invocation);
        return result;
    }
}
