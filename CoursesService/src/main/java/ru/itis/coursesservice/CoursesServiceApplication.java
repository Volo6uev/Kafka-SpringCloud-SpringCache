package ru.itis.coursesservice;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@EnableCaching
@SpringBootApplication
public class CoursesServiceApplication {

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        JsonJacksonCodec codec = new JsonJacksonCodec();
        Map<String, CacheConfig> config = new HashMap<>();
        config.put("cache", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config, codec);
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // Настройки Redisson, например:
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6380");
        return Redisson.create(config);
    }



    public static void main(String[] args) {
        SpringApplication.run(CoursesServiceApplication.class, args);
    }

}
