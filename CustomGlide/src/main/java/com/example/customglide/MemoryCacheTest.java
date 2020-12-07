package com.example.customglide;

import android.util.Log;

import com.example.customglide.cache.MemoryCache;
import com.example.customglide.cache.MemoryCacheCallback;
import com.example.customglide.resource.Value;

/**
 * 测试LruCache
 */
public class MemoryCacheTest {

    private static String TAG = "MemoryCacheTest";

    public static void test() {

        MemoryCache memoryCache = new MemoryCache(4);
        // 最少使用算法生效
        memoryCache.setMemoryCacheCallback(new MemoryCacheCallback() {
            @Override
            public void entryRemovedMemoryCache(String key, Value oldValue) {
                Log.d(TAG, "entryRemovedMemoryCache: 被移除了：oldValue" + oldValue);
            }
        });

        memoryCache.put("ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668",
                new Value());
        memoryCache.put("ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a46682",
                new Value());
        memoryCache.put("ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a46683",
                new Value());
        memoryCache.put("ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a46684",
                new Value());
        memoryCache.put("ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a46685",
                new Value());

        Value value = memoryCache.get("ac037ea49e34257dc5577d1796bb137dbaddc0e42a9dff051beee8ea457a4668");

    }

    public static void main(String[] args) {
        test();
    }

}
