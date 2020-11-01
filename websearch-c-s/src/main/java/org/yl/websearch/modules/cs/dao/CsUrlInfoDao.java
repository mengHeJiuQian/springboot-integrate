package org.yl.websearch.modules.cs.dao;

import java.util.List;
import org.yl.websearch.modules.cs.model.CsUrlInfo;

/**
 * @author yang.liu
 * @createtime 2020/10/25 21:39
 * @description 数据库访问层接口.
 */
public interface CsUrlInfoDao {

    CsUrlInfo selectById(String id);

    /**
     * 根据id判断数据是否存在，存在返回true,不存在返回false
     * @param id
     * @return
     */
    boolean exists(String id);

    /**
     * 插入list里所有的数据.
     * @param urlInfoList url列表
     * @return
     */
    void insertAll(List<CsUrlInfo> urlInfoList);

    /**
     * 插入一条记录.
     * @param urlInfo
     * @return
     */
    int insertOne(CsUrlInfo urlInfo);

    /**
     * 获取Collection里最后一条记录.
     * @return
     */
    CsUrlInfo getLatest();

    /**
     * 删除url.
     */
    int deleteByUrl(String url);

}
