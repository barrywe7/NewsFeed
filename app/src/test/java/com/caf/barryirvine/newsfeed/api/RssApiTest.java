package com.caf.barryirvine.newsfeed.api;

import com.caf.barryirvine.newsfeed.model.Feed;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class RssApiTest extends ApiTest {

    @Rule
    public final MockWebServer mMockWebServer = new MockWebServer();
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private RssAPI mRssAPI;


    @Before
    public void setUp() throws IOException {
        mRssAPI = new Retrofit.Builder()
                .baseUrl(mMockWebServer.url("/"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(RssAPI.class);
    }

    @Test
    public void testApi_success() throws Exception {
        mMockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(getFileContents("rss.xml")));
        final Feed feed = mRssAPI.getItems("uk").blockingFirst();
        assertNotNull(feed);
        assertNotNull(feed.getChannel());
        assertEquals(feed.getChannel().getFeedItems().size(), 28);
    }

    @Test
    public void testApi_failure() throws Exception {
        mMockWebServer.enqueue(new MockResponse().setResponseCode(404));
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("HTTP 404 Client Error");
        mRssAPI.getItems("uk").blockingFirst();
    }

}
