package org.yl.websearch.modules;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yang.liu
 * @createtime 2020/10/24 20:57
 * @description 测试服务是否可以用。
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/{name}")
    public String hello(@PathVariable(required = false) String name) {
        return name;
    }

}
