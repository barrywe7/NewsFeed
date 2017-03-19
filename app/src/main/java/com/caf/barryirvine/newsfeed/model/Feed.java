package com.caf.barryirvine.newsfeed.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class Feed {
    @Element(name = "channel")
    private Channel mChannel;

    public Feed() {
    }

    public Channel getChannel() {
        return mChannel;
    }
}

