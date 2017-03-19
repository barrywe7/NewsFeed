package com.caf.barryirvine.newsfeed.api;

import com.caf.barryirvine.newsfeed.model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RssAPI {

    @GET("/news/{category}/rss.xml")
    Call<Feed> getItems(@Path("category") final String category);

}
