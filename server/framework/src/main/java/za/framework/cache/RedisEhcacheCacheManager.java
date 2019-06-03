package za.framework.cache;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class RedisEhcacheCacheManager implements CacheManager {

    private final static Object EhCacheManager_Locker = new Object();

    private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

    private RedisEhcacheProperties redisEhcacheProperties;

    private RedisTemplate<Object, Object> redisTemplate;

    private boolean dynamic = true;

    private Set<String> cacheNames;

    private net.sf.ehcache.CacheManager ehCacheManager;

    public RedisEhcacheCacheManager(RedisEhcacheProperties redisEhcacheProperties,
                                    RedisTemplate<Object, Object> redisTemplate) throws IOException {
        super();
        this.redisEhcacheProperties = redisEhcacheProperties;
        this.redisTemplate = redisTemplate;
        this.dynamic = redisEhcacheProperties.isDynamic();
        this.cacheNames = redisEhcacheProperties.getCacheNames();
        setAboutEhCache();

    }

    private void setAboutEhCache() throws IOException {
        this.ehCacheManager = net.sf.ehcache.CacheManager.create(this.redisEhcacheProperties.getEhcache().getConfig().getURL());
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = cacheMap.get(name);
        if (cache != null) {
            return cache;
        }
        if (!dynamic && !cacheNames.contains(name)) {
            return cache;
        }

        net.sf.ehcache.Cache eCache = ehCacheManager.getCache(name);
        if (eCache == null) {
            synchronized (EhCacheManager_Locker) {
                eCache = ehCacheManager.getCache(name);
                if (eCache == null) {
                    ehCacheManager.addCacheIfAbsent(name);
                    eCache = ehCacheManager.getCache(name);
                }
            }
        }
        cache = new RedisEhcacheCache(name, redisTemplate, eCache, redisEhcacheProperties);
        Cache oldCache = cacheMap.putIfAbsent(name, cache);
        log.debug("create cache instance, the cache name is : {}", name);
        return oldCache == null ? cache : oldCache;
    }

    public net.sf.ehcache.Cache getEhcache(String name) {
        net.sf.ehcache.Cache res = ehCacheManager.getCache(name);
        if (res != null) {
            return res;
        }
        return ehCacheManager.getCache(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.cacheNames;
    }

    public void clearLocal(String cacheName, Object key, Integer sender) {
        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            return;
        }
        RedisEhcacheCache redisEhcacheCache = (RedisEhcacheCache) cache;
        //如果是发送者本身发送的消息，就不进行key的清除
        if (redisEhcacheCache.getLocalCache().hashCode() != sender) {
            redisEhcacheCache.clearLocal(key);
        }
    }

}