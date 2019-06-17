package com.yu.config.cache;

import com.yu.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Auther: yuchanglong
 * @Date: 2019-6-14
 * @Description: mybatis redis实现二级缓存
 */
@Slf4j
public class MybatisRedisCache implements Cache {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private RedisTemplate<String, Object> redisTemplate;

    private String id;

    public MybatisRedisCache(final String id){
        if(id == null){
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        log.info("key: {}; value: {}", key, value);
        if (redisTemplate == null){
            redisTemplate = (RedisTemplate<String, Object>)SpringBeanUtil.getBean("redisTemplate");
        }
        if(value != null){
            redisTemplate.opsForList().rightPush(id, key.toString());
            redisTemplate.opsForValue().set(key.toString(),value, 5, TimeUnit.MINUTES);
        }
    }

    @Override
    public Object getObject(Object key) {
        log.info("key: {}", key.toString());
        if (redisTemplate == null){
            redisTemplate = (RedisTemplate<String, Object>)SpringBeanUtil.getBean("redisTemplate");
        }
        try {
            if(key != null){
                Object obj = redisTemplate.opsForValue().get(key.toString());
                return obj;
            }
        } catch (Exception e) {
            log.error("redis getObject fail ！e:{}", e);
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        log.info("key: {}", key);
        if (redisTemplate == null){
            redisTemplate = (RedisTemplate<String, Object>)SpringBeanUtil.getBean("redisTemplate");
        }
        try {
            if(key != null){
                redisTemplate.expire(key.toString(), 1, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void clear() {
        log.info("clear");
        if (redisTemplate == null){
            redisTemplate = (RedisTemplate<String, Object>)SpringBeanUtil.getBean("redisTemplate");
        }
        try {
            //Set<String> keys = redisTemplate.keys("*" + this.id + "*");
            List range = redisTemplate.opsForList().range(id,0, -1);
            range.add(id);
            if (!CollectionUtils.isEmpty(range)) {
                redisTemplate.delete(range);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getSize() {
        log.info("getSize");
        if (redisTemplate == null){
            redisTemplate = (RedisTemplate<String, Object>)SpringBeanUtil.getBean("redisTemplate");
        }
        Long size = redisTemplate.execute(new RedisCallback<Long>(){
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        log.info("getReadWriteLock");
        return this.readWriteLock;
    }
}
