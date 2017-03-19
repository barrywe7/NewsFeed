package com.caf.barryirvine.newsfeed.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "thumbnail", strict = false)
public class Thumbnail {
    @Attribute(name = "url")
    private String mUrl;

    public Thumbnail() {

    }

    public Thumbnail(final String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

}
