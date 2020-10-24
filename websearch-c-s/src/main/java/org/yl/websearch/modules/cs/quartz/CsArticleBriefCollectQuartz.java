package org.yl.websearch.modules.cs.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.yl.websearch.modules.cs.service.CsArticlePageUrlCollectService;

/**
 * @author yang.liu
 * @createtime 2020/10/24 21:15
 * @description 简单的解析页面里所有的url进行保存
 */
@Controller
public class CsArticleBriefCollectQuartz {

    @Autowired
    private CsArticlePageUrlCollectService csArticlePageUrlCollectServiceImpl;

    // todo 需要设置启动后开始执行，执行一段时间后（小于一天）自动终止。

    /**
     * 简单的提取出页面里所有的url，保存到mongo和
     */
    @Scheduled
    public void collectArticlePageUr() {
        csArticlePageUrlCollectServiceImpl.collectArticlePageUr();
    }
}
