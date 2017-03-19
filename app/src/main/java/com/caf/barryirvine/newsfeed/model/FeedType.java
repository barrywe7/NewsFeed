package com.caf.barryirvine.newsfeed.model;

import android.support.annotation.StringRes;

import com.caf.barryirvine.newsfeed.R;

public enum FeedType {

    UK("uk", R.string.uk),
    TECHNOLOGY("technology", R.string.technology);

    @StringRes
    private final int mDescription;
    private final String mPath;


    FeedType(final String path, @StringRes final int description) {
        mDescription = description;
        mPath = path;
    }

    public String getPathSegment() {
        return mPath;
    }

    @StringRes
    public int getDescription() {
        return mDescription;
    }

}

