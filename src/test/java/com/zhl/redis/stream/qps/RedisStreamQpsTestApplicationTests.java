package com.zhl.redis.stream.qps;

import com.zhl.redis.stream.qps.cache.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RedisStreamQpsTestApplicationTests {

    @Resource
    private RedisService redisService;

    @Test
    void contextLoads() {


    }

}
