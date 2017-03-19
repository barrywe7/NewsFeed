package com.caf.barryirvine.newsfeed.network;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CachingControlInterceptor implements Interceptor {

    private final Context mContext;
    private final boolean mForceNetwork;

    public CachingControlInterceptor(@NonNull final Context context, final boolean forceNetwork) {
        mContext = context;
        mForceNetwork = forceNetwork;
    }

    @Override
    public Response intercept(@NonNull final Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("GET")) {
            final CacheControl.Builder builder = new CacheControl.Builder();
            if (ConnectivityUtils.isConnected(mContext)) {
                final CacheControl cacheControl = (mForceNetwork ? builder.noCache() : builder).build();
                request = request.newBuilder().cacheControl(cacheControl).build();
            } else {
                request = request.newBuilder().cacheControl(builder.maxStale(7, TimeUnit.DAYS).build()).build();
            }
        }
        return chain.proceed(request).newBuilder().header("Cache-Control", "max-age=600").build();
    }
}
