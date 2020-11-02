package org.yl.websearch.modules.cs.service.impl;

import com.alibaba.fastjson.JSON;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yl.websearch.modules.cs.dao.CsUrlInfoDao;
import org.yl.websearch.modules.cs.model.CsArticleInfo;
import org.yl.websearch.modules.cs.model.CsUrlInfo;
import org.yl.websearch.modules.cs.service.CsArticlePageUrlCollectService;

/**
 * @author yang.liu
 * @createtime 2020/10/24 21:26
 * @description
 */
@Slf4j
@Service
public class CsArticlePageUrlCollectServiceImpl implements CsArticlePageUrlCollectService {

    private static Pattern urlPattern = Pattern.compile("\"https://blog.csdn.net/.*?\"");

    private static String beginUrl = "https://blog.csdn.net/qq_34644750/article/details/82788788";

    @Autowired
    private CsUrlInfoDao csUrlInfoDaoImpl;

    @Override
    public void collectArticlePageUrl() {
        // 1.获取表最后一条数据url
        String url = beginUrl;
        CsUrlInfo latestUrlInfo = csUrlInfoDaoImpl.getLatest();
        if (latestUrlInfo != null) {
            url = latestUrlInfo.getUrl();
        }

        // 2.从页面文本中提取包含的所有url
        List<CsUrlInfo> urlList = collectUrlFromPage(url);
        log.info("【collectArticlePageUr从url={}，提取到的url】:{}", url, JSON.toJSONString(urlList));

        // 3.保存到mongo
        csUrlInfoDaoImpl.insertAll(urlList);
    }

    /**
     * 从页面里正则匹配出url.
     *
     * @return
     */
    public List<CsUrlInfo> collectUrlFromPage(String url) {
        List<CsUrlInfo> urlInfoList = null;
        try {
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            String htmlText = document.toString();
            Matcher matcher = urlPattern.matcher(htmlText);
            urlInfoList = new ArrayList<>();
            while (matcher.find()) {
                String group = matcher.group();
                // 去掉头尾的引号
                String urlStr = group.substring(1, group.length() - 1);
                String id = DigestUtils.md5Hex(urlStr);
                CsUrlInfo urlInfo = new CsUrlInfo(id, urlStr, LocalDateTime.now());
                if (!filterUselessUrl(urlInfo.getUrl())) {
                    continue;
                }
                urlInfoList.add(urlInfo);
            }
        } catch (Exception e) {
            log.error("【获取url={}出现异常】：{}", url, e);
            int count = csUrlInfoDaoImpl.deleteByUrl(url);
            log.info("【删除url={}，影响行数={}】。", url, count);
        }
        return urlInfoList;
    }

    /**
     * 从url中获取文章浏览信息.
     *
     * @param url 博客文章url https://blog.csdn.net/wzsy_ll/article/details/94451796
     * @return
     */
    public CsArticleInfo parseFromPage(String url) {
        CsArticleInfo articleInfo = null;
        try {
            articleInfo = new CsArticleInfo();
            articleInfo.setUrl(url);

            // 创建链接
            Connection connect = Jsoup.connect(url);
            // 请求url,获取网页信息
            Document document = connect.get();
            Elements elements = document.getElementsByClass("blog-content-box");
            Elements nickNameSelect = elements.select(".follow-nickName");
            articleInfo.setNickName(nickNameSelect.text());

            Elements articleType = elements.select(".article-type-img");
            String src = articleType.attr("src");
            if (src.contains("original")) {
                articleInfo.setOriginal(true);
            } else {
                articleInfo.setOriginal(false);
            }

            Elements readCount = elements.select(".read-count");
            articleInfo.setReadCount(Integer.parseInt(readCount.text()));

            Elements upTime = elements.select(".up-time");
            String upTimetext = upTime.text();
            int latestUp = upTimetext.indexOf("最后发布");
            int firstUp = upTimetext.indexOf("首次发布");
            /*System.out.println(upTimetext);
            System.out.println(upTimetext.length());
            System.out.println(latestUp);
            System.out.println(firstUp);*/

            String latestTime = upTimetext.substring(latestUp + 5, latestUp + 5 + 19);
            String createTime = upTimetext.substring(firstUp + 5, firstUp + 5 + 19);
            articleInfo.setUpdateTime(latestTime);
            articleInfo.setCreateTime(createTime);

            // Elements tagBox = elements.select(".artic-tag-box");
            //tagBox.
            //articleInfo.setTag(tag.text());

            Elements moreToolBox = document.getElementsByClass("more-toolbox");
            Elements spanCount = moreToolBox.select("#spanCount");
            articleInfo.setLike(Integer.parseInt(spanCount.text()));

            System.out.println(JSON.toJSONString(articleInfo));
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("【获取url出现异常】：{}", e);
        }
        return articleInfo;
    }

    private boolean filterUselessUrl(String url) {
        if (url.length() < "https://blog.csdn.net/xxx/article/details".length()) {
            return false;
        }
        if (url.contains("article/month")) {
            return false;
        }
        return true;
    }
}
