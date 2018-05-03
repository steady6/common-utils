package com.framework.common.concurrent;

import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/5/3
 * @description
 */
public abstract class CallableTask<T> implements Callable<T> {

    private final Map mdcContext = MDC.getCopyOfContextMap();

    @Override
    public T call() throws Exception {
        try {
            beforeTask();
            return doTask();
        } finally {
            endTask();
        }
    }

    protected void beforeTask() {
        if (mdcContext != null) {
            MDC.setContextMap(mdcContext);
        }
    }

    protected abstract T doTask();

    protected void endTask() {
        MDC.clear();
    }

    protected abstract String getTaskName();
}
