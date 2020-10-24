package org.yl.websearch.modules.cs.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.yl.websearch.modules.cs.model.CsArticleInfo;
import org.yl.websearch.modules.cs.service.CsArticlePageUrlCollectService;

/**
 * @author yang.liu
 * @createtime 2020/10/24 21:26
 * @description
 */
@Slf4j
@Service
public class CsArticlePageUrlCollectServiceImpl implements CsArticlePageUrlCollectService {

    @Override
    public void collectArticlePageUr() {
        // 1.获取url

        // 2.从页面文本中提取包含的所有url
        parseFromPage("");

        // 3.保存到mysql
    }

    /**
     * 从url中获取文章浏览信息.
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
}
