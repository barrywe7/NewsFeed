package com.caf.barryirvine.newsfeed.api;


import android.content.Context;
import android.support.annotation.NonNull;

import com.caf.barryirvine.newsfeed.network.CachingControlInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RssService {

    private static volatile Retrofit.Builder sRetrofitBuilder;

    private static Retrofit.Builder get() {
        if (sRetrofitBuilder == null) {
            synchronized (RssService.class) {
                if (sRetrofitBuilder == null) {
                    sRetrofitBuilder = new Retrofit.Builder()
                            .baseUrl(RssAPI.BASE_URL)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(SimpleXmlConverterFactory.create());
                }
            }
        }
        return sRetrofitBuilder;
    }

    public static RssAPI get(@NonNull final Context context, final boolean forceNetwork) {
        final Retrofit retrofit = get()
                .client(new OkHttpClient.Builder()
                        .cache(HttpCache.get(context))
                        .addInterceptor(new CachingControlInterceptor(context, forceNetwork))
                        .build())
                .build();
        return retrofit.create(RssAPI.class);
    }


}
