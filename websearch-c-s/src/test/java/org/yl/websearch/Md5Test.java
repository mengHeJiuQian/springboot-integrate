package org.yl.websearch;


import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

/**
 * @author yang.liu
 * @createtime 2020/10/24 22:28
 * @description
 */
public class Md5Test {

    @Test
    public void urlMd5() {
        String url = "https://blog.csdn.net/iotisan/article/details/106559889";
        String encode = "c8a15c2093c299d4f52d60048edb68fd";
        // md5加密后的字节数组转为16进制字符串
        System.out.println(DigestUtils.md5Hex(url));
        assert DigestUtils.md5Hex(url).equals(encode);
    }
}
