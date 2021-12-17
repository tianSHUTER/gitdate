package dubbo_hmily.smb_provider;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

@Data
@Configuration
@PropertySource("classpath:redis.properties")
public class CacheConfig extends CachingConfigurerSupport {


    @Value("${redis.pool.maxTotal}")
    private int maxTotal;

    @Value("${redis.pool.maxIdle}")
    private int maxIdle;

    @Value("${redis.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;

    @Value("${redis.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${redis.pool.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${redis.pool.softMinEvictableIdleTimeMillis}")
    private int softMinEvictableIdleTimeMillis;

    @Value("${redis.pool.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${redis.pool.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${redis.pool.blockWhenExhausted}")
    private boolean blockWhenExhausted;


    @Value("${redis.hostName}")
    private String hostName;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.dbIndex}")
    private int dbIndex;

    @Value("${redis.usePool}")
    private boolean usePool;


    @Qualifier("jedisfactory")
    @Autowired
    private JedisConnectionFactory factory;

    //注入sentinel哨兵配置
    @Qualifier("jedisfactory")
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        Set<String> setRedisNode = new HashSet<>();
        setRedisNode.add("121.42.157.181:26379");
        setRedisNode.add("121.42.157.181:26380");
        setRedisNode.add("121.42.157.181:26381");
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration("mymaster", setRedisNode);

        JedisPoolConfig config = new JedisPoolConfig();

        //设置链接池的信息
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        config.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        config.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestWhileIdle(testWhileIdle);
        config.setBlockWhenExhausted(blockWhenExhausted);

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisSentinelConfiguration,config);
        //设置链接的基本信息
//        connectionFactory.setHostName(hostName);
//        connectionFactory.setPort(port);
        connectionFactory.setTimeout(timeout);
        connectionFactory.setPassword(password);
        connectionFactory.setDatabase(dbIndex);
        connectionFactory.setUsePool(usePool);

        return connectionFactory;

    }

    /**
     * 自定义生成redis-key
     *
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append(".");
            sb.append(method.getName()).append(".");
            for (Object obj : objects) {
                sb.append(obj.toString()).append(".");
            }
            //System.out.println("keyGenerator=" + sb.toString());
            return sb.toString();
        };
    }



    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        redisTemplate.setKeySerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(cacheManager());
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 用于捕获从Cache中进行CRUD时的异常的回调处理器。
        return new SimpleCacheErrorHandler();
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration =
                defaultCacheConfig()
                        .disableCachingNullValues()
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }
}