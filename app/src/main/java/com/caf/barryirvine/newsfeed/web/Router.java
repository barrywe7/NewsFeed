package com.caf.barryirvine.newsfeed.web;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Allows the launch of custom tabs to be unit tested by wrapping the launch call
 */
public class Router {

    private final Context mContext;

    public Router(@NonNull final Context context) {
        mContext = context;
    }

    public void startUrl(final String title, final String link) {
        CustomTabsHelper.startUrl(mContext, title, link);
    }
}
