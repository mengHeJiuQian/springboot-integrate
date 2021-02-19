package org.yl.websearch.modules.bos.service.impl;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.yl.websearch.modules.bos.service.BosJobCollectService;

/**
 * @author yang.liu
 * @createtime 2021/2/18 22:40
 * @description
 */
@Slf4j
@Service
public class BosJobCollectServiceImpl implements BosJobCollectService {

    private static String beginUrl
            = "https://www.zhipin.com/job_detail/?query=golang&city=101180700&industry=&position=";

    private static String COOKIE = "lastCity=101020100; __zp_seo_uuid__=b1a9841b-88c6-4404-94da-9e253f3f1ad0; __g=-; __l=r=https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3D086t6tnhof7KJeFnHmhEHWqhl8UMQJP7XE3qeP6pEBY5sLTbXELvuYc-MOtKeuai%26ck%3D3638.3.88.931.517.846.517.306%26shh%3Dwww.baidu.com%26sht%3Dmonline_7_dg%26wd%3D%26eqid%3Dd32a999a000fc79e00000003602fac9d&l=%2Fwww.zhipin.com%2Fshanghai%2F&s=1&g=&s=3&friend_source=0; __fid=7159290f1132451d31967735cc862733; __zp_stoken__=5bbdbaR9wOxkrA0BXBHgUKl1EGhQ3Q2RnW2FVcxp8NicpO3x5bTdifjRMUnsLBREhAh8SLigvKHlVEncdfHB4P3tSVWAiUzQ8Y2ALaS9ncy5vE3pJTU0CfgkRFkFNfCobBE4YFztOewZUb2l%2BYQ%3D%3D; __c=1613737125; __a=84961833.1613654747.1613654747.1613737125.12.2.4.12";

    @Override
    public void collectJobInArea() {
        Integer jobCount = collectUrlFromPage(beginUrl);
        log.info("职位数量大概为：{}", jobCount);
    }

    private Integer collectUrlFromPage(String url) {
        Integer jobCount = 0;
        try {
            Connection connect = Jsoup.connect(url);
            connect.cookies(convertCookie(COOKIE));
            Document document = connect.get();
            Element element = document.selectFirst("#job-tab");
            String dataKeyword = element.attr("data-keyword");
            String dataResCount = element.attr("data-rescount");
            log.info("-----dataKeyword={}", dataKeyword);
            log.info("-----dataResCount={}", dataResCount);
        } catch (Exception e) {
            log.error("【异常处理，获取url={}出现异常】：{}", url, e);
        }
        return jobCount;
    }

    private HashMap<String, String> convertCookie(String cookie) {
        HashMap<String, String> cookiesMap = new HashMap<>();
        String[] items = cookie.trim().split(";");
        for (String item : items) {
            cookiesMap.put(item.split("=")[0], item.split("=")[1]);
        }
        return cookiesMap;
    }

}
