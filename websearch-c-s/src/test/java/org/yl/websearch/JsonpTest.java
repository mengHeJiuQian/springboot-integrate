package org.yl.websearch;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
        String url = "https://blog.csdn.net/qq_39390545/article/details/109080050";
        // 创建链接
        Connection connect = Jsoup.connect(url);
        // 请求url,获取网页信息
        Document document = connect.get();
        Elements elements = document.getElementsByClass("blog-content-box");
        Elements nickNameSelect = elements.select(".follow-nickName");
        System.out.println(nickNameSelect.text());
    }
}
