package com.zhl.redis.stream.qps.config;

import com.zhl.redis.stream.qps.constant.RedisPrefix;
import com.zhl.redis.stream.qps.consumer.ConsumeListener01;
import com.zhl.redis.stream.qps.consumer.ConsumeListener02;
import com.zhl.redis.stream.qps.consumer.ConsumeListener03;
import com.zhl.redis.stream.qps.consumer.ConsumeListener04;
import com.zhl.redis.stream.qps.errorHandler.StreamErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>消费者组配置</h1>
 */
@Configuration
public class RedisStreamConfiguration {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer() {
        AtomicInteger index = new AtomicInteger();
        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), r -> {
            Thread thread = new Thread(r);
            thread.setName("async-stream-consumer-" + index.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        });

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        //一次拿多少数据
                        .batchSize(5000)
                        //运行Stream 的poll task
                        .executor(executor)
                        // Stream 中没有消息时，阻塞多长时间，需要比 `spring.redis.timeout` 的时间小
                        .pollTimeout(Duration.ofSeconds(2))
                        // 获取消息的过程或获取到消息给具体的消息者处理的过程中，发生了异常的处理
                        .errorHandler(new StreamErrorHandler())
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer =
                StreamMessageListenerContainer.create(redisConnectionFactory, options);

        // 独立消费
        String streamKey = RedisPrefix.TEST_STREAM;

        // 消费组no.01,不自动ack
        streamMessageListenerContainer.receiveAutoAck(Consumer.from(RedisPrefix.TEST_GROUP, RedisPrefix.TEST_GROUP_CONSUMER_NAME_01),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()), new ConsumeListener01());

        // 消费组no.02,不自动ack
        streamMessageListenerContainer.receiveAutoAck(Consumer.from(RedisPrefix.TEST_GROUP, RedisPrefix.TEST_GROUP_CONSUMER_NAME_02),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()), new ConsumeListener02());

        // 消费组no.03,不自动ack
        streamMessageListenerContainer.receiveAutoAck(Consumer.from(RedisPrefix.TEST_GROUP, RedisPrefix.TEST_GROUP_CONSUMER_NAME_03),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()), new ConsumeListener03());

        // 消费组no.04,不自动ack
        streamMessageListenerContainer.receiveAutoAck(Consumer.from(RedisPrefix.TEST_GROUP, RedisPrefix.TEST_GROUP_CONSUMER_NAME_04),
                StreamOffset.create(streamKey, ReadOffset.lastConsumed()), new ConsumeListener04());

        return streamMessageListenerContainer;
    }


}
