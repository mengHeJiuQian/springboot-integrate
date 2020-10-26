package org.yl.websearch.modules.cs.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
        for (CsUrlInfo urlInfo : urlInfoList) {
            if (!exists(urlInfo.getId())) {
                insertOne(urlInfo);
            }
        }
    }

    @Override
    public int insertOne(CsUrlInfo urlInfo) {
        mongoTemplate.save(urlInfo);
        return 1;
    }
}
