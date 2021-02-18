package org.yl.websearch.modules.bos.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yl.websearch.modules.bos.service.BosJobCollectService;

/**
 * @author yang.liu
 * @createtime 2021/2/18 22:15
 * @description
 */
@Slf4j
@Component
@PropertySource(value = "classpath:quartz.properties")
public class BosJobCollectQuartz {

    @Autowired
    private BosJobCollectService bosJobCollectServiceImpl;

    /**
     * 查询一个城市或地区的职业数量.
     */
    @Scheduled(cron = "${quartz.collectJobInArea}")
    public void collectJobInArea() {
        log.info("【开始执行一次collectJobInArea】");
        bosJobCollectServiceImpl.collectJobInArea();
        log.info("【结束执行一次collectJobInArea】");
    }

}
