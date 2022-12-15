package com.zhl.redis.stream.qps.consumer;

import com.zhl.redis.stream.qps.cache.RedisService;
import com.zhl.redis.stream.qps.constant.RedisPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>消费者 no.4</h1>
 */
@Slf4j
@Component
public class ConsumeListener04 implements StreamListener<String, MapRecord<String, String, String>> {

    @Resource
    private RedisService redisService;

    private static ConsumeListener04 consumeListener04;

    @PostConstruct
    public void init() {
        consumeListener04 = this;
        consumeListener04.redisService = this.redisService;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        String stream = message.getStream();
        RecordId recordId = message.getId();
        Map<String, String> map = message.getValue();
        //接收到消息
        log.info("消费者04  接收到消息:[{}],消息id:[{}]", map, recordId);
        //进行ack 删除消息
        consumeListener04.redisService.ack(stream, RedisPrefix.TEST_GROUP, recordId.getValue());
        consumeListener04.redisService.del(stream, recordId.getValue());
    }
}
