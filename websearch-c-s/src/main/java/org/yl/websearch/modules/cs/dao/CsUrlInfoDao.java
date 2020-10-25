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
     * 插入list里所有的数据.
     * @param urlInfoList url列表
     * @return
     */
    int insertAll(List<CsUrlInfo> urlInfoList);

    /**
     * 查找最新的一条记录.
     */
    //CsUrlInfo select();

}
