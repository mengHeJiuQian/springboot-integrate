package org.yl.websearch.modules.cs.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.yl.websearch.modules.cs.service.CsArticlePageUrlCollectService;

/**
 * @author yang.liu
 * @createtime 2020/10/24 21:15
 * @description 简单的解析页面里所有的url进行保存
 */
@Slf4j
@Controller
public class CsArticleBriefCollectQuartz {

    @Autowired
    private CsArticlePageUrlCollectService csArticlePageUrlCollectServiceImpl;

    // todo 需要设置启动后开始执行，执行一段时间后（小于一天）自动终止。

    /**
     * 简单的提取出页面里所有的url，保存到mongo
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void collectArticlePageUrl() {
        log.info("【开始执行一次collectArticlePageUrl】");
        csArticlePageUrlCollectServiceImpl.collectArticlePageUrl();
        log.info("【结束执行一次collectArticlePageUrl】");
    }
}
