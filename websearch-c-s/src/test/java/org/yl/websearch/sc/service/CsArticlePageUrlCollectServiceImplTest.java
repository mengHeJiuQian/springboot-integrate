package org.yl.websearch.sc.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.yl.websearch.common.utils.StringTool;
import org.yl.websearch.modules.cs.model.CsArticleInfo;
import org.yl.websearch.modules.cs.model.CsUrlInfo;

/**
 * @author yang.liu
 * @createtime 2020/10/22 12:45
 * @description
 */
public class CsArticlePageUrlCollectServiceImplTest {

    /**
     * test case:
     * 获取页面里所有的url.
     */
    @Test
    public void testCollectUrlFromPage() throws IOException {
        String url = "https://blog.csdn.net/wzsy_ll/article/details/94451796";
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        String htmlText = document.toString();
        Pattern compile = Pattern.compile("\"https://blog.csdn.net/.*?\"");
        Matcher matcher = compile.matcher(htmlText);
        List<String> urlList = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            urlList.add(group.substring(1, group.length() - 1));
        }
        System.out.println(JSON.toJSONString(urlList));
    }

    /**
     * 参考 https://www.cnblogs.com/wpbing/p/11225238.html
     */
    @Test
    public void testParseFromPage() throws IOException {
        CsArticleInfo articleInfo = new CsArticleInfo();

        String url = "https://blog.csdn.net/qq_39390545/article/details/109080050";
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
        String createTime = upTimetext.substring(firstUp + 5, firstUp + 5 +19);
        articleInfo.setUpdateTime(latestTime);
        articleInfo.setCreateTime(createTime);

        // Elements tagBox = elements.select(".artic-tag-box");
        //tagBox.
        //articleInfo.setTag(tag.text());

        Elements moreToolBox = document.getElementsByClass("more-toolbox");
        Elements spanCount = moreToolBox.select("#spanCount");
        articleInfo.setLike(Integer.parseInt(spanCount.text()));

        System.out.println(JSON.toJSONString(articleInfo));
    }
}
