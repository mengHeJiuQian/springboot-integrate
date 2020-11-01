package org.yl.websearch.modules.cs.dao.impl;

import com.mongodb.client.result.DeleteResult;
import java.util.List;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.yl.websearch.modules.cs.dao.CsUrlInfoDao;
import org.yl.websearch.modules.cs.model.CsUrlInfo;

/**
 * @author yang.liu
 * @createtime 2020/10/25 21:40
 * @description
 */
@Repository
public class CsUrlInfoDaoImpl implements CsUrlInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CsUrlInfo selectById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        CsUrlInfo one = mongoTemplate.findOne(query, CsUrlInfo.class);
        return one;
    }

    @Override
    public boolean exists(String id) {
        return selectById(id) == null ? false : true;
    }


    @Override
    public void insertAll(List<CsUrlInfo> urlInfoList) {
        Optional<List<CsUrlInfo>> listInOptional = Optional.ofNullable(urlInfoList);
        System.out.println(listInOptional.isPresent());
        listInOptional.ifPresent(urlList -> urlList.stream().forEach(urlInfo -> {
            if (!exists(urlInfo.getId())) {
                insertOne(urlInfo);
            }
        }));
    }

    @Override
    public int insertOne(CsUrlInfo urlInfo) {
        mongoTemplate.save(urlInfo);
        return 1;
    }

    /**
     * 排序操作可以参考文章，https://blog.csdn.net/baidu_38990811/article/details/80095088
     * @return
     */
    @Override
    public CsUrlInfo getLatest() {
        Query query = new Query();
        // 只取一个
        query.limit(1);
        // 根据CreateTime进行时间倒序
        query.with(Sort.by(Sort.Direction.DESC, "createtime"));
        CsUrlInfo urlInfo = mongoTemplate.findOne(query, CsUrlInfo.class);
        return urlInfo;
    }

    @Override
    public int deleteByUrl(String url) {
        String id = DigestUtils.md5Hex(url);
        Query query = new Query(Criteria.where("id").is(id));
        DeleteResult remove = mongoTemplate.remove(query, CsUrlInfo.class);
        return (int) remove.getDeletedCount();
    }
}
