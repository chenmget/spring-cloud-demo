package com.iwhalecloud.retail.warehouse.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * Created by fadeaway on 2019/05/29.
 */
@Component
@Slf4j
public class ExcutorServiceUtils {

    // 核心线程数
    private static Integer corePoolSize = Runtime.getRuntime().availableProcessors();
    // 超时时间100秒
    private static long keepAliveTime = 100;

    private static final Integer perNum = 1000;


    public static ExecutorService initExecutorService() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);
        return executorService;
    }

}
