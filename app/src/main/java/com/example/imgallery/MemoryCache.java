package com.example.imgallery;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class MemoryCache {
    private LruCache<String, Bitmap> cache;

    public MemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        cache = new LruCache<>(cacheSize);
    }

    public Bitmap get(String key) {
        Bitmap bitmap = cache.get(key);
        if (bitmap != null) {
            Log.d("MemoryCache", "Image retrieved from cache with key: " + key);
        } else {
            Log.d("MemoryCache", "Image not found in cache for key: " + key);
        }
        return bitmap;
    }

    public void put(String key, Bitmap bitmap) {
        cache.put(key, bitmap);
        Log.d("MemoryCache", "Image put into cache with key: " + key);
    }
}
