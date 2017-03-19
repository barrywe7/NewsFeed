package com.caf.barryirvine.newsfeed.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class FeedItem {

    @Element(name = "title")
    private String mTitle;
    @Element(name = "thumbnail", required = false)
    private Thumbnail mThumbnail;
    @Element(name = "description")
    private String mDescription;
    @Element(name = "link")
    private String mLink;
    @Element(name = "pubDate")
    private String mPubDate;

    public FeedItem() {

    }

    private FeedItem(final Builder builder) {
        mTitle = builder.mTitle;
        mThumbnail = builder.mThumbnail;
        mDescription = builder.mDescription;
        mLink = builder.mLink;
        mPubDate = builder.mPubDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mThumbnail.getUrl();
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLink() {
        return mLink;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public static class Builder {
        private String mTitle;
        private Thumbnail mThumbnail;
        private String mDescription;
        private String mLink;
        private String mPubDate;

        public Builder title(final String title) {
            mTitle = title;
            return this;
        }

        public Builder description(final String description) {
            mDescription = description;
            return this;
        }

        public Builder thumbnail(final Thumbnail thumbnail) {
            mThumbnail = thumbnail;
            return this;
        }

        public Builder link(final String link) {
            mLink = link;
            return this;
        }

        public Builder pubDate(final String pubDate) {
            mPubDate = pubDate;
            return this;
        }

        public FeedItem build() {
            return new FeedItem(this);
        }
    }
}
