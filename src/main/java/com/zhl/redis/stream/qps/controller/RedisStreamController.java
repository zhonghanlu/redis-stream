package com.zhl.redis.stream.qps.controller;

import com.zhl.redis.stream.qps.cache.RedisService;
import com.zhl.redis.stream.qps.constant.RedisPrefix;
import com.zhl.redis.stream.qps.service.BuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>redis stream 测试接口</h1>
 */
@Slf4j
@RestController
@RequestMapping("/stream")
@RequiredArgsConstructor
public class RedisStreamController {

    private final RedisService redisService;

    private final BuildService buildService;

    private static Map map = new ConcurrentHashMap();

    /**
     * <h2>插入数据</h2>
     */
    @GetMapping("/xAdd")
    public Map xAdd(@RequestParam(value = "jsonModel", required = false) String jsonModel,
                    @RequestParam(value = "jsonNums", required = false) Long jsonNums) {
        List<Map> data = buildService.buildData(jsonModel, jsonNums);
        redisService.addQueue(RedisPrefix.TEST_STREAM, data);
        return map;
    }

}
