package org.yl.websearch;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.yl.websearch.modules.cs.model.CsArticleInfo;

/**
 * @author yang.liu
 * @createtime 2020/10/22 12:45
 * @description
 */
public class JsonpTest {

    /**
     * 参考 https://www.cnblogs.com/wpbing/p/11225238.html
     */
    public static void main(String[] args) throws IOException {
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
