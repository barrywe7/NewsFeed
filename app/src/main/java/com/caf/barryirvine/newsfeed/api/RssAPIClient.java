package com.caf.barryirvine.newsfeed.api;


import android.content.Context;
import android.support.annotation.NonNull;

import com.caf.barryirvine.newsfeed.network.CachingControlInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RssAPIClient {

    private static final String BASE_URL = "http://feeds.bbci.co.uk";

    public static RssAPI get(@NonNull final Context context, final boolean forceNetwork) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder()
                        .cache(HttpCache.get(context))
                        .addInterceptor(new CachingControlInterceptor(context, forceNetwork))
                        .build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        return retrofit.create(RssAPI.class);
    }
}
