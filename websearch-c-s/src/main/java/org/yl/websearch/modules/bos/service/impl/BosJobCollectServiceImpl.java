package org.yl.websearch.modules.bos.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.yl.websearch.modules.bos.service.BosJobCollectService;
import org.yl.websearch.modules.cs.model.CsUrlInfo;

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

    @Override
    public void collectJobInArea() {
        Integer jobCount = collectUrlFromPage(beginUrl);
        log.info("职位数量大概为：{}", jobCount);
    }

    private Integer collectUrlFromPage(String url) {
        Integer jobCount = 0;
        try {
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            String htmlText = document.toString();
            log.info("--------");
            log.info(htmlText);
            log.info("--------");
//            Matcher matcher = urlPattern.matcher(htmlText);
//            urlInfoList = new ArrayList<>();
//            while (matcher.find()) {
//                String group = matcher.group();
//                // 去掉头尾的引号
//                String urlStr = group.substring(1, group.length() - 1);
//                String id = DigestUtils.md5Hex(urlStr);
//                CsUrlInfo urlInfo = new CsUrlInfo(id, urlStr, LocalDateTime.now());
//                if (isUselessUrl(urlInfo.getUrl())) {
//                    int count = csUrlInfoDaoImpl.deleteByUrl(urlStr);
//                    log.info("【删除url={}，影响行数={}】。", urlStr, count);
//                    continue;
//                }
//                urlInfoList.add(urlInfo);
//            }
        } catch (Exception e) {
            log.error("【异常处理，获取url={}出现异常】：{}", url, e);
        }
        return jobCount;
    }

}
