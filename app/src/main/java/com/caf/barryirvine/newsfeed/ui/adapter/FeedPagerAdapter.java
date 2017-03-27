package com.caf.barryirvine.newsfeed.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.caf.barryirvine.newsfeed.model.FeedType;
import com.caf.barryirvine.newsfeed.ui.fragment.FeedFragment;

public class FeedPagerAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;

    public FeedPagerAdapter(@NonNull final Context context, final FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public final Fragment getItem(final int position) {
        return FeedFragment.newInstance(FeedType.values()[position].getPathSegment());
    }

    @Override
    public final int getCount() {
        return FeedType.values().length;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return mContext.getString(FeedType.values()[position].getDescription());
    }

}