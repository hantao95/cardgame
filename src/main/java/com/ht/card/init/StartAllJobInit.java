package com.ht.card.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 初始化缓存
 */
@Component // 注意 这里必须有
//@Order(2) 如果有多个类需要启动后执行 order注解中的值为启动的顺序
public class StartAllJobInit implements CommandLineRunner {
    @Override
    public void run(String... args) {
        Cache.initCard();
    }
}
