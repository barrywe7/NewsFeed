package com.caf.barryirvine.newsfeed.api;

import com.caf.barryirvine.newsfeed.model.Feed;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RssAPI {

    String BASE_URL = "http://feeds.bbci.co.uk/";

    @GET("news/{category}/rss.xml")
    Observable<Feed> getItems(@Path("category") final String category);

}
