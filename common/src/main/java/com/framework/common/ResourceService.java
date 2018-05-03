package com.framework.common;

import com.framework.common.entity.ResourceQuery;
import com.framework.common.entity.ResourceVO;


/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/5/3
 * @description
 */
public interface ResourceService {

    ResourceVO searchResource(ResourceQuery request);
}
