package org.yl.websearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yang.liu
 * @createtime 2020/9/29 12:57
 * @description 启动类
 */
@EnableScheduling
@SpringBootApplication
public class WebSearchCsSpiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSearchCsSpiderApplication.class, args);
    }
}
