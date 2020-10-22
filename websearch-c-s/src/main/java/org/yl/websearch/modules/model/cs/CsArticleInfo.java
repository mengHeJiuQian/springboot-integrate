package org.yl.websearch.modules.model.cs;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yang.liu
 * @createtime 2020/10/22 13:01
 * @description
 */
@Getter
@Setter
public class CsArticleInfo {

    private String url;
    private String nickName;
    /** 是否原创 */
    private boolean original;
    /** 分类专栏 */
    private String  category;
    /** 文章标签 */
    private String tagList;
    /** 阅读量 */
    private int readCount;

     /** 点赞数 */
    private int like;
     /** 首次发布 */
    private String createTime;
     /** 最后发布 */
    private String updateTime;
}
