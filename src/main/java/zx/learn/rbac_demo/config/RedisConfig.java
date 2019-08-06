package zx.learn.rbac_demo.config;//package zx.learn.board.config;
//
//
//import org.springframework.boot.autoconfigure.cache.CacheProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
//@Configuration
//public class RedisConfig {
//
//
//    private RedisConnectionFactory connectionFactory = null;
//
//    public RedisConnectionFactory initRedisConnectionFactory(){
//        if (this.connectionFactory != null) {
//            return this.connectionFactory;
//        }
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//
//        poolConfig.setMaxIdle(30);
//
//        poolConfig.setMaxTotal(50);
//
//        poolConfig.setMaxWaitMillis(2000);
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
//        connectionFactory.setHostName("127.0.0.1");
//        connectionFactory.setPort(6379);
//        this.connectionFactory = connectionFactory;
//        return connectionFactory;
//    }
//
//
//    public RedisTemplate<Object,Object> initRedisTemplate(){
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        RedisSerializer stringRedisSerializer = redisTemplate.getStringSerializer();
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setHashValueSerializer(stringRedisSerializer);
//        redisTemplate.setConnectionFactory(initRedisConnectionFactory());
//        return redisTemplate;
//    }
//
//
//}
