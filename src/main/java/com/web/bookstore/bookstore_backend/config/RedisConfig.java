package com.web.bookstore.bookstore_backend.config;


import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    //避免redis服务中断后导致dao不返回信息
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
                System.out.println("redis error");
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
                System.out.println("redis error");
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
                System.out.println("redis error");
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                System.out.println("redis error");
            }
        };
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        // Hash key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // Hash value序列化
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}
