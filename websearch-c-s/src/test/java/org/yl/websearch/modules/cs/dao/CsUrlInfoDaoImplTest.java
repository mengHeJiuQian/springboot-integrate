package org.yl.websearch.modules.cs.dao;

import java.time.LocalDateTime;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.yl.websearch.modules.cs.model.CsUrlInfo;

/**
 * @author yang.liu
 * @createtime 2020/11/1 21:49
 * @description
 */
@SpringBootTest
public class CsUrlInfoDaoImplTest {

    @Autowired
    private CsUrlInfoDao csUrlInfoDaoImpl;

    @Test
    public void testInsertOne() {
        String url = "aaa";
        String id = DigestUtils.md5Hex(url);
        CsUrlInfo urlInfo = new CsUrlInfo(id, url, LocalDateTime.now());
        int count = csUrlInfoDaoImpl.insertOne(urlInfo);
        assert count == 1;
    }

    @Test
    public void testSelectById() {
        //String url = "https://blog.csdn.net/weixin_46246297/article/shareArticleCardPage?article_id=109401726";
        String url = "aaa";
        String id = DigestUtils.md5Hex(url);
        CsUrlInfo urlInfo = csUrlInfoDaoImpl.selectById(id);
        assert urlInfo == null;
    }

    @Test
    public void testDeleteByUrl() {
        // String url = "https://blog.csdn.net/weixin_46246297/article/shareArticleCardPage?article_id=109401726";
        String url = "aaa";
        int count = csUrlInfoDaoImpl.deleteByUrl(url);
        assert count == 0;
    }

}
