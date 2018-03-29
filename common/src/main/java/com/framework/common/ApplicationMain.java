package com.framework.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/3/29
 * @description
 */
@SpringBootApplication(scanBasePackages={"com.framework.common"})
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}
