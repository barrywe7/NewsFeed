package com.caf.barryirvine.newsfeed.model;

import java.util.UUID;

public class MockModelUtils {

    private static String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    public static FeedItem createMockFeedItem() {
        return new FeedItem.Builder()
                .title(generateRandomString())
                .description(generateRandomString())
                .link("http://www.test.com/random_link.html")
                .thumbnail(new Thumbnail("http://www.test.com/image.jpg"))
                .build();
    }
}
