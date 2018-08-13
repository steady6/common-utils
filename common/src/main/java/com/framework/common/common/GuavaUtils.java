package com.framework.common.common;

import com.google.common.base.Splitter;

/**
 * @Author: zhangkun
 * @Date: 2018/8/6 17:37
 * @Description:
 */
public class GuavaUtils {

    /**
     * 逗号分隔符
     */
    public static final Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    /**
     * k-v分隔符
     */
    public static final Splitter.MapSplitter MAP_SPLITTER = Splitter.on(";").withKeyValueSeparator(":");


}
