```java
@Configuration
@EnableCaching
public class RedisBean {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedissonClient redissonClient() {
        return RedissonConfig.createRedissonClient(host, port);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return RedisConfig.createRedisConnectionFactory(host, port);
    }

    @Bean
    public CacheManager cacheManager() {
        return RedisConfig.createCacheManager(redisConnectionFactory());
    }
}
```