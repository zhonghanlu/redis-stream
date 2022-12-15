package com.zhl.redis.stream.qps.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * <h1>队列失败处理，可以对pening-list进行重新消费</h1>
 */
@Slf4j
@Component
public class StreamErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        log.error("stream is error :[{}]", t.getMessage());
    }
}
