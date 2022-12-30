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
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>消费者 no.5</h1>
 */
@Slf4j
@Component
public class ConsumeListener06 implements StreamListener<String, MapRecord<String, String, String>> {

    @Resource
    private RedisService redisService;

    private static ConsumeListener06 consumeListener06;

    @PostConstruct
    public void init() {
        consumeListener06 = this;
        consumeListener06.redisService = this.redisService;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        log.info("当前线程名：【{}】",Thread.currentThread().getName());
        String stream = message.getStream();
        RecordId recordId = message.getId();
        Map<String, String> map = message.getValue();
        //接收到消息
        ExecutorService service = Executors.newWorkStealingPool();
//        AtomicInteger index = new AtomicInteger();
//        ExecutorService executor = new ThreadPoolExecutor(10, 20, 20, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>() , r -> {
//            Thread thread = new Thread(r);
//            thread.setName("async-stream-consumer-" + index.getAndIncrement());
//            thread.setDaemon(true);
//            return thread;
//        });
        try {
//            service.submit(() -> {
                new Thread(()->{
                    log.info("当前线程名：【{}】，消费者02 消息id:[{}]",Thread.currentThread().getName(), recordId);
                    try {
                        Thread.sleep(new Random().nextInt(1999));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //进行ack 删除消息
                    consumeListener06.redisService.ack(stream, RedisPrefix.TEST_GROUP_02, recordId.getValue());
                    consumeListener06.redisService.del(stream, recordId.getValue());
                });
//            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
