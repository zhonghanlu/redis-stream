package com.zhl.redis.stream.qps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisStreamQpsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisStreamQpsTestApplication.class, args);
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
