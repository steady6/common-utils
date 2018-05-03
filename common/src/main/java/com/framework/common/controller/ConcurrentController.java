package com.framework.common.controller;

import com.framework.common.ResourceService;
import com.framework.common.concurrent.CallableTask;
import com.framework.common.concurrent.CallableTaskService;
import com.framework.common.concurrent.ResourceDataTask;
import com.framework.common.entity.ResourceQuery;
import com.framework.common.entity.ResourceVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/5/3
 * @description
 */
@RestController
public class ConcurrentController {

    private ResourceService resourceService;

    @RequestMapping(value = "/{version}/pt/my/statistics/{userId}", method = RequestMethod.GET)
    public void myStatistics(@PathVariable("userId") Long userId) {
        Assert.notNull(userId, "userId cannot be null");
        List<CallableTask<ResourceVO>> tasks = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            tasks.add(new ResourceDataTask(resourceService, new ResourceQuery()));
        }
        CallableTaskService<ResourceVO> taskService = new CallableTaskService<ResourceVO>(20, "callableTaskService");
        List<ResourceVO> dataList = taskService.queryAll(tasks);
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (ResourceVO scrollData : dataList) {
                //....
            }
        }

        return;
    }

}
