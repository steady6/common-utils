package com.framework.common.concurrent;


import com.framework.common.ResourceService;
import com.framework.common.entity.ResourceQuery;
import com.framework.common.entity.ResourceVO;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/5/3
 * @description
 */
public class ResourceDataTask extends CallableTask<ResourceVO> {

    private ResourceService resourceService;
    private ResourceQuery request;

    public ResourceDataTask(ResourceService resourceService, ResourceQuery request) {
        this.resourceService = resourceService;
        this.request = request;
    }

    @Override
    protected ResourceVO doTask() {
        ResourceVO dataResponse = resourceService.searchResource(request);
        return dataResponse;
    }

    @Override
    protected String getTaskName() {
        return "ResourceDataTask_" + Thread.currentThread().getName();
    }
}
