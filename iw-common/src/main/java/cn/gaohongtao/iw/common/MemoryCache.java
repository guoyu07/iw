package cn.gaohongtao.iw.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Memory cache
 *
 * Created by gaoht on 15/6/27.
 */
public class MemoryCache {

    private final Cache<String, String> cache;

    private static MemoryCache keepCache;

    static {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        keepCache = new MemoryCache(cache);
    }

    public MemoryCache(Cache<String, String> cache) {
        this.cache = cache;
    }

    public static MemoryCache keep() {
        return keepCache;
    }

    public String get(String key) {
        return cache.getIfPresent(key);
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }
}
