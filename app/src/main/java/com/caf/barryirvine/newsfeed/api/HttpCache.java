package com.caf.barryirvine.newsfeed.api;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;

class HttpCache {

    private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MiB
    private static final String CACHE_DIR = "http";
    private static volatile Cache sInstance;

    static Cache get(final Context context) {
        if (sInstance == null) {
            synchronized (HttpCache.class) {
                if (sInstance == null) {
                    sInstance = new Cache(new File(context.getCacheDir(), CACHE_DIR), CACHE_SIZE);
                }
            }
        }
        return sInstance;
    }
}
