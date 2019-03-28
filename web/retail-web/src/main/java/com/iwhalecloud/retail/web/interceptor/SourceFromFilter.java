package com.iwhalecloud.retail.web.interceptor;

import com.alibaba.dubbo.rpc.*;
import com.iwhalecloud.retail.dto.AbstractRequest;
import com.iwhalecloud.retail.web.consts.WebConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class SourceFromFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String sourceFrom = WebConst.SOURCE_FROM;
        Object[] arguments = invocation.getArguments();
        for(Object argument : arguments){
            //如果是继承了AbstractRequest基类的请求，给未设置sourceFrom的对象设置默认值
            if (argument != null && argument instanceof AbstractRequest) {
                AbstractRequest request = (AbstractRequest)argument;

                if (StringUtils.isEmpty(request.getSourceFrom())) {
                    request.setSourceFrom(sourceFrom);
                }
            }

        }

        RpcContext.getContext().setAttachment("SOURCE_FROM", sourceFrom);

        return invoker.invoke(invocation);
    }
}
