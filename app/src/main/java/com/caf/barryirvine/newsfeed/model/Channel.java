package com.caf.barryirvine.newsfeed.model;

import android.net.Uri;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "channel", strict = false)
public class Channel {
    @ElementList(inline = true, name = "item")
    private List<FeedItem> mFeedItems;

    public Channel() {
    }

    public List<FeedItem> getFeedItems() {
        return mFeedItems;
    }

    public List<Uri> getUrls() {
        final List<Uri> urls = new ArrayList<>(5);
        if (mFeedItems != null) {
            for (int i = 0; i < Math.min(mFeedItems.size(), 5); i++) {
                urls.add(Uri.parse(mFeedItems.get(i).getLink()));
            }
        }
        return urls;
    }
}
