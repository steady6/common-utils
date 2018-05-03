package com.framework.common.concurrent;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/5/3
 * @description
 */
public class CallableTaskService<T> {

    private static final Logger logger = LoggerFactory.getLogger(CallableTaskService.class);
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(20);

    //秒
    private int timeout;
    private String taskServiceName = "callableTaskService";

    public List<T> queryAll(List<CallableTask<T>> baseQueryList) {
        long startTime = System.currentTimeMillis();
        try {
            CompletionService<T> completionService = new ExecutorCompletionService<T>(threadPool);
            List<T> resultList = Lists.newArrayList();
            for (CallableTask<T> callable : baseQueryList) {
                try {
                    completionService.submit(callable);
                } catch (Exception e) {
                    logger.error("task cannot be scheduled for execution", e);
                }
            }
            for (CallableTask<T> callable : baseQueryList) {
                try {
                    //可以设置每个线程的超时时间
                    Future<T> future = completionService.poll(getTimeout(), TimeUnit.SECONDS);
                    if (null == future) {
                        logger.error("get task result time out for " + callable.getTaskName());
                    } else {
                        T result = future.get();
                        resultList.add(result);
                    }
                } catch (Exception e) {
                    logger.error("get task result error for " + callable.getTaskName(), e);
                }
            }
            if (resultList.size() == baseQueryList.size()) {
                logger.info(getTaskServiceName() + " exec allok");
            } else {
                logger.info(getTaskServiceName() + " exec notok");
            }
            return resultList;
        } catch (Exception e) {
            logger.error(getTaskServiceName() + " exec exception", e);
        } finally {
        }
        return Lists.newArrayList();
    }

    public CallableTaskService(int timeout, String taskServiceName) {
        super();
        this.timeout = timeout;
        this.taskServiceName = taskServiceName;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getTaskServiceName() {
        return taskServiceName;
    }

    public void setTaskServiceName(String taskServiceName) {
        this.taskServiceName = taskServiceName;
    }
}
