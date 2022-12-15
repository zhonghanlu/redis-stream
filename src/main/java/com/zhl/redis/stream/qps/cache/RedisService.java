package com.zhl.redis.stream.qps.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <h1>Redis 通用工具</h1>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate template;

    /**
     * 创建消费者组
     */
    public String createGroup(String key, String group) {
        return template.opsForStream().createGroup(key, group);
    }


    /**
     * 队列添加消息
     */
    public String addQueue(String key, Map<String, String> map) {
        return template.opsForStream().add(key, map).getValue();
    }

    /**
     * 添加Record
     */
    public String addRecord(Record<String, Object> record) {
        return template.opsForStream().add(record).getValue();
    }

    /**
     * 确认消费
     */
    public Long ack(String key, String group, String... recordIds) {
        return template.opsForStream().acknowledge(key, group, recordIds);
    }

    /**
     * 删除消息
     */
    public Long del(String key, String... recordIds) {
        return template.opsForStream().delete(key, recordIds);
    }

}
