package org.yl.websearch.modules.cs.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yang.liu
 * @createtime 2020/10/24 22:14
 * @description url信息，各种各样的url。
 */
@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "article_base")
public class CsUrlInfo {

    /** url字符串进行md5编码后的字节再转为16进制字符串. */
    @Id
    private String id;
    /** url字符串. */
    private String url;
    /** 插入数据的当前时间. */
    private LocalDateTime createDateTime;

}
