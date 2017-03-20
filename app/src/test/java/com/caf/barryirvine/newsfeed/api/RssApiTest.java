package com.caf.barryirvine.newsfeed.api;

import com.caf.barryirvine.newsfeed.model.Feed;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class RssApiTest extends ApiTest {


    @Rule
    public final MockWebServer mMockWebServer = new MockWebServer();
    private RssAPI mRssAPI;
    private Retrofit mRetrofit;


    @Before
    public void setUp() throws IOException {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mMockWebServer.url("/"))
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        mRssAPI = mRetrofit.create(RssAPI.class);
    }

    @Test
    public void testApi_success() throws Exception {
        mMockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(getFileContents("rss.xml")));
        final Response<Feed> feedResponse = mRssAPI.getItems(Mockito.anyString()).execute();
        assertNotNull(feedResponse);
        assertNotNull(feedResponse.body());
        assertNotNull(feedResponse.body().getChannel());
        assertEquals(feedResponse.body().getChannel().getFeedItems().size(), 28);
        assertEquals(feedResponse.code(), 200);

    }

    @Test
    public void testApi_failure() throws Exception {
        mMockWebServer.enqueue(new MockResponse().setResponseCode(404));
        final Response<Feed> feedResponse = mRssAPI.getItems(Mockito.anyString()).execute();
        assertNotNull(feedResponse);
        assertNull(feedResponse.body());
        assertEquals(feedResponse.code(), 404);
    }

}
