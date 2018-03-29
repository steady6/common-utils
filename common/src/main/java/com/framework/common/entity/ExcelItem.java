package com.framework.common.entity;

import java.io.Serializable;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/3/29
 * @description
 */
public class ExcelItem implements Serializable {
    private static final long serialVersionUID = -5246293374546060023L;

    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
